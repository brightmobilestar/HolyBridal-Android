package com.steven.holybridal;

import com.steven.model.GlobalScope;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity implements OnClickListener {
	private Button btnGoRegister;
	private Button btnLogin;
	private EditText edtUserEmail;
	private EditText edtUserPassword;
	private Button btnForgotPassword;
	private AlertDialog singleAlert;
	private AlertDialog doubleAlert;
	
	private EditText edtYourEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		initializeObjects();
	}

	private void initializeObjects(){
		singleAlert = new AlertDialog.Builder(this).setIcon(R.drawable.ic_launcher).setPositiveButton("OK", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
		}}).create();		

		btnGoRegister = (Button)findViewById(R.id.btnGoRegister);
		btnGoRegister.setOnClickListener(this);
		btnLogin = (Button)findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(this);
		btnForgotPassword = (Button)findViewById(R.id.btnForgotPassword);
		btnForgotPassword.setOnClickListener(this);
		
		edtUserEmail = (EditText)findViewById(R.id.edtUserEmail);
		edtUserPassword = (EditText)findViewById(R.id.edtUserPassword);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btnGoRegister:
			GlobalScope.isSwipable = false;
			loadRegisterPage();
			finish();
			break;
		case R.id.btnLogin:
			GlobalScope.currentUser = GlobalScope.preferences.getUserInfo();
			
			if (edtUserEmail.getText().toString().equals("")){
				singleAlert.setTitle(GlobalScope.alertDialogErrorTitle);
				singleAlert.setMessage("Please enter email address.");
				singleAlert.show();
			} else if (!edtUserEmail.getText().toString().equals(GlobalScope.currentUser.email) || !edtUserPassword.getText().toString().equals(GlobalScope.currentUser.password)){
				singleAlert.setTitle(GlobalScope.alertDialogErrorTitle);
				singleAlert.setMessage("Please enter correct email address and password.");
				singleAlert.show();
			} else {
				GlobalScope.preferences.registerLogin();
				loadSuppliers();
				finish();
			}
			break;
		case R.id.btnForgotPassword:
			recoveryPassword();
			break;
		default:
			break;
		}
	}
	
	private void loadRegisterPage(){
		Intent iRegister = new Intent(Login.this, Register.class);
		Bundle bndNavigation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.animator.slide_in_left, R.animator.slide_out_left).toBundle();
		startActivity(iRegister, bndNavigation);
	}
	
	private void loadSuppliers(){
		Intent iFindSupplies = new Intent(Login.this, MainActivity.class);
		Bundle bndNavigation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.animator.slide_in_left, R.animator.slide_out_left).toBundle();
		startActivity(iFindSupplies, bndNavigation);
	}
	
	private void recoveryPassword(){	
		edtYourEmail = new EditText(this);
		edtYourEmail.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		
		doubleAlert = new AlertDialog.Builder(this).setTitle("Forgotten password").setMessage("Please enter your email address to recover your password").setView(edtYourEmail).setIcon(R.drawable.ic_launcher).setPositiveButton("Recover", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				registerNewPassword();
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}).create();
		
		doubleAlert.show();
	}
	
	private void registerNewPassword(){
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
				String sPassword = GlobalScope.preferences.randomizePassword();
				String sEmail = edtYourEmail.getText().toString();
				
				String sRequestUrl = GlobalScope.SERVER_URL + "/api/v1/account/password?email=" + sEmail + "&password=" + sPassword;
				String sResponse = GlobalScope.backend.toString(GlobalScope.backend.responseHTTP(sRequestUrl));
				
				GlobalScope.preferences.setUserPassword(sPassword);
		    }
		});

		thread.start(); 
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
	}
}
