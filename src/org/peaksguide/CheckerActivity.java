package org.peaksguide;

import org.peaksguide.hardware.HardwareChecker;
import org.wojda.getPeaks.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

/**
 * Klasa uruchamiana jako pierwsza. Ma za zadanie przetestować urządzenie pod względem kompatybilności z aplikacją.
 * @author Daniel Wojda
 * TODO: klasa do poprawy, wyswietlanie dialogu nie działa, logowanie do dupy, nawet jezeli wykryje problem z urzadzeniem to i tak przechdzi dalej...
 *
 */
public class CheckerActivity extends Activity{
    
	private final static int DIALOG_CAMERA_ERROR = -105;
	private final static int DIALOG_GPS_UNAVAILABLE = -106;
	private final static int DIALOG_COMPASS_UNAVAILABLE = -107;
	private final static int DIALOG_GPS_OFF = -108;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger logger = Logger.getInstance();
        logger.log("CheckerActivity.onCreate");
        
        boolean allHardwareOK = true;
  
        HardwareChecker checker = new HardwareChecker(getApplicationContext());
        
        if(checker.checkCameraHardware()){
        	logger.log("Znaleziono kamerę");
		}
		else{
			allHardwareOK = false;
			
			String errorMessage = getResources().getString(R.string.camera_error) + "\n"
								+ getResources().getString(R.string.app_will_be_closed);
			logger.error(errorMessage);
			
			showDialog(DIALOG_CAMERA_ERROR);
		}
        
        if(checker.checkGPS()){
        	logger.log("znaleziono GPS");

        }
        else{
        	allHardwareOK = false;
			
			String errorMessage = getResources().getString(R.string.gps_unavailable_dialog_msg) + "\n";
			errorMessage += getResources().getString(R.string.app_will_be_closed);
			logger.error(errorMessage + " wyświetlono alertdialog");
			
			showDialog(DIALOG_GPS_UNAVAILABLE);
        }
        
        if(checker.checkCompass()){
        	logger.log("Znaleziono kompas");        	
        }
        else{
        	allHardwareOK = false;
			
			String errorMessage = getResources().getString(R.string.compass_unavailable_dialog_msg) + "\n";
			errorMessage += getResources().getString(R.string.app_will_be_closed);
			logger.error(errorMessage + " wyświetlono alertdialog");
			
			showDialog(DIALOG_COMPASS_UNAVAILABLE);
        }
    }
    
    
    @Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String message;
		
		switch(id){
		case DIALOG_CAMERA_ERROR:
			message = getResources().getString(R.string.camera_error_dialog_msg);
			builder.setMessage(message)
			       .setCancelable(false)
			       .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
			    	   
			           public void onClick(DialogInterface dialog, int id) {
							Logger.getInstance().error("Wystąpił problem z pobraniem podglądu aparatu. Wyświetlono AlertDialog z info");
							
							finish();
			           }
			           
			       });
			
			dialog = builder.create();			
			break;
			
		case DIALOG_GPS_UNAVAILABLE:
			message = getResources().getString(R.string.gps_unavailable_dialog_msg);
			builder.setMessage(message)
			       .setCancelable(false)
			       .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
			    	   
			           public void onClick(DialogInterface dialog, int id) {
							Logger.getInstance().error("Brak GPS. Wyświetlono AlertDialog z info");
							
							finish();
			           }
			           
			       });
			
			dialog = builder.create();	
			break;
			
		case DIALOG_COMPASS_UNAVAILABLE:
			message = getResources().getString(R.string.compass_unavailable_dialog_msg);
			builder.setMessage(message)
			       .setCancelable(false)
			       .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
			    	   
			           public void onClick(DialogInterface dialog, int id) {
							Logger.getInstance().error("Brak kompasu. Wyświetlono AlertDialog z info");
							
							finish();
			           }
			           
			       });
			
			dialog = builder.create();	
			break;
			
		case DIALOG_GPS_OFF:			
			
			String positive = getString(android.R.string.yes);
			String negative = getString(android.R.string.no);
			
		    builder.setMessage(getString(R.string.gps_off_dialog_question))
		           .setCancelable(false)
		           .setPositiveButton(positive, new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int id) {
		                   startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		               }
		           })
		           .setNegativeButton(negative, new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int id) {
		                    dialog.cancel();
		                    
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

    
    @Override
    protected void onResume() {
    	super.onResume();
    	
        Logger logger = Logger.getInstance();
        logger.log("CheckerActivity.onResume()");
        
    	//2 b) Sprawdzenie czy nadajnik GPS urządzenia jest włączony
    	LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    	if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
    		logger.log("GPS jest włączony");
    		
    		startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
    		
    		finish();
    	}
    	else{
    		logger.error("Gps jest wyłączony");
    		
    		showDialog(DIALOG_GPS_OFF);
    	}
    	

    }

}
