package com.example.smartcitybeijing.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * @author Administrator
 *@company Newbie
 *@date 2016-8-15
 *@des splash工具类，检测版本更新。
 */
public class splashUtils {
	

	
	public static void putBoolean(Context context,String key, Boolean value){
		SharedPreferences sharedPreferences = context.getSharedPreferences(myConstantValue.SPL_FILE_CONFIGUE_NAME, Context.MODE_PRIVATE);
		sharedPreferences.edit().putBoolean(key, value).commit();
		}
	
	
	public static Boolean getbBoolean(Context context,String key, Boolean defValue){
		SharedPreferences sharedPreferences = context.getSharedPreferences(myConstantValue.SPL_FILE_CONFIGUE_NAME, Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean(key, defValue);
	}

	public static void putString(Context context,String key, String value){
		SharedPreferences sharedPreferences = context.getSharedPreferences(myConstantValue.SPL_FILE_CONFIGUE_NAME, Context.MODE_PRIVATE);
		sharedPreferences.edit().putString(key, value).commit();
		}
	
	
	public static String getString(Context context,String key, String defValue){
		SharedPreferences sharedPreferences = context.getSharedPreferences(myConstantValue.SPL_FILE_CONFIGUE_NAME, Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, defValue);
	}
	
	
	public static void putInt(Context context,String key, int value){
		SharedPreferences sharedPreferences = context.getSharedPreferences(myConstantValue.SPL_FILE_CONFIGUE_NAME, Context.MODE_PRIVATE);
		sharedPreferences.edit().putInt(key, value).commit();
		}
	
	
	public static int getInt(Context context,String key, int defValue){
		SharedPreferences sharedPreferences = context.getSharedPreferences(myConstantValue.SPL_FILE_CONFIGUE_NAME, Context.MODE_PRIVATE);
		return sharedPreferences.getInt(key, defValue);
	}
	
	
	public static void putLong(Context context,String key, long value){
		SharedPreferences sharedPreferences = context.getSharedPreferences(myConstantValue.SPL_FILE_CONFIGUE_NAME, Context.MODE_PRIVATE);
		sharedPreferences.edit().putLong(key, value).commit();
		}
	
	
	public static long getLong(Context context,String key, long defValue){
		SharedPreferences sharedPreferences = context.getSharedPreferences(myConstantValue.SPL_FILE_CONFIGUE_NAME, Context.MODE_PRIVATE);
		return sharedPreferences.getLong(key, defValue);
	}
	

}
