package no.hiof.android.nodhjelp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;

import no.hiof.android.nodhjelp.R;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
			return true;
			
		case R.id.get_gps_history:
			Intent intentGPS = new Intent(this, GPSTracker.class);
			startActivity(intentGPS);
			return true;
			
				
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
		float lon,lat,alt, acc, speed, time;
		lon = (float) location.getLongitude();
		lat = (float) location.getLatitude();
		time = (float) location.getTime();
		alt = (float) location.getAltitude();
		acc = (float) location.getAccuracy();
		speed =  (float) location.getSpeed();
		Date date = new Date((long) time);
		
		String text = "Din posisjon er: " +
		"\nLengdegrader = " + lon +
		"\nBreddegrader = " + lat +
		"\n\nHøyde = " + alt + 
		"\nTreffsikkerhet = " + acc + 
		"\n Fart = " + speed + "klokka er "+ date +" Zulo (UTC-0)";
		//String toastText = "Din pos er " +lon +" " +lat;
		
		//Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
		tv.setText(text);
		
		UtilGPSdb myGPSdb = new UtilGPSdb(this);
		myGPSdb.open();
		myGPSdb.insertRows(lat, lon, alt,  date.toString());
		myGPSdb.close();
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
	
	private static String getTextUrl(String address) {
		BufferedReader in;
		try{
			URL url = new URL (address);
			InputStream is = url.openStream();
			InputStreamReader ir = new InputStreamReader(is, "utf-8");
			
			in = new BufferedReader(ir);
			
			StringBuilder stringbuilder = new StringBuilder();
			String string;
			while ((string = in.readLine()) != null) {
				stringbuilder.append(string);
				stringbuilder.append("\n");
			}
			in.close();
			
			return stringbuilder.toString();
		}
		catch(Exception ex){
			System.out.println(ex.toString());
			return null;
		}
		
	}
}
