package org.peaksguide;

import org.wojda.getPeaks.R;

import android.app.Activity;
import android.os.Bundle;

public class SettingsActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
	}
}

//TODO: dodać zapisywanie ustawień do pliku properties.
/*
Ustawienia do zapisania:
- opóźnienie pomiędzy wysyłaniem klatek do image processora (częstotliwość wysyłania tych klatek)
- jakość obrazów przetwarzanych przez imageProcessor. Zgadać jaka jest potrzebna, im mniejsza tym powinien szybciej działać.

*/