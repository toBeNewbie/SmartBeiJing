package com.example.smartcitybeijing.newscenterpages;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartcitybeijing.R;
import com.example.smartcitybeijing.activity.HomeActivity;
import com.example.smartcitybeijing.domain.NewCenterJsonBean.Data.Children;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.TabPageIndicator;

public class NewsCenterPageItem_News extends BaseNewsCenterPage {

	//页签组件
	@ViewInject(R.id.tpi_news_center_page_index)
	private TabPageIndicator mTabPageIndicator;
	
	//显示新闻条目的页面
	@ViewInject(R.id.vp_news_center_items)
	private ViewPager mViewPager;
	
	private List<Children> children;
	
	public NewsCenterPageItem_News(HomeActivity mContext, List<Children> children) {
		super(mContext);
		
		this.children=children;
		
		initData();
	}

	/**
	 * 
	 * @author Administrator
	 *@company Newbie
	 *@date 2016-10-29
	 *@des 给新闻子页面设置适配器
	 */
	public class MyAdapter extends PagerAdapter{

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			
			TextView textView=new TextView(mContext);
			Children childrenData = children.get(position);
			textView.setGravity(Gravity.CENTER);
			textView.setTextSize(30);
			textView.setText(childrenData.title);
			
			container.addView(textView);
			return textView;
		
		}
		
		//页签显示调用的方法
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return children.get(position).title;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return children.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}
		
	}
	
	
	@Override
	public View initView() {
		
		View rootView = View.inflate(mContext, R.layout.news_center_item_news_layout, null);
		//注入，获得组件
		ViewUtils.inject(this, rootView);
		return rootView;
	}
	
	
	@Override
	public void initData() {
		
		MyAdapter myAdapter=new MyAdapter();
		
		mViewPager.setAdapter(myAdapter);
		mTabPageIndicator.setViewPager(mViewPager);
		
		super.initData();
	}
	
	
	@Override
	public void initEvent() {
		
		mTabPageIndicator.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				if (position==0) {
					//页签第一个，可以滑出侧面菜单
					mContext.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				}else {
					//不可以滑出侧面菜单
					mContext.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				}
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});
		
		super.initEvent();
	}

}
