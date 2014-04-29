package no.hiof.android.nodhjelp;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

public class HospitalMap extends ActionBarActivity implements LocationListener,
		OnMarkerClickListener {

	private GoogleMap map;
	LatLng nearestHos;
	String displayName;
	float lon, lat;
	public Hospital myHospital;
	boolean hasGSPCoord;

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hospital_map);

		// obtain GPS position
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// update pos
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, this);
		// map
		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();

	}

	@Override
	public void onLocationChanged(Location location) {

		lon = (float) location.getLongitude();
		lat = (float) location.getLatitude();

		if (hasGSPCoord == false) {

			buildMyHos();
			hasGSPCoord = true;
		}

	}

	public void Reader(String string) {
		Gson gson = new Gson();
		myHospital = gson.fromJson(string, Hospital.class);

		LatLng nearestHos = new LatLng(
				Double.valueOf(myHospital.HealthServiceLatitude),
				Double.valueOf(myHospital.HealthServiceLongitude));
		String displayName = new String(myHospital.HealthServiceDisplayName);

		// set a marker and zooms maps to nearest hospital
		map.setOnMarkerClickListener(this);
		Marker Hos = map.addMarker(new MarkerOptions().position(nearestHos)
				.title(displayName));

		Hos.showInfoWindow();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(nearestHos, 15));

	}

	public void gpsHistory() {

	}

	public void buildMyHos() {
		// Get data from helsenorge
		DFiles dF = new DFiles(HospitalMap.this);
		dF.execute("https://data.helsenorge.no/External.svc/Services/KA02/"
				+ HospitalMap.this.lon + "/" + HospitalMap.this.lat);
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		UtilHospitalCall.alertMessage(myHospital.HealthServiceDisplayName,
				myHospital.HealthServicePhone, HospitalMap.this);
		return false;
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

}
