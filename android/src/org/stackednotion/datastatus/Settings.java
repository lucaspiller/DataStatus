package org.stackednotion.datastatus;

import android.content.Context;

public class Settings {
	public static final String LOG_TAG = "DataStatus";

	private static Context context;

	public static void init(Context applicationContext) {
		context = applicationContext;
	}

	public static Context getContext() {
		return context;
	}
	
	public static SignalStrengthListener signalStrengthListener;
}
