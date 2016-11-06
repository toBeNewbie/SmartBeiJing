package com.example.smartcitybeijing.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.example.smartcitybeijing.R;
import com.example.smartcitybeijing.utils.myConstantValue;

/**
 * 
 * @author Administrator
 * @company Newbie
 * @date 2016-11-6
 * @des 具体新闻信息的详细界面
 */
public class newsContentDeailActivity extends Activity {
	private ImageView mIvBack;
	private ImageView mIvTextSize;
	private ImageView mIvShare;
	private WebView mWbNewDeails;
	private ProgressBar mPbLoadNews;
	private WebSettings mWebSettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		initView();

		initData();

		initEvent();
	}

	private void initEvent() {
		// 给webview添加事件
		mWbNewDeails.setWebViewClient(new WebViewClient() {

			// 网页新闻数据加载完成时调用
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);

				// 网页数据加载完成，隐藏加载进度条
				mPbLoadNews.setVisibility(View.GONE);

			}

		});

		OnClickListener mOnClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.iv_back_item:// 返回
					finish();
					break;

				case R.id.iv_title_bar_share:// 分享
					shareNewsDatas();
					break;

				case R.id.iv_title_bar_text_size:// 修改字体大小
					// 显示修改字体大小的对话框
					showModifiTextSizeAlerdialog();
					break;
				default:
					break;
				}

			}
		};

		// 给标题按钮设置点击事件
		mIvBack.setOnClickListener(mOnClickListener);
		mIvShare.setOnClickListener(mOnClickListener);
		mIvTextSize.setOnClickListener(mOnClickListener);

	}
	
	//默认的字体大小
	protected int textSizeDefault=2;

	/**
	 * 显示修改字体的对话框
	 */
	protected void showModifiTextSizeAlerdialog() {
		AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(this);
		mAlertDialog.setTitle("修改字体大小");
		mAlertDialog.setSingleChoiceItems(new String[] {"1号", "2号", "3号", "4号", "5号"}, textSizeDefault,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						textSizeDefault=which;
						
						modificationTextSize(textSizeDefault+1);
						mAlertDialogText.dismiss();
					}

				});
		
		mAlertDialogText = mAlertDialog.create();
		mAlertDialogText.show();
	}

	/**
	 * 字体大小常量
	 */
	private static final int LARGEST = 5;
	private static final int LARGER = 4;
	private static final int NORMAL = 3;
	private static final int SMALLER = 2;
	private static final int SMALLEST = 1;
	private AlertDialog mAlertDialogText;

	/**
	 * 修改字体大小
	 */
	protected void modificationTextSize(int textSize) {

		switch (textSize) {
		case LARGEST:// 最大
			mWebSettings.setTextSize(TextSize.LARGEST);
			break;

		case LARGER:
			mWebSettings.setTextSize(TextSize.LARGER);
			break;

		case NORMAL:
			mWebSettings.setTextSize(TextSize.NORMAL);
			break;

		case SMALLER:
			mWebSettings.setTextSize(TextSize.SMALLER);
			break;

		case SMALLEST:
			mWebSettings.setTextSize(TextSize.SMALLEST);
			break;

		default:
			break;
		}

	}

	/**
	 * 分享新闻数据
	 */

		private void shareNewsDatas() {
			 ShareSDK.initSDK(this);
			 OnekeyShare oks = new OnekeyShare();
			 //关闭sso授权
			 oks.disableSSOWhenAuthorize(); 

			// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
			 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
			 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
			 oks.setTitle("魔法革");
			 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
			 oks.setTitleUrl("http://sharesdk.cn");
			 // text是分享文本，所有平台都需要这个字段
			 oks.setText("断了的弦，再怎么连。我的感觉，你已听不见！");
			 //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
			 oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
			 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
			 //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
			 // url仅在微信（包括好友和朋友圈）中使用
			 oks.setUrl("http://sharesdk.cn");
			 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
			 oks.setComment("我是测试评论文本");
			 // site是分享此内容的网站名称，仅在QQ空间使用
			 oks.setSite("ShareSDK");
			 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
			 oks.setSiteUrl("http://sharesdk.cn");

			// 启动分享GUI
			 oks.show(this);
			 }

	

	private void initData() {

		// 获得网页数据IP地址
		String newsDataUrl = getIntent().getStringExtra(
				myConstantValue.NEWS_CONTENT_DEAILS_URL);

		// 加载网页数据
		mWbNewDeails.loadUrl(newsDataUrl);

	}

	private void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_news_content_deail);

		// 隐藏菜单按钮
		findViewById(R.id.iv_menu_item).setVisibility(View.GONE);
		// 隐藏标题
		findViewById(R.id.tv_title_item).setVisibility(View.GONE);

		// 显示返回

		mIvBack = (ImageView) findViewById(R.id.iv_back_item);
		mIvBack.setVisibility(View.VISIBLE);

		// 显示字体大小

		mIvTextSize = (ImageView) findViewById(R.id.iv_title_bar_text_size);
		mIvTextSize.setVisibility(View.VISIBLE);

		// 显示分享

		mIvShare = (ImageView) findViewById(R.id.iv_title_bar_share);
		mIvShare.setVisibility(View.VISIBLE);

		// 获取显示新闻网页的组件

		mWbNewDeails = (WebView) findViewById(R.id.wb_news_detail_view);

		// 获取webview的设置信息
		mWebSettings = mWbNewDeails.getSettings();
		// 设置可以运行js
		mWebSettings.setJavaScriptEnabled(true);
		// 设置缩放按钮
		mWebSettings.setBuiltInZoomControls(true);
		// 设置双击放大和缩小
		mWebSettings.setUseWideViewPort(true);

		// 获取加载更多新闻信息的进度条

		mPbLoadNews = (ProgressBar) findViewById(R.id.pb_news_loading_view);
	}
}
