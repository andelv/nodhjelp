package com.example.nodhjelp;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class PositionActivity extends ActionBarActivity implements LocationListener{
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		
		case R.id.call_emergency:
			Util.alertMessage(this);
			return true;
			
		case R.id.get_gps_pos:
			Intent intent = new Intent(this, PositionActivity.class);
			startActivity(intent);
			
			
			
		default:
			return super.onOptionsItemSelected(item); }
			
		}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//obtain GPS position
		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
			
	}
	
	@Override
	public void onLocationChanged(Location location) {
		TextView tv = (TextView) findViewById(R.id.GPS_pos);
		float lon,lat,alt, acc, speed;
		lon = (float) location.getLongitude();
		lat = (float) location.getLatitude();
		alt = (float) location.getAltitude();
		acc = (float) location.getAccuracy();
		speed =  (float) location.getSpeed();
		
		String text = "Din posisjon er: " +
		"\nLengdegrader = " + lon +
		"\nBreddegrader = " + lat +
		"\n\nHøyde = " + alt + 
		"\nTreffsikkerhet = " + acc + 
		"\n Fart = " + speed;
		//String toastText = "Din pos er " +lon +" " +lat;
		
		//Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
		tv.setText(text);
		
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
