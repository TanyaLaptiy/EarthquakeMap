package com.lineate.buscompany.dao;

import java.util.List;

import com.lineate.buscompany.model.LandQuakeMarker;
import com.lineate.buscompany.model.OceanQuakeMarker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;


public interface QuakeDAO {

	public void setQuakeMarkers(List<Marker> quakeMarkers);

	public void addQuakeMarker(LandQuakeMarker Marker);

	public void addQuakeMarker(OceanQuakeMarker Marker);

	public List<Marker> getQuakeMarkers();

//	public void checkEarthquakesForClick(EarthquakeMarker marker);

//	public boolean isLand(PointFeature earthquake);

//	public void printQuakes();

//	public boolean isInCountry(PointFeature earthquake, Marker country);
}
