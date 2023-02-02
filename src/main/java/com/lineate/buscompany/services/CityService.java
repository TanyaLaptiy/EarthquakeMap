package com.lineate.buscompany.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.lineate.buscompany.dao.QuakeDAO;
import com.lineate.buscompany.dao.QuakeDAOImplementation;
import com.lineate.buscompany.model.*;
import com.lineate.buscompany.parsing.ProcessingParse;
import de.fhpotsdam.unfolding.marker.Marker;

public class CityService {
	private static final QuakeDAO quakeDAO = new QuakeDAOImplementation();
	ProcessingParse parser = new ProcessingParse();

	private List<Marker> countryMarkers;
	private List<Marker> cityMarkers;
	private static CityService instance; // #1

	private CityService() {
	}

	public static CityService getInstance() {
		if (instance == null) {
			instance = new CityService();
		}
		return instance;
	}

	public void parse() {
		parser.parseCountries();
	}
	public void setCityMarkers(List<Marker> cityMarkers) {
		this.cityMarkers = cityMarkers;
	}

	public List<Marker> getCityMarkers() {
		return cityMarkers;
	}

	public void addCityMarker(CityMarker city) {
		this.cityMarkers.add(city);
	}

	public void hideCityMarkers(Marker mark) { //
		MarkerService.getInstance().setLastClicked((CommonMarker) mark);

		MarkerService.getInstance().getLastClicked().setClicked(true);
		for (Marker mhide : cityMarkers) {
			mhide.setHidden(true);
		}

	}

	public void hideCityInfo() {
		ButtonModel.setClick(false);
		for (Marker m : cityMarkers) {
			CityMarker city = (CityMarker) m;
			city.showInfo(false);
		}

	}

	public void setCityMarkers() {
		setCityMarkers(new ArrayList<Marker>());
	}

	public void checkCitiesForClick(Marker marker) {
		menuMarker menuCity = (menuMarker) MenuService.getInstance().getMenuMarkerByIndex(0);
		menuCity.setClicked(true);
		menuMarker menuQuacke = (menuMarker) MenuService.getInstance().getMenuMarkerByIndex(1);
		menuQuacke.setClicked(true);
		menuMarker menu = (menuMarker) MenuService.getInstance().getMenuMarkerByIndex(5);
		menu.setClicked(true);

		cityMarkers.stream().forEach(a -> ((CityMarker) a).showInfo(false));

		if (!marker.isHidden()) {
			CityMarker city = (CityMarker) marker;
			if (city != null) {
				if (ButtonModel.getClick()) {
					ButtonModel.setClick(false);
					city.showInfo(false);
				} else {
					ButtonModel.setClick(true);
					city.showInfo(true);

				}
			}
			if (AirportService.getInstance().getRouteList().stream().filter(a -> !a.isHidden())
					.collect(Collectors.toList()).size() == 0) {
				hideMarkers(marker, cityMarkers);
			}
			for (Marker mhide : quakeDAO.getQuakeMarkers()) {
				EarthquakeMarker quakeMarker = (EarthquakeMarker) mhide;
				if (quakeMarker.getDistanceTo(marker.getLocation()) > quakeMarker.threatCircle()) {
					quakeMarker.setHidden(true);
				}
			}
			return;
		}
	}

	private static void hideMarkers(Marker marker, List<Marker> array) {

		MarkerService.getInstance().setLastClicked((CommonMarker) marker);
		for (Marker mhide : array) {
			if (mhide != MarkerService.getInstance().getLastClicked()) {

				mhide.setHidden(true);
			}
		}
	}

	public List<Marker> getCountryMarkers() {
		return countryMarkers;
	}

	public void setCountryMarkers(List<Marker> countryMarkers) {
		this.countryMarkers = countryMarkers;
	}

}
