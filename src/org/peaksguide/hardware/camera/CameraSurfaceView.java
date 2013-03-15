package org.peaksguide.hardware.camera;

import java.io.IOException;
import java.util.List;

import org.peaksguide.Logger;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
	
	private Camera camera = null;
	private SurfaceHolder surfaceHolder;
	

	public CameraSurfaceView(Context context, Camera camera) {
		super(context);
		this.camera = camera;
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);	//TODO: poczytać o typach i zależnościach między nimi
		
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		if(surfaceHolder == null){
			return;
		}
		
		try{
//			camera.stopPreview();
//			camera.setPreviewDisplay(surfaceHolder);
			camera.startPreview();
		}
		catch (Exception exception) {
			Logger.getInstance().error("Problem uruchomieniem i zatrzymywaniem podglądu", exception);

		}
		
	}
	

	public void surfaceCreated(SurfaceHolder holder) {
		try {
			camera.setPreviewDisplay(surfaceHolder);
//			camera.startPreview();
			
		} catch (IOException exception) {
			Logger.getInstance().error("Problem z zainicjalizowaniem kamery", exception);
		}
		
	}

	
	public void surfaceDestroyed(SurfaceHolder holder) {
		
		
		/*
		 * Za usuwanie obiektu camery odpowiedzialna jest activity, która używa klasy CameraSurfaceView
		 */
	}
	
	
	public boolean capture(Camera.PictureCallback jpegHandler){
		//TODO
		return false;		
	}
	

	
}
