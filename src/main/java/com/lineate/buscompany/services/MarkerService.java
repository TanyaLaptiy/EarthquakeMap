package com.lineate.buscompany.services;

import java.util.List;

import com.lineate.buscompany.dao.QuakeDAO;
import com.lineate.buscompany.dao.QuakeDAOImplementation;
import com.lineate.buscompany.database.DataBase;
import com.lineate.buscompany.model.CommonMarker;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import processing.core.PGraphics;

public class MarkerService {
	private static final QuakeDAO quakeDAO = new QuakeDAOImplementation();
	private UnfoldingMap map;
	private UnfoldingMap consolePanel;
	private CommonMarker lastSelected;
	private CommonMarker lastClicked;
	private static MarkerService instance; // #1

	private MarkerService() {
	}

	public static MarkerService getInstance() {
		if (instance == null) {
			instance = new MarkerService();
		}
		return instance;
	}

	public void hideMarkers(Marker mark, List<Marker> array, boolean hide) {
		lastClicked = (CommonMarker) mark;
		lastClicked.setClicked(hide);
		for (Marker mhide : array) {
			mhide.setHidden(true);

		}
	}

	public CommonMarker getLastClicked() {
		return lastClicked;
	}

	public void setLastClickedNull() {
		lastClicked = null;
	}

	public void setLastClicked(CommonMarker marker) {
		lastClicked = marker;
	}

	public void unselected() {
		lastSelected.setSelected(false);
		lastSelected = null;
	}

	public CommonMarker getLastSelected() {
		return lastSelected;
	}

	public void drawTitleOnTop(PGraphics buffer, float mouseX, float mouseY) {
		lastSelected.drawTitleOnTop(buffer, mouseX, mouseY);
	}

	public void setMap(UnfoldingMap map) {
		this.map = map;
	}

	public UnfoldingMap getMap() {
		return map;
	}

	public void selectMarkerIfHover(CommonMarker marker) {
		lastSelected = marker;
		marker.setSelected(true);
		return;

	}

	public void addMarkersToMap() {
		map.addMarkers(AirportService.getInstance().getRouteList());
		map.addMarkers(DataBase.getInstance().getQuakeMarkers());
		map.addMarkers(AirportService.getInstance().getAirportList());
		map.addMarkers(CityService.getInstance().getCityMarkers());

	}
	public void addEarthQuakeToMap() {
		map.addMarkers(DataBase.getInstance().getQuakeMarkers());

	}
	public void addMenuMarkersToMap() {
		consolePanel.addMarkers(MenuService.getInstance().getMenuMarkers());
	}

	public void setSecMap(UnfoldingMap map) {
		this.consolePanel = map;
	}

	public UnfoldingMap getConsole() {
		return consolePanel;
	}

	public static void selectByCountry(String country) {
		if (country != null && !country.isEmpty()) {
			for (Marker r : AirportService.getInstance().getAirportList()) {

				if (r.getProperty("country").toString().equals(country)) {
					r.setHidden(false);
				} else {
					r.setHidden(true);
				}
			}
			for (Marker r : quakeDAO.getQuakeMarkers()) {

				String quakeTitle = r.getProperty("title").toString();
				String quakeCountry = quakeTitle.substring(quakeTitle.lastIndexOf(','));

				if (quakeCountry.equals(country)) {
					r.setHidden(false);
				} else {
					r.setHidden(true);
				}

			}
			for (Marker r : CityService.getInstance().getCityMarkers()) {

				if (r.getProperty("country").toString().equals(country)) {
					r.setHidden(false);
				} else {
					r.setHidden(true);
				}
			}
		}

	}

	public static void unhideMarkers() {
		unhideMarkers(quakeDAO.getQuakeMarkers());
		unhideMarkers(AirportService.getInstance().getRouteList());
		unhideMarkers(CityService.getInstance().getCityMarkers());
		unhideMarkers(AirportService.getInstance().getAirportList());
	}

	private static void unhideMarkers(List<Marker> array) {
		for (Marker marker : array) {
			marker.setHidden(false);
		}
	}

}
