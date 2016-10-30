package com.example.smartcitybeijing.itemtabnewspage;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcitybeijing.R;
import com.example.smartcitybeijing.activity.HomeActivity;
import com.example.smartcitybeijing.domain.NewCenterJsonBean.Data.Children;
import com.example.smartcitybeijing.domain.NewsDetailData;
import com.example.smartcitybeijing.domain.NewsDetailData.Data.TopNews;
import com.example.smartcitybeijing.utils.PrintLog;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 页面对应的页面
 * 
 * @author Administrator
 * 
 */
public class BaseItemTabNewPages {

	@ViewInject(R.id.vp_item_news_center_pages)
	private ViewPager vp_lunbos;

	@ViewInject(R.id.tv_item_news_center_desc)
	private TextView tv_desc;

	@ViewInject(R.id.ll_guide_gray_pointers)
	private LinearLayout ll_points;

	@ViewInject(R.id.lv_item_news_center_mess)
	private ListView lv_newsData;

	private HomeActivity mContext;
	private View rootView;
	private Children mChildren;

	private List<TopNews> mTopnews;

	private VPAdapter mVpAdapter;

	private BitmapUtils mBitmapUtils;

	public BaseItemTabNewPages(HomeActivity context, Children children) {
		this.mContext = context;
		this.mChildren = children;

		//加载图片的工具
		
		mBitmapUtils = new BitmapUtils(mContext);
		
		initView();

		initData();

	}

	private void initData() {
		// 初始化数据

		// 网络获取
		String newsDetailUrl = mContext.getResources().getString(
				R.string.base_url)
				+ mChildren.url;
		getDataFromNet(newsDetailUrl);

	}

	private void getDataFromNet(String newsDetailUrl) {
		// 网络获取数据
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, newsDetailUrl,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// 1.获取json数据
						String jsonData = responseInfo.result;
						// 2. 解析json数据
						NewsDetailData detailData = parseData(jsonData);
						// 3. 处理json数据
						processData(detailData);

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// 网络请求失败
						Toast.makeText(mContext, "网络请求数据失败", 1).show();

					}
				});

	}

	/**
	 * 处理数据
	 * 
	 * @param detailData
	 */
	protected void processData(NewsDetailData detailData) {
		// TODO Auto-generated method stub

		// 轮播图的数据

		mTopnews = detailData.data.topnews;
		setLunBoData();
	}

	private void setLunBoData() {
		if (mVpAdapter == null) {
			mVpAdapter = new VPAdapter();
			vp_lunbos.setAdapter(mVpAdapter);
		} else {
			mVpAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * ViewPager 的适配器
	 * 
	 * @author Administrator
	 * 
	 */
	private class VPAdapter extends PagerAdapter {

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			
			PrintLog.printLog("新闻中心初始化界面");
			
			//图片
			ImageView iv = new ImageView(mContext);
			iv.setScaleType(ScaleType.FIT_XY);
			
			
			// 数据
			TopNews topNews = mTopnews.get(position);
			String picUrl = topNews.topimage;
			
			//设置显示数据 
			mBitmapUtils.display(iv, picUrl);
			
			container.addView(iv);
			return iv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (mTopnews != null) {
				return mTopnews.size();
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}

	}

	/**
	 * 解析json数据
	 * 
	 * @param jsonData
	 */
	protected NewsDetailData parseData(String jsonData) {
		// 1. gson
		Gson gson = new Gson();
		// 2. class
		NewsDetailData detailData = gson.fromJson(jsonData,
				NewsDetailData.class);
		// 3. result
		return detailData;

	}

	public View getRootView() {
		return rootView;
	}

	private void initView() {

		rootView = View.inflate(mContext, R.layout.tab_news_page_item_news, null);

		ViewUtils.inject(this, rootView);

	}

}
