package org.peaksguide;

import org.wojda.getPeaks.R;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class SensorActivity extends Activity implements SensorEventListener {
	
	private SensorManager sensorManager;
	private CompassView compassView;
	
	private float[] magnitude_values;
	private float[] accelerometer_values;
	private boolean sensorReady = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor);
		compassView = (CompassView) findViewById(R.id.compass_view);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.i("SensorActivity.onResume()", "start");

		int rate = SensorManager.SENSOR_DELAY_NORMAL;
		Sensor compassSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(this, compassSensor, rate);
		sensorManager.registerListener(this, accelerometerSensor, rate);
	}
	
	
	public void onSensorChanged(SensorEvent event) {
		
		switch(event.sensor.getType()){
		case Sensor.TYPE_MAGNETIC_FIELD:
			magnitude_values = event.values.clone();
			sensorReady = true;
			break;
		case Sensor.TYPE_ACCELEROMETER:
			accelerometer_values = event.values.clone();			
		}
		
		if(magnitude_values != null && accelerometer_values != null && sensorReady){
			sensorReady = false;
			float[] R = new float[16];
			float[] transformedR = new float[16];
			float[] I = new float[16];
			float[] actualOrientation = new float[3];
			
			SensorManager.getRotationMatrix(R, I, accelerometer_values, magnitude_values);
			SensorManager.remapCoordinateSystem(R, SensorManager.AXIS_X, SensorManager.AXIS_Z, transformedR);
			SensorManager.getOrientation(transformedR, actualOrientation);
			
			StringBuilder builder = new StringBuilder();
			builder.append("Values in rad: ");
			builder.append(actualOrientation[0] + ", " + actualOrientation[1] + ", " + actualOrientation[2]);
			Log.i("onSensorChanged", builder.toString());
			
			double azimuth = Math.toDegrees(actualOrientation[0]);
			compassView.updateDirection((float) azimuth);
		}
				
	}
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
	}
	
	
}
