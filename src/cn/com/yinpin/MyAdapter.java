package cn.com.yinpin;

import java.util.List;
import java.util.Map;

import cn.com.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{
	Context context;
	List<String>data;
	public MyAdapter(Context context
			,List<String>data){
		this.context=context;
		this.data=data;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		if(data!=null&&data.size()>0)
			return data.size();
		return 0;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		view=LayoutInflater.from(context).inflate(R.layout.item1, null);
		TextView tv=(TextView) 
		view.findViewById(R.id.tv);	
		tv.setTextColor(Color.WHITE);
		tv.setText(data.get(position));
		return view;
	}

}
