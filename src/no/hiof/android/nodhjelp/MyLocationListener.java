package no.hiof.android.nodhjelp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MyLocationListener implements LocationListener {
	Activity context;
	
	
	
	public MyLocationListener(Activity context) {
		this.context = context;
		
	}
		
		@Override
		public void onLocationChanged(Location location) {
			TextView tv = (TextView) context.findViewById(R.id.GPS_pos);
			float lon,lat,alt;
			lon = (float) location.getLongitude();
			lat = (float) location.getLatitude();
			alt = (float) location.getAltitude();
			String text = "Din posisjon er: " +
			"\nLengdegrader = " + lon +
			"\nBreddegrader = " + lat +
			"\n\nHøyde = " + alt;
			String toastText = "Din pos er " +lon +" " +lat;
			
			Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
			tv.setText(text);
			
			Intent intent = new Intent();
			intent.setClass(context, PositionActivity.class);
			Bundle extras = new Bundle();
			
			extras.putFloat("lende", lon);
			extras.putFloat("Bredde", lat);
			
			intent.putExtras(extras);
			context.startActivity(intent);
		}
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
			
		@Override
		public void onProviderEnabled(String provider) {
			Toast.makeText(context, "GPS skrudd på", Toast.LENGTH_SHORT).show();
		}
			
		@Override
		public void onProviderDisabled(String provider) {
			Toast.makeText(context, "GPS skrudd av", Toast.LENGTH_SHORT).show();
		}
	};
