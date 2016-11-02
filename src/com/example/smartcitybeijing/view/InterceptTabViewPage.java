package com.example.smartcitybeijing.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.viewpagerindicator.TabPageIndicator;

public class InterceptTabViewPage extends TabPageIndicator {

	public InterceptTabViewPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public InterceptTabViewPage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		//申请父控件不拦截
		getParent().requestDisallowInterceptTouchEvent(true);
		return super.dispatchTouchEvent(ev);
	}
	
}








