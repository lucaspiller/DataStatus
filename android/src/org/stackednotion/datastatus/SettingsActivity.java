package org.stackednotion.datastatus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class SettingsActivity extends PreferenceActivity {
	private OnSharedPreferenceChangeListener preferenceListener;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.preferences_view);

		// create preference listener
		preferenceListener = new OnSharedPreferenceChangeListener() {
			public void onSharedPreferenceChanged(
					SharedPreferences sharedPreferences, String key) {
				// start the service when checked
				// user clicked enable service
				if (key.equals("service_enabled")) {
					// state is enabled
					if (sharedPreferences.getBoolean(key, false)) {
						Log
								.d(DataStatusApplication.LOG_TAG,
										"SettingsActivity#onSharedPreferenceChanged: Starting service");

						// start service
						startService(new Intent(DataStatusApplication
								.getContext(), DataStatusService.class));
					}
				}
			}
		};
	}

	@Override
	public void onResume() {
		super.onResume();

		// update settings with current context
		DataStatusApplication.init(getApplicationContext());

		// if the service is enabled fire an intent to start it, if
		// it isn't already
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		if (sharedPreferences.getBoolean("service_enabled", false)) {
			Log.d(DataStatusApplication.LOG_TAG,
					"SettingsActivity#onResume: Starting service");

			// start service
			startService(new Intent(this, DataStatusService.class));
		}

		// listen to sharedPreferences changes
		sharedPreferences
				.registerOnSharedPreferenceChangeListener(preferenceListener);
	}

	@Override
	public void onPause() {
		// stop listening to sharedPreferences changes
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		sharedPreferences
				.unregisterOnSharedPreferenceChangeListener(preferenceListener);

		super.onPause();
	}
}