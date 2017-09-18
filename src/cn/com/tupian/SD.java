package cn.com.tupian;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import cn.com.R;


public class SD extends BaseActivity{
	public static List<String>data=new ArrayList<String>();
	List<String>data1=new ArrayList<String>();
	MyAdapter adapter;
	ListView listView;
	File preFile;
	ListView view;
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tupianlist);
		ContentResolver resolver = getContentResolver();
		android.database.Cursor cursor = resolver.query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
				null);
		while (cursor.moveToNext()) {
			String path = cursor
					.getString(cursor
							.getColumnIndexOrThrow(MediaStore.Images/* ��ƵAudio��ƵVideoͼƬImages */.Media.DATA));
			data.add(path);
			data1.add(path.substring(path.lastIndexOf("/")+1, path.length()));
			Log.i(">>>>>>>>*****<<<<<<", path);
		}
	view=(ListView)findViewById(R.id.lv);
	adapter=new MyAdapter(this, data1);
	view.setAdapter(adapter);
	view.setOnItemClickListener(new ListView.OnItemClickListener(){

	
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
				long arg3) {
			Intent intent =new Intent(SD.this,GalleryActivity.class);
			intent.putExtra("result", data.get(pos));
			intent.putExtra("pos", pos);
			startActivity(intent);
		}
		
	});
			
	}		
		public void toPre(View view){
			File[]fs=preFile.getParentFile().listFiles();
			data.clear();//??????
			for(File f:fs){
				data.add(f.getAbsolutePath());//???????
			}
			adapter.notifyDataSetChanged();	//????
			}
		
}		
			
			
			
			
			

			
			
			
			
			
