package cn.com.hwb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.com.R;
import cn.com.luxiangji.luxiang;
import cn.com.shipin.Surfaceview;
import cn.com.tupian.SD;
import cn.com.yinpin.MediaActivity1;
import cn.com.zhaoxiangji.ExistCameraTest;

public class MediaActivity extends Activity implements OnClickListener {
	Button yinpin, shipin, tupian, zhaoxiangji, luxiangji;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		yinpin = (Button) findViewById(R.id.yinpin);
		shipin = (Button) findViewById(R.id.shipin);
		zhaoxiangji = (Button) findViewById(R.id.zhaoxiang);
		tupian = (Button) findViewById(R.id.tupian);
		luxiangji = (Button) findViewById(R.id.luxiangji);

		luxiangji.setOnClickListener(this);
		tupian.setOnClickListener(this);
		zhaoxiangji.setOnClickListener(this);
		shipin.setOnClickListener(this);
		yinpin.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		Class<?> cla = null;
		if (v == yinpin) {
			Log.i("Ìø×ªÖÁÖÐ", "lll");
			cla = MediaActivity1.class;
		} else if (v == shipin) {
			cla = Surfaceview.class;
		} else if (v == zhaoxiangji) {
			cla = ExistCameraTest.class;
		} else if (v == tupian) {
			cla = SD.class;
		} else if (v == luxiangji) {
			cla = luxiang.class;
		}
		startActivity(new Intent(MediaActivity.this, cla));
	}

}
