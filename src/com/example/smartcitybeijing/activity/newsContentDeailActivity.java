package com.example.smartcitybeijing.activity;

import com.example.smartcitybeijing.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * 
 * @author Administrator
 *@company Newbie
 *@date 2016-11-6
 *@des 具体新闻信息的详细界面
 */
public class newsContentDeailActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_news_content_deail);
	}
}
