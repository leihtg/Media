package cn.com.tupian;

import cn.com.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class ImageActivity extends Activity {
	  ImageView im;
	    Bitmap bitmap;
	    float scale=1,root=0;
	    String path;
	    GestureDetector gesture;
	    int deplay;
	   
	    private static final int TOBIG=Menu.FIRST;
	    private static final int TOSMALL=Menu.FIRST+1;
	    private static final int TOLEFT=Menu.FIRST+2;
	    private static final int TORIGHT=Menu.FIRST+3;
	    private static final int AUTOPLAY=Menu.FIRST+4;
	    private static final int FANHUI=Menu.FIRST+5;
	    public Handler handler=new Handler(){
	    	public void handleMessage(Message msg){
	    		switch(msg.what){
	    		case 0:
	    			swapImage(true);
	    			break;
	    			default:
	    				break;
	    		}
	    	}
	    };
	    Runnable run=new Runnable() {
	    	public void run(){
	    		handler.sendEmptyMessage(0);
	    		handler.postDelayed(this, deplay);//?????
	    	}
			};
			public void start(View view){
				handler.post(run);
			}
			public void stop(View view){
				handler.removeCallbacks(run);//?????????
			}
	    
	     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tupian);
        BaseActivity.allActivity.add(this);
        getGestrue();
        im=(ImageView)findViewById(R.id.im);
        Intent intent=getIntent();
        String path=intent.getStringExtra("path");
        if(path!=null){
        	setImage(path);
        }else{
        	Toast.makeText(this, R.string.no_image, 1).show();
        }
        setImage(path); }
	     public void setImage(String path){
	         BitmapFactory.Options options=new BitmapFactory.Options();
	         options.inSampleSize=4;
	         bitmap=BitmapFactory.decodeFile(path,options);
	         im.setImageBitmap(bitmap);}
	     @Override
	     public boolean onCreateOptionsMenu(Menu menu){
	    	 menu.add(0, TOBIG, 0, getString(R.string.tobig));//放大
	    	 menu.add(0, TOSMALL, 0, getString(R.string.tosmall));//缩小
	    	 menu.add(0, TOLEFT, 0, getString(R.string.toleft));//左转
	    	 menu.add(0, TORIGHT, 0, getString(R.string.toright));//右转
	    	 menu.add(0, AUTOPLAY, 0, getString(R.string.autoplay));//自动播放
	    	 menu.add(0, FANHUI, 0, getString(R.string.fanhui));//返回
	    	return super.onCreateOptionsMenu(menu);
	    	 
	     }
	     public void getAutoplayDialog(){
	        	
	        	AlertDialog.Builder dialog=new AlertDialog.Builder(this);
	        	View view=getLayoutInflater().inflate(R.layout.s, null);
	        	dialog.setTitle("自动播放模式");
	        	dialog.setView(view);
	        	final RadioButton sd,sm,wm;
	        	sd = (RadioButton) view.findViewById(R.id.shoudong);
	        	sm = (RadioButton) view.findViewById(R.id.sm);
	        	wm = (RadioButton) view.findViewById(R.id.wm);
	        	dialog.setNegativeButton("取消", new DialogInterface.OnClickListener(){
	        		public void onClick(DialogInterface dialog,int which){
	        			dialog.dismiss();
	        		}
	        	});
					dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						
						public void onClick(DialogInterface dialog, int which) {
							
							if(sd.isChecked()){handler.removeCallbacks(run);}
							if(sm.isChecked()){
								deplay=3000;
								handler.post(run);
							
						}
							if(wm.isChecked()){
								deplay=5000;
								handler.post(run);
					}
					
					}
				});
					dialog.show();
	        }
	    	public void to(float s,float t) {
	    		Matrix matrix = new Matrix();
	    		scale*=s;
	    		root+=t;
	        	matrix.postScale(scale, scale);
	        	matrix.postRotate(root);
	        	Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, 
	        			bitmap.getWidth(), bitmap.getHeight(),
	        			matrix, true);
	        	im.setImageBitmap(bm);
	    	}
	     
	@Override
	public boolean onMenuItemSelected(int featureId,MenuItem item){
		switch(item.getItemId()){
		case TOBIG:
			to(1.2f, 0);
			break;
		case TOSMALL:
			to(0.8f, 0);
			break;
		case TOLEFT:
			to(1, -15);
			break;
		case TORIGHT:
			to(1, 15);
			break;
		case AUTOPLAY:
			getAutoplayDialog();
			break;
		case FANHUI:
			startActivity(new Intent(ImageActivity.this,SD.class));
			break;
    	}	
			
		
		return super.onMenuItemSelected(featureId, item);
	}
	     
	     
	     @Override
	     public boolean dispatchTouchEvent(MotionEvent ev){
	    	 gesture.onTouchEvent(ev);
	    	 im.onTouchEvent(ev);//??????????????
			return super.dispatchTouchEvent(ev);
	    	 
	     }
	     @Override
	    	public boolean onTouchEvent(MotionEvent event) {
	    		gesture.onTouchEvent(event);
	    		return super.onTouchEvent(event);
	    	}
	     
	     
	     private void getGestrue() {
	    	 gesture =new GestureDetector(new GestureDetector.OnGestureListener() {
				
		
				public boolean onSingleTapUp(MotionEvent e) {
					return false;
				}
				
			
				public void onShowPress(MotionEvent e) {
					
				}
				
			
				public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
						float distanceY) {
					return false;
				}
				
				public void onLongPress(MotionEvent e) {
					
				}
				
				
				public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
						float velocityY) {
					float x1 = e1.getX();
    				float x2 = e2.getX();
    				float c = x2-x1;
    				if(c<0){
    					swapImage(true);
    					
    				}else if(c>0){
    					swapImage(false);
    				}  //???????????????????????????
					return false;
				}
		
				public boolean onDown(MotionEvent e) {
					return false;
				}
			},handler);
			
		}
		public void swapImage(boolean flag) {
		scale=1;
		root=0;
		int pos = SD.data.indexOf(path);
		if(flag){
			if(pos<SD.data.size()-1){
				pos++;
				path=SD.data.get(pos);
				setImage(path);
				Toast.makeText(this, pos+1+"/"+SD.data.size(),1).show();
			}else{
				Toast.makeText(this, R.string.the_first_image,1).show();
				handler.removeCallbacks(run);
			}
		}else{
			if(pos>0){
				pos--;
				path=SD.data.get(pos);
				setImage(path);
				Toast.makeText(this,pos+1+"/"+SD.data.size(), 1).show();
				}else {
    				Toast.makeText(this, R.string.the_first_image, 1).show();
    				
    			}
		}
		
	}
		  public void option(View v){  //监听事件
	            switch(v.getId()){
	            case R.id.fd:
	            	to(1.2f,0);
	            	break;
	            case R.id.sx:
	            	to(0.8f,0);
	            	break;
	            case R.id.zx:
	            	to(1,-15);
	            	break;
	            case R.id.yx:
	            	to(1,15);
	            	break;
	            }
	        }   

	    	@Override
	    	public void finish(){
	    		handler.removeCallbacks(run);
	    		super.finish();
	    	}
}