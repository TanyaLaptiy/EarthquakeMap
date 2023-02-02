package com.lineate.buscompany.dto;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PGraphics;

public class menuMarkerRequest extends CommonMarkerRequest {

	public menuMarkerRequest(Location loc) {
		super(loc);

	}

	public menuMarkerRequest(Feature city) {
		super(((PointFeature) city).getLocation(), city.getProperties());

	}

	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.pushStyle();
		pg.fill(253, 255, 159);
		pg.stroke(204, 102, 0);
		pg.ellipse(x, y, 10, 10);
		pg.popStyle();

	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
	}
}
