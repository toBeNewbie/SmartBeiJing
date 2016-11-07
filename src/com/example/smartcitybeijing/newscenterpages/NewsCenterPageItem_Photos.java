package com.example.smartcitybeijing.newscenterpages;

import java.util.List;

import com.example.smartcitybeijing.R;
import com.example.smartcitybeijing.activity.HomeActivity;
import com.example.smartcitybeijing.domain.GroupPhotoData;
import com.example.smartcitybeijing.domain.GroupPhotoData.Data.News;
import com.example.smartcitybeijing.utils.myConstantValue;
import com.example.smartcitybeijing.utils.splashUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewsCenterPageItem_Photos extends BaseNewsCenterPage {

	@ViewInject(R.id.lv_group_photo_listview)
	private ListView lv_showDatas;
	
	@ViewInject(R.id.gv_group_photo_gridview)
	private GridView gv_showDatas;

	private MyAdapter mAdapter;

	private List<News> mGroupPhotoNews;

	private BitmapUtils mBitmapUtils;
	
	public NewsCenterPageItem_Photos(HomeActivity mContext) {
		super(mContext);
		mBitmapUtils = new BitmapUtils(mContext);
	}

	
	@Override
	public void initData() {
		// 本地缓存
		
		//网络数据
		String groupPhotoUrl = mContext.getResources().getString(R.string.group_photo_url);
		//获取缓存数据
		String localDatas = splashUtils.getString(mContext, myConstantValue.GROUP_PHOTO_DATA_JSON, "");
		if (!TextUtils.isEmpty(localDatas)) {
			//有缓存数据
			GroupPhotoData jsonDatas = parseJsonDatas(localDatas);
			//处理数据
			processGroutPhotoDatas(jsonDatas);
		}
		
		
		//从网络获得数据
		getGroupPhotoDataFromNet(groupPhotoUrl);
		
		super.initData();
	}
	
	
	private void getGroupPhotoDataFromNet(String groupPhotoUrl) {
		
		HttpUtils mHttpUtils=new HttpUtils();
		mHttpUtils.send(HttpMethod.GET, groupPhotoUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				//获取json数据
				String jsonData = responseInfo.result;
				
				//保存json数据到本地
				splashUtils.putString(mContext, myConstantValue.GROUP_PHOTO_DATA_JSON, jsonData);
				
				//解析json数据
				GroupPhotoData mGroupPhotoDatas = parseJsonDatas(jsonData);
				
				//处理json数据
				processGroutPhotoDatas(mGroupPhotoDatas);
				
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				// 网络请求失败
				Toast.makeText(mContext, "网络获取数据失败", 1).show();
			}
		});
		
	}


	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			if (mGroupPhotoNews==null) {
				
				return 0;
			}else {
				
				return mGroupPhotoNews.size();
			}
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mGroupPhotoNews.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder mViewHolder=null;
			if (convertView==null) {
				convertView = View.inflate(mContext, R.layout.item_group_photo_view, null);
				mViewHolder=new ViewHolder();
				
				mViewHolder.iv_groupPhotoPic=(ImageView) convertView.findViewById(R.id.iv_item_group_photo_picture);
				mViewHolder.tv_groupPhotoDesc=(TextView) convertView.findViewById(R.id.tv_item_group_photo_desc);
				
				convertView.setTag(mViewHolder);
			}else {
				mViewHolder = (ViewHolder) convertView.getTag();
			}
			
			//数据
			News mNews = mGroupPhotoNews.get(position);
			mViewHolder.tv_groupPhotoDesc.setText(mNews.title);
			mBitmapUtils.display(mViewHolder.iv_groupPhotoPic,mNews.listimage);
			
			return convertView;
		}
		
	}
	
	private class ViewHolder{
		
		ImageView iv_groupPhotoPic;
		
		TextView tv_groupPhotoDesc;
	
	} 
	
	
	private boolean ifShowGroupListView=true;//组图默认显示listview页面
	
	public void switchGroupPhotoView(ImageView v){
		ifShowGroupListView=!ifShowGroupListView;
		if (ifShowGroupListView) {
			//显示listview页面
			lv_showDatas.setVisibility(View.VISIBLE);
			gv_showDatas.setVisibility(View.GONE);
			
			//改变组图页面按钮图标
			v.setImageResource(R.drawable.icon_pic_grid_type);
			
		}else {
			//显示gridview页面
			lv_showDatas.setVisibility(View.GONE);
			gv_showDatas.setVisibility(View.VISIBLE);
			
			v.setImageResource(R.drawable.icon_pic_list_type);
		}
	}
	
	
	
	/**
	 * 处理数据       使gridview和listview显示数据
	 * 
	 * @param mGroupPhotoDatas
	 */
	protected void processGroutPhotoDatas(GroupPhotoData mGroupPhotoDatas) {
		
		//获取新闻数据
		mGroupPhotoNews = mGroupPhotoDatas.data.news;
		
		if (mAdapter==null) {
			mAdapter = new MyAdapter();
			gv_showDatas.setAdapter(mAdapter);
			lv_showDatas.setAdapter(mAdapter);
		}else {
			mAdapter.notifyDataSetChanged();
		}
		
	}


	/**
	 * 解析json数据 组图
	 * @param jsonData
	 */
	protected GroupPhotoData parseJsonDatas(String jsonData) {
		
		Gson mGson=new Gson();
		
		GroupPhotoData mPhotoDatas = mGson.fromJson(jsonData, GroupPhotoData.class);
		
		return mPhotoDatas;
	}


	@Override
	public View initView() {
		
		//获取组图界面的布局文件
		View rootView = View.inflate(mContext, R.layout.group_photo_view, null);
		
		//注入
		ViewUtils.inject(this, rootView);
	
		return rootView;
	}

}
