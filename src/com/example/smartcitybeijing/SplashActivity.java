package com.example.smartcitybeijing;

import com.example.smartcitybeijing.activity.GuidActivity;
import com.example.smartcitybeijing.activity.HomeActivity;
import com.example.smartcitybeijing.utils.PrintLog;
import com.example.smartcitybeijing.utils.myConstantValue;
import com.example.smartcitybeijing.utils.splashUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

public class SplashActivity extends Activity {

	private RelativeLayout rl_splash;
	private AnimationSet animationSet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initView();
		
		initAnimation();
		
		initEvent();
	
	}

	private void initEvent() {
		animationSet.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// 动画结束，是否进入设置界面
				Boolean inSetFinish = splashUtils.getbBoolean(getApplicationContext(), myConstantValue.IF_SETUP_FINISH, false);
				if (inSetFinish) {
					
					//进入主界面
					Intent homeIntent=new Intent(SplashActivity.this, HomeActivity.class);
					startActivity(homeIntent);
					//关闭splash界面
					finish();
					PrintLog.printLog("进入设置界面");
				}else {
					//进入设置向导界面
					Intent guideIntent=new Intent(SplashActivity.this, GuidActivity.class);
					startActivity(guideIntent);
					
					//关闭splash界面
					finish();
				}
			}
		});
	}

	private void initAnimation() {
		animationSet = new AnimationSet(false);
		//旋转动画
		RotateAnimation ra=new RotateAnimation(0, 360, 
												Animation.RELATIVE_TO_SELF, 0.5f, 
												Animation.RELATIVE_TO_SELF, 0.5f);
		ra.setDuration(2000);
		//比例动画
		ScaleAnimation sa=new ScaleAnimation(0, 1, 0, 1,  
											Animation.RELATIVE_TO_SELF, 0.5f, 
											Animation.RELATIVE_TO_SELF, 0.5f);
		sa.setDuration(2000);
		//透明动画
		AlphaAnimation aa=new AlphaAnimation(0, 1);
		aa.setDuration(2000);
		animationSet.addAnimation(aa);
		animationSet.addAnimation(sa);
		animationSet.addAnimation(ra);
		animationSet.setDuration(2000);
		
		rl_splash.startAnimation(animationSet);
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_splash);
		rl_splash = (RelativeLayout) findViewById(R.id.rl_splash_activity);
	}


	
}
