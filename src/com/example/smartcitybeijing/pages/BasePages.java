package com.example.smartcitybeijing.pages;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcitybeijing.R;
import com.example.smartcitybeijing.activity.HomeActivity;
import com.example.smartcitybeijing.newscenterpages.NewsCenterPageItem_Photos;

/**
 * 
 * @author Administrator
 *@company Newbie
 *@date 2016-10-26
 *@des 5个新闻信息界面基类
 */
public class BasePages {

	protected HomeActivity mContext;
	private View rootView;
	protected ImageView iv_menu;
	protected TextView tv_title;
	protected FrameLayout fl_pagesContent;
	protected ImageView iv_showGroutViewDatas;
	
	public BasePages(HomeActivity context) {
		
		this.mContext = context;
		
		initView();
		
		initData();
		
		
		initEvent();
	}
	
	public void initLocalDatas() {
		// TODO Auto-generated method stub

	}
	
	
	public void initEvent() {
		
		//给组图按钮添加点击事件，完成页面切换
		iv_showGroutViewDatas.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 完成组图页面listview和gridView页面的切换
				NewsCenterPageItem_Photos mCenterPageItem_Photos = (NewsCenterPageItem_Photos) v.getTag();
				
				mCenterPageItem_Photos.switchGroupPhotoView((ImageView) v);
			
			}
		});
		
		
		// TODO Auto-generated method stub
		iv_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				控制滑动界面的拉伸
				mContext.getSlidingMenu().toggle();
			}
		});
	}

	private void initView(){
		
		rootView = View.inflate(mContext, R.layout.basepage_content_layout, null);
		
		iv_menu = (ImageView) rootView.findViewById(R.id.iv_menu_item);
		tv_title = (TextView) rootView.findViewById(R.id.tv_title_item);
		fl_pagesContent = (FrameLayout) rootView.findViewById(R.id.fl_content_mess);
		
		iv_showGroutViewDatas = (ImageView) rootView.findViewById(R.id.iv_title_bar_gred_view);
	}
	
	public View getRootView(){
		
		return rootView;
	}
	
	public void initData(){
		
	}
	
	/**
	 * 子类覆盖此方法，完成界面的跳转
	 * @param pageIndex
	 */
	public void setSelectedPage(int pageIndex){
		
	}
}
