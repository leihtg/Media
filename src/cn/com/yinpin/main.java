package cn.com.yinpin;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;

public class main extends Activity {
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		startActivity(new Intent(this,MediaActivity1.class));
		finish();
	}

	public static void main(String[] args) {
		

	}

}
