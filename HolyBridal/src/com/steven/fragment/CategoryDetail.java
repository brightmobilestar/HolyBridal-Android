package com.steven.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;

import javax.microedition.khronos.opengles.GL;

import org.json.JSONException;

import com.google.android.gms.internal.gl;
import com.steven.adapter.ImageAdapter;
import com.steven.adapter.SupplierAdapter;
import com.steven.holybridal.MainActivity;
import com.steven.holybridal.R;
import com.steven.holybridal.WebPage;
import com.steven.model.GlobalScope;
import com.steven.utils.OnSwipeTouchListener;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.LinearGradient;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryDetail extends Fragment implements OnClickListener {
    private Button btnBackFromCategoryDetailToCategory;
    private Button btnBackFromCategoryDetailToCategory2;
    private Button btnFavoriteCategory; 
    private Button btnFavoriteCategory2;
    private TextView txvCategoryName;
    private MainActivity parent;
	private TextView txvCategorySmallLabel;
	private TextView txvCategoryBigLabel;
	private TextView txVSpecilizedIn;
	private TextView txvAbout;
	private TextView txvCategoryDescription;
	private TextView txvTouch;
	private TextView txvEmail;
	private TextView txvSupplierEmail;
	private TextView txvMobile;
	private TextView txvSupplierMobile;
	private TextView txvAddress;
	private TextView txvSupplierAddress;
	private TextView txvWebsite;
	private TextView txvSupplierWebsite;
	private RelativeLayout lltCategoryDetailWrapper;
	private View rootView;
	private Button btnTouchViaEmail;
	private Button btnTouchViaMobile;
	private Button btnTouchViaAddress;
	private Button btnTouchViaWebsite;
	private Button btnContactRequest;
	private AlertDialog singleAlert;
	private AlertDialog doubleAlert;
	private LinearLayout lltSpecializedBtnWrapper;
	private LinearLayout lltPageIndicatorWrapper;
	private int nStartButtonResId = 19881031;
	ProgressDialog pd;
	private ImageView imvLogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	parent = (MainActivity)getActivity();
    	
    	rootView = inflater.inflate(R.layout.fragment_category_detail, container, false);
        
    	pd = new ProgressDialog(getActivity(), R.style.MyTheme);
//        pd.setMessage("Dowonloading Images..");
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small); 
        pd.setCancelable(false);
        pd.show(); 
        arrangeObjects();
        
        return rootView;
    }
    
    private void arrangeObjects(){
        Typeface font3 = Typeface.createFromAsset(getActivity().getAssets(), "atwriter.ttf");

    	lltSpecializedBtnWrapper = (LinearLayout) rootView.findViewById(R.id.lltSpecializedBtnWrapper);
    	lltPageIndicatorWrapper = (LinearLayout) rootView.findViewById(R.id.lltPageIndicatorWrapper);
    	
    	for (int i = 0 ; i < GlobalScope.currentCategory.arrFeaturedImages.size() ; i++){
    		Button btnIndicator = new Button(parent);
    		LayoutParams params = new LayoutParams(20, 20);
    		params.setMargins(10, 0, 10, 0);
    		btnIndicator.setLayoutParams(params);
    		btnIndicator.setBackgroundResource(i == 0 ? R.drawable.btn_page_selected : R.drawable.btn_page);
    		btnIndicator.setId(nStartButtonResId + i);
    		
    		lltPageIndicatorWrapper.addView(btnIndicator);
    	}
    	
    	for (int i = 0 ; i < GlobalScope.currentCategory.arrSuppliers.size() ; i ++){
        	TextView txvSpecializedIn = new TextView(parent);
        	LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, 50);
        	params.setMargins(5, 0, 5, 0);
        	txvSpecializedIn.setLayoutParams(params);
        	txvSpecializedIn.setText(GlobalScope.currentCategory.arrSuppliers.get(i).sSupplierName);
        	txvSpecializedIn.setBackgroundResource(R.color.pinkColor);
        	txvSpecializedIn.setTextSize(9);
        	txvSpecializedIn.setTextColor(parent.getResources().getColor(R.color.whiteColor));
        	txvSpecializedIn.setId(i);
        	txvSpecializedIn.setTypeface(font3);
        	txvSpecializedIn.setPadding(10, 3, 10, 3);
        	txvSpecializedIn.setGravity(Gravity.CENTER);
        	
        	lltSpecializedBtnWrapper.addView(txvSpecializedIn);
    	}
    	
    	imvLogo = (ImageView) rootView.findViewById(R.id.imvLogo);
    	new TheTask().execute();
    	
    	btnTouchViaEmail = (Button) rootView.findViewById(R.id.btnTouchViaEmail);
    	btnTouchViaEmail.setOnClickListener(this);
    	
    	btnTouchViaMobile = (Button) rootView.findViewById(R.id.btnTouchViaMobile);
    	btnTouchViaMobile.setOnClickListener(this);
    	
    	btnTouchViaAddress = (Button) rootView.findViewById(R.id.btnTouchViaAddress);
    	btnTouchViaAddress.setOnClickListener(this);
    	
    	btnTouchViaWebsite = (Button) rootView.findViewById(R.id.btnTouchViaWebsite);
    	btnTouchViaWebsite.setOnClickListener(this);
    	
    	btnContactRequest = (Button) rootView.findViewById(R.id.btnContactRequest);
    	btnContactRequest.setOnClickListener(this);
    	
    	btnFavoriteCategory = (Button) rootView.findViewById(R.id.btnFavoriteCategory);
    	btnFavoriteCategory.setOnClickListener(this);
        
    	btnFavoriteCategory2 = (Button) rootView.findViewById(R.id.btnFavoriteCategory2);
    	btnFavoriteCategory2.setOnClickListener(this);
        
        drawFavoriteButton();
        
        btnBackFromCategoryDetailToCategory = (Button) rootView.findViewById(R.id.btnBackFromCategoryDetailToCategory);
        btnBackFromCategoryDetailToCategory.setOnClickListener(this);
        
        btnBackFromCategoryDetailToCategory2 = (Button) rootView.findViewById(R.id.btnBackFromCategoryDetailToCategory2);
        btnBackFromCategoryDetailToCategory2.setOnClickListener(this);
        
        Typeface font = Typeface.createFromAsset(parent.getAssets(), "atwriter.ttf");
        
        txvCategoryName = (TextView) rootView.findViewById(R.id.txvCategoryDetailTobBarTitle);
        txvCategoryName.setText(GlobalScope.currentCategory.sName);
        txvCategoryName.setTypeface(font);
        
        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        
        
        
        final ImageAdapter adapter = new ImageAdapter(parent, GlobalScope.currentCategory.arrFeaturedImages);
        viewPager.setAdapter(adapter);
        
        viewPager.setOnPageChangeListener(new OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int position) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				int nFirstImageButtonResId = nStartButtonResId;
		        
		        for (int i = 0 ; i < GlobalScope.currentCategory.arrFeaturedImages.size() ; i ++){
		      	  rootView.findViewById(nFirstImageButtonResId + i).setBackgroundResource(R.drawable.btn_page);
		        }
		        
		        rootView.findViewById(nFirstImageButtonResId + position).setBackgroundResource(R.drawable.btn_page_selected);
			}
        	
        });
        
//        txvCategorySmallLabel = (TextView) rootView.findViewById(R.id.txvCategorySmallLabel);
//        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "Baskerville.ttf");
//        txvCategorySmallLabel.setTypeface(font1);
//        txvCategorySmallLabel.setText("The Florist For Your Dream Devon Wedding");
//        
//        txvCategoryBigLabel = (TextView) rootView.findViewById(R.id.txvCategoryBigLabel);
//        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "Verdana.ttf");
//        txvCategoryBigLabel.setTypeface(font2);
//        txvCategoryBigLabel.setText("Anna Rawlinson");
        
        txVSpecilizedIn = (TextView) rootView.findViewById(R.id.txVSpecilizedIn);
        txVSpecilizedIn.setTypeface(font3);
        
        txvAbout = (TextView) rootView.findViewById(R.id.txvAbout);
        txvAbout.setTypeface(font3);
        
        txvCategoryDescription = (TextView) rootView.findViewById(R.id.txvCategoryDescription);
        txvCategoryDescription.setText(GlobalScope.currentCategory.sAbout);
        txvCategoryDescription.setTypeface(font3);
        
        txvTouch = (TextView) rootView.findViewById(R.id.txvTouch);
        txvTouch.setTypeface(font3);
        
        txvEmail = (TextView) rootView.findViewById(R.id.txvEmail);
        txvEmail.setTypeface(font3);
        
        txvSupplierEmail = (TextView) rootView.findViewById(R.id.txvSupplierEmail);
        txvSupplierEmail.setText(GlobalScope.currentCategory.sEmail);
        txvSupplierEmail.setTypeface(font3);
        
        txvMobile = (TextView) rootView.findViewById(R.id.txvMobile);
        txvMobile.setTypeface(font3);
        
        txvSupplierMobile = (TextView) rootView.findViewById(R.id.txvSupplierMobile);
        txvSupplierMobile.setText(GlobalScope.currentCategory.sTelephone);
        txvSupplierMobile.setTypeface(font3);
        
        txvAddress = (TextView) rootView.findViewById(R.id.txvAddress);
        txvAddress.setTypeface(font3);
        
        txvSupplierAddress = (TextView) rootView.findViewById(R.id.txvSupplierAddress);
        txvSupplierAddress.setText(GlobalScope.currentCategory.sAddress_l1 + "\n" + GlobalScope.currentCategory.sAddress_l2 + "\n" + GlobalScope.currentCategory.sTown + "\n" + GlobalScope.currentCategory.sCountry + "\n" + GlobalScope.currentCategory.sPostcode);
        txvSupplierAddress.setTypeface(font3);
        
        txvWebsite = (TextView) rootView.findViewById(R.id.txvWebsite);
        txvWebsite.setTypeface(font3);
        
        txvSupplierWebsite = (TextView) rootView.findViewById(R.id.txvSupplierWebsite);
        txvSupplierWebsite.setText(GlobalScope.currentCategory.sWebsite);
        txvSupplierWebsite.setTypeface(font3);
        
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
        
        lltCategoryDetailWrapper = (RelativeLayout) rootView.findViewById(R.id.lltCategoryDetailWrapper);
        lltCategoryDetailWrapper.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {

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
        
        singleAlert = new AlertDialog.Builder(getActivity()).setIcon(R.drawable.ic_launcher).setPositiveButton("OK", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
		}}).create();
        
        
    }
    
    class TheTask extends AsyncTask<Void,Void,Void>
    {
    	Bitmap image;
    	
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            try
            {
            //URL url = new URL( "http://a3.twimg.com/profile_images/670625317/aam-logo-v3-twitter.png");


            image = parent.downloadBitmap(GlobalScope.currentCategory.sLogo);
            
            
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pd.dismiss();
            if(image!=null)
            {
            	imvLogo.setImageBitmap(image);

            }

        }   
    }
    
    private void drawFavoriteButton(){
        if (GlobalScope.preferences.isFavoriteCategory(GlobalScope.currentCategory.sID))btnFavoriteCategory.setBackgroundResource(R.drawable.btn_favorite_remove);
        else btnFavoriteCategory.setBackgroundResource(R.drawable.btn_favorite);
    }
    
    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}
	
	private void sendEmail(){
	  String[] recipients = {GlobalScope.currentCategory.sEmail};
	  Intent email = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
	  email.setType("message/rfc822");
	  email.putExtra(Intent.EXTRA_EMAIL, recipients);
	  email.putExtra(Intent.EXTRA_SUBJECT, "Enquiry from The Holy Bridal");
	  email.putExtra(Intent.EXTRA_TEXT, "");
	  try {
	     startActivity(Intent.createChooser(email, "Choose an email client from..."));
	  } catch (android.content.ActivityNotFoundException ex) {
	     Toast.makeText(parent, "No email client installed.", Toast.LENGTH_LONG).show();
	  }
    }
	
	private void makeCall(){
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:" + GlobalScope.currentCategory.sTelephone));
		parent.startActivity(intent);
	}

	private void loadMap(String sWebPageUrl){
		GlobalScope.sWebPageUrl = sWebPageUrl;
		Intent iWebPage = new Intent(parent.getApplicationContext(), WebPage.class);
		Bundle bndNavigation = ActivityOptions.makeCustomAnimation(getActivity().getApplicationContext(), R.animator.push_up_in, R.animator.push_up_out).toBundle();
		getActivity().startActivity(iWebPage, bndNavigation);
	}
	
	private void registerFavorite(){
		if (GlobalScope.preferences.isFavoriteCategory(GlobalScope.currentCategory.sID)){
			doubleAlert = new AlertDialog.Builder(parent).setTitle("Already Favourited").setMessage("You have already added this supplier to your favourites, would you like to remove it?").setIcon(R.drawable.ic_launcher).setPositiveButton("Remove", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					GlobalScope.preferences.setFavoriteCategory(GlobalScope.currentCategory.sID, false);
					drawFavoriteButton();
					
				}
			}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			}).create();
			
			doubleAlert.show();
		} else {
			GlobalScope.preferences.setFavoriteCategory(GlobalScope.currentCategory.sID, true);
			drawFavoriteButton();
			singleAlert.setTitle("Success");
			singleAlert.setMessage("The supplier has been added to your favourites!");
			singleAlert.show();
		}
	}
	
	private void requestContactByThread(){
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
				String sResponse = "";
				try {
					sResponse = GlobalScope.backend.requestContact(GlobalScope.currentCategory.sID);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				showResult(sResponse);
		    }
		});

		thread.start(); 
	}
	
	private void showResult(final String sResponse){
    	parent.runOnUiThread(new Runnable() {
    	    public void run() {
    			if (sResponse.equals("")){
    				singleAlert.setTitle("Contact requested");
    				singleAlert.setMessage("Your details have successfully been sent to the supplier.");
    				singleAlert.show();
    			} else {
    				singleAlert.setTitle("Enquiry from The Holy Bridal");
    				singleAlert.setMessage(sResponse);
    				singleAlert.show();
    			}
    	    }
    	});
	}
	
	private void requestContact(){
		doubleAlert = new AlertDialog.Builder(parent).setTitle("Request contact").setMessage("Are you sure you wish to request contact? Your details will be sent to this supplier so they can get in touch with you.").setIcon(R.drawable.ic_launcher).setPositiveButton("Request", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				GlobalScope.currentUser = GlobalScope.preferences.getUserInfo();
				requestContactByThread();
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}).create();
		
		doubleAlert.show();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnBackFromCategoryDetailToCategory:
			if(GlobalScope.isFromFavorite) parent.returnFragmentByAnimation(new FavoriteSuppliers());
			else parent.returnFragmentByAnimation(new FindCategory());
			break;
		case R.id.btnBackFromCategoryDetailToCategory2:
			if(GlobalScope.isFromFavorite) parent.returnFragmentByAnimation(new FavoriteSuppliers());
			else parent.returnFragmentByAnimation(new FindCategory());
			break;
		case R.id.btnFavoriteCategory:
			registerFavorite();
			break;
		case R.id.btnFavoriteCategory2:
			registerFavorite();
			break;
		case R.id.btnTouchViaEmail:
			sendEmail();
			break;
		case R.id.btnTouchViaMobile:
			makeCall();
			break;
		case R.id.btnTouchViaAddress:
			loadMap("https://www.google.com.sg/maps?q=" + GlobalScope.currentCategory.sLocationLat + "," + GlobalScope.currentCategory.sLocationLng);
			break;
		case R.id.btnTouchViaWebsite:
			loadMap("http://" + GlobalScope.currentCategory.sWebsite);
			break;
		case R.id.btnContactRequest:
			requestContact();
			break;
		default:
			break;
		}
	}
}


