package cn.com.tupian;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.KeyEvent;

public class BaseActivity extends Activity{
	public static List<Activity> allActivity=new ArrayList<Activity>();//?????
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		allActivity.add(this);
		
	}
	@Override 
	public boolean onKeyDown(int keyCode,KeyEvent event){
		if(keyCode==KeyEvent.KEYCODE_BACK){//?????????
			getActivity();
		}
		return super.onKeyDown(keyCode, event);
		
	}
	public  void getActivity() {
		AlertDialog.Builder dialog=new AlertDialog.Builder(this);
		dialog.setTitle("��ʾ");
		dialog.setMessage("ȷ��Ҫ�˳���");
		dialog.setPositiveButton("ȷ��", new OnClickListener() {
			
			
		
			public void onClick(DialogInterface dialog, int which) {
				for(Activity ac:allActivity){ //???????????
					ac.finish();
				}
				
			}
		});dialog.setNegativeButton("ȡ��", new OnClickListener() {
			
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.show();
		
	}
		
	}

