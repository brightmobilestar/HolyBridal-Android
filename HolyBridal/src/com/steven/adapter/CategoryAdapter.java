package com.steven.adapter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.steven.holybridal.R;
import com.steven.model.CategoryItem;
import com.steven.model.GlobalScope;

import android.content.Context;
import android.graphics.Typeface;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter {
	private Context context;
	private List<CategoryItem> categoryItems;
	
	public CategoryAdapter(Context c, List<CategoryItem> items) {
		context = c;
		categoryItems = items;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return categoryItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return categoryItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		View rowView = convertView;
		
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			rowView = inflater.inflate(R.layout.cell_category_item, parent, false);

			holder = new ViewHolder();
			holder.txvCategoryName = (TextView) rowView.findViewById(R.id.txvCategoryName);
			holder.txvCategoryDesc = (TextView) rowView.findViewById(R.id.txvCategoryDesc);
			holder.txvCategoryMile = (TextView) rowView.findViewById(R.id.txvCategoryMile);
			
			Typeface font = Typeface.createFromAsset(context.getAssets(), "atwriter.ttf");
			holder.txvCategoryName.setTypeface(font);
			holder.txvCategoryDesc.setTypeface(font);
			holder.txvCategoryMile.setTypeface(font);

			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}

		CategoryItem cItem = categoryItems.get(position);

		holder.txvCategoryName.setText(cItem.sName);
		holder.txvCategoryDesc.setText(cItem.sAddress);
		if (cItem.sLocationLat.equals(""))cItem.sLocationLat = "0";
		if (cItem.sLocationLng.equals(""))cItem.sLocationLng = "0";
		double sMile = GlobalScope.preferences.distance(GlobalScope.currentLocation.getLatitude(), GlobalScope.currentLocation.getLongitude(), Double.parseDouble(cItem.sLocationLat), Double.parseDouble(cItem.sLocationLng), "M");
//		Location selected_location=new Location("locationA");
//	    selected_location.setLatitude(Double.parseDouble(cItem.sLocationLat));
//	    selected_location.setLongitude(Double.parseDouble(cItem.sLocationLng));
//		double sKilometre = GlobalScope.currentLocation.distanceTo(selected_location);
//		sMile = sKilometre / 1000.0 / 1.60934;
		BigDecimal bd = new BigDecimal(sMile).setScale(2, RoundingMode.HALF_EVEN);
		sMile = bd.doubleValue();
		holder.txvCategoryMile.setText(sMile + " miles");
		
		return rowView;
	}

	private class ViewHolder {
		TextView txvCategoryName;
		TextView txvCategoryDesc;
		TextView txvCategoryMile;
	}
}

