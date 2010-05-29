package org.stackednotion.datastatus;

import org.stackednotion.datastatus.server.ServerApplication;
import org.stackednotion.datastatus.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class MainActivity extends Activity {
	private TelephonyManager telephonyManager;
	
	private final int serverPort = 17362;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Settings.signalStrengthListener = new SignalStrengthListener();
      	telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    }
    
    @Override
    public void onResume()
    {
    	Settings.init(getApplicationContext());
    
    	telephonyManager.listen(Settings.signalStrengthListener, PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
    	ServerApplication.startServer(serverPort);
    	
    	super.onResume();
    }
    
    @Override
    public void onPause()
    {
    	telephonyManager.listen(Settings.signalStrengthListener, PhoneStateListener.LISTEN_NONE);
    	ServerApplication.stopServer();
    	
    	super.onPause();
    }
}