package com.lineate.buscompany.model;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public class CityMarker extends CommonMarker {

	public static int TRI_SIZE = 5;
	private PImage img;
	private PImage logo;
	private boolean show = false;
	private static boolean overButton = false;
	private String url;
	private Dimension dimension;
	
	public CityMarker(Location location) {
		super(location);
	}

	public CityMarker(Feature city, PImage img, PImage logo, 
			String url, Dimension dimension) {
		super(((PointFeature) city).getLocation(), city.getProperties());
		this.img = img;
		this.logo = logo;
		this.url = url;
		this.dimension = dimension;
		// properties: "name" (city name), "country" (country name)
		// "population" (population, in millions)
	}
	
	
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.pushStyle();
		pg.imageMode(PConstants.CORNER);
		pg.image(img, x - 10, y - 15, 30, 35);
		if (show) {
			// System.out.print("\n\n\n ss");
			// pg.image(logo,860,10);
			pg.image(logo, dimension.width - 440, 10);

			// create bottom
			if (overButton == true) {
				pg.stroke(204, 102, 0);
				pg.fill(215, 215, 215);
			} else {
				pg.stroke(204, 102, 0);
				pg.noFill();
			}

			int width = dimension.width - 100;
			pg.rect(width, 60 + 130 - 8, 30, 30);

			pg.line(width + 5, 105 + 130 - 28, width + 25, 85 + 130 - 28);

			pg.line(width + 10, 85 + 130 - 28, width + 25, 85 + 130 - 28);

			pg.line(width + 25, 85 + 130 - 28, width + 25, 100 + 130 - 28);

			ButtonModel.setUrl(url);

		}


	}

	public boolean isShow() {
		return show;
	}
	public void showInfo(boolean show) {
		this.show = show;

	}

	public void showTitle(PGraphics pg, float x, float y) {
		//pg.clear();
		String name = getCity() + " " + getCountry() + " ";
		String pop = "Pop: " + getPopulation() + " Million";
		pg.beginDraw();

		pg.pushStyle();

		pg.fill(255, 255, 255);
		pg.textSize(12);
		pg.rectMode(PConstants.CORNER);
		pg.rect(x, y - TRI_SIZE - 39, Math.max(pg.textWidth(name), pg.textWidth(pop)) + 6, 39);
		pg.fill(0, 0, 0);
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.text(name, x + 3, y - TRI_SIZE - 33);
		pg.text(pop, x + 3, y - TRI_SIZE - 18);

		pg.popStyle();
		

	}

	private String getCity() {

		return getStringProperty("name");
	}

	public String getCountry() {
		return getStringProperty("country");
	}

	private float getPopulation() {
		return Float.parseFloat(getStringProperty("population"));
	}


	public static void clickForLink(boolean click) {
		overButton = click;

	}

	public static boolean checkclickForLink() {
		return overButton;
	}

	@Override
	public String toString() {
		return "CityMarker [clicked=" + clicked + ", radius=" + radius + ", color=" + color + ", strokeColor="
				+ strokeColor + ", strokeWeight=" + strokeWeight + ", highlightColor=" + highlightColor
				+ ", highlightStrokeColor=" + highlightStrokeColor + ", location=" + location + ", properties="
				+ properties + ", selected=" + selected + ", hidden=" + hidden + ", id=" + id + "]";
	}

}
