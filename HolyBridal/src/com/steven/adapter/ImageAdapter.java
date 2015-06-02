package com.steven.adapter;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.androidquery.AQuery;
import com.steven.holybridal.MainActivity;
import com.steven.holybridal.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageAdapter extends PagerAdapter {
	Context context;
	MainActivity parent;
	
	ProgressDialog pd;
	ViewPager viewPager;	
	
    public ArrayList<String> arrGalUrls;
    public ArrayList<ImageView> arrImv;
    
    public ImageAdapter(Context context, ArrayList<String> arrG){
    	this.context=context;
    	
    	parent = (MainActivity) context;
    	
    	arrGalUrls = arrG;
    	arrImv = new ArrayList<ImageView>();
    	
    	pd = new ProgressDialog(context, R.style.MyTheme);
//        pd.setMessage("Dowonloading Images..");
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small); 
        pd.setCancelable(false); 
        pd.show(); 

        for (int i = 0 ; i < arrG.size() ; i ++){
    		ImageView imageView = new ImageView(context);
    		int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_small);
    		imageView.setPadding(padding, padding, padding, padding);
    		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    		int nId = 19941203 + i;
    		imageView.setId(nId);
    		imageView.setImageResource(R.drawable.default_loading);

    		new TheTask(i).execute();
    		
    		arrImv.add(imageView);
    	}
    }
    
    @Override
    public int getCount() {
      return arrGalUrls.size();
    }
    
	@Override
    public boolean isViewFromObject(View view, Object object) {
      return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
    	viewPager =  ((ViewPager) container);
    	viewPager.addView(arrImv.get(position), 0);
    	return arrImv.get(position);
    }
    
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      ((ViewPager) container).removeView((ImageView) object);
    }
    
    class TheTask extends AsyncTask<Void,Void,Void>
    {
    	int nIndex;
    	Bitmap image;

    	public TheTask(int iID) {
	        this.nIndex = iID;
	    }
    	
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


            image = parent.downloadBitmap(arrGalUrls.get(nIndex));
            
            
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
                arrImv.get(nIndex).setImageBitmap(image);

            }

        }   
    }
  }
