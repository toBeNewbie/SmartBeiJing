package com.example.smartcitybeijing.view;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.smartcitybeijing.R;
import com.example.smartcitybeijing.pages.BasePages;
import com.example.smartcitybeijing.pages.HomePage;
import com.example.smartcitybeijing.pages.NewsPage;
import com.example.smartcitybeijing.pages.SettiongCenterPage;
import com.example.smartcitybeijing.pages.SmartServicePage;
import com.example.smartcitybeijing.pages.govaffairsPage;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainFragment extends BaseFragment {

	@ViewInject(R.id.vp_main_fragment)
	private LazyViewPager mViewPager;
	
	@ViewInject(R.id.rg_main_item)
	private RadioGroup mRadioGroup;
	
	private List<BasePages> mPageViews=new ArrayList<BasePages>();
	
	@Override
	public View initView() {
		// TODO Auto-generated method stub
		
		View rootView = View.inflate(mContext, R.layout.sliding_fragment_contents_view, null);
		
		ViewUtils.inject(this,rootView);
		
		return rootView;
	}
	
	
	@Override
	public void initData() {
		
		mRadioGroup.check(R.id.rb_home_activity);
		
		//初始化滑动界面
		switchPageItem();
		
		//添加页面
		mPageViews.add(new HomePage(mContext));
		mPageViews.add(new NewsPage(mContext));
		mPageViews.add(new SmartServicePage(mContext));
		mPageViews.add(new govaffairsPage(mContext));
		mPageViews.add(new SettiongCenterPage(mContext));
		
		
		MyAdapter myAdapter=new MyAdapter();
		mViewPager.setAdapter(myAdapter);
		
		super.initData();
	}
	
	private class MyAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mPageViews.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view==object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BasePages basePages = mPageViews.get(position);
			View view = basePages.getRootView();
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View) object);
		}
		
		
		
	}
	
	private int selectedIndex;
	@Override
	public void initEvent() {
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rb_home_activity://首页
					selectedIndex=0;
					break;
				case R.id.rb_news_center_activity://新闻中心
					selectedIndex=1;
					break;
					
				case R.id.rb_smart_service_activity://智慧服务
					selectedIndex=2;
					break;
				case R.id.rb_govaffairs_activity://政务
					selectedIndex=3;
					break;
				case R.id.rb_setting_activity://设置中心
					selectedIndex=4;
					break;
				default:
					break;
				}
				
				switchPageItem();
			}
		});
		super.initEvent();
	}

	/*
	 * 获得选择的page
	 */
	public BasePages getSelectedPage(){
		
		return mPageViews.get(selectedIndex);
	
	}
	
	
	public void switchPageItem(){
		
		if (selectedIndex==0||selectedIndex==4) {
			mContext.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}else {
			mContext.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}
		mViewPager.setCurrentItem(selectedIndex);
		
	}


	
}
