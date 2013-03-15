package org.peaksguide;

import org.wojda.getPeaks.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Klasa odpowiedzialna za wyświetlenie i obsłużenie głównego menu aplikacji
 * @author Daniel Wojda
 *
 */
public class MainMenuActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		
		initButtons();
	}
	
	private void initButtons() {
		Button SensorButton = (Button) findViewById(R.id.sensor_button);
		SensorButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), SensorActivity.class));
			}
		});
		
		Button startEdgeDetectionButton = (Button) findViewById(R.id.start_edge_detection_button);
		startEdgeDetectionButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), StartEdgeDetectorActivity.class));
			}
		});
		
		Button startButton = (Button) findViewById(R.id.start_button);
		startButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), StartActivity.class));
			}
		});
		
		Button settingsButton = (Button) findViewById(R.id.settings_button);
		settingsButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), SettingsActivity.class));
			}
		});
		
		Button importButton = (Button) findViewById(R.id.import_button);
		importButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), ImportActivity.class));
			}
		});
		
		Button helpButton = (Button) findViewById(R.id.help_button);
		helpButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), HelpActivity.class));
			}
		});
		
	}

	
}
