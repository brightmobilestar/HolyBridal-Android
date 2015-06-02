package com.steven.model;

import java.util.ArrayList;

import com.steven.service.HolyBrialService;
import com.steven.service.Preferences;

import android.app.Application;
import android.location.Location;

public class GlobalScope extends Application {
	public static final int SPLASH_TIME_OUT = 1000;
	public static int nIndexRightSlideMenu;
	
	public static ArrayList<RightMenuItem> arrRightMenus;
	public static ArrayList<SupplierItem> arrSuppliers;
	public static ArrayList<CategoryItem> arrCategories;
	public static ArrayList<CategoryItem> arrTotalCategories;

	public static SupplierItem currentSupplier;
	public static CategoryItem currentCategory;
	
	public static boolean isSwipable;
	public static boolean isFromFavorite;
	
	public static Preferences preferences;
	public static HolyBrialService backend;
	
	public static UserInfo currentUser;
	public static String alertDialogMessageTitle = "HOLYBRIAL";
	public static String alertDialogErrorTitle = "ERROR";
	
	public static String SERVER_URL = "http://www.theholybridal.co.uk";
	public static String sWebPageUrl;
	
	public static Location currentLocation;
	
	public static String[] arrMonth = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
}
