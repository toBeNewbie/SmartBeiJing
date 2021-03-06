package com.example.smartcitybeijing.itemtabnewspage;

import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcitybeijing.R;
import com.example.smartcitybeijing.activity.HomeActivity;
import com.example.smartcitybeijing.activity.newsContentDeailActivity;
import com.example.smartcitybeijing.domain.NewCenterJsonBean.Data.Children;
import com.example.smartcitybeijing.domain.NewsDetailData;
import com.example.smartcitybeijing.domain.NewsDetailData.Data.News;
import com.example.smartcitybeijing.domain.NewsDetailData.Data.TopNews;
import com.example.smartcitybeijing.utils.DensityUtil;
import com.example.smartcitybeijing.utils.PrintLog;
import com.example.smartcitybeijing.utils.myConstantValue;
import com.example.smartcitybeijing.utils.splashUtils;
import com.example.smartcitybeijing.view.refreshListView;
import com.example.smartcitybeijing.view.refreshListView.OnRefreshDataListerner;
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
 * 新闻页面对应的页面
 * 
 * @author Administrator
 * 
 */
public class BaseItemTabNewPages {

	private NewsDetailData detailData;
	
	@ViewInject(R.id.vp_item_news_center_pages)
	private ViewPager vp_lunbos;

	@ViewInject(R.id.tv_item_news_center_desc)
	private TextView tv_desc;

	@ViewInject(R.id.ll_pointers_view_tab)
	private LinearLayout ll_points;

	@ViewInject(R.id.lv_item_news_center_mess)
	private refreshListView lv_newsData;

	MyHandle mHandler;
	
	
	private HomeActivity mContext;
	private View rootView;
	private Children mChildren;

	private List<TopNews> mTopnews;

	private VPAdapter mVpAdapter;

	private BitmapUtils mBitmapUtils;

	private List<News> mLvItemNewsDatas;

	private LVAdapter mLVAdapter;

	private View lunBoView;

	private String newsDetailUrl;
	
	private boolean ifRefreshing=false;

	private String mLoadingMoreDataUrl="";//加载更多数据的URL

	private String moreUrl;
	
	public BaseItemTabNewPages(HomeActivity context, Children children) {
		this.mContext = context;
		this.mChildren = children;

		//加载图片的工具
		
		mBitmapUtils = new BitmapUtils(mContext);
		
		initView();

		initData();

		//初始化本地数据
		initLocalDatas();
		
		initEvent();
}

	private void initEvent() {
		
		
		/**
		 * 给新闻条目添加点击事件
		 */
		lv_newsData.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 获得点击的新闻位置
				News newsDeail = mLvItemNewsDatas.get(position-1);
				//获得新闻网页的URL地址
				String newsDeailUrl = newsDeail.url;
				
				//获取新闻的ID
				String newsId = newsDeail.id;
				String newsDeailIds = splashUtils.getString(mContext, myConstantValue.HAVE_READED_NEWS_ID, null);
				if (TextUtils.isEmpty(newsDeailIds)) {
					//为空
					newsDeailIds=newsId;
				}else {
					//不为空
					newsDeailIds+=","+newsId;
				}
				
				//保存newsId
				splashUtils.putString(mContext, myConstantValue.HAVE_READED_NEWS_ID, newsDeailIds);
				
				//更新界面
				mLVAdapter.notifyDataSetChanged();
				
				
				
				//启动具体新闻界面
				Intent newsDeailIntent = new Intent(mContext, newsContentDeailActivity.class);
				newsDeailIntent.putExtra(myConstantValue.NEWS_CONTENT_DEAILS_URL, newsDeailUrl);
				
				mContext.startActivity(newsDeailIntent);
			}
		});
		
		
		//正在刷新监听器
		lv_newsData.setOnRefreshDataListerner(new OnRefreshDataListerner() {
			
			@Override
			public void refresh() {
				
				//设置状态为正在刷新状态
				ifRefreshing=true;
				
				// 更新数据
				getDataFromNet(newsDetailUrl,false);
				
				
			/*	new Thread(){
					@Override
					public void run() {
						
						//在这里处理加载更多数据的业务
						
						SystemClock.sleep(2000);
						//更新状态
						mContext.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								// 更新状态
								lv_newsData.updateRefreshState();
							}
						});
					}
				}.start();*/
			}

			@Override
			public void loadingMoreData() {
				// 加载更多数据逻辑
			
				//获得更多数据
				if (!TextUtils.isEmpty(moreUrl)) {
					//有更多数据
					System.out.println("有更多数据要加载.............");
					
					getDataFromNet(mLoadingMoreDataUrl,true);
					
//					mLoadingMoreDataUrl="";
					
				}else {
					//没有更多数据
					Toast.makeText(mContext, "没有更多数据", 1).show();
					//更新刷新view状态
					lv_newsData.updateRefreshViewState();
				}
				
			}
		});
		
		
		
		//给轮播图设置点击事件
		vp_lunbos.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// 设置点的状态
				setPointersState(position);
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	//初始化本地缓存数据
	private void initLocalDatas() {
		// TODO Auto-generated method stub
		String itemTabNewsDatas = splashUtils.getString(mContext, myConstantValue.LOCAL_ITEM_TAB_NEWS_DATAS, null);
		if (!TextUtils.isEmpty(itemTabNewsDatas)) {
			// 2. 解析json数据
			detailData = parseData(itemTabNewsDatas);
			// 3. 处理json数据
			processData(detailData);
		}
	}

	private void initData() {
		// 初始化数据

		newsDetailUrl = mContext.getResources().getString(
				R.string.base_url)
				+ mChildren.url;
		getDataFromNet(newsDetailUrl,false);

		
	}

	private void getDataFromNet(String newsDetailUrl,final boolean ifLoadingMore) {
		// 网络获取数据
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, newsDetailUrl,
				new RequestCallBack<String>() {

				

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// 1.获取json数据
						String jsonData = responseInfo.result;
						
						//本地缓存新闻中心列表数据
						splashUtils.putString(mContext, myConstantValue.LOCAL_ITEM_TAB_NEWS_DATAS, jsonData);
						
						detailData = parseData(jsonData);
						
						//不是加载更多数据
						if (!ifLoadingMore) {
							// 3. 处理json数据
							processData(detailData);
							
							if (ifRefreshing) {
								//刷新成功
								Toast.makeText(mContext, "刷新数据成功", 0).show();
								
								ifRefreshing=false;
								
								//更新下拉刷新状态处理
								lv_newsData.updateRefreshViewState();
							}
						}else {
							//加载更多数据
							//在原有的数据新闻添加新的新闻数据
							mLvItemNewsDatas.addAll(detailData.data.news);
							
PrintLog.printLog("加载更多数据");
							//通知刷新界面
							mLVAdapter.notifyDataSetChanged();
							//更新刷新状态
							lv_newsData.updateRefreshViewState();
							
							Toast.makeText(mContext, "成功加载更多数据", 1).show();
						}
						

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
		//设置轮播图的数据
		setLunBoData();

		//初始化点的数据
		initPointers();

		//设置点的状态，和描述信息
		setPointersState(0);
		
		//开始图片轮播
		startLunBo();
		
		//获得listview新闻中心数据
		mLvItemNewsDatas = detailData.data.news;
		
		//设置新闻中心listview的条目数据
		setLvNewsItemDatas();
	}

	
	
	//给新闻中心listview设置适配器
	private class LVAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			
			if (mLvItemNewsDatas!=null) {
				
				return mLvItemNewsDatas.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 数据显示
			ViewHolder mHolder=null;
			if (convertView==null) {
				
				convertView = View.inflate(mContext, R.layout.item_news_center_lv_mess, null);
				
				mHolder=new ViewHolder();
				mHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_item_news_lv_mess);
				mHolder.iv_menu=(ImageView) convertView.findViewById(R.id.iv_item_news_lv_menu);
				mHolder.tv_mess = (TextView) convertView.findViewById(R.id.tv_item_news_lv_desc);
				mHolder.tv_time=(TextView) convertView.findViewById(R.id.tv_item_news_lv_time);
				
				convertView.setTag(mHolder);
			}else {
				mHolder = (ViewHolder)convertView.getTag();
			}
			
			News mNews = mLvItemNewsDatas.get(position);
			//显示新闻图标
			mBitmapUtils.display(mHolder.iv_icon, mNews.listimage);
			
			//取出news的ID号
			String newsIds = splashUtils.getString(mContext, myConstantValue.HAVE_READED_NEWS_ID, "");
			String newId = mNews.id;
			if (newsIds.contains(newId)) {
				//包含  标记为已读新闻   标记为灰色
				mHolder.tv_mess.setTextColor(Color.GRAY);
				mHolder.tv_time.setTextColor(Color.GRAY);
			}else {
				//不包含 标记为未读新闻   标记为黑色
				mHolder.tv_mess.setTextColor(Color.BLACK);
				mHolder.tv_time.setTextColor(Color.BLACK);
			}
			
			
			mHolder.tv_mess.setText(mNews.title);
			mHolder.tv_time.setText(mNews.pubdate);
			
			//获取数据并赋值
			return convertView;
		}
		
	}  
	
	
	private class ViewHolder{
		ImageView iv_icon;
		TextView tv_mess;
		TextView tv_time;
		ImageView iv_menu;
	}
	
	//给listview新闻数据绑定适配器
	private void setLvNewsItemDatas(){
		if (mLVAdapter==null) {
			
			mLVAdapter = new LVAdapter();
			lv_newsData.setAdapter(mLVAdapter);
		
		}else {
			//刷新数据
			mLVAdapter.notifyDataSetChanged();
		}
	}
	
	
	private class MyHandle extends Handler implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// 主线程 每隔2秒执行一次
			vp_lunbos.setCurrentItem((vp_lunbos.getCurrentItem()+1)%vp_lunbos.getAdapter().getCount());
		
			postDelayed(this, 2000);//再发一个消息
		}
		
		private void startLunBo() {
			// 开始轮播
			stopLunBo();
			
			postDelayed(this, 2000);
		}
		
		
		private void stopLunBo() {
			// 停止轮播
			removeCallbacksAndMessages(null);
		}
	}
	
	/**
	 * 开始轮播图片
	 */
	private void startLunBo() {
		// TODO Auto-generated method stub
		if (mHandler==null) {
			mHandler=new MyHandle();
		}
		
		mHandler.startLunBo();
		/*//移除所有的消息，初始化本地和网络数据调用两次需清理消息在执行延时任务时
		mHandler.removeCallbacksAndMessages(null);
		
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// 主线程 每隔2秒执行一次
				vp_lunbos.setCurrentItem((vp_lunbos.getCurrentItem()+1)%vp_lunbos.getAdapter().getCount());
			
				mHandler.postDelayed(this, 2000);
			}
		}, 2000);*/
	}

	/**
	 * 设置点的数据，和轮播图片信息的描述
	 * @param position
	 * 			：轮播图片的位置
	 */
	private void setPointersState(int position) {
		// 设置轮播图片的描述信息
		tv_desc.setText(mTopnews.get(position).title);
		
		//点的可用状态设置
		for (int i = 0; i < mTopnews.size(); i++) {
			
				//获取点的组件
				View view = ll_points.getChildAt(i);
				//设置点的信息可用
				view.setEnabled(position==i);
			
		}
	}

	/**
	 * 初始化添加点的数据
	 */
	private void initPointers() {
		
		//添加点之前先清理点的数据
		ll_points.removeAllViews();
		
		for (int i = 0; i < mTopnews.size(); i++) {
			View mView = new View(mContext);
			mView.setBackgroundResource(R.drawable.item_pointers_v_selector);
			mView.setEnabled(false);
			
			//设置点数据显示的基本参数
			LayoutParams params = new LayoutParams(DensityUtil.dip2px(mContext, 5), DensityUtil.dip2px(mContext, 5));
			params.leftMargin = DensityUtil.dip2px(mContext, 8);
			
			//给view设置布局参数
			mView.setLayoutParams(params);
			
			//添加到线性布局中
			ll_points.addView(mView);
		}
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
			
			//给轮播图片添加触摸事件
			iv.setOnTouchListener(new OnTouchListener() {
				
				private float downX;
				private float downY;
				private long downTime;

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN://按下
						downX = event.getX();
						downY = event.getY();
						downTime = System.currentTimeMillis();
						//停止轮播
						mHandler.stopLunBo();
						break;
						
					case MotionEvent.ACTION_UP://松开
						float upX = event.getX();
						float upY = event.getY();
						//按下的点在5个像素之内视为点击的同一个点
						if (Math.round(upX-downX)<5 && Math.round(upY-downY)<5) {
							//获取当前时间
							long upTime = System.currentTimeMillis();
							if (upTime-downTime>500) {
								//视为点击，处理点击事件
								Toast.makeText(mContext, "你可以给点击图片添加事件", 1).show();
							}
						}
						
						//继续轮播图片
						mHandler.startLunBo();
						break;
					
					case MotionEvent.ACTION_CANCEL:
						//移动到屏幕外面，取消.则开始轮播
						mHandler.startLunBo();
						break;
					default:
						break;
					}
					
					// 自己处理消费事件
					return true;
				}
			});
			
			
			
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
		
		moreUrl = detailData.data.more;
		if (!TextUtils.isEmpty(moreUrl)) {
			
			//加载更多数据的url
			
			mLoadingMoreDataUrl=mContext.getResources().getString(R.string.base_url)+moreUrl;
			PrintLog.printLog(mLoadingMoreDataUrl+"8888888888");
		}else {
			moreUrl="";
		}
		
		// 3. result
		return detailData;

	}

	/*
	 * 返回根布局
	 */
	public View getRootView() {
		return rootView;
	}

	private void initView() {

		rootView = View.inflate(mContext, R.layout.tab_news_page_item_news, null);
		View lunBoView = View.inflate(mContext, R.layout.item_lunbo_view, null);
		
		
		ViewUtils.inject(this, rootView);
		ViewUtils.inject(this,lunBoView);
		
		//给listview添加新闻消息
		lv_newsData.addLunBoView(lunBoView);
	}

}
