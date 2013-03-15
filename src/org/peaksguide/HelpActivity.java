package org.peaksguide;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.wojda.getPeaks.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends Activity{
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.getInstance().log("HelpActivity.onCreate");
		setContentView(R.layout.help);
		
		String helpText = readHelpFile();
		if(helpText == null){
			helpText = new String(getResources().getString(R.string.readhelperror));
		}
		
		TextView helpTextView = (TextView) findViewById(R.id.help_textview);
		helpTextView.setText(helpText);
	 }
	 
	 
	 /**
	  * Wczytuje nieprzetworzony plik res/raw/help
	  * @return Łańcuch znaków wczytany z pliku help lub null w przypadku gdy nie udało się otworzyć pliku.
	  */
	 private String readHelpFile(){
		try{
			InputStream helpFile = getResources().openRawResource(R.raw.help);
			BufferedReader buffReader = new BufferedReader(new InputStreamReader(helpFile, "UTF-8"));
			
			Logger.getInstance().log("Otwarto plik res/raw/help");
			
			String helpFileContents = new String("");
			String line;
			while((line = buffReader.readLine()) != null){
				helpFileContents += line + "\n";
			}
				
			helpFile.close();	
			
			return helpFileContents;
		}
		catch(Exception exception){
			Logger.getInstance().error("Wystąpił błąd podczas operowania na pliku res/raw/help", exception);
			return null;
		}
	 }
	
}
