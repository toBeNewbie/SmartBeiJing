package com.example.smartcitybeijing.newscenterpages;

import com.example.smartcitybeijing.activity.HomeActivity;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class NewsCenterPageItem_Interactivie extends BaseNewsCenterPage {

	public NewsCenterPageItem_Interactivie(HomeActivity mContext) {
		super(mContext);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView() {
		TextView tv =new TextView(mContext);
		tv.setText("新闻中心：互动的内容");
		tv.setTextSize(40);
		tv.setGravity(Gravity.CENTER);
		return tv;
	}

}
