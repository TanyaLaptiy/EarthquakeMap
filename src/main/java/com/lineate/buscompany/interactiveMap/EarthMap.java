package com.lineate.buscompany.interactiveMap;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import com.lineate.buscompany.parsing.Parser;
import com.lineate.buscompany.services.AirportService;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class EarthMap extends PApplet {

	private static final long serialVersionUID = 1L;
	private UnfoldingMap map;
	private SimplePointMarker berlinMarker;

	public void setup() {

		size(200, 230);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("./data/mouse7.png");
		Cursor c = toolkit.createCustomCursor(image, new Point(getX(), getY()), "img");
		setCursor(c);

		startApp();

	}

	public void startApp() {

		map = new UnfoldingMap(this, 0, 0, 200, 230, new MBTilesMapProvider(Parser.getmbTilesString()));
		map.setZoomRange(1.7f, 3);
		map.panTo(new Location (49.7,22.5));

		map.setTweening(true);
		map.zoom(1.7f);

		MapUtils.createDefaultEventDispatcher(this, map);

	}

	public void draw() {
		background(215, 215, 215);

		map.draw();

	}

	@Override
	public void mouseMoved() {

	}

	@Override
	public void mouseClicked() {
		map.getMarkerManager(0).removeMarker(berlinMarker);
		Location location = map.getLocation(mouseX, mouseY);

		berlinMarker = new SimplePointMarker(location);
		TextFieldRequest.settlat(location.getLat());
		TextFieldRequest.settlon(location.getLon());
		map.addMarkers(berlinMarker);

		Location second = null;

		double dist = AirportService.getInstance().getAirportList().get(0).getDistanceTo(location);
		for (Marker r : AirportService.getInstance().getAirportList()) {


			if (dist > location.getDistance(r.getLocation())) {
				second = r.getLocation();
				dist = location.getDistance(r.getLocation());
				System.out.print("\n\n ! " +r.getStringProperty("city").toString() + ", " + r.getStringProperty("country").toString());
				TextFieldRequest.setloc(
						r.getStringProperty("city").toString() + "; " + r.getStringProperty("country").toString());
			}

		}
		TextFieldRequest.setloc((int) dist);
		double PI = Math.PI;
		double dTeta = Math
				.log(Math.tan((second.getLat() / 2) + (PI / 4)) / Math.tan((location.getLat() / 2) + (PI / 4)));
		double dLon = Math.abs(location.getLon() - second.getLon());
		double teta = Math.atan2(dLon, dTeta);
		double direction = Math.round(Math.toDegrees(teta));


		String res = null;
		if (direction >= 350 ||direction >= 0 && direction < 10) {
			res = "N";
		} else if (direction >= 10 && direction < 35) {
			res = "NNE";
		} else if (direction >= 35 && direction < 60) {
			res = "NE";
		} else if (direction >= 60 && direction < 80) {
			res = "ENE";
		} else if (direction >= 80 && direction < 100) {
			res = "E";
		} else if (direction >= 100 && direction < 125) {
			res = "ESE";
		} else if (direction >= 125 && direction < 150) {
			res = "SE";
		} else if (direction >= 150 && direction < 170) {
			res = "SSE";
		} else if (direction >= 170 && direction < 190) {
			res = "S";
		} else if (direction >= 190 && direction < 215) {
			res = "SSW";
		} else if (direction >= 215 && direction < 235) {
			res = "SW";
		} else if (direction >= 235 && direction < 260) {
			res = "WSW";
		} else if (direction >= 260 && direction < 280) {
			res = "W";
		} else if (direction >= 280 && direction < 305) {
			res = "WNW";
		} else if (direction >= 305 && direction < 325) {
			res = "NW";
		} else if (direction >= 325 && direction < 350) {
			res = "NW";
		}
		TextFieldRequest.setDirection(res);

	}

	public float getLat() {
		return berlinMarker.getLocation().getLat();
	}

	public float getLon() {
		return berlinMarker.getLocation().getLon();
	}

}
