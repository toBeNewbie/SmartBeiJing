package com.example.smartcitybeijing.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPage extends LazyViewPager {

	public NoScrollViewPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public NoScrollViewPage(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
