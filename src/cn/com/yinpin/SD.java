package cn.com.yinpin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.com.R;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SD extends ListActivity {
	List<String> data = new ArrayList<String>();
	List<String> data1=new ArrayList<String>();

	MyAdapter adapter;
	ListView listView;
	File preFile;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yinpinlist);
		ContentResolver resolver = getContentResolver();
		Cursor cursor = resolver.query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
				null);
		while (cursor.moveToNext()) {
			String path = cursor
					.getString(cursor
							.getColumnIndexOrThrow(MediaStore.Audio/* 音频Audio视频Video图片Images */.Media.DATA));
			data.add(path);
			data1.add(path.substring(path.lastIndexOf("/")+1, path.length()));
			Log.i(">>>>>>>>*****<<<<<<", path);
		}
		listView =getListView();
		adapter =new MyAdapter(this,data1);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				File file=new File(data.get(pos));
				if(file.isFile()){
					Toast.makeText(SD.this, "马上播放",1).show();
				}else if(file.isDirectory()){  //
					preFile =file;
					Log.i(">>>>>>>>><<<<<<<",preFile.toString());
					data.clear();
					File[]fs=file.listFiles();
					if(fs!=null){
						for(File f:fs){
							data.add(f.getAbsolutePath());
						}
					}
					adapter.notifyDataSetChanged();
				}
				Intent intent =new Intent();
				intent.putExtra("result", data.get(pos));
				SD.this.setResult(RESULT_OK,intent);
				SD.this.finish();
			}
		});
		
		
	}
	public void toPre(View view){
		
		File[]fs=preFile.getParentFile().listFiles();
		data.clear();
	  if(fs!=null){
		  for(File f:fs){
		  data.add(f.getAbsolutePath());
	  }
	}
	adapter.notifyDataSetChanged();

}
	}
















