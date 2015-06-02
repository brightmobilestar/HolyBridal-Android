package com.steven.adapter;

import java.util.List;

import com.androidquery.AQuery;
import com.steven.holybridal.R;
import com.steven.model.SupplierItem;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SupplierAdapter extends BaseAdapter {
	private Context context;
	private List<SupplierItem> supplierItems;
	
    AQuery aq;
    
	public SupplierAdapter(Context c, List<SupplierItem> items) {
		context = c;
		supplierItems = items;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return supplierItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return supplierItems.get(position);
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

			rowView = inflater.inflate(R.layout.cell_supplier_item, parent, false);

			holder = new ViewHolder();
			holder.imvIndividualSupplier = (ImageView) rowView.findViewById(R.id.imvIndividualSupplier);
			holder.imvFrame = (ImageView) rowView.findViewById(R.id.imvFrame);
			holder.txvSupplierName = (TextView) rowView.findViewById(R.id.txvSupplierName);
			Typeface font = Typeface.createFromAsset(context.getAssets(), "atwriter.ttf");
			holder.txvSupplierName.setTypeface(font);

			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}

		SupplierItem sItem = supplierItems.get(position);

		holder.txvSupplierName.setText(sItem.sSupplierName);
		
//		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//		int nMarginHorizontal = (int) (holder.imvFrame.getLayoutParams().width * 32.0 / 587.0);
//		
//		LinearLayout.LayoutParams lpFrame = (LinearLayout.LayoutParams) holder.imvFrame.getLayoutParams();
//		
//		lp.setMargins(nMarginHorizontal, lpFrame.topMargin, nMarginHorizontal, lpFrame.bottomMargin);
//		holder.imvIndividualSupplier.setLayoutParams(lp);
		
		aq = new AQuery(rowView);
		aq.id(R.id.imvIndividualSupplier).image(sItem.sImageUrl, true, true, 0, R.drawable.flower1);
		
		return rowView;
	}

	private class ViewHolder {
		ImageView imvIndividualSupplier;
		ImageView imvFrame;
		TextView txvSupplierName;
	}
}

