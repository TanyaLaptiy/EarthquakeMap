package com.lineate.buscompany.model;


import java.util.HashMap;

import com.lineate.buscompany.interactiveMap.TextFieldRequest;
import com.lineate.buscompany.services.AirportService;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public abstract class EarthquakeMarker extends CommonMarker {

	protected boolean isOnLand;

	// ������ ������� �������������
	protected float radius;
	

	// ��������� ��� ����������
	protected static final float kmPerMile = 1.6f;

	// ������ ��� ����� ����� ������ - ��������� �������������
	public static final float THRESHOLD_MODERATE = 5;
	// ������ ��� ����� ����� ������ - ������ �������������
	public static final float THRESHOLD_LIGHT = 4;

	// ������ ��� ����� ����� ������ - ��� ������������� �������
	public static final float THRESHOLD_INTERMEDIATE = 70;
	// ������ ��� ����� ����� ������ - ������� �������
	public static final float THRESHOLD_DEEP = 300;

//	private static final QuakeDAO quakeDAO = new QuakeDAOImplementation();
	PImage logo;

	// ����������� �����, ������������� � ����������� �������
	public abstract void drawEarthquake(PGraphics pg, float x, float y);

	public EarthquakeMarker(PointFeature feature, PImage logo) {
		super(feature.getLocation());
		java.util.HashMap<String, Object> properties = feature.getProperties();
		float magnitude = Float.parseFloat(properties.get("magnitude").toString());
		properties.put("radius", 2 * magnitude);
		//System.out.println(properties.toString());
		setProperties(properties);
		this.radius = 1.75f * getMagnitude();
		this.logo = logo;
		
	}
	
	public EarthquakeMarker(float latitude, float longitude, float magnitude, String location, String age, float depth, String county, PImage logo ) {
		super(new Location(latitude,longitude));
		java.util.HashMap<String, Object> properties=new HashMap<>();
		properties.put("depth",depth);
		properties.put("country",county);
		properties.put("magnitude",magnitude);
		properties.put("title","M"+magnitude+" "+getTitle(latitude,longitude));
		properties.put("radius", 2*radius);
		properties.put("age",age);
		setProperties(properties);
		this.radius = 1.75f * getMagnitude();
		this.logo = logo;
	}
	private String getTitle(float latitude, float longitude){
		String title;
		String loc = " ";
		Location location = new Location(latitude, longitude);

		Location second = null;

		double dist = AirportService.getInstance().getAirportList().get(0).getDistanceTo(location);
		for (Marker r : AirportService.getInstance().getAirportList()) {
			// System.out.print("\n" + r.getProperties().toString());

			if (dist > location.getDistance(r.getLocation())) {
				second = r.getLocation();
				dist = location.getDistance(r.getLocation());
				//System.out.print("\n\n ! " +r.getStringProperty("city").toString() + ", " + r.getStringProperty("country").toString());

				loc=r.getStringProperty("city").toString() + "; " + r.getStringProperty("country").toString();
//				TextFieldRequest.setloc(
//						r.getStringProperty("city").toString() + "; " + r.getStringProperty("country").toString());
			}

			// TextFieldRequest.setloc(r.getStringProperty("city").toString()+",
			// "+r.getStringProperty("country").toString());
			// System.out.println(r.getStringProperty("city").toString());
		}
		title= String.valueOf(dist).substring(0,7)+" km";
		//TextFieldRequest.setloc((int) dist);
		double PI = Math.PI;
		double dTeta = Math
				.log(Math.tan((second.getLat() / 2) + (PI / 4)) / Math.tan((location.getLat() / 2) + (PI / 4)));
		double dLon = Math.abs(location.getLon() - second.getLon());
		double teta = Math.atan2(dLon, dTeta);
		double direction = Math.round(Math.toDegrees(teta));
		//System.out.print("\n\n ! " + direction);
		// return direction;

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
		title+=" "+res+" of "+loc;
		//TextFieldRequest.setDirection(res);
		return title;
	}

	// �������� ����������� ����� drawEarthquake, � ����� ��������� ������� � ���
	// ������������� ������
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.pushStyle();
		pg.noStroke();
		colorDetermine(pg);

		// �������� ����������� �����, ������������� � �������� ������, ��� ���������
		// ����� �������
		drawEarthquake(pg, x, y);

		String age = getStringProperty("age");
		if ("Past Hour".equals(age)) {

			pg.image(logo, x, y, 15, 15);

		}

		pg.popStyle();

	}

	public void showTitle(PGraphics pg, float x, float y) {
		String title = getTitle();
		pg.pushStyle();

		pg.rectMode(PConstants.CORNER);
		pg.stroke(110);
		pg.fill(255, 255, 255);
		pg.rect(x, y + 15, pg.textWidth(title) + 6, 18, 5);

		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.fill(0);
		pg.text(title, x + 3, y + 18);
		pg.popStyle();

	}

	public double threatCircle() {
		double miles = 20.0f * Math.pow(1.8, 2 * getMagnitude() - 5);
		double km = (miles * kmPerMile);
		return km;
	}

	private void colorDetermine(PGraphics pg) {
		float depth = getDepth();
	//	pg.stroke(color);
	//	pg.stroke
		
		int alpha=50;
		
		if (depth < THRESHOLD_INTERMEDIATE) { // shallow
			
			pg.fill(153, 255, 255);
			//pg.stroke(153, 255, 255, 90);
			pg.stroke(0, 128, 255, alpha+50);
		} else if (depth < THRESHOLD_DEEP) {// intermediate
			pg.fill(0, 128, 255);
			pg.stroke(0, 128, 255, alpha);
		} else {
			pg.fill(0, 0, 255);// deep
			pg.stroke(0, 0, 255, alpha);
		}
		pg.strokeWeight(5);

	}

	@Override
	public String toString() {
		return "EarthquakeMarker [isOnLand=" + isOnLand + ", radius=" + radius + ", clicked=" + clicked + ", color="
				+ color + ", strokeColor=" + strokeColor + ", strokeWeight=" + strokeWeight + ", highlightColor="
				+ highlightColor + ", highlightStrokeColor=" + highlightStrokeColor + ", location=" + location
				+ ", properties=" + properties + ", selected=" + selected + ", hidden=" + hidden + ", id=" + id + "]";
	}

	public float getMagnitude() {
		return Float.parseFloat(getProperty("magnitude").toString());
	}

	public float getDepth() {
		return Float.parseFloat(getProperty("depth").toString());
	}

	public String getTitle() {

		return (String) getProperty("title");

	}

	public float getRadius() {
		return Float.parseFloat(getProperty("radius").toString());
	}

	public boolean isOnLand() {
		return isOnLand;
	}

	public void showInfo(PGraphics pg, float x, float y) {
		// TODO Auto-generated method stub
		
	}
}
