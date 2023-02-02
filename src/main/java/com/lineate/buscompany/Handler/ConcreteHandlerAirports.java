package com.lineate.buscompany.Handler;

import com.lineate.buscompany.services.AirportService;
import com.lineate.buscompany.services.MarkerService;
import de.fhpotsdam.unfolding.marker.Marker;


public class ConcreteHandlerAirports implements HandlerInterface {

	@Override
	public boolean markerCheckClick(float mouseX, float mouseY) {
		for (Marker marker : AirportService.getInstance().getAirportList()) {
			if (marker.isInside(MarkerService.getInstance().getMap(), mouseX, mouseY)) {
				AirportService.getInstance().checkAirForClick(marker);
			}

		}
		return false;
	}

}
