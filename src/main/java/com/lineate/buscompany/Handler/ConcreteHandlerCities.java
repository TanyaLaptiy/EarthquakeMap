package com.lineate.buscompany.Handler;

import com.lineate.buscompany.model.CityMarker;
import com.lineate.buscompany.services.CityService;
import com.lineate.buscompany.services.MarkerService;
import de.fhpotsdam.unfolding.marker.Marker;



public class ConcreteHandlerCities implements HandlerInterface {

	@Override
	public boolean markerCheckClick(float mouseX, float mouseY) {
		for (Marker marker : CityService.getInstance().getCityMarkers()) {
			if (marker.isInside(MarkerService.getInstance().getMap(), mouseX, mouseY)) {
				CityService.getInstance().checkCitiesForClick(marker);
				return true;
			}
		}
		return false;
	}

	void checkButtons(float mouseX, float mouseY) {
		if (mouseX > 105 && mouseX < 180 && mouseY > 60 && mouseY < 135) {

			CityMarker.clickForLink(true);
		} else {
			CityMarker.clickForLink(false);
		}
	}

}
