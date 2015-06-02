package com.steven.model;

import java.util.ArrayList;

public class CategoryItem {
	public String sDescription;
	public String sMile;
	public String sID;
	public String sName;
	public String sAddress;
	public String sLogo;

	public String sAbout;
	public String sEmail;
	public String sTelephone;
	public String sWebsite;
	public String sAddress_l1;
	public String sAddress_l2;
	public String sCountry;
	public String sTown;
	public String sPostcode;

	public ArrayList<String> arrFeaturedImages;
	public ArrayList<SupplierItem> arrSuppliers;
	
	public String sLocationLat;
	public String sLocationLng;

	public boolean isFavorite;
	
	public CategoryItem(){
		
	}
	
}
