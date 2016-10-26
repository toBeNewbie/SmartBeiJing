package com.example.smartcitybeijing.view;

import android.view.View;
import android.widget.TextView;

public class LeftFragment extends BaseFragment {

	@Override
	public View initView() {

		TextView textView=new TextView(mContext);
		textView.setText("LeftFragment");
		textView.setTextSize(40);
		return textView;
	}

}
