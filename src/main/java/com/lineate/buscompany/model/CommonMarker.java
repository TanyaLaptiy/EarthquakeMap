package com.lineate.buscompany.model;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;

public abstract class CommonMarker extends SimplePointMarker {

	protected boolean clicked = false;


	public CommonMarker(Location location) {

		super(location);

	}

	public CommonMarker(Location location, java.util.HashMap<java.lang.String, java.lang.Object> properties) {
		super(location, properties);
	}

	public boolean getClicked() {
		return clicked;
	}

	public void setClicked(boolean state) {
		clicked = state;
	}

	public void draw(PGraphics pg, float x, float y) {

		if (!hidden) {
			drawMarker(pg, x, y);

		}
	}
	public void drawTitleOnTop(PGraphics buffer, float x, float y){
		 
		if (!hidden) {
			
			if (selected){
				buffer.clear();
				showTitle(buffer, x, y);  // Implemented in the subclasses
				
			} else {
				buffer.clear();
			}
		}
	}
	

	public abstract void drawMarker(PGraphics pg, float x, float y);

	public abstract void showTitle(PGraphics pg, float x, float y);

	@Override
	public String toString() {
		return "CommonMarker [clicked=" + clicked + "]";
	}

}