package org.peaksguide.image;

import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.hardware.Camera;

/**
 * 
 * 
 * @author user
 *
 */
public interface ImageProcessor {
	
	/**
	 * Sets quality of image (created from catched frame).
	 * @param quality Hint to the compressor, 0-100. 0 meaning compress for small size, 100 meaning compress for max quality.
	 */
	public void setImageQuality(int quality);
	public void processImage(byte[] image, Camera.Size imageSize, int imageFormat);
}
