package cn.com.zhaoxiangji;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ExistCameraTest extends Activity {
    /** Called when the activity is first created. */
	ImageView image;
	String path;
	Bitmap bitmap;
	public static String road;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhaoxiangji);
        //构造意图
        Uri uri=getUri();
        path=uri.getPath();
        image=(ImageView) findViewById(R.id.image);
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
         startActivityForResult(intent,100);
    }
    public Uri getUri(){
    	Uri uri=null;
    	if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
    		File pubPicFile=Environment
    		.getExternalStoragePublicDirectory(
    				Environment.DIRECTORY_PICTURES);
    		File myFile=new File(pubPicFile,"myApp");
    		road=myFile.getAbsolutePath();
    		if(!myFile.exists()){
    			myFile.mkdirs();
    		}
    			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    			File mediaFile=new File(myFile.getPath()+File.separator+"laowei_"+timeStamp+".jpg");
    			uri=Uri.fromFile(mediaFile);
    	}else{
    		Toast.makeText(this, "SD卡不可用", 1).show();
    	}
		return uri;
    	
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data){
    	super.onActivityResult(requestCode, resultCode, data);
    	//String path=data.getData().getPath();
    	if(resultCode==RESULT_OK){
    		BitmapFactory.Options options=new BitmapFactory.Options();
    		options.inSampleSize=4;
    		bitmap=BitmapFactory.decodeFile(path,options);
    		image.setImageBitmap(bitmap);
    	}
    	
    }
    public void takePic(View view){
    	Uri uri=getUri();
        path=uri.getPath();
       
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
         image.setImageBitmap(null);
         bitmap.recycle();
         startActivityForResult(intent,100);
    
    }
    public void toLook(View view){
    	Intent intent=new Intent(ExistCameraTest.this,LiulanText.class);
    	startActivity(intent);
    }
    
    
}