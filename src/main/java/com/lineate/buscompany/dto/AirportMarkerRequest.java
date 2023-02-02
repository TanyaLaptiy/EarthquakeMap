package com.lineate.buscompany.dto;

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

public class AirportMarkerRequest extends CommonMarkerRequest {
	public static List<SimpleLinesMarker> routes;
	Feature feature;

	public AirportMarkerRequest(Feature city) {
		super(((PointFeature) city).getLocation(), city.getProperties());
		feature = city;

	}

	public Feature getFeature() {
		return feature;
	}

	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.fill(186, 254, 255);

		pg.ellipse(x, y, 5, 5);

	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		// show rectangle with title
		String name = getName();
		String where = "Country: " + getCountry() + " City" + getCity();
		String altitude = "Altitude: " + getAltitude();

		pg.pushStyle();

		pg.fill(204, 255, 255);
		pg.textSize(12);
		pg.rectMode(PConstants.CORNER);
		pg.rect(x, y - 5 - 39, Math.max(Math.max(pg.textWidth(name), pg.textWidth(where)), pg.textWidth(altitude)) + 6,
				54);
		pg.fill(102, 204, 255);
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.fill(57, 90, 255);
		pg.text(name, x + 3, y - 5 - 33);
		pg.fill(102, 204, 255);
		pg.text(where, x + 3, y - 5 - 18);
		pg.text(altitude, x + 3, y - 5 - 3);
		pg.popStyle();
		// show routes

	}

	private String getCountry() {
		return getStringProperty("country");
	}

	private int getAltitude() {
		return Integer.parseInt(getStringProperty("altitude"));
	}

	private String getCode() {
		return getStringProperty("code");
	}

	private String getCity() {
		return getStringProperty("city");
	}

	private String getName() {
		return getStringProperty("name");
	}
}
