package org.stackednotion.datastatus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingsActivity extends PreferenceActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.preferences_view);

		// bind to shared preferences
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		sharedPreferences
				.registerOnSharedPreferenceChangeListener(new OnSharedPreferenceChangeListener() {
					public void onSharedPreferenceChanged(
							SharedPreferences sharedPreferences, String key) {
						// start the service when checked
						// user clicked enable service
						if (key.equals("service_enabled"))
						{
							// state is enabled
							if (sharedPreferences.getBoolean(key, false))
							{
								// start service
								startService(new Intent(Settings.getContext(), DataStatusService.class));
							}
						}
					}
				});
	}

	@Override
	public void onResume() {
		super.onResume();

		// update settings with current context
		Settings.init(getApplicationContext());
	}

	@Override
	public void onPause() {
		super.onPause();
	}
}