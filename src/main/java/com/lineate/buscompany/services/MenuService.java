package com.lineate.buscompany.services;

import java.util.List;


import com.lineate.buscompany.dao.QuakeDAO;
import com.lineate.buscompany.dao.QuakeDAOImplementation;
import com.lineate.buscompany.model.menuMarker;
import com.lineate.buscompany.parsing.ProcessingParse;
import de.fhpotsdam.unfolding.marker.Marker;
import processing.core.PImage;


public class MenuService {
	private static final QuakeDAO quakeDAO = new QuakeDAOImplementation();
	ProcessingParse parser = new ProcessingParse();

	private List<Marker> menuMarkers;
	private static MenuService instance; // #1

	private MenuService() {
	}

	public static MenuService getInstance() {
		if (instance == null) {
			instance = new MenuService();
		}
		return instance;
	}

	public void setProfileLogo(){
		parser.setProfileLogo();
	}
	public PImage getProfileLogo(){
		return parser.getProfileLogo();
	}
	public void parse() {
		parser.parseMenuMarkers();
	}
	public void setMenuMarkers(List<Marker> menuMarkers) {
		this.menuMarkers = menuMarkers;
	}

	public List<Marker> getMenuMarkers() {
		return menuMarkers;
	}

	public void addMenuMarker(menuMarker menuMarker) {
		menuMarkers.add(menuMarker);
	}

	public int getIndexOfMenu(Marker mark) {
		return (menuMarkers).indexOf(mark) - 1;
	}

	public boolean isClickenSixMarket() {
		menuMarker menu = (menuMarker) getMenuMarkerByIndex(5);
		return menu.getClicked();
	}

	public static void secondIsUnclicked() {
		MarkerService.getInstance().getLastClicked().setClicked(false);
		unhideMarkers(quakeDAO.getQuakeMarkers());
		CityService.getInstance().hideCityInfo();
	}

	public static void firstIsUnclicked() {
		MarkerService.getInstance().getLastClicked().setClicked(false);
		unhideMarkers(CityService.getInstance().getCityMarkers());
		CityService.getInstance().hideCityInfo();
	}

	public static void thirdIsUnclicked() {
		MarkerService.getInstance().getLastClicked().setClicked(true);
		unhideMarkers(AirportService.getInstance().getAirportList());
		CityService.getInstance().hideCityInfo();
	}

	private static void unhideMarkers(List<Marker> array) {
		for (Marker marker : array) {
			marker.setHidden(false);
		}
	}

	public Marker getMenuMarkerByIndex(int index) {
		return menuMarkers.get(index);
	}

}
