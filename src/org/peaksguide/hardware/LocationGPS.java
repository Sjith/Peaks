package org.peaksguide.hardware;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

public class LocationGPS {
	
	public LocationGPS(Context context) {
		locationManager =  (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    	recentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);	
	};
	
	
	/**
	 * @return Długość geograficzną urządzenia
	 */
	public double getLongitude(){
			recentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);	//odświerzenie położenia
			return recentLocation.getLongitude();
	}
	
	
	/**
	 * @return Szerokość geograficzną urządzenia
	 */
	public double getLatitude(){
			recentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);	//odświerzenie położenia
			return recentLocation.getLatitude();
	}
	
	
	public String toString() {
		recentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);	//odświerzenie położenia
		return recentLocation.toString();
	};
	
	

	private LocationManager locationManager;
	private Location recentLocation;
}
