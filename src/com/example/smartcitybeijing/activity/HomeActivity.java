package com.example.smartcitybeijing.activity;

import com.example.smartcitybeijing.R;

import android.app.Activity;
import android.os.Bundle;

public class HomeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_home);
	}
}
