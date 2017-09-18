package cn.com.shipin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class MyReceiver extends BroadcastReceiver {

	public void onReceive(Context context,Intent intent){
		TelephonyManager tm=(TelephonyManager)
		context.getSystemService(Context.TELEPHONY_SERVICE);
		switch(tm.getCallState()){
		case TelephonyManager.CALL_STATE_IDLE:
			Surfaceview.mp.start();
			break;
		case TelephonyManager.CALL_STATE_RINGING:
			Surfaceview.mp.pause();
			break;
		}
	}

}
