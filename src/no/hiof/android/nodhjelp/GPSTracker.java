package no.hiof.android.nodhjelp;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class GPSTracker extends ActionBarActivity {

	ListView listView;
	List<RowItem> rowItems;
	List<RowItem> rowItems2;
	double[] historyPos;

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
			
		case R.id.get_show_contacts:
			Intent intentContacts = new Intent(this, ShowContacts.class);
			startActivity(intentContacts);
			return true;
			
		case R.id.get_gps_history:
			Intent intentGPS = new Intent(this, GPSTracker.class);
			startActivity(intentGPS);
			return true;
			
		case R.id.get_show_instructions:
			Intent intentInstructions = new Intent(this, FirstAidInstructions.class);
			startActivity(intentInstructions);
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

		final Button btnSend = (Button) findViewById(R.id.btn_send);
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final int historyNr = Integer
						.parseInt(((EditText) findViewById(R.id.edit_msg))
								.getText().toString());

				UtilGPSdb shortDb = new UtilGPSdb(GPSTracker.this);
				shortDb.open();
				final Cursor cursorShort = shortDb.getAllRows();
				cursorShort.moveToFirst();

				rowItems2 = new ArrayList<RowItem>();

				historyPos = new double[historyNr * 2];

				for (int i = 0; i < historyNr; i++) {
					RowItem item = new RowItem(cursorShort.getString(4),
							"lat: " + cursorShort.getString(1), ", lon: "
									+ cursorShort.getString(2), ", alt: "
									+ cursorShort.getString(3));

					rowItems2.add(item);
					historyPos[2 * i] = (Double.parseDouble(cursorShort
							.getString(1)));// lat
					historyPos[2 * i + 1] = (Double.parseDouble(cursorShort
							.getString(2)));// long
					cursorShort.moveToNext();
					cursorShort.isAfterLast();

				}

				shortDb.close();

				listView = (ListView) findViewById(R.id.listView1);
				CustomListViewAdapter adapter = new CustomListViewAdapter(
						GPSTracker.this, R.id.list_item, rowItems2);
				listView.setAdapter(adapter);

				btnSend.setVisibility(View.VISIBLE);

				final Button btnShowMap = (Button) findViewById(R.id.btn_show_map);
				btnShowMap.setVisibility(View.VISIBLE);
				btnShowMap.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(GPSTracker.this,
								HistoryMap.class);
						intent.putExtra("HISTORY POSITIONS", historyPos);
						startActivity(intent);
					}
				});

			}
		});

		UtilGPSdb gpsdb = new UtilGPSdb(this);
		gpsdb.open();
		Cursor cursor = gpsdb.getAllRows();
		cursor.moveToFirst();

		rowItems = new ArrayList<RowItem>();

		for (int i = 0; i < cursor.getCount(); i++) {
			RowItem item = new RowItem(cursor.getString(4), "lat: "
					+ cursor.getString(1), ", lon: " + cursor.getString(2),
					", alt: " + cursor.getString(3));

			rowItems.add(item);
			cursor.moveToNext();
		}

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
