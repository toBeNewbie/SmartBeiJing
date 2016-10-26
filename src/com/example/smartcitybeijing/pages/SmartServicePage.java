package com.example.smartcitybeijing.pages;

import com.example.smartcitybeijing.activity.HomeActivity;

import android.view.Gravity;
import android.widget.TextView;

public class SmartServicePage extends BasePages {

	public SmartServicePage(HomeActivity context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		tv_title.setText("智慧服务");
		TextView tv =new TextView(mContext);
		tv.setText("智慧服务的内容");
		tv.setTextSize(40);
		tv.setGravity(Gravity.CENTER);
		
		//添加到帧布局中
		fl_pagesContent.addView(tv);
	}
	
}
