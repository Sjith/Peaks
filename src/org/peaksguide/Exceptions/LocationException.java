package org.peaksguide.Exceptions;

/**
 * Wyjątek rzucany przy błędach związanych z lokalizacją np z GPS
 * @author user
 *
 */
public class LocationException extends Exception{

	public LocationException() {
		super();
	}
	
	@Override
	public String getMessage() {
		return "Localization exception, " + super.getMessage();
	}
}
