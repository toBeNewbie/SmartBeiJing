package com.example.smartcitybeijing.view;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smartcitybeijing.R;
import com.example.smartcitybeijing.domain.NewCenterJsonBean;
import com.example.smartcitybeijing.pages.BasePages;
import com.example.smartcitybeijing.utils.PrintLog;

public class LeftFragment extends BaseFragment {

	private List<NewCenterJsonBean.Data> mMenuDatas=new ArrayList<NewCenterJsonBean.Data>();
	private ListView mLv_menu;
	private MyAdapter mAdapter;
	private int selectIndex = 0;
	@Override
	public View initView() {

		mLv_menu = new ListView(mContext);
		//设置左侧菜单listview的显示参数
		
		mLv_menu.setBackgroundColor(Color.BLACK);
		mLv_menu.setDividerHeight(0);
		mLv_menu.setSelector(new ColorDrawable(Color.TRANSPARENT));
		mLv_menu.setPadding(0, 50, 0, 0);
		
		mAdapter = new MyAdapter();
		
		mLv_menu.setAdapter(mAdapter);
		
		return mLv_menu;
	}
	
	
	/**
	 * 获取左侧菜单数据的方法
	 * @param menuDatas
	 */
	public void setLeftMenuData(List<NewCenterJsonBean.Data> menuDatas){
		
		this.mMenuDatas=menuDatas;
		
		PrintLog.printLog(mMenuDatas.get(0).children.get(0).url);
		//显示左侧菜单的listview
		mAdapter.notifyDataSetChanged();
		
	}
	
	//创建左侧菜单listview的适配器
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mMenuDatas.size();
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
			// TODO Auto-generated method stub
			if (convertView==null) {
				convertView=View.inflate(mContext, R.layout.item_menu_left_lv, null);
			}
			TextView tv_title=(TextView) convertView;
			//获取数据
			String menuTitle = mMenuDatas.get(position).title;
			tv_title.setText(menuTitle);
			
			//设置菜单item被点击的状态
			if (selectIndex==position) {
				//选择的是当前item
				tv_title.setEnabled(true);
			}else {
				//选择的不是当前item
				tv_title.setEnabled(false);
			}
			
			return tv_title;
		}
		
	}
	
	/**
	 * 回调接口
	 */
	private OnleftMenuChangeListener mOnleftMenuChangeListener;
	
	public void setOnleftMenuChangeListener(OnleftMenuChangeListener onleftMenuChangeListener){
		this.mOnleftMenuChangeListener=onleftMenuChangeListener;
	}
	
	public interface OnleftMenuChangeListener{
		void setSelectedPage(int pageIndex);
	}
	//end callback
	
	
	
	
	@Override
	public void initEvent() {
		//给菜单listview设置点击事件
		
		mLv_menu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//记录左侧菜单按钮选择的下标
				selectIndex = position;
				//通知界面刷新
				mAdapter.notifyDataSetChanged();
				
				//让新闻中心页面显示：新闻、组图、互动、专题中的任一界面
				
				if (mOnleftMenuChangeListener!=null) {
					//使用回调
					mOnleftMenuChangeListener.setSelectedPage(selectIndex);
				}else {
					//不使用回调
					BasePages basePages = mContext.getMainFragment().getSelectedPage();
					
					basePages.setSelectedPage(selectIndex);
				}
				
				//显示和关闭左侧菜单
				mContext.getSlidingMenu().toggle();
			}
		});
		
		super.initEvent();
	}

}
