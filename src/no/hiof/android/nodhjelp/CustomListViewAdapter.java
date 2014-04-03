package no.hiof.android.nodhjelp;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListViewAdapter extends ArrayAdapter<RowItem> {
	/*
	 * Used tutorial from
	 * http://theopentutorials.com/tutorials/android/listview/android
	 * -custom-listview-with-image-and-text-using-arrayadapter
	 */
	
	Context context;

	public CustomListViewAdapter(Context context, int resourceId,
			List<RowItem> items) {
		super(context, resourceId, items);
		this.context = context;
	}

	/* private view holder class */
	private class ViewHolder {
		TextView txtTime;
		TextView txtLat;
		TextView txtLon;
		TextView txtAltitude;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		RowItem rowItem = getItem(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.txtTime = (TextView) convertView.findViewById(R.id.time);
			holder.txtLat = (TextView) convertView.findViewById(R.id.lat);
			holder.txtLon = (TextView) convertView.findViewById(R.id.lon);
			holder.txtAltitude = (TextView) convertView.findViewById(R.id.altitude);
			
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		holder.txtTime.setText(rowItem.getTime());
		holder.txtLat.setText(rowItem.getLat());
		holder.txtLon.setText(rowItem.getLon());
		holder.txtAltitude.setText(rowItem.getAlt());

		return convertView;
	}

}
