package com.example.smartcitybeijing.domain;

import java.util.List;

/**
 * 
 * @author Administrator
 *@company Newbie
 *@date 2016-10-27
 *@des 封装来自网络的json数据bean类
 */
public class NewCenterJsonBean {
	public int retcode;
	public List<Data> data;
	public List<String> extend;
	
	public class Data{
		public List<Children> children;
		public class Children{
			public String id;
			public String title;
			public String type;
			public String url;
		}
		public String id;
		public String title;
		public String type;
		public String url;
		public String url1;
		public String dayurl;
		public String excurl;
		public String weekurl;
	}
}
