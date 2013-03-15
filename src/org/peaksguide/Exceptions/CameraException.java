package org.peaksguide.Exceptions;

public class CameraException extends Exception{
	
	public CameraException() {
		super();
	}
	
	@Override
	public String getMessage() {
		return "Camera exception, " + super.getMessage();
	}
}
