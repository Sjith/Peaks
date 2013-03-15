package org.peaksguide;

import org.peaksguide.hardware.camera.CameraSurfaceView;
import org.peaksguide.hardware.camera.CameraUtil;
import org.peaksguide.hardware.camera.FrameCatcher;
import org.peaksguide.image.ImageProcessor;
import org.peaksguide.image.ImageProcessorImpl;
import org.wojda.getPeaks.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.FrameLayout;

/**
 * 
 * @author user
 *
 */
public class StartEdgeDetectorActivity extends Activity{
	
	private CameraSurfaceView cameraSurfaceView = null;
	private Camera camera = null;
	private ImageProcessor imageProcessor = null;
	private final static int DIALOG_CAMERA_ERROR = 3;
	private Logger LOGGER = Logger.getInstance();	//TODO I've create custom Logger class. It was a bad idea, android api provides 2 very good (i think that) implemented class for logs, use one of them.


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		
		LOGGER.log("StartActivity.onCreate()");
		LOGGER.log("Uruchomiono activity podglądu aparatu");
		
		imageProcessor = new ImageProcessorImpl();
		imageProcessor.setImageQuality(100);				//the best quality, for performance set lower value
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		
		LOGGER.log("StartActivity.onStart()");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		LOGGER.log("StartEdgeDetectorActivity.onResume()");

		initCamera();
	}
		

	
	@Override
	protected void onPause() {
		super.onPause();
		LOGGER.log("StartActivity.onPause");
		
		if(camera != null){
			camera.stopPreview();
			camera.release();
			camera = null;
			
			LOGGER.log("Kamera zwolniona");
		}

	}
	
	
	@Override
	protected void onStop() {
		super.onStop();
		
		LOGGER.log("StartActivity.onStop()");

	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		LOGGER.log("StartActivity.onDestroy()");
	}
	
	
	@Override
	protected void onRestart() {
		super.onRestart();
		
		LOGGER.log("StartActivity.onRestart()");
		
		setContentView(R.layout.start);
	}

	
	private void initCamera(){
		camera = CameraUtil.getCameraInstance();	//TODO: consider if onResume is correct place for getting camera instance.
		if (camera != null) {
			FrameCatcher frameCatcher = new FrameCatcher(imageProcessor, camera.getParameters());
			frameCatcher.setDelayBetweenSendingFrames(3000l);	//TODO: load this value from properties file
			
			cameraSurfaceView = new CameraSurfaceView(this.getApplicationContext(), camera);
			camera.setPreviewCallbackWithBuffer(frameCatcher);
			camera.addCallbackBuffer(frameCatcher.getCallbackBuffer());
			
//			FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
//			preview.addView(cameraSurfaceView);
		} 
		else {
			showDialog(DIALOG_CAMERA_ERROR); //TODO: Method showDialog is deprecated. Find another solution.
		}
	}
	
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		
		switch(id){
		case DIALOG_CAMERA_ERROR:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			String message = getResources().getString(R.string.camera_error_dialog_msg);
			builder.setMessage(message)
			       .setCancelable(false)
			       .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
			    	   
			           public void onClick(DialogInterface dialog, int id) {
							LOGGER.error("Wystąpił problem z pobraniem podglądu aparatu. Wyświetlono AlertDialog z info");
							finish();
			           }
			           
			       });
			
			dialog = builder.create();			
			break;
			
		default:
			dialog = null;
		}
		
		
		return dialog;
	}
	
	

}
