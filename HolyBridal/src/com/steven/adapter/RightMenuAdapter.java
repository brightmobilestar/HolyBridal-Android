package com.steven.adapter;

import java.util.List;
import java.util.prefs.Preferences;

import com.steven.holybridal.R;
import com.steven.model.GlobalScope;
import com.steven.model.RightMenuItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class RightMenuAdapter extends BaseAdapter {
	private final Context context;
	private final List<RightMenuItem> menuItems;

	public RightMenuAdapter(Context c, List<RightMenuItem> paramMenuItems) {
		context = c;
		menuItems = paramMenuItems;
	}

	@Override
	public int getCount() {
		return menuItems.size();
	}

	@Override
	public Object getItem(int location) {
		return menuItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View rowView = convertView;
		
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			rowView = inflater.inflate(R.layout.cell_right_menu, parent, false);

			holder = new ViewHolder();
			holder.imvRightMenuIndividualItem = (ImageView) rowView.findViewById(R.id.imvRightMenuIndividualItem);
			holder.imvRightMenuItemBackground = (ImageView) rowView.findViewById(R.id.imvRightMenuItemBackground);

			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}

		RightMenuItem menuItem = menuItems.get(position);

		holder.imvRightMenuItemBackground.setTag(position);
		holder.imvRightMenuIndividualItem.setTag(position + 1000);
		
		if (GlobalScope.nIndexRightSlideMenu == position){
			holder.imvRightMenuItemBackground.setBackgroundColor(context.getResources().getColor(R.color.slideItemSelected));
			holder.imvRightMenuIndividualItem.setBackgroundResource(menuItem.nIdentifierSelected);
		} else {
			holder.imvRightMenuItemBackground.setBackgroundColor(context.getResources().getColor(R.color.slideItemNormal));
			holder.imvRightMenuIndividualItem.setBackgroundResource(menuItem.nIdentifierNormal);
		}
		
		return rowView;
	}
	
	private class ViewHolder {
		ImageView imvRightMenuItemBackground;
		ImageView imvRightMenuIndividualItem;
	}
}
