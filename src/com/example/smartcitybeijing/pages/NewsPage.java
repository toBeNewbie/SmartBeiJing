package com.example.smartcitybeijing.pages;

import android.view.Gravity;
import android.widget.TextView;

import com.example.smartcitybeijing.activity.HomeActivity;


public class NewsPage extends BasePages {

	public NewsPage(HomeActivity context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		tv_title.setText("新闻中心");
		
		TextView tv =new TextView(mContext);
		tv.setText("新闻中心的内容");
		tv.setTextSize(40);
		tv.setGravity(Gravity.CENTER);
		
		//添加到帧布局中
		fl_pagesContent.addView(tv);
	}
	
}
