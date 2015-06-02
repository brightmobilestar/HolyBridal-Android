package com.steven.model;

public class UserInfo {
	public String name;
	public String email;
	public String password;
	public String mobile;
	public boolean contactViaMobile;
	public String weddingDate;
	
	public UserInfo(){
		
	}
	
	public void setName(String s){
		name = s;
	}
	public void setEmail(String s){
		email = s;
	}
	public void setPassword(String s){
		password = s;
	}
	public void setMobileNumber(String s){
		mobile = s;
	}
	public void setContactMethod(String s){
		contactViaMobile = (!s.equals("email"));
	}
	public void setWeddingDate(String s){
		weddingDate = s;
	}
}
