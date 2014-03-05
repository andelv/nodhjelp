package no.hiof.android.nodhjelp;

import java.util.ArrayList;

import no.hiof.android.nodhjelp.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class GPSTracker extends ActionBarActivity {
	
	
	private ArrayList<Object> listContents;
	private ListView list;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		
		case R.id.call_emergency:
			Util.alertMessage(this);
			return true;
			
		case R.id.get_gps_pos:
			Intent intent = new Intent(this, PositionActivity.class);
			startActivity(intent);
			return true;
			
		case R.id.get_gps_history:
			Intent intentGPS = new Intent(this, GPSTracker.class);
			startActivity(intentGPS);
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
		listContents = new ArrayList<Object>();
		
		for (int i = 0; i < cursor.getCount(); i++) {
			listContents.add("\nBreddegrader " +cursor.getString(1) + " Lengdegrader " +
					cursor.getString(2) +"Høyde "+ cursor.getString(3) +"Tid " +cursor.getString(4));
			cursor.moveToNext();
		}
		gpsdb.close();
		//ArrayAdapter<T> adapter = new ArrayAdapter<T>(this, android.R.layout.simple_dropdown_item_1line, listContents)
		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,listContents);
		list = (ListView)findViewById(R.id.listView1);
		list.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
