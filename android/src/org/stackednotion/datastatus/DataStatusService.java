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
	
    @Override
    public void onCreate() {
    	Settings.signalStrengthListener = new SignalStrengthListener();
      	telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    	
      	// bind to shared preferences
		SharedPreferences sharedPreferences = PreferenceManager
		.getDefaultSharedPreferences(this);
sharedPreferences
		.registerOnSharedPreferenceChangeListener(new OnSharedPreferenceChangeListener() {
			public void onSharedPreferenceChanged(
					SharedPreferences sharedPreferences, String key) {
				// stop the service when unchecked
				// user clicked enable service
				if (key.equals("service_enabled"))
				{
					// state is not enabled
					if (!sharedPreferences.getBoolean(key, false))
					{
						// stop service
						stopService(new Intent(Settings.getContext(), DataStatusService.class));
					}
				}
			}
		});
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	telephonyManager.listen(Settings.signalStrengthListener, PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
    	ServerApplication.startServer(serverPort);
    	
    	Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
    	
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
    	telephonyManager.listen(Settings.signalStrengthListener, PhoneStateListener.LISTEN_NONE);
    	ServerApplication.stopServer();
    	
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
    }

}
