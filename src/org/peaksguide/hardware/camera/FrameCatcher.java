package org.peaksguide.hardware.camera;

import java.io.ByteArrayOutputStream;

import org.peaksguide.Logger;
import org.peaksguide.image.ImageProcessor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.text.format.Time;

/**
 * This class is used to capture a frame from the camera (camera preview) without stopping preview.
 * Captured frame will be used e.g. for detecting edge, or another picture operations.
 * FrameCatcher sends not converted frames to image processor.
 * @author Daniel Wojda
 *
 */
public class FrameCatcher implements PreviewCallback{
	private Logger LOGGER = Logger.getInstance();
	private ImageProcessor imageProcessor = null;
	private long lastFrameSendedTime;
	private long delayBetweenSendingFrames = 0;
	private byte[] callbackBuffer;
	
	
	/**
	 * 
	 * @param imageProcessor - object where FrameCatcher sends catched frames.
	 */
	public FrameCatcher(ImageProcessor imageProcessor, Camera.Parameters cameraParameters) {
		this.imageProcessor=imageProcessor;
		this.lastFrameSendedTime = System.currentTimeMillis();
//		callbackBuffer = new byte[calculatePreviewFrameSizeInBytes(cameraParameters)]; //		//TODO: zbadać tą funkcję, dziwna sprawa, zwraca 30700 a powinna (wg camery) 460800
		callbackBuffer = new byte[460800];
	}
	
	
	public void onPreviewFrame(byte[] data, Camera camera) {
//		LOGGER.log("FrameCatcher start");
		
		if(isItTimeForSendingFrame()) {
			Camera.Parameters cameraParams = camera.getParameters();
			Camera.Size previewSize = cameraParams.getPreviewSize();
			int previewImageFormat = cameraParams.getPreviewFormat();
			
			this.imageProcessor.processImage(data, previewSize, previewImageFormat);
			
			this.lastFrameSendedTime = System.currentTimeMillis();
		}
		
		camera.addCallbackBuffer(callbackBuffer);
		
//		LOGGER.log("FrameCatcher end");
	}
	
	
	/**
	 * Sets delay between sending frames to image processor.
	 * Default is 0.
	 * @param delay in milliseconds, must be greater or equal 0
	 */
	public void setDelayBetweenSendingFrames(long delay){
		if(delay < 0){
			throw new IllegalArgumentException("Delay value must be greater or equal 0. You want set delay: "+delay);
		}
		this.delayBetweenSendingFrames = delay;
	}
	
	
	public byte[] getCallbackBuffer() {
		return callbackBuffer;
	}
	
	
	private boolean isItTimeForSendingFrame(){
		return (lastFrameSendedTime + delayBetweenSendingFrames) < System.currentTimeMillis();
	}
	
	
	/**
	 * Buffer size determined by one frame size. So different camera resolution - different buffer size.
	 * @param cameraParams
	 * @return preview frame size in bytes.
	 */
	private int calculatePreviewFrameSizeInBytes(Camera.Parameters cameraParams){
		int size = -1;
		int previewFormat = cameraParams.getPreviewFormat();
		
		if(previewFormat == ImageFormat.YV12) {
			int yStride = (int) Math.ceil(cameraParams.getPreviewSize().width /16.0) * 16;
			int uvStride = (int) Math.ceil((yStride/2) / 16.0) * 16;
			int ySize  = yStride * cameraParams.getPreviewSize().height;
			int uvSize = uvStride * cameraParams.getPreviewSize().height / 2;
			size = ySize + uvSize * 2;
		}
		else {
			size = cameraParams.getPreviewSize().width * cameraParams.getPreviewSize().height * (ImageFormat.getBitsPerPixel(previewFormat) / 8);
		}
		//TODO: zbadać tą funkcję, dziwna sprawa, zwraca 30700 a powinna (wg camery) 460800
		return size;
	}
}
