package cn.com.shipin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.com.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class Surfaceview extends Activity implements
		SurfaceHolder.Callback {
	SurfaceView sur;
	SurfaceHolder holder;
	Button  stop, spsf;
	int a = 1,b=1;
	List<String> videos = new ArrayList<String>();
	private String path;
	SeekBar seek, voice;
	AudioManager am;
	int max = 0;
	int pos = 0;
	boolean canStart = false, canStop = false, canPrepare = false;

	public static final MediaPlayer mp = new MediaPlayer();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shipin);

		sur = (SurfaceView) findViewById(R.id.sv);
		holder = sur.getHolder();
		holder.addCallback(this);
		holder.setType(holder.SURFACE_TYPE_PUSH_BUFFERS);

		ContentResolver resolver = getContentResolver();
		Cursor cursor = resolver.query(
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null,
				null);
		while (cursor.moveToNext()) {
			String path = cursor.getString(cursor
					.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
			videos.add(path);
			Log.i("sdjflsjglsajg", path);
		}

		Uri uri = this.getIntent().getData();
		if (uri != null) {
			mp.reset();
			try {
				mp.setDataSource(uri.getEncodedPath());
				mp.prepare();
				mp.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		voice=(SeekBar) findViewById(R.id.voice);
		   am=(AudioManager) getSystemService(Context.AUDIO_SERVICE);
	        voice.setMax(am.getStreamMaxVolume
	        		(AudioManager.STREAM_MUSIC));
	        voice.setProgress(am.getStreamVolume(
	        		AudioManager.STREAM_MUSIC));
	        voice.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				
				
				public void onStopTrackingTouch(SeekBar seekBar) {
					
					
				}
				
				public void onStartTrackingTouch(SeekBar seekBar) {
				
					
				}
				
				
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					
					am.setStreamVolume(AudioManager.STREAM_MUSIC
							, progress,AudioManager.FLAG_SHOW_UI);
				}
			});


		spsf=(Button) findViewById(R.id.spsf);
		spsf.setOnClickListener(new OnClickListener() {

			
			public void onClick(View v) {
				if(a==1){
					spsf.setText("ÔÝÍ£");
					play(videos.get(pos));
					a=2;
				}else if(mp.isPlaying()){
					spsf.setText("²¥·Å");
					mp.pause();
					a=3;
				}else if(a==3&&b!=2){
					spsf.setText("ÔÝÍ£");
					mp.start();
					xianch();
				}else if(b==2){
					spsf.setText("ÔÝÍ£");
					play(videos.get(pos));
				}
				
			}
		}
	);
		
		
		stop = (Button) findViewById(R.id.stop);
		 stop.setOnClickListener(new OnClickListener() {
		
	
		 public void onClick(View v) {
			 if(mp.isPlaying()||a==3){
				 mp.stop();
				 b=2;
			 }
		 }
		 });

		mp.setOnCompletionListener(new OnCompletionListener() {

			
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				toNext(null);
			}
		});
		 
	}

	

	public void play(String path) {
		mp.reset();
		try {
			mp.setDataSource(path);
			mp.prepare();
			max=mp.getDuration();
			Log.i("mac", max+"");
			mp.start();
			pl();
			} catch (Exception e) {
			e.printStackTrace();
		}
	}
    @Override
	public void finish() {
		mp.stop();
		super.finish();
	}

	public void toPre(View view) {
		pos = (pos - 1) > 0 ? (pos - 1) : videos.size()-1;
		
		play(videos.get(pos));
	}

	public void toNext(View view) {
	
		pos = (pos + 1) < videos.size() ? (pos+1) :0;

		play(videos.get(pos));
	}


	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		

	}

	
	public void surfaceCreated(SurfaceHolder holder) {
		mp.setDisplay(holder);
		mp.start(); // ???????????????
	
	}

	
	public void surfaceDestroyed(SurfaceHolder holder) {
	

	}
	
	 public void sd(View view){
	    	startActivityForResult(new Intent(Surfaceview.this,SD.class),1);
	    	
	    }
	 public void pl(){
		 seek = (SeekBar) findViewById(R.id.seek);
			seek.setMax(max);
			seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub

				}

			
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub

				}

				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					if (fromUser)
						mp.seekTo(progress);
				}
			});
			xianch();
			
		 
	 }
	 public void xianch() {
			new Thread() {
				public void run() {
					while (true) {
						if (mp.isPlaying()) {
							seek.setProgress(mp.getCurrentPosition());
						}
					}
				}
			}.start();
		}
	   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	// TODO Auto-generated method stub
	    	super.onActivityResult(requestCode, resultCode, data);
	    	if(resultCode==RESULT_OK){
	    		path=data.getExtras().getString("result");
	    		
	    	}
	    	play(path);
	    }

}
