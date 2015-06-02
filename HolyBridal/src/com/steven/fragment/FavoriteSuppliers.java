package com.steven.fragment;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL;

import com.steven.adapter.CategoryAdapter;
import com.steven.adapter.SupplierAdapter;
import com.steven.holybridal.MainActivity;
import com.steven.holybridal.R;
import com.steven.model.CategoryItem;
import com.steven.model.GlobalScope;
import com.steven.model.SupplierItem;
import com.steven.utils.OnSwipeTouchListener;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FavoriteSuppliers extends Fragment implements OnItemClickListener {
	MainActivity parent;
	private CategoryAdapter adapter;
	private TextView txvFavoriteTobBarTitle;
	private ListView lstFavoriteList;
	private Button btnFilter;
	private Button btnFilter2;
	private Button btnMenuForFavoriteFragment;
	private Button btnMenuForFavoriteFragment2;
	private Button btnFilterSuppliers;
	private Button btnSelectFilterItem;
	private RelativeLayout rltPickerWrapper;
	private ArrayList<CategoryItem> mList;
	private NumberPicker pickerFavorite;
	private RelativeLayout rltFavoriteList;
	private View rootView;
	private ArrayList<CategoryItem> arrC;
	private TextView txvNoFavoriteSuppliers;
	private String[] arrItems;
	
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	parent = (MainActivity)getActivity();
    	
    	GlobalScope.isFromFavorite = true;
    	
    	arrC = new ArrayList<CategoryItem>();
    	GlobalScope.arrCategories = new ArrayList<CategoryItem>();
    	
    	rootView = inflater.inflate(R.layout.fragment_favorite, container, false);

    	arrangeObjects();
        
    	getFavoriteData();
    	
    	return rootView;
	}
    
    private void getFavoriteData(){
    	if (GlobalScope.arrTotalCategories != null){
        	for (int i = 0 ; i < GlobalScope.arrTotalCategories.size() ; i ++){
        		CategoryItem cItem = GlobalScope.arrTotalCategories.get(i);
        		if (GlobalScope.preferences.isFavoriteCategory(cItem.sID)){
        			arrC.add(cItem);
        			GlobalScope.arrCategories.add(cItem);
        		}
        	}
    	}
    	
    	reloadListView();
    	setPickerFilter();
    }
	
    private void reloadListView(){
    	parent.runOnUiThread(new Runnable() {
    	    public void run() {
    	        adapter = new CategoryAdapter(parent.getBaseContext(), arrC);
    	        lstFavoriteList.setAdapter(adapter);
    	    	adapter.notifyDataSetChanged();
    	    	if (arrC.size() == 0) txvNoFavoriteSuppliers.setVisibility(View.VISIBLE);	
    	    	else txvNoFavoriteSuppliers.setVisibility(View.GONE);
    	    }
    	});

    }
    
    private void filterFavoriteLists(){
    	rltPickerWrapper.setVisibility(View.VISIBLE);
    	Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(300);

        AnimationSet animation = new AnimationSet(true);
        animation.addAnimation(fadeIn);
        rltPickerWrapper.setAnimation(animation);
    }
    
    private void hidePickerView(){
        Animation fadeOut = new AlphaAnimation(1, 0);
//        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(300);

        AnimationSet animation = new AnimationSet(true);
        animation.addAnimation(fadeOut);
        rltPickerWrapper.setAnimation(animation);
    	rltPickerWrapper.setVisibility(View.GONE);
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
	 
	 private void arrangeObjects(){
    	rltFavoriteList = (RelativeLayout) rootView.findViewById(R.id.rltFavoriteList);
    	
        mList = new ArrayList<CategoryItem>();
        
        GlobalScope.arrCategories = mList;
    	
        txvFavoriteTobBarTitle = (TextView) rootView.findViewById(R.id.txvFavoriteTobBarTitle);
        Typeface font = Typeface.createFromAsset(parent.getAssets(), "atwriter.ttf");
        txvFavoriteTobBarTitle.setTypeface(font);
        
        txvNoFavoriteSuppliers = (TextView) rootView.findViewById(R.id.txvNoFavoriteSuppliers);
        txvNoFavoriteSuppliers.setTypeface(font);
        
        lstFavoriteList = (ListView) rootView.findViewById(R.id.lstFavoriteList);
        
        adapter = new CategoryAdapter(parent.getBaseContext(), arrC);
        lstFavoriteList.setAdapter(adapter);
        lstFavoriteList.setOnItemClickListener(this);
        
        rltPickerWrapper = (RelativeLayout) rootView.findViewById(R.id.rltPickerWrapper);
        
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
        
        lstFavoriteList.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {

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
        
        
        rltPickerWrapper.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {

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
        
        btnFilter = (Button) rootView.findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				filterFavoriteLists();
			}
		});   
        
        btnFilter2 = (Button) rootView.findViewById(R.id.btnFilter2);
        btnFilter2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				filterFavoriteLists();
			}
		});
        
        btnMenuForFavoriteFragment = (Button) rootView.findViewById(R.id.btnMenuForFavoriteFragment);
        btnMenuForFavoriteFragment.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				parent.loadRightMenu();
			}
		});
        
        btnMenuForFavoriteFragment2 = (Button) rootView.findViewById(R.id.btnMenuForFavoriteFragment2);
        btnMenuForFavoriteFragment2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				parent.loadRightMenu();
			}
		});
        
        btnFilterSuppliers = (Button) rootView.findViewById(R.id.btnFilterSuppliers);
        btnFilterSuppliers.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hidePickerView();
			}
		});

        setPickerFilter();
        
        btnSelectFilterItem = (Button) rootView.findViewById(R.id.btnSelectFilterItem);
        btnSelectFilterItem.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hidePickerView();
				filterSuppliers();
			}
		});
	 }
	 
	 private void setPickerFilter(){
        pickerFavorite = (NumberPicker) rootView.findViewById(R.id.pickerFavorite);
        
        ArrayList<String> arrFilterItems = new ArrayList<String>();
        arrFilterItems.add("All");
        
        for (int i = 0 ; i < arrC.size() ; i ++){
        	CategoryItem cItem = arrC.get(i);
        	for (int k, j = 0 ; j < cItem.arrSuppliers.size() ; j ++){
        		String sName = cItem.arrSuppliers.get(j).sSupplierName;
        		for (k = 0 ; k < arrFilterItems.size() ; k ++){
        			if (sName.equals(arrFilterItems.get(k)))break;
        		}
        		if (k == arrFilterItems.size()){
        			arrFilterItems.add(cItem.arrSuppliers.get(j).sSupplierName);
        		}
        	}
        }
        
        pickerFavorite.setDisplayedValues(null);
        
        arrItems = arrFilterItems.toArray(new String[arrFilterItems.size()]);
        
        pickerFavorite.setMaxValue(arrItems.length - 1);
        pickerFavorite.setMinValue(0);
        pickerFavorite.setDisplayedValues(arrItems);
        pickerFavorite.setWrapSelectorWheel(false);
        setDividerColor(pickerFavorite, Color.WHITE);
	 }
	 
	 private void filterSuppliers(){
		 String sFilter = arrItems[pickerFavorite.getValue()];
		 
		 arrC = new ArrayList<CategoryItem>();
		 
		 for (int i = 0 ; i < GlobalScope.arrCategories.size() ; i ++){
			 for (int j = 0 ; j < GlobalScope.arrCategories.get(i).arrSuppliers.size() ; j ++){
				 String sName = GlobalScope.arrCategories.get(i).arrSuppliers.get(j).sSupplierName;
				 
				 if (sFilter.equals("All") || sFilter.equals(sName)){
					 arrC.add(GlobalScope.arrCategories.get(i));
					 break;
				 }
			 }
		 }
		 
		 reloadListView();
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
	public void onItemClick(AdapterView<?> parentView, View view, int position,
			long id) {
		GlobalScope.currentCategory = arrC.get(position);
		parent.loadFragmentByAnimation(new CategoryDetail());
	}
}
