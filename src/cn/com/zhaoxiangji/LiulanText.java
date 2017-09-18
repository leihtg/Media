package cn.com.zhaoxiangji;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.com.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class LiulanText extends Activity{
	Gallery gallery;
	ImageView image;
	List<String> data=new ArrayList<String>();
	String path;
    int position;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.liulanzhaopian);
		path=ExistCameraTest.road;
		gallery=(Gallery) findViewById(R.id.gallery);
		image=(ImageView) findViewById(R.id.image);
		Photo(path);
		gallery.setAdapter(new BaseAdapter(){

			
			public int getCount() {
				// TODO Auto-generated method stub
				return data.size();
			}

			
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return data.get(position);
			}

			
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				ImageView image=new ImageView(LiulanText.this);
				BitmapFactory.Options options=new BitmapFactory.Options();
				options.inSampleSize=2;
				Bitmap bitmap=BitmapFactory.decodeFile(data.get(position), options);
				image.setLayoutParams(new Gallery.LayoutParams(160,160));
				image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				image.setImageBitmap(bitmap);
				return image;
			}
			
		});
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				LiulanText.this.position=position;
				Bitmap bitmap=BitmapFactory.decodeFile(data.get(position));
			    image.setImageBitmap(bitmap);
			}

			
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});

	}
	public void Photo(String path){
		File file=new File(path);
		File[] fs=file.listFiles();
		if(fs!=null){
			for(File f:fs){
				String str=f.getAbsolutePath();
				data.add(str);
			}
		}
	}
    
}
