package com.lineate.buscompany.model;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;
import processing.core.PImage;

public class OceanQuakeMarker extends EarthquakeMarker {

	public OceanQuakeMarker(PointFeature quake, PImage logo) {
		super(quake, logo);
		isOnLand = false;
	}
	public OceanQuakeMarker(float latitude, float longitude, float magnitude, String location, String age, float depth,String county, PImage logo){
		super(latitude,  longitude,  magnitude,  location,  age,  depth, county,logo);
		isOnLand = false;
	}
	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) {
		pg.pushStyle();

		pg.smooth(2);

		pg.rect(x - radius, y - radius, 2 * radius, 2 * radius);
		pg.popStyle();

	}

	@Override
	public String toString() {
		return "OceanQuakeMarker [isOnLand=" + isOnLand + ", radius=" + radius + ", clicked=" + clicked + ", color="
				+ color + ", strokeColor=" + strokeColor + ", strokeWeight=" + strokeWeight + ", highlightColor="
				+ highlightColor + ", highlightStrokeColor=" + highlightStrokeColor + ", location=" + location
				+ ", properties=" + properties + ", selected=" + selected + ", hidden=" + hidden + ", id=" + id + "]";
	}

}
