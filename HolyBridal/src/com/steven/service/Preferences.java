package com.steven.service;

import java.util.Random;

import com.steven.model.UserInfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Preferences {
	public static SharedPreferences preferences;
	Editor editor;

	@SuppressLint("CommitPrefEdits")
	public Preferences(Context context) {
		preferences = (SharedPreferences) context.getSharedPreferences(GlobalConstants.sAppName, Context.MODE_PRIVATE);
		editor = preferences.edit();
	}

	public static SharedPreferences getInstance(Context context) {
		if (preferences == null)preferences = (SharedPreferences) context.getSharedPreferences(GlobalConstants.sAppName, Context.MODE_PRIVATE);
		return preferences;
	}
	
	public boolean isLogin(){
		return preferences.getBoolean(GlobalConstants.sIsLogin, false);
	}
	
	public UserInfo getUserInfo(){
		UserInfo userInfo = new UserInfo();
		
		userInfo.setName(preferences.getString(GlobalConstants.sUserName, ""));
		userInfo.setEmail(preferences.getString(GlobalConstants.sUserEmail, ""));
		userInfo.setPassword(preferences.getString(GlobalConstants.sUserPass, ""));
		userInfo.setMobileNumber(preferences.getString(GlobalConstants.sUserMobile, ""));
		userInfo.setContactMethod(preferences.getString(GlobalConstants.sUserContactMethod, "email"));
		userInfo.setWeddingDate(preferences.getString(GlobalConstants.sUserWeddingDate, ""));
		
		return userInfo;
	}
	
	public void registerLogin(){
		editor.putBoolean(GlobalConstants.sIsLogin, true).apply();
	}
	
	public String randomizePassword(){
		String sPassword = "";
	    String[] arrNumStr = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
	    long lCurrentSec = System.currentTimeMillis();
	    Random rGenerator = new Random();
	    int r = rGenerator.nextInt(arrNumStr.length);
	    
	    for (int i = 0 ; i < lCurrentSec % 20 ; i ++){
	    	r = rGenerator.nextInt();
	    }
	    
	    for (int i = 0 ; i < 4 ; i ++){
	    	r = rGenerator.nextInt(arrNumStr.length);
	    	sPassword = sPassword + arrNumStr[r];
	    }

	    return sPassword;
	}
	
	public void resetUserInfo(){
		editor.putBoolean(GlobalConstants.sIsLogin, false).apply();
	}
	
	public void setUserName(String sName){
		editor.putString(GlobalConstants.sUserName, sName).apply();
	}
	
	public void setUserEmail(String sEmail){
		editor.putString(GlobalConstants.sUserEmail, sEmail).apply();
	}
	
	public void setUserPassword(String sPassword){
		editor.putString(GlobalConstants.sUserPass, sPassword).apply();
	}
	
	public void setUserContactMethod(String sContactMethod){
		editor.putString(GlobalConstants.sUserContactMethod, sContactMethod).apply();
	}
	
	public void setUserWeddingDate(String sDate){
		editor.putString(GlobalConstants.sUserWeddingDate, sDate).apply();
	}
	
	public void setUserMobile(String sPhoneNumber){
		editor.putString(GlobalConstants.sUserMobile, sPhoneNumber).apply();
	}
	
	public String getUserContactMethod(){
		return preferences.getString(GlobalConstants.sUserContactMethod, "email");
	}
	
	public String getUserPassword(){
		return preferences.getString(GlobalConstants.sUserPass, "");
	}
	
	public boolean isFavoriteCategory(String sID){
		return preferences.getBoolean(GlobalConstants.sIsFavorite + sID, false);
	}
	
	public void setFavoriteCategory(String sID, boolean bV){
		editor.putBoolean(GlobalConstants.sIsFavorite + sID, bV).apply();
	}
	
	public double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
	  double theta = lon1 - lon2;
	  double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
	  dist = Math.acos(dist);
	  dist = rad2deg(dist);
	  dist = dist * 60 * 1.1515;
	  if (unit.equals("K")) {
	    dist = dist * 1.609344;
	  } else if (unit.equals("N")) {
	    dist = dist * 0.8684;
	    }
	  return (dist);
	}

	private double rad2deg(double rad) {
	  return (rad * 180 / Math.PI);
	}

	private double deg2rad(double deg) {
	  return (deg * Math.PI / 180.0);
	}
}
