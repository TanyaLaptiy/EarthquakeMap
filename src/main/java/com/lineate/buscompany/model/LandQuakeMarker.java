package com.lineate.buscompany.model;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;
import processing.core.PImage;

public class LandQuakeMarker extends EarthquakeMarker {

	public LandQuakeMarker(PointFeature quake, PImage logo) {
		super(quake, logo);
		isOnLand = true;
	}
	public LandQuakeMarker(float latitude, float longitude, float magnitude, String location, String age, float depth, String county, PImage logo){
		super(latitude,  longitude,  magnitude,  location,  age,  depth, county, logo);
		isOnLand = true;
	}

	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) {
		pg.ellipse(x, y, 2 * radius, 2 * radius);

	}

	public String getCountry() {
		return (String) getProperty("country");
	}

	@Override
	public String toString() {
		return "LandQuakeMarker [isOnLand=" + isOnLand + ", radius=" + radius + ", clicked=" + clicked + ", color="
				+ color + ", strokeColor=" + strokeColor + ", strokeWeight=" + strokeWeight + ", highlightColor="
				+ highlightColor + ", highlightStrokeColor=" + highlightStrokeColor + ", location=" + location
				+ ", properties=" + properties + ", selected=" + selected + ", hidden=" + hidden + ", id=" + id + "]";
	}

}