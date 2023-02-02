package com.lineate.buscompany.parsing;

import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import processing.core.PApplet;

public interface interactiveMapDAO {


	List<PointFeature> parseEarthquakeToday(PApplet p);

	List<PointFeature> parseEarthquake(PApplet p,boolean offline);

	List<PointFeature> parseAirports(PApplet p);

	List<ShapeFeature> parseRoutes(PApplet p);

	HashMap<String, Float> loadLifeExpectancyFromCSV(PApplet p, String fileName);

}
