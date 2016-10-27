package com.example.smartcitybeijing.newscenterpages;

import android.view.View;

import com.example.smartcitybeijing.activity.HomeActivity;

public abstract class BaseNewsCenterPage {
	protected HomeActivity mContext;
	private View rootView;
	public BaseNewsCenterPage(HomeActivity mContext) {
		
		this.mContext = mContext;
		rootView = initView();
	}
	
	public abstract View initView();
	
	public View getRootView(){
		
		return rootView;
	}
}
