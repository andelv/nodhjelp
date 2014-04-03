package no.hiof.android.nodhjelp;

import android.text.AlteredCharSequence;

public class RowItem {
	/*
	 * Used tutorial from
	 * http://theopentutorials.com/tutorials/android/listview/android
	 * -custom-listview-with-image-and-text-using-arrayadapter
	 */

	private String time;
	private String lat;
	private String lon;
	private String altitude;

	public RowItem(String time, String lat, String lon, String altitude) {
		this.time = time;
		this.lat = lat;
		this.lon = lon;
		this.altitude = altitude;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String Time) {
		this.time = time;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}
	
	public String getLon() {
		return lon;
	}

	public void setLot(String lon) {
		this.lon = lon;
	}
	
	public String getAlt() {
		return altitude;
	}
	
	public void setAlt(String altitude) {
		this.altitude = altitude;
	}

	@Override
	public String toString() {
		return time + "\n" + lat + " " + lon + "altitude";
	}

}
