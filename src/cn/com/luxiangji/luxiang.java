package cn.com.luxiangji;

import cn.com.R;
import android.app.Activity;
import android.content.Intent;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;

public class luxiang extends Activity implements Callback{

	SurfaceView sv;
	SurfaceHolder ah;
	MediaRecorder mr=new MediaRecorder();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luxiangji);
        sv=(SurfaceView) findViewById(R.id.sf);
        ah=sv.getHolder();
        ah.addCallback(this);
        ah.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
	
		mr.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
		mr.setVideoSource(MediaRecorder.VideoSource.CAMERA);
		//step3
		mr.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//		 mr.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_LOW));

		mr.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		mr.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
		//4
		mr.setOutputFile("mnt/sdcard/123");
		
		//5
		mr.setPreviewDisplay(ah.getSurface());
		try{
			mr.prepare();
		}catch(Exception e){
			finish();
			Log.i("fianinmmmmm", e.getMessage());
		}
	}
	
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mr.release();
	}
	public void start(View v){
		mr.start();
	}
	public void stop(View v){
		mr.stop();
	}
	public void next(View view){
		Intent intent=new Intent(luxiang.this,luxiang.class);
	}
}
