package org.peaksguide.image;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;

public class ImageProcessorImpl implements ImageProcessor{
	private int imageQuality = 100;
	
	
	/**
	 * Sets quality of image (created from catched frame).
	 * @param quality Hint to the compressor, 0-100. 0 meaning compress for small size, 100 meaning compress for max quality.
	 */
	public void setImageQuality(int quality) {
		if(quality<0 || quality>100){
			throw new IllegalArgumentException("Quality must be <0,100>, getted value:" + quality);
		}
		
		this.imageQuality = quality;
	}
	
	
	public void processImage(byte[] image, Size imageSize, int imageFormat) {
		Log.i("processImage", "start");
		
//		YuvImage yuvImage = generateYuvImage(image, imageFormat, imageSize);
//		Bitmap jpegImage = generateJpeg(yuvImage, imageSize);

		Log.i("processImage", "end");
	}
		
		
	private YuvImage generateYuvImage(byte[] data, int imageFormat, Camera.Size imageSize){
		return new YuvImage(data, imageFormat, imageSize.width, imageSize.height, null);
	}
	
	
	private Bitmap generateJpeg(YuvImage yuvImage, Camera.Size size){
		Bitmap bitmap = null;
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		yuvImage.compressToJpeg(new Rect(0, 0, size.width, size.height), this.imageQuality, jpegOutputStream);	//TODO I'm not sure if this Rect object is correct. Create some Junit test for it.
		bitmap = BitmapFactory.decodeByteArray(jpegOutputStream.toByteArray(), 0, jpegOutputStream.size());
		return bitmap;
	}

}
