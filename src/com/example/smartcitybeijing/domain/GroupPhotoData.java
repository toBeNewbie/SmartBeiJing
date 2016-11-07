package com.example.smartcitybeijing.domain;

import java.util.List;

/**
 * 
 * @author Administrator
 * @company Newbie
 * @date 2016-11-7
 * @des 新闻中心组图的数据
 */
public class GroupPhotoData {
	public String retcode;
	public Data data;

	public class Data {
		public String countcommenturl;
		public String more;
		public List<News> news;

		public class News {
			public String comment; // true
			public String commentlist; // http://zhbj.qianlong.com/static/api/news/10003/72/82772/comment_1.json
			public String commenturl; // http://zhbj.qianlong.com/client/user/newComment/82772
			public String id; // 82772
			public String largeimage; // http://zhbj.qianlong.com/static/images/2014/11/07/70/476518773M7R.jpg
			public String listimage; // http://10.0.2.2:8080/zhbj/photos/images/46728356JDGO.jpg
			public String pubdate; // 2014-11-07 11:40
			public String smallimage; // http://zhbj.qianlong.com/static/images/2014/11/07/79/485753989TVL.jpg
			public String title; // 北京·APEC绚丽之夜
			public String type; // news
			public String url; // http://zhbj.qianlong.com/static/html/2014/11/07/7743665E4E6B10766F26.html
		}

		public String title;
		public List topic;
	}
}
