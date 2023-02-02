package com.lineate.buscompany.Handler;

import com.lineate.buscompany.model.EarthquakeMarker;
import com.lineate.buscompany.services.EarthquakeService;
import com.lineate.buscompany.services.MarkerService;
import de.fhpotsdam.unfolding.marker.Marker;


public class ConcreteHandlerEarthquakes implements HandlerInterface {

	@Override
	public boolean markerCheckClick(float mouseX, float mouseY) {
		for (Marker m : EarthquakeService.getQuakeMarkers()) {
			EarthquakeMarker marker = (EarthquakeMarker) m;
			if (marker.isInside(MarkerService.getInstance().getMap(), mouseX, mouseY)) {
				EarthquakeService.checkEarthquakesForClick(marker);
				return true;

			}
		}
		return false;
	}

}
