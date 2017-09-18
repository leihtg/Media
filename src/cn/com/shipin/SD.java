package cn.com.shipin;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class SD extends ListActivity {
	List<String>data=new ArrayList<String>();
	List<String>data1=new ArrayList<String>();
	MyAdapter adapter;
	ListView listview;
	File preFile;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shipinlist);
		
		ContentResolver resolver=getContentResolver();
		Cursor cursor=resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
		while(cursor.moveToNext()){
			String path=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
		data.add(path);
		data1.add(path.substring(path.lastIndexOf("/")+1, path.length()));
		}
		listview=getListView();
		adapter=new MyAdapter(this,data1);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new ListView.OnItemClickListener(){

			
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				File file =new File(data.get(pos));
				if(file.isFile()){
					Toast.makeText(SD.this, "¬Ì…œ≤•∑≈", 1).show();
					
				}
				else if(file.isDirectory()){
					preFile=file;
					data.clear();
				    File[]fs=file.listFiles();
					
					for(File f:fs){
						data.add(f.getAbsolutePath());
					}
					
					adapter.notifyDataSetChanged();
				}
				Intent intent=new Intent();
				intent.putExtra("result", data.get(pos));
				SD.this.setResult(RESULT_OK,intent);
				SD.this.finish();
				
				
				
			}
			
	});
	
	}
	public void toPre(View view) {
		File[] fs = preFile.getParentFile().listFiles();
		data.clear();
		
			for (File f : fs) {
				data.add(f.getAbsolutePath());
			}
		
		adapter.notifyDataSetChanged();

	}
}