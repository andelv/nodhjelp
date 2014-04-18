package no.hiof.android.nodhjelp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class HistoryMap extends ActionBarActivity {

	private GoogleMap map;
	double[] histPos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_map);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(59.22,
				10.94), 15));

		PolylineOptions line = new PolylineOptions();
		line.width(5);
		line.color(Color.RED);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		histPos = bundle.getDoubleArray("HISTORY POSITIONS");

		for (int i = 0; i < histPos.length / 2; i++) {
			LatLng points = new LatLng(histPos[2 * i], histPos[2 * i + 1]);
			line.add(points);
		}

		map.addPolyline(line);

	}

	/*
	 * Polyline myLine = map.addPolyline(new PolylineOptions() .add(new
	 * LatLng(59, 10)) .width(1) .color(Color.RED));
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

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

		case R.id.get_hospital_map:
			Intent intenHos = new Intent(this, HospitalMap.class);
			startActivity(intenHos);
			return true;

		default:
			return super.onOptionsItemSelected(item);

		}
	}

}
