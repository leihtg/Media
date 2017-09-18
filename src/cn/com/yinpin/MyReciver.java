package cn.com.yinpin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class MyReciver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		TelephonyManager tm=(TelephonyManager)
		context.getSystemService(Context.TELEPHONY_SERVICE);
		switch(tm.getCallState()){
		case TelephonyManager.CALL_STATE_IDLE:
			MediaActivity1.mp.start();
			break;
		case TelephonyManager.CALL_STATE_RINGING:
			MediaActivity1.mp.pause();
			break;
		}
		
	}

}
