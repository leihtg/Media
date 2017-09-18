package cn.com.shipin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class main extends Activity{
	protected void onCreate(Bundle savedInstanceStatae){
	startActivity(new Intent(this,Surfaceview.class));
	finish();
	}
	public static void main(String[]args){
		
	}

}
