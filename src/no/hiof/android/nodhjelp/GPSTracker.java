package no.hiof.android.nodhjelp;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class GPSTracker extends ActionBarActivity {

	ListView listView;
	List<RowItem> rowItems;

	/*
	 * private ArrayList<Object> listContents; private ListView list;
	 */

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.call_emergency:
			UtilEMSCall.alertMessage(this);
			return true;

		case R.id.get_gps_pos:
			Intent intent = new Intent(this, PositionActivity.class);
			startActivity(intent);
			return true;

		case R.id.get_gps_history:
			Intent intentGPS = new Intent(this, GPSTracker.class);
			startActivity(intentGPS);
			return true;

		case R.id.get_hospital_map:
			Intent intenHos = new Intent(this, HospitalMap.class);
			startActivity(intenHos);
			return true;

		default:
			return super.onOptionsItemSelected(item);

		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gpstracker);

		UtilGPSdb gpsdb = new UtilGPSdb(this);
		gpsdb.open();
		Cursor cursor = gpsdb.getAllRows();
		cursor.moveToFirst();
		// listContents = new ArrayList<Object>();

		rowItems = new ArrayList<RowItem>();

		for (int i = 0; i < cursor.getCount(); i++) {
			RowItem item = new RowItem(cursor.getString(4), "lat: "
					+ cursor.getString(1), ", lon: " + cursor.getString(2),
					", alt: " + cursor.getString(3));

			rowItems.add(item);
			cursor.moveToNext();
		}
		gpsdb.close();

		listView = (ListView) findViewById(R.id.listView1);
		CustomListViewAdapter adapter = new CustomListViewAdapter(this,
				R.id.list_item, rowItems);
		listView.setAdapter(adapter);

		/*
		 * ArrayAdapter adapter = new ArrayAdapter(this,
		 * android.R.layout.simple_dropdown_item_1line, listContents); list =
		 * (ListView) findViewById(R.id.listView1); list.setAdapter(adapter);
		 */

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
