package com.example.smartcitybeijing.newscenterpages;

import android.view.View;

import com.example.smartcitybeijing.activity.HomeActivity;

/**
 * 
 * @author Administrator
 *@company Newbie
 *@date 2016-10-29
 *@des 新闻中心轮播界面的基类
 */
public abstract class BaseNewsCenterPage {
	protected HomeActivity mContext;
	private View rootView;
	public BaseNewsCenterPage(HomeActivity mContext) {
		
		this.mContext = mContext;
		rootView = initView();
		
		
		initEvent();
	}
	
	/**
	 * 子类覆盖此方法，完成事件初始化。
	 */
	public void initEvent() {
		// TODO Auto-generated method stub
		
	}

	public abstract View initView();
	
	public View getRootView(){
		
		return rootView;
	}
	
	/**
	 * 子类覆盖此方法，完成数据初始化
	 */
	public void initData(){
		
	}
	
	
}
