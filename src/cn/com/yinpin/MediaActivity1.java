package cn.com.yinpin;

import java.text.SimpleDateFormat;
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
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MediaActivity1 extends Activity implements OnClickListener {
	int h = 0;
	String path;
	int deplay;
	public static List<String> musics = new ArrayList<String>();
	Button start, stop, pause;
	SeekBar seek, voice;
	AudioManager am;
	boolean canStart = false, canStop = false, canPrepare = false;
	public static MediaPlayer mp = new MediaPlayer();
	RadioButton dq, sj, sx;
	int pos = 0;
	int max = 0;
	private TextView tv1, tv2;
	Handler handler = new Handler();
	int position = 0, a = 1;
	Runnable run = new Runnable() {
		public void run() {
			handler.postDelayed(this, 1000);
			String str = new SimpleDateFormat("mm:ss").format(mp
					.getCurrentPosition());
			tv1.setText(str);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yinpin);
		tv1 = (TextView) findViewById(R.id.start_time);
		tv2 = (TextView) findViewById(R.id.end_time);
		handler.post(run);
		ContentResolver resolver = getContentResolver();
		Cursor cursor = resolver.query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
				null);
		while (cursor.moveToNext()) {
			String path = cursor.getString(cursor
					.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
			Log.i("mediaStore_images", path);
			musics.add(path);

			Uri uri = this.getIntent().getData();
			if (uri != null) {
				try {
					mp.setDataSource(uri.getEncodedPath());
					mp.prepare();
					max = mp.getDuration();
				} catch (Exception e) {
				}
			} else {
				if (a == 1) {
					play(musics.get(pos));
				}
			}
			dq = (RadioButton) findViewById(R.id.dq);
			sj = (RadioButton) findViewById(R.id.sj);
			sx = (RadioButton) findViewById(R.id.sx);
			seek = (SeekBar) findViewById(R.id.seek);
			voice = (SeekBar) findViewById(R.id.voice);

			am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			voice.setMax(am.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
			voice.setProgress(am.getStreamVolume(AudioManager.STREAM_MUSIC));

			voice.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				public void onStopTrackingTouch(SeekBar seekBar) {
				}
				public void onStartTrackingTouch(SeekBar seekBar) {
				}
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					am.setStreamVolume(AudioManager.STREAM_MUSIC, progress,
							AudioManager.FLAG_SHOW_UI);
				}
			});
			
			am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

			start = (Button) findViewById(R.id.start);
			stop = (Button) findViewById(R.id.stop);
			pause = (Button) findViewById(R.id.pause);
			start.setOnClickListener(this);
			stop.setOnClickListener(this);
			pause.setOnClickListener(this);

			mp.setOnCompletionListener(new OnCompletionListener() {
				public void onCompletion(MediaPlayer mp) {
					if (sx.isChecked()) {
						toNext(null);
					} else if (dq.isChecked()) {
						play(musics.get(pos));
					} else if (sj.isChecked()) {
						toNext(null);
					}

				}
			});
		}

	}

	@Override
	public void onClick(View v) {
		if (v == start) {
			if (canPrepare)
				try {
					h++;
					mp.prepare();
				} catch (Exception e) {
				}
			if (canStart) {
				h++;
				mp.start();
			}
			if (!canStop) {
				h++;
				play(musics.get(pos));
			}
		} else if (v == stop) {
			mp.stop();
			mp.reset();
			// canStop = true;
			canPrepare = false;
			position = 0;
		} else if (v == pause) {
			if (mp.isPlaying()) {
				position = mp.getCurrentPosition();
				mp.stop();
			}
			// canStart = true;
			canStop = false;
		}
	}

	public void play(String path) {
		if (h > 0) {

			mp.reset();
			try {
				mp.setDataSource(path);
				mp.prepare();
				mp.seekTo(position);
				max = mp.getDuration();
				mp.start();
				tv2.setText(new SimpleDateFormat("mm:ss").format(max));
				pl();

			} catch (Exception e) {

			}
		}

	}

	@Override
	public void finish() {
		mp.stop();
		mp.reset();
		super.finish();
	}

	public void toPre(View view) {
		if (sx.isChecked() || dq.isChecked()) {
			if (pos == 0) {
				pos = musics.size() - 1;

			} else
				pos--;
		} else if (sj.isChecked()) {
			pos = (int) (Math.random() * musics.size());
		}
		play(musics.get(pos));
	}

	public void toNext(View view) {
		if (sx.isChecked() || dq.isChecked()) {
			if (pos == musics.size() - 1) {
				pos = 0;
			} else
				pos++;

		} else if (sj.isChecked()) {
			pos = (int) (Math.random() * musics.size());
		}
		play(musics.get(pos));
	}

	public boolean onKeydown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			mp.stop();
			finish();
		}
		return super.onKeyDown(keyCode, event);

	}

	public void sd(View view) {
		startActivityForResult(new Intent(MediaActivity1.this, SD.class), 1);
		// TODO Auto-generated method stub
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			path = data.getExtras().getString("result");

		}
		play(path);
	}

	public void pl() {
		seek.setMax(max);
		seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			public void onStopTrackingTouch(SeekBar seekBar) {

			}

			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (fromUser)
					mp.seekTo(progress);

			}
		});

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

}
