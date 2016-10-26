package com.example.smartcitybeijing.pages;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.smartcitybeijing.activity.HomeActivity;


public class SettiongCenterPage extends BasePages {

	public SettiongCenterPage(HomeActivity context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		tv_title.setText("设置中心");
		
		//隐藏菜单按钮
		iv_menu.setVisibility(View.GONE);
		
		
		TextView tv =new TextView(mContext);
		tv.setText("设置中心的内容");
		tv.setTextSize(40);
		tv.setGravity(Gravity.CENTER);
		
		//添加到帧布局中
		fl_pagesContent.addView(tv);
		super.initData();
	}
}
