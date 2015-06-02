package com.steven.service;

import android.app.Application;

public class GlobalConstants extends Application {
	public static String sAppName = "HolyBrial19881031";
	
	public static String sIsLogin = "HolyBridal.UserInfoLogin";
	
	public static String sUserName = "UserInfo.UserName";
	public static String sUserEmail = "UserInfo.UserEmail";
	public static String sUserPass = "UserInfo.Password";
	public static String sUserMobile = "UserInfo.TelephoneNumber";
	public static String sUserContactMethod = "UserInfo.ContactMethod";
	public static String sUserWeddingDate = "UserInfo.DateOfWedding";
	public static String sIsFavorite = "HolyBridal.FavoriteCategory";
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
}
