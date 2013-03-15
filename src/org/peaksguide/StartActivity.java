package org.peaksguide;

import org.peaksguide.hardware.camera.CameraSurfaceView;
import org.peaksguide.hardware.camera.CameraUtil;
import org.wojda.getPeaks.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.hardware.Camera;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;

public class StartActivity extends Activity{
	
	private CameraSurfaceView cameraPreview = null;
	private Camera camera = null;
	private final static int DIALOG_CAMERA_ERROR = 3;
	private Logger LOGGER = Logger.getInstance();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		
		LOGGER.log("StartActivity.onCreate()");
		LOGGER.log("Uruchomiono activity podglądu aparatu");
		

		if(!connectCamera()){						//podłączanie podglądu kamery
			showDialog(DIALOG_CAMERA_ERROR);
		}

	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		
		LOGGER.log("StartActivity.onStart()");
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		LOGGER.log("StartActivity.onResume()");

		if(camera == null){
			boolean cameraConnectionResult = connectCamera();	//podłączenie podglądu kamery
			if(!cameraConnectionResult){						
				showDialog(DIALOG_CAMERA_ERROR);
			}
		}

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
	
	
	/**
	 * Funkcja wczytująca podgląd kamery do layoutu
	 * @return false - w przypadku niepowodzenia
	 */
	private boolean connectCamera(){
		camera = CameraUtil.getCameraInstance();
		boolean isConnectSucceded = false;
		
		if(camera != null){
			cameraPreview = new CameraSurfaceView(getApplicationContext(),camera);
			FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
			
			preview.addView(cameraPreview); 
			
			isConnectSucceded = true;
		}

		return isConnectSucceded;
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
