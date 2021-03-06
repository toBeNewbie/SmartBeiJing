package com.example.smartcitybeijing.pages;

import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcitybeijing.R;
import com.example.smartcitybeijing.activity.HomeActivity;
import com.example.smartcitybeijing.domain.NewCenterJsonBean;
import com.example.smartcitybeijing.newscenterpages.BaseNewsCenterPage;
import com.example.smartcitybeijing.newscenterpages.NewsCenterPageItem_Interactivie;
import com.example.smartcitybeijing.newscenterpages.NewsCenterPageItem_News;
import com.example.smartcitybeijing.newscenterpages.NewsCenterPageItem_Photos;
import com.example.smartcitybeijing.newscenterpages.NewsCenterPageItem_Topic;
import com.example.smartcitybeijing.utils.PrintLog;
import com.example.smartcitybeijing.utils.myConstantValue;
import com.example.smartcitybeijing.utils.splashUtils;
import com.example.smartcitybeijing.view.LeftFragment;
import com.example.smartcitybeijing.view.LeftFragment.OnleftMenuChangeListener;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;


public class NewsPage extends BasePages {

	
	private List<BaseNewsCenterPage> mBaseNewsCenterPages=new ArrayList<BaseNewsCenterPage>();
	
	//封装json数据
	private NewCenterJsonBean newCenterJsonBean;
	
	
	public NewsPage(HomeActivity context) {
		
		super(context);
		// TODO Auto-generated constructor stub
		initLocalDatas();
		 
	}

	@Override
	public void initLocalDatas() {
		// TODO Auto-generated method stub
		//获取本地缓存数据
		String localJsonDatas = splashUtils.getString(mContext, myConstantValue.LOCAL_NEWS_DATAS, null);
		
		if (!TextUtils.isEmpty(localJsonDatas)) {
			//解析json数据
			newCenterJsonBean = parseJsonData(localJsonDatas);
			
			//处理本地缓存的数据
			parseJsonFinish(newCenterJsonBean);
		}
		super.initLocalDatas();
	}
	
	@Override
	public void initData() {
		
		//动态获取来自服务器的数据
		//1.请求URL
		String dataUrl=mContext.getResources().getString(R.string.news_center_url);
		//从网络请求数据
		getDataFromNet(dataUrl);
	}
		
	
	@Override
	public void setSelectedPage(int pageIndex) {
		PrintLog.printLog("页面显示"+pageIndex);
		//显示
		displayMenuItemPage(pageIndex);
		
		super.setSelectedPage(pageIndex);
	}
	
	
	
	/*
	 * 显示左侧菜单item相关的页面
	 */
	private void displayMenuItemPage(int pageIndex) {
		// TODO Auto-generated method stub
		viewNewsItemPages(pageIndex);
	}
	
	private void getDataFromNet(String dataUrl) {
		// TODO Auto-generated method stub
		HttpUtils httpUtils=new HttpUtils();
		httpUtils.send(HttpMethod.GET, dataUrl, new RequestCallBack<String>() {
			
			
			
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				//2.读取json数据
				String jsonStr = responseInfo.result;
				
				
				//链接网络成功，储存本地缓存
				splashUtils.putString(mContext, myConstantValue.LOCAL_NEWS_DATAS, jsonStr);
				
				newCenterJsonBean = parseJsonData(jsonStr);
				
				//4.处理数据，显示在左侧菜单
				parseJsonFinish(newCenterJsonBean);
			}
			
			@Override
			public void onFailure(HttpException error, String msg) {
				
				// 网络请求失败
				Toast.makeText(mContext, "网络请求失败", 0).show();
				
				
				
			}
		});
	}
	
	/**
	 * 显示获得的数据
	 * @param newCenterJsonBean
	 */
	protected void parseJsonFinish(NewCenterJsonBean newCenterJsonBean) {
		
		PrintLog.printLog("请求数据。。。。"+newCenterJsonBean.data.get(0).children.get(0).title);
		
		//设置左侧菜单的逻辑数据
		setLeftMenuDatas(newCenterJsonBean);
		
		//设置四个新闻的页面
		initNewsItemPages(newCenterJsonBean);
		
		//显示数据,默认显示第一个页面
		viewNewsItemPages(0);
		
		
	}
	
	private void viewNewsItemPages(int i) {
		tv_title.setText(newCenterJsonBean.data.get(i).title);
		
		//移除所有的添加的view
		fl_pagesContent.removeAllViews();
		//获得要显示的view组件
		BaseNewsCenterPage baseNewsCenterPage = mBaseNewsCenterPages.get(i);
		
		//初始化页面数据
		baseNewsCenterPage.initData();
		
		//判断显示的页面是否是组图，处理标题菜单按钮显示
		if (baseNewsCenterPage instanceof NewsCenterPageItem_Photos) {
			//如果是组图页面,显示组图按钮
			iv_showGroutViewDatas.setVisibility(View.VISIBLE);
			
			//给iv_showGroutViewDatas设置标记存储页面*********
			iv_showGroutViewDatas.setTag(baseNewsCenterPage);
			
		}else {
			
			//隐藏组图按钮
			iv_showGroutViewDatas.setVisibility(View.GONE);
		
		}
		
		View view = baseNewsCenterPage.getRootView();
		
		//添加到帧布局中
		fl_pagesContent.addView(view);
		
	}
	
	private void initNewsItemPages(NewCenterJsonBean newCenterJsonBean) {
		
		//刷新数据，清空集合
		mBaseNewsCenterPages.clear();
		
		//创建四个新闻页面
		for (NewCenterJsonBean.Data data : newCenterJsonBean.data) {
			int type = Integer.parseInt(data.type);
			
			switch (type) {
			case 1://新闻
				mBaseNewsCenterPages.add(new NewsCenterPageItem_News(mContext,newCenterJsonBean.data.get(0).children));
				break;
				
			case 10://专题
				mBaseNewsCenterPages.add(new NewsCenterPageItem_Topic(mContext));
				break;
				
			case 2://组图
				mBaseNewsCenterPages.add(new NewsCenterPageItem_Photos(mContext));
				break;
				
			case 3://互动
				mBaseNewsCenterPages.add(new NewsCenterPageItem_Interactivie(mContext));
				break;
			default:
				break;
			}
		}
	}
	
	public void setLeftMenuDatas(NewCenterJsonBean newCenterJsonBean) {
		//获得左侧菜单的fragment
		LeftFragment leftFragment = mContext.getLeftFragment();
		//设置左侧菜单数据
		leftFragment.setLeftMenuData(newCenterJsonBean.data);
		
		leftFragment.setOnleftMenuChangeListener(new OnleftMenuChangeListener() {
			
			@Override
			public void setSelectedPage(int pageIndex) {
				// TODO Auto-generated method stub
				PrintLog.printLog("call back ......页面显示"+pageIndex);
				displayMenuItemPage(pageIndex);
			}
		});
	}
	
	protected NewCenterJsonBean parseJsonData(String jsonStr) {
		// TODO Auto-generated method stub
		Gson mGson=new Gson();
		
		//获得解析的json数据
		NewCenterJsonBean jsonData = mGson.fromJson(jsonStr, NewCenterJsonBean.class);
		
		return jsonData;
	}
	
}
		
		
	
		
		
		
		

	
