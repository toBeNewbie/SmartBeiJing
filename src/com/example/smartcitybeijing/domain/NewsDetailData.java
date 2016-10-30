package com.example.smartcitybeijing.domain;

import java.util.List;


/**
 * 
 * @author Administrator
 *@company Newbie
 *@date 2016-10-29
 *@des 解析新闻中心数据的class类
 */
public class NewsDetailData {
	
	public int retcode;

	public class Data {
		public String countcommenturl;
		public String more;
		public String title;

		public List<News> news;

		public class News {
			public String comment;// true
			public String commentlist;// http://10.0.2.2:8080/zhbj/10007/comment_1.json
			public String commenturl;// http://zhbj.qianlong.com/client/user/newComment/35319
			public String id;// 35311
			public String listimage;// http://10.0.2.2:8080/zhbj/10007/2078369924F9UO.jpg
			public String pubdate;// 2014-10-1113:18
			public String title;// 网上大讲堂第368期预告：义务环保人人有责
			public String type;// news
			public String url;// http://10.0.2.2:8080/zhbj/10007/724D6A55496A11726628.html
		}

		public List<Topic> topic;

		public class Topic {
			public String description;// 11111111
			public String id;// 10101
			public String listimage;// http://10.0.2.2:8080/zhbj/10007/1452327318UU91.jpg
			public String sort;// 1
			public String title;// 北京
			public String url;// http://10.0.2.2:8080/zhbj/10007/list_1.json
		}

		public List<TopNews> topnews;

		public class TopNews {
			public String comment;// true
			public String commentlist;// http://10.0.2.2:8080/zhbj/10007/comment_1.json
			public String commenturl;// http://zhbj.qianlong.com/client/user/newComment/35301
			public String id;// 35301
			public String pubdate;// 2014-04-0814:24
			public String title;// 蜗居生活
			public String topimage;// http://10.0.2.2:8080/zhbj/10007/1452327318UU91.jpg
			public String type;// news
			public String url;// http://10.0.2.2:8080/zhbj/10007/724D6A55496A11726628.html
		}

	}

	public Data data;
}
