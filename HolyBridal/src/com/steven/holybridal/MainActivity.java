package com.steven.holybridal;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.SlidingMenu.OnCloseListener;
import com.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.slidingmenu.lib.SlidingMenu.OnOpenedListener;
import com.steven.adapter.RightMenuAdapter;
import com.steven.fragment.FavoriteSuppliers;
import com.steven.fragment.FindSuppliers;
import com.steven.fragment.FragmentRegister;
import com.steven.model.GlobalScope;
import com.steven.model.RightMenuItem;
import com.steven.service.GPSTracker;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends FragmentActivity {
	private SlidingMenu menu;
	private ListView listView_right_slide;
	
	public GPSTracker gps;
	private boolean isAlreadyAsked = false;
	
	Timer timer;
	TimerTask timerTask;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        GlobalScope.currentLocation = new Location("HolyBridal");
        GlobalScope.currentLocation.setLatitude(0);
        GlobalScope.currentLocation.setLongitude(0);
        
        gps = new GPSTracker(MainActivity.this);
        
        startTracking();
        
        timer = new Timer();
        timerTask = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				startTracking();
			}
		};

		timer.schedule(timerTask, 100, 10000);
		
		
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    LocationListener ll = new mylocationlistener();
	    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
		
        FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();
		fragmentTransaction.replace(R.id.container, new FindSuppliers());
		fragmentTransaction.commit();

		get_references();
        slidemenu_listener();
		
        listView_right_slide = (ListView)findViewById(R.id.lstRightSlideMenu);
     
        initialize_menu();
    }
	
	private class mylocationlistener implements LocationListener {
	    @Override
	    public void onLocationChanged(Location location) {
	        if (location != null) {
	        Log.e("LOCATION CHANGED", location.getLatitude() + "");
	        Log.e("LOCATION CHANGED", location.getLongitude() + "");
//	        Toast.makeText(MainActivity.this,
//	            location.getLatitude() + "" + location.getLongitude(),
//	            Toast.LENGTH_LONG).show();
	        }
	    }
	    @Override
	    public void onProviderDisabled(String provider) {
	    }
	    @Override
	    public void onProviderEnabled(String provider) {
	    }
	    @Override
	    public void onStatusChanged(String provider, int status, Bundle extras) {
	    }
	    }
	
   private void startTracking(){
        // check if GPS enabled     
        if(gps.canGetLocation()){
             
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            
            GlobalScope.currentLocation.setLatitude(latitude);
            GlobalScope.currentLocation.setLongitude(longitude);
             
            Log.e("WI", "Your Location is - \nLat: " + latitude + "\nLong: " + longitude);
        }else if(isAlreadyAsked == false){
        	isAlreadyAsked = true;
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }
	
    private void initialize_menu(){
    	GlobalScope.nIndexRightSlideMenu = 0;
    	
    	int[] nMenuBackgroundImageNormal = new int[4];
	    int[] nMenuBackgroundImageSelected = new int[4];

	    nMenuBackgroundImageNormal[0] = getResources().getIdentifier("menu_find_suppliers1", "drawable", getPackageName());
    	nMenuBackgroundImageNormal[1] = getResources().getIdentifier("menu_favorites1", "drawable", getPackageName());
    	nMenuBackgroundImageNormal[2] = getResources().getIdentifier("menu_my_account1", "drawable", getPackageName());
    	nMenuBackgroundImageNormal[3] = getResources().getIdentifier("menu_share1", "drawable", getPackageName());
    	
    	nMenuBackgroundImageSelected[0] = getResources().getIdentifier("menu_find_suppliers2", "drawable", getPackageName());
    	nMenuBackgroundImageSelected[1] = getResources().getIdentifier("menu_favorites2", "drawable", getPackageName());
    	nMenuBackgroundImageSelected[2] = getResources().getIdentifier("menu_my_account2", "drawable", getPackageName());
    	nMenuBackgroundImageSelected[3] = getResources().getIdentifier("menu_share2", "drawable", getPackageName());
	    
    	GlobalScope.arrRightMenus = new ArrayList<RightMenuItem>();        
        
        for (int i = 0 ; i < 4 ; i ++){
        	RightMenuItem rItem = new RightMenuItem(nMenuBackgroundImageNormal[i], nMenuBackgroundImageSelected[i]);
        	GlobalScope.arrRightMenus.add(rItem);
        }
        
        RightMenuAdapter adapter = new RightMenuAdapter(getBaseContext(), GlobalScope.arrRightMenus);
    	
		listView_right_slide.setCacheColorHint(0);
		listView_right_slide.setDrawSelectorOnTop(true);
		
    	listView_right_slide.setAdapter(adapter);
    }
	
	private void slidemenu_listener(){
    	menu.setOnOpenedListener(new OnOpenedListener() {
			@Override
			public void onOpened() {
				listView_right_slide.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(final AdapterView<?> adapterView, View view, final int position, long lValue) {
						menu.toggle();
	
						if (position < 3){
							int nOriginalMenuIndex = GlobalScope.nIndexRightSlideMenu;
							int nResId = GlobalScope.arrRightMenus.get(nOriginalMenuIndex).nIdentifierNormal;
							adapterView.findViewWithTag(nOriginalMenuIndex + 1000).setBackgroundResource(nResId);
							adapterView.findViewWithTag(nOriginalMenuIndex).setBackgroundColor(getResources().getColor(R.color.slideItemNormal));
							
							GlobalScope.nIndexRightSlideMenu = position;						
							
							nResId = GlobalScope.arrRightMenus.get(position).nIdentifierSelected;
							adapterView.findViewWithTag(position + 1000).setBackgroundResource(nResId);
							adapterView.findViewWithTag(position).setBackgroundColor(getResources().getColor(R.color.slideItemSelected));
						}
						
						switch (position) {
						case 0:
							load_fragment(new FindSuppliers());
							break;
	
						case 1:
							load_fragment(new FavoriteSuppliers());
							break;
	
						case 2:
							GlobalScope.isSwipable = true;
							load_fragment(new FragmentRegister());
							break;
	
						case 3:
							shareApp();
							
							break;
	
						default:
							break;
						}
					}
				});
			}
		});
    	
    	menu.setOnClosedListener(new OnClosedListener() {
			@Override
			public void onClosed() {
			}
		});

		menu.setOnOpenListener(new OnOpenListener() {
			@Override
			public void onOpen() {
			}
		});

		menu.setOnCloseListener(new OnCloseListener() {
			@Override
			public void onClose() {
			}
		});
	}
	
	private void shareApp(){
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.share_subject));
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, getResources().getString(R.string.share_text));
		startActivity(Intent.createChooser(sharingIntent, "Share via"));
	}
	
	private void shareViaFacebookApp(){
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		// intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
		intent.putExtra(Intent.EXTRA_TEXT, "sdfsdfsdf");

		// See if official Facebook app is found
		List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
		for (ResolveInfo info : matches) {
		    if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook")) {
		        intent.setPackage(info.activityInfo.packageName);
		        break;
		    }
		}

		startActivity(intent);
	}
	
	private void get_references(){
    	menu = new SlidingMenu(MainActivity.this);
		menu.setMode(SlidingMenu.LEFT_RIGHT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setSecondaryMenu(R.layout.right_slide);
    }

	public void load_fragment(Fragment fr) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.replace(R.id.container, fr);
		fragmentTransaction.commit();
	}
	
	public void loadRightMenu(){
		hideKeyboard();
		menu.showSecondaryMenu();
	}
	
	@Override
	public void onBackPressed() {
		if (menu.isMenuShowing()) {
			menu.showContent(true);
		} else {
			super.onBackPressed();
		}
	}
	
	public void loadFragmentByAnimation(Fragment fr){
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left);
        ft.addToBackStack(null);
		ft.replace(R.id.container, fr);
		ft.commit();
	}

	public void returnFragmentByAnimation(Fragment fr){
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_right);
		ft.replace(R.id.container, fr);
		ft.commit();
	}
	
	 
	public void hideKeyboard() {   
	    View view = this.getCurrentFocus();
	    if (view != null) {
	        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
	        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	    }
	}
	
	 public Bitmap downloadBitmap(String url) {
    	Bitmap image = null;
        // initilize the default HTTP client object
        final DefaultHttpClient client = new DefaultHttpClient();

        //forming a HttoGet request 
        final HttpGet getRequest = new HttpGet(url);
        try {

            HttpResponse response = client.execute(getRequest);

            //check 200 OK for success
            final int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
                Log.w("ImageDownloader", "Error " + statusCode + 
                        " while retrieving bitmap from " + url);
                return null;

            }

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    // getting contents from the stream 
                    inputStream = entity.getContent();

                    // decoding stream data back into image Bitmap that android understands
                    image = BitmapFactory.decodeStream(inputStream);


                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (Exception e) {
            // You Could provide a more explicit error message for IOException
            getRequest.abort();
            Log.e("ImageDownloader", "Something went wrong while" +
                    " retrieving bitmap from " + url + e.toString());
        } 

        return image;
    }
}
