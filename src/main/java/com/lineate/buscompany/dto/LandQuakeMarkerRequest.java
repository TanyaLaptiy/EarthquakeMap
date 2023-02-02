package com.lineate.buscompany.dto;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

public class LandQuakeMarkerRequest extends EarthquakeMarkerRequest {

	public LandQuakeMarkerRequest(PointFeature quake) {

		super(quake);
		isOnLand = true;
	}

	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) {
		pg.ellipse(x, y, 2 * radius, 2 * radius);

	}

	public String getCountry() {
		return (String) getProperty("country");
	}

}