package com.steven.fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.steven.adapter.SupplierAdapter;
import com.steven.holybridal.MainActivity;
import com.steven.holybridal.R;
import com.steven.model.CategoryItem;
import com.steven.model.GlobalScope;
import com.steven.model.SupplierItem;
import com.steven.utils.OnSwipeTouchListener;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.test.UiThreadTest;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class FindSuppliers extends Fragment implements OnItemClickListener{
    private Button btnLoadRightSideMenu;
    private Button btnLoadRightSideMenu2;
    private Button btnSearchSupplies; 
    private Button btnSearchSupplies2;
    private RelativeLayout rltSupplierSearchBar;
    private RelativeLayout rltTopBar;
    private boolean isSearching = true;
    private EditText edtSearchSuppliers;
    TextView txvFindSupplies;
    private ListView lstFindSuppliersMain;
    private SupplierAdapter adapter;
    private MainActivity parent;
    private View rootView;
    private AlertDialog singleAlert;
    private Typeface font;
    private ArrayList<SupplierItem> arrS;
    private RelativeLayout rltProgressBarWrapper;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	parent = (MainActivity)getActivity();
    	
    	rootView = inflater.inflate(R.layout.fragment_find_suppliers, container, false);
    	
    	font = Typeface.createFromAsset(parent.getAssets(), "atwriter.ttf");
    	
    	if (GlobalScope.arrSuppliers == null)GlobalScope.arrSuppliers = new ArrayList<SupplierItem>();
    	
    	arrS = GlobalScope.arrSuppliers;
    	
    	arrangeObjects();
        
    	if (GlobalScope.arrSuppliers.size() == 0)getSuppliersInfo();
    	
		return rootView;
    }
    
    private void getSuppliersInfo(){
    	rltProgressBarWrapper.setVisibility(View.VISIBLE);
    	
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		    	String sRequestUrl = GlobalScope.SERVER_URL + "/api/v1/supplier/categories";
		    	String sResponse = GlobalScope.backend.toString(GlobalScope.backend.responseHTTP(sRequestUrl));
		    	
		    	try {
					parseHTTPResponse(sResponse);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					reloadListView();
					Toast.makeText(getActivity(), "Server Wrong!", Toast.LENGTH_LONG).show();
				}
		    }
		});

		thread.start(); 
    }
    
    private void parseHTTPResponse(String sResponse) throws JSONException{
    	GlobalScope.backend.getAllSuppliersInfo(sResponse);
    	arrS = GlobalScope.arrSuppliers;
    	reloadListView();
    }
    
    private void reloadListView(){
    	parent.runOnUiThread(new Runnable() {
    	    public void run() {
    	        adapter = new SupplierAdapter(parent.getBaseContext(), arrS);
    	        lstFindSuppliersMain.setAdapter(adapter);
    	    	adapter.notifyDataSetChanged();
    	    	rltProgressBarWrapper.setVisibility(View.GONE);
    	    }
    	});
    }
    
    private void arrangeObjects(){
    	rltSupplierSearchBar = (RelativeLayout) rootView.findViewById(R.id.rltSupplierSearchBar);
        rltTopBar = (RelativeLayout) rootView.findViewById(R.id.rltTopBar);
        
        edtSearchSuppliers = (EditText)rootView.findViewById(R.id.edtSearchSuppliers);        
        edtSearchSuppliers.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				arrS = new ArrayList<SupplierItem>();
				for (int i = 0 ; i < GlobalScope.arrSuppliers.size() ; i++){
					SupplierItem sItem = new SupplierItem();
					sItem = GlobalScope.arrSuppliers.get(i);
					if (sItem.sSupplierName.toLowerCase().indexOf(s.toString().toLowerCase()) != -1){
						arrS.add(sItem);
					}
				}
				reloadListView();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
        
        btnLoadRightSideMenu = (Button) rootView.findViewById(R.id.btnLoadRightSideMenu);
        btnLoadRightSideMenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show/hide the menu
            	if (rltProgressBarWrapper.getVisibility() == View.GONE)parent.loadRightMenu();
            }
        });
        
        btnLoadRightSideMenu2 = (Button) rootView.findViewById(R.id.btnLoadRightSideMenu2);
        btnLoadRightSideMenu2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show/hide the menu
            	if (rltProgressBarWrapper.getVisibility() == View.GONE)parent.loadRightMenu();
            }
        });
        
        btnSearchSupplies = (Button) rootView.findViewById(R.id.btnSearchSupplies);
        btnSearchSupplies.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (rltProgressBarWrapper.getVisibility() == View.GONE)slideDownSearchBar();
			}
		});
        
        btnSearchSupplies2 = (Button) rootView.findViewById(R.id.btnSearchSupplies2);
        btnSearchSupplies2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (rltProgressBarWrapper.getVisibility() == View.GONE)slideDownSearchBar();
			}
		});
        
        // Get title textview
        txvFindSupplies = (TextView) rootView.findViewById(R.id.txvFindSupplies);
        txvFindSupplies.setTypeface(font);
        
        lstFindSuppliersMain = (ListView) rootView.findViewById(R.id.lstSupplierList);
        
        adapter = new SupplierAdapter(parent.getBaseContext(), GlobalScope.arrSuppliers);
        lstFindSuppliersMain.setAdapter(adapter);
        
        lstFindSuppliersMain.setOnItemClickListener(this);
        
        lstFindSuppliersMain.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				hideSearchBar();
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
        });
        
        rootView.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {

			@Override
			public void onSwipeLeft() {
				if (rltProgressBarWrapper.getVisibility() == View.GONE)parent.loadRightMenu();
				super.onSwipeLeft();
			}

			@Override
			public void onSwipeRight() {
				super.onSwipeRight();
			}

		});
        
        lstFindSuppliersMain.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {

        	@Override
			public void onSwipeLeft() {
        		if (rltProgressBarWrapper.getVisibility() == View.GONE)parent.loadRightMenu();
				super.onSwipeLeft();
			}

			@Override
			public void onSwipeRight() {
				super.onSwipeRight();
			}
        	
        });
        
        rltProgressBarWrapper = (RelativeLayout) rootView.findViewById(R.id.rltProgressBarWrapper);
        
        
        singleAlert = new AlertDialog.Builder(getActivity()).setIcon(R.drawable.ic_launcher).setPositiveButton("OK", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
		}}).create();
    }

    private void slideDownSearchBar(){
    	int fMarginTop = isSearching ? rltTopBar.getLayoutParams().height : rltTopBar.getLayoutParams().height - rltSupplierSearchBar.getLayoutParams().height;
    	final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rltSupplierSearchBar.getLayoutParams();
    	ValueAnimator animator = ValueAnimator.ofInt(params.topMargin, fMarginTop);
    	animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
    	    @Override
    	    public void onAnimationUpdate(ValueAnimator valueAnimator)
    	    {
    	        params.topMargin = (Integer) valueAnimator.getAnimatedValue();
    	        rltSupplierSearchBar.requestLayout();
    	    }
    	});
    	animator.setDuration(300);
    	animator.start();
    	isSearching = !isSearching;

    	if (isSearching){
    		InputMethodManager imm = (InputMethodManager)parent.getSystemService(Context.INPUT_METHOD_SERVICE);
    		imm.hideSoftInputFromWindow(edtSearchSuppliers.getWindowToken(), 0);
    		edtSearchSuppliers.setEnabled(false);
    	} else {
    		edtSearchSuppliers.setEnabled(true);
    	}
    }
    
    private void hideSearchBar(){
    	int fMarginTop = rltTopBar.getLayoutParams().height - rltSupplierSearchBar.getLayoutParams().height;
    	final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rltSupplierSearchBar.getLayoutParams();
    	ValueAnimator animator = ValueAnimator.ofInt(params.topMargin, fMarginTop);
    	animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
    	    @Override
    	    public void onAnimationUpdate(ValueAnimator valueAnimator)
    	    {
    	        params.topMargin = (Integer) valueAnimator.getAnimatedValue();
    	        rltSupplierSearchBar.requestLayout();
    	    }
    	});
    	animator.setDuration(300);
    	animator.start();
    	isSearching = false;
		edtSearchSuppliers.setEnabled(false);
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
		// TODO Auto-generated method stub
		parent.hideKeyboard();
		GlobalScope.currentSupplier = arrS.get(position);
		parent.loadFragmentByAnimation(new FindCategory());
	}
}


