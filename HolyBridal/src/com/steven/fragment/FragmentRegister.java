package com.steven.fragment;

import java.util.Calendar;

import com.steven.holybridal.Login;
import com.steven.holybridal.MainActivity;
import com.steven.holybridal.R;
import com.steven.holybridal.WebPage;
import com.steven.model.GlobalScope;
import com.steven.model.UserInfo;
import com.steven.utils.OnSwipeTouchListener;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentRegister extends Fragment implements OnClickListener {
	MainActivity parent;
	private TextView txvRegister;
	private TextView txvUserContactMethod;
	private TextView txvUserDetails;
	private TextView txvWeddingDate;
	private TextView txvUserContactByEmail;
	private TextView txvUserContactByMobile; 
	private Button btnBackFromRegister;
	private Button btnBackFromRegister2;
	private Button btnRegister;
	private Button btnMenuForRegisterFragment;
	private Button btnMenuForRegisterFragment2;
	private Button btnWeddindDate;
	private RelativeLayout rltDatePickerWrapper;
	private Button btnDateOfWedding;
	private Button btnSelectDate;
	private Button btnShowBirthdayPickerView;
	private NumberPicker pickerDay;
	private NumberPicker pickerMonth;
	private NumberPicker pickerYear;
	private RelativeLayout rltRegisterInfoWrapper;
	private Typeface font;
	private View rootView;
	private EditText edtUserFullName;
	private EditText edtUserEmailAddress;
	private EditText edtUserPassword1;
	private EditText edtUserPassword2;
	private EditText edtUserMobile;
	private AlertDialog singleAlert;
	private Button btnTerms;
	private AlertDialog doubleAlert;
	private boolean isEmailMethod = true;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	rootView = inflater.inflate(R.layout.fragment_register, container, false);
    	
    	font = Typeface.createFromAsset(getActivity().getAssets(), "atwriter.ttf");
    	
    	arrangeObjects();
		
        return rootView;
	 }
	
	private void arrangeObjects(){
		btnBackFromRegister = (Button) rootView.findViewById(R.id.btnBackFromRegister);
		btnBackFromRegister.setOnClickListener(this);
		btnBackFromRegister2 = (Button) rootView.findViewById(R.id.btnBackFromRegister2);
		btnBackFromRegister2.setOnClickListener(this);
		
		btnRegister = (Button) rootView.findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(this);
		btnMenuForRegisterFragment = (Button) rootView.findViewById(R.id.btnMenuForRegisterFragment);
		btnMenuForRegisterFragment2 = (Button) rootView.findViewById(R.id.btnMenuForRegisterFragment2);
		
		btnDateOfWedding = (Button) rootView.findViewById(R.id.btnDateOfWedding);
		btnDateOfWedding.setOnClickListener(this);
		btnShowBirthdayPickerView = (Button) rootView.findViewById(R.id.btnShowBirthdayPickerView);
		btnShowBirthdayPickerView.setOnClickListener(this);
		btnSelectDate = (Button) rootView.findViewById(R.id.btnSelectDate);
		btnSelectDate.setOnClickListener(this);
		btnTerms = (Button) rootView.findViewById(R.id.btnTerms);
		btnTerms.setOnClickListener(this);
		
		rltDatePickerWrapper = (RelativeLayout) rootView.findViewById(R.id.rltDatePickerWrapper);
		
		txvRegister = (TextView) rootView.findViewById(R.id.txvRegister);
		txvUserContactMethod = (TextView) rootView.findViewById(R.id.txvUserContactMethod);
		txvUserDetails = (TextView) rootView.findViewById(R.id.txvUserDetails);
		txvWeddingDate = (TextView) rootView.findViewById(R.id.txvWeddingDate);
		txvUserContactByEmail = (TextView) rootView.findViewById(R.id.txvUserContactByEmail);
		txvUserContactByMobile = (TextView) rootView.findViewById(R.id.txvUserContactByMobile);
		txvRegister.setTypeface(font);
		txvUserContactMethod.setTypeface(font);
		txvUserDetails.setTypeface(font);
		txvWeddingDate.setTypeface(font);
		txvUserContactByEmail.setTypeface(font);
		txvUserContactByMobile.setTypeface(font);
		txvUserContactByEmail.setOnClickListener(this);
		txvUserContactByMobile.setOnClickListener(this);
		
		pickerDay = (NumberPicker) rootView.findViewById(R.id.pickerDay);
		pickerMonth = (NumberPicker) rootView.findViewById(R.id.pickerMonth);
		pickerYear = (NumberPicker) rootView.findViewById(R.id.pickerYear);
		pickerDay.setMaxValue(31);
		pickerDay.setMinValue(1);
		int nCurrentYear = Calendar.getInstance().get(Calendar.YEAR);
		pickerYear.setMaxValue(nCurrentYear + 9);
		pickerYear.setMinValue(nCurrentYear);
		pickerMonth.setMaxValue(GlobalScope.arrMonth.length - 1);
		pickerMonth.setMinValue(0);
		pickerMonth.setDisplayedValues(GlobalScope.arrMonth);
		pickerDay.setFocusable(true);
		pickerDay.setFocusableInTouchMode(true);
		pickerMonth.setFocusable(true);
		pickerMonth.setFocusableInTouchMode(true);
		pickerYear.setFocusable(true);
		pickerYear.setFocusableInTouchMode(true);
		setDividerColor(pickerDay, Color.WHITE);
		setDividerColor(pickerMonth, Color.WHITE);
		setDividerColor(pickerYear, Color.WHITE);
		pickerDay.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		pickerMonth.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		pickerYear.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

		pickerDay.setWrapSelectorWheel(false);
		pickerMonth.setWrapSelectorWheel(false);
		pickerYear.setWrapSelectorWheel(false);
		
		btnWeddindDate = (Button) rootView.findViewById(R.id.btnWeddindDate);
		btnWeddindDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDatePicker();
			}
		});
		
		edtUserFullName = (EditText) rootView.findViewById(R.id.edtUserFullName);
		edtUserEmailAddress = (EditText) rootView.findViewById(R.id.edtUserEmailAddress);
		edtUserPassword1 = (EditText) rootView.findViewById(R.id.edtUserPassword1);
		edtUserPassword2 = (EditText) rootView.findViewById(R.id.edtUserPassword2);
		edtUserMobile = (EditText) rootView.findViewById(R.id.edtUserMobile);
		
		if (GlobalScope.isSwipable){
			isEmailMethod = GlobalScope.preferences.getUserContactMethod().equals("email") ? true : false;
			parent = (MainActivity)getActivity();
			txvRegister.setText(getResources().getString(R.string.txv_my_account));
			btnBackFromRegister.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_logout));
			
			rootView.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
				@Override
				public void onSwipeLeft() {
					parent.loadRightMenu();
					super.onSwipeLeft();
				}

				@Override
				public void onSwipeRight() {
					super.onSwipeRight();
				}
			});
			
			rltRegisterInfoWrapper = (RelativeLayout) rootView.findViewById(R.id.rltRegisterInfoWrapper);
			rltRegisterInfoWrapper.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
				@Override
				public void onSwipeLeft() {
					parent.loadRightMenu();
					super.onSwipeLeft();
				}

				@Override
				public void onSwipeRight() {
					super.onSwipeRight();
				}
			});
			
			rltDatePickerWrapper.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
				@Override
				public void onSwipeLeft() {
					parent.loadRightMenu();
					super.onSwipeLeft();
				}

				@Override
				public void onSwipeRight() {
					super.onSwipeRight();
				}
			});
			
			btnMenuForRegisterFragment.setVisibility(View.VISIBLE);
			btnMenuForRegisterFragment2.setVisibility(View.VISIBLE);
			btnMenuForRegisterFragment.setOnClickListener(this);
			btnMenuForRegisterFragment2.setOnClickListener(this);
			
			btnRegister.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_update));
			
			edtUserPassword1.setHint(R.string.placeholder_user_reset_password);
			edtUserPassword2.setHint(R.string.placeholder_user_reset_password_repeat);
			
			setUpUserInfo();
		} else {
			txvRegister.setText(getResources().getString(R.string.btn_register));
			btnMenuForRegisterFragment.setVisibility(View.GONE);
			btnMenuForRegisterFragment2.setVisibility(View.GONE);
			btnBackFromRegister.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_back));
			btnRegister.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_register));

			edtUserPassword1.setHint(R.string.placeholder_user_password);
			edtUserPassword2.setHint(R.string.placeholder_user_password_repeat);
		}
		
		singleAlert = new AlertDialog.Builder(getActivity()).setIcon(R.drawable.ic_launcher).setPositiveButton("OK", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
		}}).create();		

	}
	
	public void hideKeyboard() {   
	    View view = getActivity().getCurrentFocus();
	    if (view != null) {
	        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	    }
	}
	
	 private void setDividerColor(NumberPicker picker, int color) {
	    java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
	    for (java.lang.reflect.Field pf : pickerFields) {
	        if (pf.getName().equals("mSelectionDivider")) {
	            pf.setAccessible(true);
	            try {
	                ColorDrawable colorDrawable = new ColorDrawable(color);
	                pf.set(picker, colorDrawable);
	            } catch (IllegalArgumentException e) {
	                e.printStackTrace();
	            } catch (Resources.NotFoundException e) {
	                e.printStackTrace();
	            }
	            catch (IllegalAccessException e) {
	                e.printStackTrace();
	            }
	            break;
	        }
	    }
	}
	
	private void showDatePicker(){
		rltDatePickerWrapper.setVisibility(View.VISIBLE);
    	Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(300);

        AnimationSet animation = new AnimationSet(true);
        animation.addAnimation(fadeIn);
        rltDatePickerWrapper.setAnimation(animation);
	}
	 
	private void hideDatePicker(){
        Animation fadeOut = new AlphaAnimation(1, 0);
//	        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(300);

        AnimationSet animation = new AnimationSet(true);
        animation.addAnimation(fadeOut);
        rltDatePickerWrapper.setAnimation(animation);
        rltDatePickerWrapper.setVisibility(View.GONE);
	}
	 
	private void setUpUserInfo(){
		UserInfo userInfo = GlobalScope.preferences.getUserInfo();
		
		edtUserFullName.setText(userInfo.name);
		edtUserEmailAddress.setText(userInfo.email);
		edtUserMobile.setText(userInfo.mobile);
		btnWeddindDate.setText(userInfo.weddingDate);
		
		if (!isEmailMethod)enableMobileContactWay();
	}

	 
    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnBackFromRegister:
			if (GlobalScope.isSwipable)askToLogout();
			else loadLoginPage();
			break;
		case R.id.btnBackFromRegister2:
			if (GlobalScope.isSwipable)askToLogout();
			else loadLoginPage();
			break;
		case R.id.btnRegister:
			if (GlobalScope.isSwipable)updateUserInfo();
			else registerUserInfo();
			break;
		case R.id.btnMenuForRegisterFragment:
			parent.loadRightMenu();
			break;
		case R.id.btnMenuForRegisterFragment2:
			parent.loadRightMenu();
			break;
		case R.id.txvUserContactByEmail:
			enableEmailContactWay();
			setUserContactMethodBy("email");
			break;
		case R.id.txvUserContactByMobile:
			enableMobileContactWay();
			setUserContactMethodBy("mobile");
			break;
		case R.id.btnDateOfWedding:
			hideDatePicker();
			break;
		case R.id.btnShowBirthdayPickerView:
			showDatePicker();
			break;
		case R.id.btnSelectDate:
			selectWeddingDate();
			break;
		case R.id.btnTerms:
			loadTermsPage();
			break;
		default:
			break;
		}
	}
	
	private void enableEmailContactWay(){
		txvUserContactByEmail.setBackground(getResources().getDrawable(R.xml.round_segment11));
		txvUserContactByMobile.setBackground(getResources().getDrawable(R.xml.round_segment20));
		txvUserContactByEmail.setTextColor(getResources().getColor(R.color.whiteColor));
		txvUserContactByMobile.setTextColor(getResources().getColor(R.color.pinkColor));
		isEmailMethod = true;
	}
	
	private void enableMobileContactWay(){
		txvUserContactByEmail.setBackground(getResources().getDrawable(R.xml.round_segment10));
		txvUserContactByMobile.setBackground(getResources().getDrawable(R.xml.round_segment21));
		txvUserContactByEmail.setTextColor(getResources().getColor(R.color.pinkColor));
		txvUserContactByMobile.setTextColor(getResources().getColor(R.color.whiteColor));
		isEmailMethod = false;
	}
	
	private void updateUserInfo(){
		singleAlert.setTitle(GlobalScope.alertDialogErrorTitle);
		if (edtUserFullName.getText().toString().equals("")){
			singleAlert.setMessage("Please enter full name");
			singleAlert.show();
		} else if (edtUserEmailAddress.getText().toString().equals("")){
			singleAlert.setMessage("Please enter email address");
			singleAlert.show();
		} else if (!isEmailMethod && edtUserMobile.getText().toString().equals("")){
			singleAlert.setMessage("Please enter mobile number");
			singleAlert.show();
		} else if (btnWeddindDate.getText().toString().equals("")){
			singleAlert.setMessage("Please add a date");
			singleAlert.show();
		} else if (!edtUserPassword1.getText().toString().equals(edtUserPassword2.getText().toString())){
			singleAlert.setMessage("Please confirm repeat password. It doesn't match with password");
			singleAlert.show();
		} else {
			saveUserInfo();
			singleAlert.setTitle(GlobalScope.alertDialogMessageTitle);
			singleAlert.setMessage("Account updated.");
			singleAlert.show();
		}
	}
	
	private void registerUserInfo(){
		singleAlert.setTitle(GlobalScope.alertDialogErrorTitle);
		if (edtUserFullName.getText().toString().equals("")){
			singleAlert.setMessage("Please enter full name");
			singleAlert.show();
		} else if (edtUserEmailAddress.getText().toString().equals("")){
			singleAlert.setMessage("Please enter email address");
			singleAlert.show();
		} else if (edtUserPassword1.getText().toString().equals("")){
			singleAlert.setMessage("Please enter password");
			singleAlert.show();
		} else if (edtUserPassword2.getText().toString().equals("")){
			singleAlert.setMessage("Please enter repeat password");
			singleAlert.show();
		} else if (!isEmailMethod && edtUserMobile.getText().toString().equals("")){
			singleAlert.setMessage("Please enter mobile number");
			singleAlert.show();
		} else if (btnWeddindDate.getText().toString().equals("")){
			singleAlert.setMessage("Please add a date");
			singleAlert.show();
		} else if (!edtUserPassword1.getText().toString().equals(edtUserPassword2.getText().toString())){
			singleAlert.setMessage("Please confirm repeat password. It doesn't match with password");
			singleAlert.show();
		} else {
			saveUserInfo();
			GlobalScope.preferences.registerLogin();
			loadSuppliers();
			getActivity().finish();
		}
	}
	
	private void saveUserInfo(){
		GlobalScope.preferences.setUserName(edtUserFullName.getText().toString());
		GlobalScope.preferences.setUserEmail(edtUserEmailAddress.getText().toString());
		GlobalScope.preferences.setUserPassword(edtUserPassword1.getText().toString().equals("") ? GlobalScope.preferences.getUserPassword() : edtUserPassword1.getText().toString());
		GlobalScope.preferences.setUserMobile(edtUserMobile.getText().toString());
		GlobalScope.preferences.setUserWeddingDate(btnWeddindDate.getText().toString());
	}
	
	private void askToLogout(){
		doubleAlert = new AlertDialog.Builder(getActivity()).setTitle("Logout?").setMessage("Are you sure you wish to logout of \n Holy Bridal?").setIcon(R.drawable.ic_launcher).setPositiveButton("Logout", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				GlobalScope.preferences.resetUserInfo();
				loadLoginPage();
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}).create();
		
		doubleAlert.show();
	}
	
	private void loadLoginPage(){
		Intent iRegister = new Intent(getActivity().getApplicationContext(), Login.class);
		Bundle bndNavigation = ActivityOptions.makeCustomAnimation(getActivity().getApplicationContext(), R.animator.slide_in_right, R.animator.slide_out_right).toBundle();
		getActivity().startActivity(iRegister, bndNavigation);
//		getActivity().overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_right);
		getActivity().finish();
	}
	
	private void loadSuppliers(){
		Intent iFindSupplies = new Intent(getActivity().getBaseContext(), MainActivity.class);
		Bundle bndNavigation = ActivityOptions.makeCustomAnimation(getActivity().getApplicationContext(), R.animator.slide_in_left, R.animator.slide_out_left).toBundle();
		getActivity().startActivity(iFindSupplies, bndNavigation);
	}
	
	private void setUserContactMethodBy(String sContactMethod){
		GlobalScope.preferences.setUserContactMethod(sContactMethod);
	}
	
	private boolean isPassedToday(int nY, int nM, int nD){
		int nCurrentYear = Calendar.getInstance().get(Calendar.YEAR);
		int nCurrentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int nCurrentDate = Calendar.getInstance().get(Calendar.DATE);
		
		if (nCurrentYear < nY)return false;
		if (nM < nCurrentMonth || (nM == nCurrentMonth && nD < nCurrentDate))return true;
		
		return false;
	}
	
	private void selectWeddingDate(){
		if (isPassedToday(pickerYear.getValue(), pickerMonth.getValue() + 1, pickerDay.getValue())){
			singleAlert.setTitle(GlobalScope.alertDialogErrorTitle);
			singleAlert.setMessage("The date you have selected for you wedding is in the past, please choose an upcoming date");
			singleAlert.show();
		} else {
			btnWeddindDate.setText(pickerDay.getValue() + " / " + GlobalScope.arrMonth[pickerMonth.getValue()] + " / " + pickerYear.getValue());
			hideDatePicker();
		}
	}
	
	private void loadTermsPage(){
		GlobalScope.sWebPageUrl = "http://www.theholybridal.co.uk/terms/";
		Intent iWebPage = new Intent(getActivity().getApplicationContext(), WebPage.class);
		Bundle bndNavigation = ActivityOptions.makeCustomAnimation(getActivity().getApplicationContext(), R.animator.push_up_in, R.animator.push_up_out).toBundle();
		getActivity().startActivity(iWebPage, bndNavigation);
	}
}
