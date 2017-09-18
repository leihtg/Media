package cn.com.tupian;

import cn.com.R;

import android.content.Intent;
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

public class GalleryActivity extends BaseActivity {
	Gallery gallery;
	ImageView image;
	int pos;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);
		BaseActivity.allActivity.add(this);
		gallery = (Gallery) findViewById(R.id.g1);
		image = (ImageView) findViewById(R.id.image);
		Intent intent = getIntent();
		pos = intent.getIntExtra("pos", 0);
		gallery.setAdapter(new BaseAdapter() {
			public int getCount() {
				return SD.data.size();
			}

			public Object getItem(int position) {
				return SD.data.get(position);
			}

			public long getItemId(int position) {
				return position;
			}

		
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				ImageView image = new ImageView(GalleryActivity.this);
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2; // ???????
				Bitmap bitmap = BitmapFactory.decodeFile(SD.data.get(position));
				image.setImageBitmap(bitmap);
				image.setLayoutParams(new Gallery.LayoutParams(160, 160));// ????????
				image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);// ??????
				return image;
			}
		});
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long id) {
				GalleryActivity.this.pos = position; // ????position
				Bitmap bitmap = BitmapFactory.decodeFile(SD.data.get(pos));
				gallery.setSelection(pos);// ?????
				image.setImageBitmap(bitmap);
			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		setImage();

		image.setOnClickListener(new ImageView.OnClickListener() {

		
			public void onClick(View v) {
				String path = SD.data.get(pos);
				Intent intent = new Intent(GalleryActivity.this,
						ImageActivity.class);
				intent.putExtra("path", path);
				startActivity(intent);
			}

		});
	}

	public void setImage() {
		Intent intent = getIntent();
		String path = intent.getStringExtra("result");
		if (path != null) {
			int pos = SD.data.indexOf(path);
			gallery.setSelection(pos);
		}
	}
}