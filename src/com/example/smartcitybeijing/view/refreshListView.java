package com.example.smartcitybeijing.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.smartcitybeijing.R;
import com.example.smartcitybeijing.domain.NewCenterJsonBean;
import com.example.smartcitybeijing.utils.PrintLog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class refreshListView extends ListView {

	private LinearLayout ll_rootView;
	private LinearLayout ll_refreshHeadView;
	private LinearLayout ll_footView;
	private int mRefreshHeadViewHeight;
	private int mFootViewHeight;
	
	private static final int DROP_DOWN_REFRESH_STATE=1;//下拉刷新
	private static final int RELEASE_STATE=2;//松开刷新
	private static final int RELEASING_STATE=3;//正在刷新
	
	private int refreshState=DROP_DOWN_REFRESH_STATE;//初始化为下拉刷新状态
	
	
	float downY=-1;
	private View m_lunBoView;
	private ImageView iv_refreshHeadArrow;
	private ProgressBar pb_refreshHeadProcess;
	private TextView tv_refreshHeadDesc;
	private TextView tv_refreshHeadTime;
	private RotateAnimation ra_up;
	private RotateAnimation ra_down;

	
	private boolean ifLoadintMoreData=false;//判断是否是加载更多
	
	public refreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		initHeadRefreshView();
		
		initFootRefreshView();
		
		initAnimation();
		
		initEvent();
	}
	
	private void initEvent() {
		// 监听是否滑动到最后一条数据
		this.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// 静止状态
				if (scrollState==OnScrollListener.SCROLL_STATE_IDLE) {
					//判断是否滑动到最后一条数据，并且不是处于加载更多数据的状态
					if (getLastVisiblePosition()==getAdapter().getCount()-1 && !ifLoadintMoreData) {
						
						//正在加载更多数据改为true
						ifLoadintMoreData=true;
						
						
						//显示刷新界面
						ll_footView.setPadding(0, 0, 0, 0);
						
						//设置显示界面下拉刷新
						setSelection(getAdapter().getCount());
						
						if (mOnRefreshDataListerner!=null) {
							
							//加载更多数据
							mOnRefreshDataListerner.loadingMoreData();
						}
					}
				}
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	/**
	 * 初始化动画
	 */
	private void initAnimation() {
		// 箭头朝上翻转动画
		
		ra_up = new RotateAnimation(0, -180
									, Animation.RELATIVE_TO_SELF, 0.5f
									, Animation.RELATIVE_TO_SELF, 0.5f);
		ra_up.setDuration(500);
		ra_up.setFillAfter(true);//设置动画结束位置
		
		//箭头向下翻转的动画
		
		ra_down = new RotateAnimation(-180, -360
									, Animation.RELATIVE_TO_SELF, 0.5f
									, Animation.RELATIVE_TO_SELF, 0.5f);
		ra_down.setDuration(500);
		ra_down.setFillAfter(true);//设置动画结束的位置
		
	}


	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// 拖动事件的处理
		
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN://按下
			
			//获取按下的屏幕坐标
			downY = ev.getY();
			
			break;
		
		case MotionEvent.ACTION_MOVE://拖动
			
			//如果下拉刷新view处于正在刷新状态，自己消费掉事件
			if (refreshState==RELEASING_STATE) {
				return true;
			}
			
			
			//轮播图没有完全显示
			if (!ifLunBoViewDisplay()) {
				//跳出switch语句
				break;
			}
			
			if (downY==-1) {
				downY=ev.getY();
			}
			
			float moveY = ev.getY();
			//获得滑动坐标差
			float dx=moveY-downY;
			//listview显示第一个数据，拖出下拉刷新的view，从上往下拖动
			if (getFirstVisiblePosition()==0 && dx>0) {
				/*//显示刷新view
				PrintLog.printLog("显示下拉刷新");
				//处理状态,求出隐藏刷新view的高度
				float hiddenHeight = -mRefreshHeadViewHeight+dx;
				//设置下拉刷新listview的view界面
				ll_refreshHeadView.setPadding(0, (int) hiddenHeight, 0, 0);*/
				float hiddenHeight = -mRefreshHeadViewHeight+dx;

				if (hiddenHeight>=0 && refreshState!=RELEASE_STATE) {
					
					//如果隐藏刷新view高度大于0并且目前不是松开释放状态
					refreshState=RELEASE_STATE;
					
					//处理下拉刷新的状态
					processRefreshState();
				
				}else if (hiddenHeight<0 && refreshState!=DROP_DOWN_REFRESH_STATE) {
				
					//如果隐藏刷新view高度小于0冰球不是下拉刷新的状态
					refreshState=DROP_DOWN_REFRESH_STATE;
					
					
					
					//处理下拉刷新的状态
					processRefreshState();
				}
				

				//设置刷新界面view的位置
				ll_refreshHeadView.setPadding(0, (int) hiddenHeight, 0, 0);
				return true;
			}
			break;
			
		case MotionEvent.ACTION_UP://抬起
			//判断状态
			if (refreshState==DROP_DOWN_REFRESH_STATE) {
				//如果是下拉刷新状态,隐藏刷新界面view
				ll_refreshHeadView.setPadding(0, -mRefreshHeadViewHeight, 0, 0);
			}else if (refreshState==RELEASE_STATE) {
				
				//改变下拉刷新界面为正在刷新界面状态
				refreshState=RELEASING_STATE;
				
				//处理刷新界面状态逻辑数据
				processRefreshState();
				
				if (mOnRefreshDataListerner!=null) {
					//开始刷新数据
					mOnRefreshDataListerner.refresh();
				}
				
				//如果是松开刷新状态，停留在屏幕最上面
				ll_refreshHeadView.setPadding(0, 0, 0, 0);
			}
			//下拉刷新
			
			
			break;
		default:
			break;
		}
		
		return super.onTouchEvent(ev);
	}
	
	
	public void updateRefreshViewState(){
		if (ifLoadintMoreData) {
			//如果是加载更多
			ll_footView.setPadding(0, -mFootViewHeight, 0, 0);
			ifLoadintMoreData=false;
		}else {
			//不是加载更多，上拉刷新
			updateRefreshState();
		}
	}
	
	
	//更新刷新状态
	public void updateRefreshState(){
		//改变状态,下拉刷新
		refreshState=DROP_DOWN_REFRESH_STATE;
		//显示箭头
		iv_refreshHeadArrow.setVisibility(View.VISIBLE);
		//隐藏正在刷新进度条
		pb_refreshHeadProcess.setVisibility(View.GONE);
		//改变文字
		tv_refreshHeadDesc.setText("下拉刷新");
		//设置时间
		tv_refreshHeadTime.setText(getCurrentTime());
		//隐藏头部刷新界面
		ll_refreshHeadView.setPadding(0, -mRefreshHeadViewHeight, 0, 0);
	}
	
	/**
	 * 获取当前的时间
	 * @return
	 */
	public String getCurrentTime(){
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}
	
	
		public void addLunBoView(View view){
		m_lunBoView = view;
		ll_rootView.addView(view);
	}

	/**
	 * 新闻中心轮播图是否完全显示
	 * @return
	 */
	public boolean ifLunBoViewDisplay(){
		
		int[] locatonScreen=new int[2];
		
		//获取listview在屏幕中的坐标位置
		this.getLocationInWindow(locatonScreen);
		//获取listview在屏幕中y坐标的位置
		int lvScreenY = locatonScreen[1];
		
		//获取轮播图在屏幕中的位置
		m_lunBoView.getLocationInWindow(locatonScreen);
		//获取轮播图在屏幕中的y坐标位置
		int lunBoViewY = locatonScreen[1];
		
		//如果轮播图片比listview的屏幕坐标位置大
		if (lunBoViewY>=lvScreenY) {
			
			return true;
		
		}
		
		return false;
	}
	
	
	//加载更多数据接口回调
	private OnRefreshDataListerner mOnRefreshDataListerner;
	
	public void setOnRefreshDataListerner(OnRefreshDataListerner refreshDataListerner){
		mOnRefreshDataListerner=refreshDataListerner;
	}
	
	public interface OnRefreshDataListerner{
		
		//正在加载更多数据
		void refresh();
		
		void loadingMoreData();
	}
	
	
	
	/*
	 * 处理下拉刷新事件的逻辑
	 */
	private void processRefreshState(){
		switch (refreshState) {
		case DROP_DOWN_REFRESH_STATE://下拉刷新
			
			PrintLog.printLog("下拉刷新.....");
			iv_refreshHeadArrow.setAnimation(ra_down);
			tv_refreshHeadDesc.setText("下拉刷新");
			break;

		case RELEASE_STATE://松开刷新
			
			PrintLog.printLog("松开刷新.....");
			iv_refreshHeadArrow.setAnimation(ra_up);
			tv_refreshHeadDesc.setText("松开刷新");
			break;
	
		case RELEASING_STATE://正在刷新
			PrintLog.printLog("正在刷新.....");
			
			//清除动画
			iv_refreshHeadArrow.clearAnimation();
			iv_refreshHeadArrow.setVisibility(View.GONE);
			pb_refreshHeadProcess.setVisibility(View.VISIBLE);
			tv_refreshHeadDesc.setText("正在刷新");
			
			break;
		default:
			break;
		}
	}
	
	
	
	/**
	 * 初始化尾部刷新view
	 */
	private void initFootRefreshView() {
		
		ll_footView=(LinearLayout) View.inflate(getContext(), R.layout.view_dorp_up_refresh_foot, null);
		
		ll_footView.measure(0, 0);
		mFootViewHeight = ll_footView.getMeasuredHeight();
		
		//隐藏加载更多刷新界面
		ll_footView.setPadding(0, -mFootViewHeight, 0, 0);
		
		addFooterView(ll_footView);
	
	}

	/**
	 * 初始化头部刷新view
	 */
	private void initHeadRefreshView() {
		
		ll_rootView = (LinearLayout) View.inflate(getContext(), R.layout.view_dorp_down_refresh_head, null);
	
		ll_refreshHeadView = (LinearLayout) ll_rootView.findViewById(R.id.ll_custom_refresh_view);
	
		//获取刷新头部view子组件
		
		iv_refreshHeadArrow = (ImageView) ll_refreshHeadView.findViewById(R.id.iv_drop_down_refresh_arrow);
		pb_refreshHeadProcess = (ProgressBar) ll_refreshHeadView.findViewById(R.id.pb_drop_down_refresh_process);
		tv_refreshHeadDesc = (TextView) ll_refreshHeadView.findViewById(R.id.tv_drop_down_refresh_desc);
		tv_refreshHeadTime = (TextView) ll_refreshHeadView.findViewById(R.id.tv_drop_down_refresh_time);
		
		
		
		ll_refreshHeadView.measure(0, 0);
		mRefreshHeadViewHeight = ll_refreshHeadView.getMeasuredHeight();
		
		//隐藏下拉刷新界面
		ll_refreshHeadView.setPadding(0, -mRefreshHeadViewHeight, 0, 0);
		
		
		addHeaderView(ll_rootView);
	}

	public refreshListView(Context context) {
		this(context,null);
	}
		
}
