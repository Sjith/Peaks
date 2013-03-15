package org.peaksguide.model.api;

import android.location.Location;

/**
 * A data class representing a peak, its name and location.
 * Class Peak is immutable. There isn't a place in code where this class can be changed.
 * @author Daniel Wojda
 *
 */
public final class Peak {
	private final String name;
	private final Location location;
	

	public Peak(String name, double latitude, double longitude, double altitude){
		this.name = name;
		Location location = new Location("");
		location.setLatitude(latitude);
		location.setLongitude(longitude);
		location.setAltitude(altitude);
		this.location = location;
	}
	
	public String getName() {
		return name;
	}
	
	public Location getLocation(){
		return new Location(this.location);
	}
	
	public double getLatitude() {
		return location.getLatitude();
	}
	
	public double getLongitude() {
		return location.getLongitude();
	}
	
	/*
	 * Returns altitude in meters above sea level.
	 */
	public double getAltitude() {
		return location.getAltitude();
	}
	
	@Override
	public String toString() {
		return "Peak, name: " + name + ", latitude: " + location.getLatitude() + ", longitude:" + location.getLongitude()+ ", altitude: " + location.getAltitude();
	}
	
}
