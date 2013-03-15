package org.peaksguide.model.dataAccessLayer;

import java.util.ArrayList;
import java.util.List;

import org.peaksguide.model.api.Peak;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DbHelper{
	private static final String DEBUG_TAG = "DbHelper SQLite";

	public static abstract class GetPeaksDB implements BaseColumns{
		public static final int DATABASE_VERSION = 1;
		public static final String DATABASE_NAME = "GetPeaksDB";
		public static final String TABLE_NAME = "peaks";
	    public static final String COLUMN_NAME = "name";
	    public static final String COLUMN_LATITUDE = "latitude";
	    public static final String COLUMN_LONGITUDE = "longitude";
	    public static final String COLUMN_ALTITUDE = "altitude";
	    public static final String[] COLUMNS = new String[] {
	    	GetPeaksDB._ID, GetPeaksDB.COLUMN_NAME, GetPeaksDB.COLUMN_LATITUDE, GetPeaksDB.COLUMN_LONGITUDE, GetPeaksDB.COLUMN_ALTITUDE};
	    
	}
	
	private static class FeedReaderContract {
		private static final String TEXT_TYPE = " TEXT";
		private static final String DOUBLE_TYPE = " REAL";
		private static final String COMMA_SEPARATOR = ",";
		private static final String SQL_CREATE = 
				"CREATE TABLE " + GetPeaksDB.TABLE_NAME + " (" +
				GetPeaksDB._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
				GetPeaksDB.COLUMN_NAME + TEXT_TYPE + COMMA_SEPARATOR +
				GetPeaksDB.COLUMN_LATITUDE + DOUBLE_TYPE + COMMA_SEPARATOR +
				GetPeaksDB.COLUMN_LONGITUDE + DOUBLE_TYPE + COMMA_SEPARATOR +
				GetPeaksDB.COLUMN_ALTITUDE + DOUBLE_TYPE + 
				" )";
		private static final String SQL_DELETE = 
				"DROP TABLE IF EXISTS " + GetPeaksDB.TABLE_NAME;
				
		//Prevents the class from being instantiated
		private FeedReaderContract() {}
	}

	
	/**
	 * @author user
	 *
	 */
	public class DbOpenHelper extends SQLiteOpenHelper{
	
		public DbOpenHelper(Context context){
			super(context, GetPeaksDB.DATABASE_NAME, null, GetPeaksDB.DATABASE_VERSION);
		}
	
		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(DEBUG_TAG, "Database creating... start");
			db.execSQL(FeedReaderContract.SQL_CREATE);		
			Log.d(DEBUG_TAG, "Database creating... stop" + "Table " + GetPeaksDB.TABLE_NAME + " created.");
		}
		
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d(DEBUG_TAG, "Database updating... start. All data will be lost");
			db.execSQL(FeedReaderContract.SQL_DELETE);
			onCreate(db);
			Log.d(DEBUG_TAG, "Database updating... stop");
		}
		
		@Override
		public void onOpen(SQLiteDatabase db) {
			super.onOpen(db);
		}
	}
	

	private SQLiteDatabase database;
	private final DbOpenHelper dbOpenHelper;
	
	public DbHelper(Context context) {
		dbOpenHelper = new DbOpenHelper(context);
		establishDBConnection();
	}
	
	private void establishDBConnection(){
		if(database==null){
			database = dbOpenHelper.getWritableDatabase();
		}
	}
	
	public void cleanup(){
		if(database != null){
			database.close();
			database = null;
		}
	}
	
	public void insert(Peak peak){
		ContentValues values = new ContentValues();
		values.put(GetPeaksDB.COLUMN_NAME, peak.getName());
		values.put(GetPeaksDB.COLUMN_LATITUDE, peak.getLatitude());
		values.put(GetPeaksDB.COLUMN_LONGITUDE, peak.getLongitude());
		values.put(GetPeaksDB.COLUMN_ALTITUDE, peak.getAltitude());
		database.insert(GetPeaksDB.TABLE_NAME, null, values);
	}
	
	/*
	 * Returns all peaks from database.
	 */
	public List<Peak> getAllPeaks(){
		List<Peak> peaks;
		Cursor cursor = null;
		
		try{
			cursor = database.query(GetPeaksDB.TABLE_NAME, GetPeaksDB.COLUMNS, null, null, null, null, null);
			peaks = extractPeaksFromQueryResult(cursor);
		
		}finally{
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
			}
		}
		
		return peaks;
	}
	
	
	/*
	 * Converts query result (saved in cursor) into list of peaks.
	 * This function doesn't close the cursor.
	 */
	private List<Peak> extractPeaksFromQueryResult(final Cursor c){
		List<Peak> peaks = new ArrayList<Peak>();
		
		int numberOfRows = c.getCount();
		c.moveToFirst();
		for(int i=0; i<numberOfRows; i++){
			String name = c.getString(1);
			Double latitude = c.getDouble(2);
			Double longitude = c.getDouble(3);
			Double altitude = c.getDouble(4);
			Peak peak = new Peak(name, latitude, longitude, altitude);
			peaks.add(peak);
			
			c.moveToNext();
		}
		
		return peaks;
	}

	
	/*
	 * Returns peaks in bounding box. Peaks are ordered by longitude.
	 */
	public List<Peak> getPeaksInBoundingBox(Double latitudeMin, Double longitudeMin, Double latitudeMax, Double longitudeMax){
		List<Peak> peaks;
		Cursor cursor = null;
		String whereClause = GetPeaksDB.COLUMN_LATITUDE + " > " + latitudeMin + " and " +
							GetPeaksDB.COLUMN_LATITUDE + " < " + latitudeMax + " and " +
							GetPeaksDB.COLUMN_LONGITUDE + " > " + longitudeMin + " and " +
							GetPeaksDB.COLUMN_LONGITUDE + " < " + longitudeMax;
		String orderBy = GetPeaksDB.COLUMN_LONGITUDE;
		
		try{
			cursor = database.query(GetPeaksDB.TABLE_NAME, GetPeaksDB.COLUMNS, whereClause, null, null, null, orderBy);
			peaks = extractPeaksFromQueryResult(cursor);
		
		}finally{
			if(cursor != null && !cursor.isClosed()){
				cursor.close();
			}
		}
		
		return peaks;
	}
	
	
	
	
}
