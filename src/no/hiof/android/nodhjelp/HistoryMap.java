package no.hiof.android.nodhjelp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class HistoryMap extends ActionBarActivity {

	double[] histPos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_map);

		// map
		SupportMapFragment map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map));
		map.getMap();

		// options for polyline
		PolylineOptions line = new PolylineOptions();
		line.width(5);
		line.color(Color.RED);

		// gets values from database via intent from gpstracker
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		histPos = bundle.getDoubleArray("HISTORY POSITIONS");

		// moves map cam to first pos
		map.getMap().moveCamera(
				CameraUpdateFactory.newLatLngZoom(new LatLng(histPos[2],
						histPos[3]), 12));

		// add positions to line
		for (int i = 0; i < histPos.length / 2; i++) {
			// 2i = lat 2i +1 = lng
			LatLng points = new LatLng(histPos[2 * i], histPos[2 * i + 1]);
			line.add(points);

		}

		// draw polyline between latlng points
		map.getMap().addPolyline(line);
	}

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

		case R.id.get_show_instructions:
			Intent intentInstructions = new Intent(this,
					FirstAidInstructions.class);
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

}
