package no.hiof.android.nodhjelp;



import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

public class PositionActivity extends ActionBarActivity implements LocationListener{
	float lon,lat,alt, acc, speed, time;
	public Hospital hospital;
	
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
		setContentView(R.layout.activity_position);
		
		final int GPS_TIME_INTERVAL = 60000; //gps update time interval in ms
		
		//obtain GPS position
		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		//update pos
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, GPS_TIME_INTERVAL, 0, this);
			
		//button for nearest hospital
		Button btnShowHos = (Button) findViewById(R.id.button1);
		btnShowHos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DownloadFiles dF = new DownloadFiles(PositionActivity.this);
				dF.execute("https://data.helsenorge.no/External.svc/Services/KA02/"+
						PositionActivity.this.lon + "/" + PositionActivity.this.lat); /*+
						"?callback=ProcessResults&" +
						"$select=HealthServiceLatitude,HealthServiceLongitude,HealthServiceDisplayName");
						*/
				
				Button btnCallHos = (Button) findViewById(R.id.button2);
				btnCallHos.setVisibility(View.VISIBLE);
				btnCallHos.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						UtilHospitalCall.alertMessage(hospital.HealthServiceDisplayName, hospital.HealthServicePhone, PositionActivity.this);
						
					}
				});
				
				
			}
		});
		
		
	}
	
	@Override
	public void onLocationChanged(Location location) {
		TextView tv = (TextView) findViewById(R.id.GPS_pos);
		
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
		"\nFart = " + speed + "" +
		"\nKlokka er "+ date + ".";
		//String toastText = "Din pos er " +lon +" " +lat;
		
		//Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
		tv.setText(text);
		
		//if(new DateTime(location.getTime().minusMinutes(5)) > new DateTime(oldTime));
		
		UtilGPSdb myGPSdb = new UtilGPSdb(this);
		myGPSdb.open();
		myGPSdb.insertRows(lat, lon, alt,  date.toString());
		myGPSdb.close();
	}
	
	public void Reader(String string){
		Gson gson = new Gson();
		hospital = gson.fromJson(string, Hospital.class);
		
		
		TextView tvHospital = (TextView) findViewById(R.id.Hospital);
		String nHospital = "Nærmeste sykehus er " + hospital.HealthServiceDisplayName +
				" telefonnr er " + hospital.HealthServicePhone;
		tvHospital.setText(nHospital);
		
		
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
