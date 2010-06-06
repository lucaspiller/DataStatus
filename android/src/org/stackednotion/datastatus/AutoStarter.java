package org.stackednotion.datastatus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class AutoStarter extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(DataStatusApplication.LOG_TAG, "AutoStarter#onReceive: Received intent " + intent.getAction());
		
		// init settings from context
		DataStatusApplication.init(context);
		
		SharedPreferences sharedPreferences = PreferenceManager
		.getDefaultSharedPreferences(context);
		
		// check if start on boot is enabled
		if (sharedPreferences.getBoolean("start_on_boot", false))
		{
			// check if service is enabled
			if (sharedPreferences.getBoolean("service_enabled", false))
			{
				Log.d(DataStatusApplication.LOG_TAG, "AutoStarter#onReceive: Starting service");
			
				// start service
				context.startService(new Intent(DataStatusApplication.getContext(), DataStatusService.class));
			}
		}
	}
}
