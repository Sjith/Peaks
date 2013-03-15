package org.peaksguide;

import android.util.Log;

/**
 * Klasa umożliwiająca tworzenie logów. 
 * Singleton
 * @author Daniel Wojda
 */
public class Logger {
	//TODO: Dopisać możliwość logowania do pliku
	
	
	private Logger(){
	}
	
	/**
	 * @return Unikatową i dostępną globalnie instancję klasy Logger.
	 */
	public static Logger getInstance(){
		if(logger == null){
			logger = new Logger();
		}
		
		return logger;
	}
	
	
	public void log(String logMessage){
		Log.i(logTag, logMessage);
	}
	
	public void error(String errorMessage){
		Log.e(errorTag, errorMessage);
	}
	
	public void error(String errorMessage, Throwable exception){
		Log.e(errorTag, errorMessage, exception);
	}
	
	
	private final String logTag = "GetPeaks Log";
	private final String errorTag = "GetPeaks Error";
	private static Logger logger = null;
}
