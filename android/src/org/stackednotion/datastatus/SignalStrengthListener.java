package org.stackednotion.datastatus;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class SignalStrengthListener extends PhoneStateListener {
	private int dataConnectionType = 0;
	
	/* Monitors the data connection type */
	@Override
	public void onDataConnectionStateChanged(int state, int networkType)
	{
		switch(state)
		{
			case TelephonyManager.DATA_CONNECTED:
				dataConnectionType = networkType;
				break;
			default:
				dataConnectionType = 0;
				break;
		}
	}
	
	public int getDataConnectionType()
	{
		return dataConnectionType;
		
		/*switch(dataConnectionType)
		{
			case TelephonyManager.NETWORK_TYPE_HSUPA:
			case TelephonyManager.NETWORK_TYPE_HSPA:
			case TelephonyManager.NETWORK_TYPE_HSDPA:
				return "H";
				
			case TelephonyManager.NETWORK_TYPE_UMTS:
				return "3G";
				
			case TelephonyManager.NETWORK_TYPE_EDGE:
				return "E";
				
			case TelephonyManager.NETWORK_TYPE_GPRS:
				return "G";
				
			default:
				return "--";
		}*/
	}
}
