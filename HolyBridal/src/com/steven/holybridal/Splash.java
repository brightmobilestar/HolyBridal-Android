package com.steven.holybridal;

import com.steven.model.GlobalScope;
import com.steven.service.GPSTracker;
import com.steven.service.HolyBrialService;
import com.steven.service.Preferences;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.Toast;


public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        GlobalScope.preferences = new Preferences(this);
        GlobalScope.backend = new HolyBrialService();
        
        GlobalScope.currentUser = GlobalScope.preferences.getUserInfo();
        
        new Handler().postDelayed(new Runnable() {
        	 
            // Showing splash screen with a timer.
 
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
            	
            	Intent iNextActivity = null;
            	
            	if (GlobalScope.preferences.isLogin()){
            		iNextActivity = new Intent(Splash.this, MainActivity.class);
            	} else {
            		iNextActivity = new Intent(Splash.this, Login.class);	
            	}
            	
                startActivity(iNextActivity);
                finish();
            }
        }, GlobalScope.SPLASH_TIME_OUT);
    }
}
