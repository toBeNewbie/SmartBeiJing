package com.example.smartcitybeijing.pages;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.smartcitybeijing.activity.HomeActivity;


public class HomePage extends BasePages {

	public HomePage(HomeActivity context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		tv_title.setText("首页");
		
		//隐藏菜单按钮
		iv_menu.setVisibility(View.GONE);
		
		TextView tv =new TextView(mContext);
		tv.setText("首页的内容");
		tv.setTextSize(40);
		tv.setGravity(Gravity.CENTER);
		
		//添加到帧布局中
		fl_pagesContent.addView(tv);
		
		super.initData();
	}
}
