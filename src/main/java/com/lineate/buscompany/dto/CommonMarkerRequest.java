package com.lineate.buscompany.dto;

import com.lineate.buscompany.exceptions.MapInteractiveUtils;
import com.lineate.buscompany.exceptions.ServerException;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;

import processing.core.PGraphics;

public abstract class CommonMarkerRequest extends SimplePointMarker {

	protected boolean clicked = false;

	public CommonMarkerRequest(Location location) {
		super(location);
		try {
			MapInteractiveUtils.checkProperty(location);
		} catch (ServerException e) {
			e.printStackTrace();
		}
	}

	public CommonMarkerRequest(Location location, java.util.HashMap<java.lang.String, java.lang.Object> properties) {
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
			if (selected) {
				showTitle(pg, x, y);
			}
		}
	}

	public abstract void drawMarker(PGraphics pg, float x, float y);

	public abstract void showTitle(PGraphics pg, float x, float y);
}