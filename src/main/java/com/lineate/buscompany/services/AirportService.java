package com.lineate.buscompany.services;

import java.util.ArrayList;
import java.util.List;

import com.lineate.buscompany.model.AirportMarker;
import com.lineate.buscompany.model.CommonMarker;
import com.lineate.buscompany.parsing.ProcessingParse;

import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;


public class AirportService {
	private List<Marker> routeList = new ArrayList<>();
	private List<Marker> airportList;
	ProcessingParse parser = new ProcessingParse();
	private static AirportService instance; // #1

	private AirportService() {
	}

	public static AirportService getInstance() {
		if (instance == null) {
			instance = new AirportService();
		}
		return instance;
	}

	public void parse() {
		parser.parseAirports();
	}
	public void hideRouteByAir() {
		if (airportList.contains(MarkerService.getInstance().getLastClicked())) {

			AirportMarker r = (AirportMarker) MarkerService.getInstance().getLastClicked();
			String airStr = r.getFeature().getId().toString();

			for (Marker rout : routeList) {

				String routStr = rout.getProperty("destination").toString();
				String sourceStr = rout.getProperty("source").toString();

				if (routStr.equals(airStr) || sourceStr.equals(airStr)) {
					rout.setHidden(true);
				}
			}
		}
	}

	public void hideAirMarkers(Marker mark) {
		MarkerService.getInstance().setLastClicked((CommonMarker) mark);
		MarkerService.getInstance().getLastClicked().setClicked(false);
		for (Marker mhide : airportList) {
			mhide.setHidden(true);

		}
		for (Marker mhide : routeList) {
			mhide.setHidden(true);
		}

	}

	private void hideMarkers(Marker marker, List<Marker> array) {

		MarkerService.getInstance().setLastClicked((CommonMarker) marker);
		for (Marker mhide : array) {
			if (mhide != MarkerService.getInstance().getLastClicked()) {

				mhide.setHidden(true);
			}
		}
	}

	public void checkAirForClick(Marker marker) {

		if (!marker.isHidden()) {

			hideMarkers(marker, airportList);
			hideAirRouts();

			for (Marker m : routeList) {
				AirportMarker r = (AirportMarker) marker;
				String airStr = r.getFeature().getId().toString();
				String routStr = m.getProperty("destination").toString();
				String sourceStr = m.getProperty("source").toString();

				if (routStr.equals(airStr) || sourceStr.equals(airStr)) {
					m.setHidden(false);

					for (Marker mhide : airportList) {
						AirportMarker otherAiroports = (AirportMarker) mhide;
						String airID = otherAiroports.getFeature().getId().toString();
						if (routStr.equals(airID) || sourceStr.equals(airID)) {
							mhide.setHidden(false);
						}
					}
				}
			}

		}
	}

	public void hideAirRouts() {

		for (Marker r : routeList) {
			r.setHidden(true);

		}
		for (Marker ai : airportList) {
			ai.setHidden(true);

		}
	}

	public void setRouteList(List<Marker> routeList) {
		this.routeList = routeList;
	}

	public List<Marker> getRouteList() {
		return routeList;
	}

	public void addRoute(SimpleLinesMarker Marker) {
		this.routeList.add(Marker);
	}

	public void setAirportList(List<Marker> airportList) {
		this.airportList = airportList;
	}

	public List<Marker> getAirportList() {
		return airportList;
	}

	public void addAirport(AirportMarker Marker) {
		this.airportList.add(Marker);
	}

}
