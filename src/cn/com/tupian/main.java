package cn.com.tupian;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class main extends BaseActivity {
	protected void onCreate(Bundle savedInstanceState){
		BaseActivity.allActivity.add(this); //????????
		Uri uri=getIntent().getData();//???????
		String path=uri.getPath();//
		Intent intent=new Intent(main.this,GalleryActivity.class);
		intent.putExtra("path",path);//????????
		startActivity(intent);
		finish();
		
	}

}
