package org.peaksguide.hardware.camera;

import org.peaksguide.Logger;

import android.hardware.Camera;

public class CameraUtil {
	
	/**  
	 * A safe way to get an instance of the Camera object.
	 * returns null if camera is unavailable
	*/
	public static Camera getCameraInstance(){
	    Camera camera = null;
	    
	    try {
	        camera = Camera.open(); // attempt to get a Camera instance
	    }
	    catch (Exception exception){
	        // Camera is not available (in use or does not exist)
			Logger.getInstance().error("Nie można otworzyć kamery", exception);

	    }
	    
	    return camera; 
	}

}
