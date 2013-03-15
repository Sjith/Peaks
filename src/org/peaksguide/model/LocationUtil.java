package org.peaksguide.model;

import android.location.Location;

public class LocationUtil {
	
	public static Location computeLocation(Location startLocation, int distance, CardinalDirection direction){
		Location location = new Location(startLocation);
		
		if(direction == CardinalDirection.NORTH){
			double newLatitude = startLocation.getLatitude() + distance * (1/110.54);
			location.setLatitude(newLatitude);
		}else if(direction == CardinalDirection.SOUTH){
			double newLatitude = startLocation.getLatitude() - distance * (1/110.54);
			location.setLatitude(newLatitude);
		}else if(direction == CardinalDirection.EAST){
			double newLongitude = startLocation.getLongitude() + distance * (1/(111.32 * Math.cos(startLocation.getLongitude())));
			location.setLongitude(newLongitude);
		}else if(direction == CardinalDirection.WEST){
			double newLongitude = startLocation.getLongitude() - distance * (1/(111.32 * Math.cos(startLocation.getLongitude())));
			location.setLongitude(newLongitude);
		}
		
		return location;
	}
	
	
	
}
