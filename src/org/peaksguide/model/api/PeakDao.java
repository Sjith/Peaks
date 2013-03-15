package org.peaksguide.model.api;

import java.util.List;


import android.location.Location;

/**
 * @author user
 *
 */
public interface PeakDao {
	
	/*
	 * Returns all stored peaks.
	 */
	public List<Peak> getAll();
	
	/**
	 * Returns peaks in bounding box. Bounding box is defined by distance from middle.
	 * @param distance - distance from middle in kilometers.
	 * @param middle - location of middle of bounding box.
	 * @return List of peaks
	 */
	public List<Peak> getPeaksInBoundingBox(int distance, Location middle);
	
	/*
	 * Addds new peak.
	 */
	public void add(Peak peak);
}
