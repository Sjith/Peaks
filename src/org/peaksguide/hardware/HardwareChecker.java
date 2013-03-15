package org.peaksguide.hardware;

import android.content.Context;
import android.content.pm.PackageManager;

public class HardwareChecker {
	
	public HardwareChecker(Context context) {
		this.context = context;
	}
	
	
	/**
	 * Sprawdza czy urządzenie ma aparat.
	 * @return true jeżeli urządzenie ma aparat, lub false jeżeli go nie ma
	 */
	public boolean checkCameraHardware() {
		return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
	}
	
	
	/**
	 * Sprawdza czy urządzenie posiada kompas, czyli czujnik magnetyczny
	 * @return
	 */
	public boolean checkCompass() {
		return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS);
	}
	
	
	/**
	 * Sprawdza czy urządzenie posiada lokalizację GPS
	 * @return
	 */
	
	public boolean checkGPS(){
		return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
	}
	
	
	
	Context context;
}
