package org.peaksguide.model;

import java.util.List;

import org.peaksguide.model.CardinalDirection;
import org.peaksguide.model.LocationUtil;
import org.peaksguide.model.api.Peak;
import org.peaksguide.model.api.PeakDao;
import org.peaksguide.model.dataAccessLayer.DbHelper;

import android.content.Context;
import android.location.Location;

public class PeakDaoImpl implements PeakDao{
	private DbHelper dbHelper;
	
	public PeakDaoImpl(Context context) {
		dbHelper = new DbHelper(context);
	}

	public List<Peak> getAll() {
		return dbHelper.getAllPeaks();
	}

	public void add(Peak peak) {
		dbHelper.insert(peak);
	}

	public List<Peak> getPeaksInBoundingBox(int distance, Location middle) {
		Double longitudeMin;
		Double longitudeMax;
		Double latitudeMin;
		Double latitudeMax;
		
		Location boundaryLocation = LocationUtil.computeLocation(middle, distance, CardinalDirection.NORTH);
		latitudeMin = boundaryLocation.getLongitude();
		boundaryLocation = LocationUtil.computeLocation(middle, distance, CardinalDirection.SOUTH);
		latitudeMax = boundaryLocation.getLatitude();
		boundaryLocation = LocationUtil.computeLocation(middle, distance, CardinalDirection.WEST);
		longitudeMin = boundaryLocation.getLongitude();
		boundaryLocation = LocationUtil.computeLocation(middle, distance, CardinalDirection.EAST);
		longitudeMax = boundaryLocation.getLongitude();
		
		return dbHelper.getPeaksInBoundingBox(latitudeMin, longitudeMin, latitudeMax, longitudeMax);
	}

	
}
