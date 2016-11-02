package com.example.smartcitybeijing.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 
 * @author Administrator
 *@company Newbie
 *@date 2016-11-1
 *@des 申请父类控件不拦截的ViewPage
 */
public class InterceptViewPage extends ViewPager {

	private float downX;
	private float downY;


	public InterceptViewPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public InterceptViewPage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	//申请父类不拦截
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		//申请父类不拦截事件
		getParent().requestDisallowInterceptTouchEvent(true);
		
		//获取当前页面的索引值
		//Move
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//按下
			downX = getX();
			downY = getY();
			
			break;
		case MotionEvent.ACTION_MOVE:
			//移动
			float moveX = getX();
			float moveY = getY();
			
			float dx = moveX-downX;
			float dy = moveY-downY;
			
			//判断是横向还是纵向
			if (Math.round(dx)>Math.round(dy)) {
				//横向
				//不是最后的page,并且从右往左滑
				if (getCurrentItem()<getChildCount() && (dx<0)) {
					//不拦截
					getParent().requestDisallowInterceptTouchEvent(true);
					
				}else if (getCurrentItem()!=0 && dx>0) {//不是第一个page，并且从左往右滑
					//不拦截
				
					getParent().requestDisallowInterceptTouchEvent(true);
				}else {
					//拦截
					getParent().requestDisallowInterceptTouchEvent(false);
				}
			}else {
				//纵向
				getParent().requestDisallowInterceptTouchEvent(false);//拦截
			}
			break;
		
		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

}
