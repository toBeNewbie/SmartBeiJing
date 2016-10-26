package com.example.smartcitybeijing.activity;

import com.example.smartcitybeijing.R;
import com.example.smartcitybeijing.view.LeftFragment;
import com.example.smartcitybeijing.view.MainFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class HomeActivity extends SlidingFragmentActivity {
	
	private static final String LEFT_FRAGMENT="left_fragment";
	
	private static final String MAIN_FRAGMENT="main_fragment";

	private FragmentManager fragmentManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();
		
		initData();
	}

	private void initData() {
		
		fragmentManager = getSupportFragmentManager();
		//开启事物
		FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
		//替换
		beginTransaction.replace(R.id.fl_left, new LeftFragment(), LEFT_FRAGMENT);
		beginTransaction.replace(R.id.fl_main, new MainFragment(), MAIN_FRAGMENT);
	
		//提交事务
		beginTransaction.commit();
	}

	
	/**
	 * 获取侧面滑动的fragment
	 * @return
	 */
	public LeftFragment getLeftFragment(){
		return (LeftFragment) fragmentManager.getFragment(null, LEFT_FRAGMENT);
	}
	/*
	 * 获取主界面的fragment
	 */
	public MainFragment getMainFragment(){
		return (MainFragment) fragmentManager.getFragment(null, MAIN_FRAGMENT);
	}
	
	
	private void initView() {
		
		//左侧菜单布局
		setBehindContentView(R.layout.fragment_left_activity);
		//设置内容和布局
		setContentView(R.layout.fragment_main_activity);
		
		SlidingMenu mSlidingMenu = getSlidingMenu();
		
		//左侧菜单显示完全后，剩余的宽度
		mSlidingMenu.setBehindOffset(200);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		mSlidingMenu.setMode(SlidingMenu.LEFT);
		
	}
}
