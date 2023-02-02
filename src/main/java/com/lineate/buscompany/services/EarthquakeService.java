package com.lineate.buscompany.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.lineate.buscompany.dao.QuakeDAO;
import com.lineate.buscompany.dao.QuakeDAOImplementation;
import com.lineate.buscompany.model.*;
import com.lineate.buscompany.parsing.ProcessingParse;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractShapeMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;


public class EarthquakeService {
	private static final QuakeDAO quakeDAO = new QuakeDAOImplementation();
	static ProcessingParse parser = new ProcessingParse();
	

	static List<String> countriesWithQuakes;
	public static void parseToBD() {
		parser.parseEatrhBD();
	}

	public static void parseByDate(LocalDate date) {
		parser.parseEarthquakesByDate(date);
	}
	public static void parse(boolean online,boolean today) {
		parser.parseEarthquakes(online,today);
	}

	public static void setQuakeMarkers(List<Marker> quakeMarkers) {
		quakeDAO.setQuakeMarkers(quakeMarkers);
	}

	public static void addQuakeMarker(LandQuakeMarker Marker) {
		quakeDAO.addQuakeMarker(Marker);
	}

	public static void addQuakeMarker(OceanQuakeMarker Marker) {
		quakeDAO.addQuakeMarker(Marker);
	}

	public static List<Marker> getQuakeMarkers() {
		return quakeDAO.getQuakeMarkers();
	}
	
	public static void hideQuakeMarkers(Marker mark) {
		MarkerService.getInstance().setLastClicked((CommonMarker) mark);
		MarkerService.getInstance().getLastClicked().setClicked(true);

		for (Marker mhide : quakeDAO.getQuakeMarkers()) {
			mhide.setHidden(true);
		}

	}
	public static void hideQuakeMarkers() {

		for (Marker mhide : quakeDAO.getQuakeMarkers()) {
			mhide.setHidden(true);
		}

	}
	public static void checkEarthquakesForClick(EarthquakeMarker marker) {
		if (!marker.isHidden()) {

			hideMarkers(marker, quakeDAO.getQuakeMarkers());

			EarthquakeMarker earth = (EarthquakeMarker) marker;
			for (Marker mhide : CityService.getInstance().getCityMarkers()) {

				if (mhide.getDistanceTo(marker.getLocation()) < 1000
						|| earth.getProperty("title").toString().contains(((CityMarker) mhide).getCountry())) {
					mhide.setHidden(false);

				} else {

					mhide.setHidden(true);
				}
			}
			return;
		}

	}

	public static boolean isLand(PointFeature earthquake) {
		for (Marker country : CityService.getInstance().getCountryMarkers()) {
			if (isInCountry(earthquake, country)) {
				return true;
			}
		}
		return false;
	}


	public static void printQuakes() {
		countriesWithQuakes = new ArrayList<>();
		int totalWaterQuakes = quakeDAO.getQuakeMarkers().size();
		for (Marker country : CityService.getInstance().getCountryMarkers()) {
			String countryName = country.getStringProperty("name");
			int numQuakes = 0;
			for (Marker marker : quakeDAO.getQuakeMarkers()) {
				EarthquakeMarker eqMarker = (EarthquakeMarker) marker;
				if (eqMarker.isOnLand()) {
					if (countryName.equals(eqMarker.getStringProperty("country"))) {
						numQuakes++;
					}
				}
			}
			if (numQuakes > 0) {
				totalWaterQuakes -= numQuakes;
				countriesWithQuakes.add(countryName + ": " + numQuakes);
			}
		}
		countriesWithQuakes.add("OCEAN QUAKES: " + totalWaterQuakes);
		int res = quakeDAO.getQuakeMarkers().size() - totalWaterQuakes;
		countriesWithQuakes.add("LAND QUAKES: " + res);

	}

	public static List<String> getCountriesWithQuakes() {
		return countriesWithQuakes;
	}


	
	public static boolean isInCountry(PointFeature earthquake, Marker country) {
		Location checkLoc = earthquake.getLocation();
		if (country.getClass() == MultiMarker.class) {

			
			for (Marker marker : ((MultiMarker) country).getMarkers()) {

				if (((AbstractShapeMarker) marker).isInsideByLocation(checkLoc)) {
					earthquake.addProperty("country", country.getProperty("name"));
					return true;
				}
			}
		}

		else if (((AbstractShapeMarker) country).isInsideByLocation(checkLoc)) {
			earthquake.addProperty("country", country.getProperty("name"));

			return true;
		}
		return false;
	}

	private static void hideMarkers(Marker marker, List<Marker> array) {

		MarkerService.getInstance().setLastClicked((CommonMarker) marker);
		for (Marker mhide : array) {
			if (mhide != MarkerService.getInstance().getLastClicked()) {

				mhide.setHidden(true);
			}
		}
	}

}
