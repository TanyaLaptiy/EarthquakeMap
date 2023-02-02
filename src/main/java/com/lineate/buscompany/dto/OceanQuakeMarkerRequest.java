package com.lineate.buscompany.dto;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

public class OceanQuakeMarkerRequest extends EarthquakeMarkerRequest {

	public OceanQuakeMarkerRequest(PointFeature quake) {
		super(quake);
		isOnLand = false;
	}

	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) {
		pg.rect(x - radius, y - radius, 2 * radius, 2 * radius);
	}

}
