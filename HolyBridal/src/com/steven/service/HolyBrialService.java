package com.steven.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import javax.microedition.khronos.opengles.GL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.steven.adapter.SupplierAdapter;
import com.steven.model.CategoryItem;
import com.steven.model.GlobalScope;
import com.steven.model.SupplierItem;

public class HolyBrialService {
	DefaultHttpClient httpClient;
	
	public HolyBrialService() {
		httpClient = new DefaultHttpClient();
	}
	
	public HttpResponse responseHTTP(String sUrl){
		HttpGet httpRequest = new HttpGet(sUrl);
		httpRequest.setHeader("Accept", "application/text");
		
		HttpResponse response = null;
		try {
			response = (HttpResponse) httpClient.execute(httpRequest);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	public String toString(HttpResponse response){
		if (response == null)return null;
		HttpEntity entity = response.getEntity();
		if (entity == null)return null;
		
		InputStream instream = null;
		try {
			instream = entity.getContent();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (instream == null)return null;
		
		String sResult= convertStreamToString(instream);
		
		try {
			sResult = URLDecoder.decode(sResult, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			instream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sResult;
	}
	
	public String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the BufferedReader.readLine()
		 * method. We iterate until the BufferedReader return null which means
		 * there's no more data to read. Each line will appended to a StringBuilder
		 * and returned as String.
		 * 
		 * (c) public domain: http://senior.ceng.metu.edu.tr/2009/praeda/2009/01/11/a-simple-restful-client-at-android/
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public void getAllSuppliersInfo(String sResponse) throws JSONException{
    	GlobalScope.arrSuppliers = new ArrayList<SupplierItem>();
    	GlobalScope.arrTotalCategories = new ArrayList<CategoryItem>();
    	
    	if (sResponse == null)return;
    	
    	JSONObject jo = new JSONObject(sResponse);
    	JSONObject ja = jo.getJSONObject("data");
    	
    	String[] keyArray = getKeyArrayFromJsonObject(ja); 
    	
    	for (int i = 0 ; i < keyArray.length ; i ++){
    		jo = ja.getJSONObject(keyArray[i]);
    		
    		SupplierItem sItem = new SupplierItem();
    		sItem.sID = jo.getString("id");
    		sItem.sSupplierName = jo.getString("name");
    		sItem.sImageUrl = jo.getString("image");
    		sItem.arrCategories = new ArrayList<CategoryItem>();
    		
    		JSONObject jss = jo.getJSONObject("suppliers");
    		
    		String[] keyArray2 = getKeyArrayFromJsonObject(jss);
    		
    		for (int j = 0, k ; j < keyArray2.length ; j ++){
    			JSONObject js = jss.getJSONObject(keyArray2[j]);
    			CategoryItem cItem = new CategoryItem();
    			cItem.sID = js.getString("id");
    			cItem.sAddress = js.getString("address");
    			cItem.sName = js.getString("name");
    			cItem.sLogo = js.getString("logo");
    			
    			cItem = readSupplierFullInfo(cItem);
    			
    			sItem.arrCategories.add(cItem);
    			
    			for (k = 0 ; k < GlobalScope.arrTotalCategories.size(); k ++){
    				CategoryItem cT = GlobalScope.arrTotalCategories.get(k);
    				if (cT.sID.equals(cItem.sID))break;
    			}
    			
    			if (k == GlobalScope.arrTotalCategories.size()){
    				GlobalScope.arrTotalCategories.add(cItem);
    			}
    		}
    		
    		if (sItem.arrCategories.size() == 0)continue;
    		GlobalScope.arrSuppliers.add(sItem);
    	}
    }	
    
    private CategoryItem readSupplierFullInfo(CategoryItem cItem){
    	String sRequestUrl = GlobalScope.SERVER_URL + "/api/v1/supplier/" + cItem.sID;
    	String sResponse = toString(responseHTTP(sRequestUrl));
    	
    	try {
    		if (sResponse == null)return cItem;
    		JSONObject jo = new JSONObject(sResponse);
        	JSONObject jcs = jo.getJSONObject("data");
        	
        	cItem.sID = jcs.getString("id");
        	cItem.sName = jcs.getString("name");
        	cItem.sLogo = jcs.getString("logo");
        	cItem.sAbout = jcs.getString("about");
        	cItem.sEmail = jcs.getString("email");
        	cItem.sTelephone = jcs.getString("telephone");
        	cItem.sAddress_l1 = jcs.getString("address_l1");
        	cItem.sAddress_l2 = jcs.getString("address_l2");
        	cItem.sCountry = jcs.getString("county");
        	cItem.sTown = jcs.getString("town");
        	cItem.sPostcode = jcs.getString("postcode");
        	cItem.sWebsite = jcs.getString("website");
        	cItem.sLocationLat = jcs.getString("lat");
        	cItem.sLocationLng = jcs.getString("lng");
        	
        	cItem.arrFeaturedImages = new ArrayList<String>();
        	
        	JSONObject jc = jcs.getJSONObject("featured");
        	String[] keys = getKeyArrayFromJsonObject(jc);
        	
        	for (int i = 0 ; i < keys.length ; i ++){
        		cItem.arrFeaturedImages.add(jc.getString(keys[i]));
        	}
        	
        	cItem.arrSuppliers = new ArrayList<SupplierItem>();
        	JSONObject ja = jcs.getJSONObject("categories");
        	
        	keys = getKeyArrayFromJsonObject(ja);
        	
        	for (int i = 0 ; i < keys.length ; i ++){
        		jc = ja.getJSONObject(keys[i]);
        		SupplierItem sItem = new SupplierItem();
        		sItem.sID = jc.getString("id");
        		sItem.sSupplierName = jc.getString("name");
        		
        		cItem.arrSuppliers.add(sItem);
        	}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return cItem;
    }

    private String[] getKeyArrayFromJsonObject(JSONObject jo){
    	Iterator keysToCopyIterator = jo.keys();
    	ArrayList<String> keysList = new ArrayList<String>();
    	
    	while(keysToCopyIterator.hasNext()) {
    	    String key = (String) keysToCopyIterator.next();
    	    keysList.add(key);
    	}
    	return keysList.toArray(new String[keysList.size()]);
    }
    
    public String requestContact(String sID) throws JSONException, UnsupportedEncodingException{
    	String sContactMethod = GlobalScope.currentUser.contactViaMobile ? "Mobilenumber" : "Email";
    	String sWeddingDate = "";
    	
    	for (int i = 0 ; i < GlobalScope.arrMonth.length ; i ++){
    		String sSearch = " / " + GlobalScope.arrMonth[i] + " / ";
    		if (GlobalScope.currentUser.weddingDate.indexOf(sSearch) != -1){
    			sWeddingDate = GlobalScope.currentUser.weddingDate.replace(sSearch, "/" + (i + 1) + "/");
    			break;
    		}
    	}
    	
    	String sRequestUrl = GlobalScope.SERVER_URL +  "/api/v1/supplier/" + sID + "/request?name=" + URLEncoder.encode(GlobalScope.currentUser.name, "UTF-8") + "&email=" + GlobalScope.currentUser.email + "&telephone=" + GlobalScope.currentUser.mobile + "&method=" + sContactMethod + "&date=" + sWeddingDate;
    	
    	String sResponse = toString(responseHTTP(sRequestUrl));
    	
    	if (sResponse == null)return "";
    	JSONObject jo = new JSONObject(sResponse);
//    	JSONObject jo = new JSONObject("{\"error\":true,\"status\":200,\"description\":\"sdfsdf\",\"data\":{\"1\":\"fsdfsF\",\"2\":\"sdfsdf\",\"3\":\"ergrtgregdgsdfgdsfgds\"}}");
        
        boolean isError = jo.getBoolean("error");
        if (!isError)return "";

        String sMessage = jo.getString("description");
        
        jo = jo.getJSONObject("data");
        
        String[] sAllKeys = getKeyArrayFromJsonObject(jo);
        
        for (int i = 0 ; i < sAllKeys.length ; i ++){
        	sMessage += "\n" + sAllKeys[i] + ": " + jo.getString(sAllKeys[i]);
        }
        
        return sMessage;
    }
}
