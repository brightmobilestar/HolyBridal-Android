package com.steven.fragment;

import java.util.ArrayList;

import com.steven.adapter.CategoryAdapter;
import com.steven.adapter.SupplierAdapter;
import com.steven.holybridal.MainActivity;
import com.steven.holybridal.R;
import com.steven.model.CategoryItem;
import com.steven.model.GlobalScope;
import com.steven.model.SupplierItem;
import com.steven.utils.OnSwipeTouchListener;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class FindCategory extends Fragment implements OnItemClickListener{
    private Button btnBackFromCategoryToSupplier;
    private Button btnBackFromCategoryToSupplier2;
    private Button btnSearchCategories; 
    private Button btnSearchCategories2;
    private RelativeLayout rltCategorySearchBar;
    private RelativeLayout rltTopBar;
    private boolean isSearching = true;
    private EditText edtSearchCategory;
    private TextView txvFindCategory;
    private MainActivity parent;
    private ListView lstFindCategoriesMain;
    private CategoryAdapter adapter;
    private View rootView;
    private ArrayList<CategoryItem> arrC;
    private Typeface font;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	parent = (MainActivity)getActivity();
    	
    	GlobalScope.isFromFavorite = false;
    	
    	rootView = inflater.inflate(R.layout.fragment_find_category, container, false);
    	
    	arrC = new ArrayList<CategoryItem>();
    	arrangeObjects();
    	loadCategory();
        
		return rootView;
    }

    
    private void loadCategory(){
    	arrC = new ArrayList<CategoryItem>();
    	GlobalScope.arrCategories = new ArrayList<CategoryItem>();
    	
    	for (int i = 0 ; i < GlobalScope.currentSupplier.arrCategories.size() ; i++){
    		CategoryItem cItem = GlobalScope.currentSupplier.arrCategories.get(i);
    		GlobalScope.arrCategories.add(cItem);
    		arrC.add(cItem);
    	}
    	
    	reloadListView();
    }
    
    private void reloadListView(){
    	parent.runOnUiThread(new Runnable() {
    	    public void run() {
    	        adapter = new CategoryAdapter(getActivity().getBaseContext(), arrC);
    	        lstFindCategoriesMain.setAdapter(adapter);
    	    	adapter.notifyDataSetChanged();
    	    }
    	});
    }

    private void slideDownSearchBar(){
    	int fMarginTop = isSearching ? rltTopBar.getLayoutParams().height : rltTopBar.getLayoutParams().height - rltCategorySearchBar.getLayoutParams().height;
    	final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rltCategorySearchBar.getLayoutParams();
    	ValueAnimator animator = ValueAnimator.ofInt(params.topMargin, fMarginTop);
    	animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
    	    @Override
    	    public void onAnimationUpdate(ValueAnimator valueAnimator)
    	    {
    	        params.topMargin = (Integer) valueAnimator.getAnimatedValue();
    	        rltCategorySearchBar.requestLayout();
    	    }
    	});
    	animator.setDuration(300);
    	animator.start();
    	
    	isSearching = !isSearching;

    	if (isSearching){
    		InputMethodManager imm = (InputMethodManager)parent.getSystemService(Context.INPUT_METHOD_SERVICE);
    		imm.hideSoftInputFromWindow(edtSearchCategory.getWindowToken(), 0);
    		edtSearchCategory.setEnabled(false);
    	} else {
    		edtSearchCategory.setEnabled(true);
    	}
    }
    
    
    private void hideSearchBar(){
    	int fMarginTop = rltTopBar.getLayoutParams().height - rltCategorySearchBar.getLayoutParams().height;
    	final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rltCategorySearchBar.getLayoutParams();
    	ValueAnimator animator = ValueAnimator.ofInt(params.topMargin, fMarginTop);
    	animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
    	    @Override
    	    public void onAnimationUpdate(ValueAnimator valueAnimator)
    	    {
    	        params.topMargin = (Integer) valueAnimator.getAnimatedValue();
    	        rltCategorySearchBar.requestLayout();
    	    }
    	});
    	animator.setDuration(300);
    	animator.start();
    	isSearching = false;
		edtSearchCategory.setEnabled(false);
    }
    
    private void arrangeObjects(){
    	rltCategorySearchBar = (RelativeLayout) rootView.findViewById(R.id.rltCategorySearchBar);
        rltTopBar = (RelativeLayout) rootView.findViewById(R.id.rltTopBar2);
        
        edtSearchCategory = (EditText) rootView.findViewById(R.id.edtSearchCategory);
        edtSearchCategory.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				arrC = new ArrayList<CategoryItem>();
				for (int i = 0 ; i < GlobalScope.arrCategories.size() ; i++){
					CategoryItem cItem = new CategoryItem();
					cItem = GlobalScope.arrCategories.get(i);
					if (cItem.sName.toLowerCase().indexOf(s.toString().toLowerCase()) != -1){
						arrC.add(cItem);
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
        
        btnSearchCategories = (Button) rootView.findViewById(R.id.btnSearchCategories);
        btnSearchCategories.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				slideDownSearchBar();
			}
		});
        
        btnSearchCategories2 = (Button) rootView.findViewById(R.id.btnSearchCategories2);
        btnSearchCategories2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				slideDownSearchBar();
			}
		});
        
        btnBackFromCategoryToSupplier = (Button) rootView.findViewById(R.id.btnBackFromCategoryToSupplier);
        btnBackFromCategoryToSupplier.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				parent.getSupportFragmentManager().popBackStackImmediate();
				parent.hideKeyboard();
				parent.returnFragmentByAnimation(new FindSuppliers());
			}
		});
        
        btnBackFromCategoryToSupplier2 = (Button) rootView.findViewById(R.id.btnBackFromCategoryToSupplier2);
        btnBackFromCategoryToSupplier2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				parent.getSupportFragmentManager().popBackStackImmediate();
				parent.hideKeyboard();
				parent.returnFragmentByAnimation(new FindSuppliers());
			}
		});
        
        // Get title textview
        txvFindCategory = (TextView) rootView.findViewById(R.id.txvFindCategory);
        txvFindCategory.setText(GlobalScope.currentSupplier.sSupplierName);
        font = Typeface.createFromAsset(parent.getAssets(), "atwriter.ttf");
        txvFindCategory.setTypeface(font);
        
        lstFindCategoriesMain = (ListView) rootView.findViewById(R.id.lstCategoryList);
        
        adapter = new CategoryAdapter(getActivity().getBaseContext(), arrC);
        lstFindCategoriesMain.setAdapter(adapter);
        lstFindCategoriesMain.setOnItemClickListener(this);
        
        lstFindCategoriesMain.setOnScrollListener(new OnScrollListener() {
			
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
				parent.loadRightMenu();
				super.onSwipeLeft();
			}

			@Override
			public void onSwipeRight() {
				super.onSwipeRight();
			}

		});
        
        lstFindCategoriesMain.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {

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
		GlobalScope.currentCategory = arrC.get(position);
		parent.loadFragmentByAnimation(new CategoryDetail());
	}
}


