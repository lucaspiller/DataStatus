package org.stackednotion.datastatus;

import org.stackednotion.datastatus.server.ServerApplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class DataStatusService extends Service {
	private TelephonyManager telephonyManager;

	private final int serverPort = 17362;

	/**
	 * Class for clients to access. Because we know this service always runs in
	 * the same process as its clients, we don't need to deal with IPC.
	 */
	public class DataStatusBinder extends Binder {
		DataStatusService getService() {
			return DataStatusService.this;
		}
	}

	// This is the object that receives interactions from clients. See
	// RemoteService for a more complete example.
	private final IBinder mBinder = new DataStatusBinder();

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	private OnSharedPreferenceChangeListener preferenceListener;

	@Override
	public void onCreate() {
		DataStatusApplication.signalStrengthListener = new SignalStrengthListener();
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

		// create preference listener
		preferenceListener = new OnSharedPreferenceChangeListener() {
			public void onSharedPreferenceChanged(
					SharedPreferences sharedPreferences, String key) {
				// stop the service when unchecked
				// user clicked enable service
				if (key.equals("service_enabled")) {
					// state is not enabled
					if (!sharedPreferences.getBoolean(key, false)) {
						Log
								.d(DataStatusApplication.LOG_TAG,
										"DataStatusService#onSharedPreferenceChanged: Stopping service");

						// stop service
						stopService(new Intent(DataStatusApplication
								.getContext(), DataStatusService.class));
					}
				}
			}
		};
	}

	private boolean isRunning = false;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (!isRunning) {
			// listen to sharedPreferences changes
			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(this);
			sharedPreferences
					.registerOnSharedPreferenceChangeListener(preferenceListener);
			
			// start server
			telephonyManager.listen(
					DataStatusApplication.signalStrengthListener,
					PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
			ServerApplication.startServer(serverPort);

			isRunning = true;

			Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
		} else {
			Log.d(DataStatusApplication.LOG_TAG,
					"DataStatusService#onStartCommand: Already running");
		}

		// We want this service to be kept alive (not necessarily running)
		// until it is explicitly stopped, so return sticky.
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		// stop listening to sharedPreferences changes
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		sharedPreferences
				.unregisterOnSharedPreferenceChangeListener(preferenceListener);

		// shutdown server
		telephonyManager.listen(DataStatusApplication.signalStrengthListener,
				PhoneStateListener.LISTEN_NONE);
		ServerApplication.stopServer();

		Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
	}

}
