package com.example.smartcitybeijing.view;

import com.example.smartcitybeijing.activity.HomeActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @author Administrator
 *@company Newbie
 *@date 2016-10-26
 *@des fragment的基类
 *
 *数据      界面       事件
 */
public abstract class BaseFragment extends Fragment {
	
	protected HomeActivity mContext;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		
		mContext = (HomeActivity) getActivity();
		
		super.onCreate(savedInstanceState);
	}
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		return initView();
	
	}
	
	public abstract View initView();
	
	/**
	 * 子类覆盖此方法，完成初始化
	 */
	public void initData(){
		
	}
	
	/**
	 * 子类覆盖此方法，完成事件初始化
	 */
	public void initEvent(){
		
	}
	
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		initData();
		initEvent();
		super.onStart();
	}
}
