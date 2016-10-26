package com.example.smartcitybeijing.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.example.smartcitybeijing.R;
import com.example.smartcitybeijing.utils.DensityUtil;

public class GuidActivity extends Activity {
	
	
	
	private ViewPager vp;
	
	private int[] page_id=new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
	
	private List<ImageView> mIv_images=new ArrayList<ImageView>();

	private MyViewPadapter myViewPageAdapter;

	private LinearLayout ll_grayPointers;

	private View v_redPointers;
	
	private int mPointersWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();
		
		initData();
		
		initEvent();
	}

	private void initEvent() {
		
		v_redPointers.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			


			@Override
			public void onGlobalLayout() {
				
			mPointersWidth = ll_grayPointers.getChildAt(1).getLeft()-ll_grayPointers.getChildAt(0).getLeft();
			
			//停止观察
			v_redPointers.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			
			}
		});

		vp.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				android.widget.FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) v_redPointers.getLayoutParams();
				layoutParams.leftMargin=Math.round(mPointersWidth*(position+positionOffset));
				v_redPointers.setLayoutParams(layoutParams);
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
	}

	private void initData() {
		
		for (int i = 0; i < page_id.length; i++) {
			ImageView imageView=new ImageView(this);
			imageView.setImageResource(page_id[i]);
			imageView.setScaleType(ScaleType.FIT_XY);
			
			mIv_images.add(imageView);
			
			View v_pointers=new View(this);
			v_pointers.setBackgroundResource(R.drawable.shape_gray_pointer);
			
			//将设备像素转换为普通像素
			int dis = DensityUtil.dip2px(this,10);
			
			LayoutParams lp =new LayoutParams(dis, dis);
			if (i!=0) {
				//不是第一个点，设置左边距
				lp.leftMargin=dis;
			}
			v_pointers.setLayoutParams(lp);
			//添加到容器中
			ll_grayPointers.addView(v_pointers);
		}
		
		
		myViewPageAdapter = new MyViewPadapter();
		vp.setAdapter(myViewPageAdapter);
		
	}

	private class MyViewPadapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mIv_images.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			View mView = mIv_images.get(position);
			container.addView(mView);
			return mView;
		}

	

		
		
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_guide);
		//获取viewPager组件
		vp = (ViewPager) findViewById(R.id.vp_setting_activity);
		
		ll_grayPointers = (LinearLayout) findViewById(R.id.ll_guide_gray_pointers);
	
		v_redPointers = findViewById(R.id.v_guide_red_pointers);
	}
}
