package com.lineate.buscompany.parsing;

import java.util.HashMap;
import java.util.Map;

public class Configurator {
	private Map<FileNames, String> fileNames = new HashMap<>();

	public Configurator() {
		fileNames.put(FileNames.mbTilesString, "C:/Users/1/eclipse-workspace/earthquakes/data/blankLight-1-3.mbtiles");
		fileNames.put(FileNames.mbTilesStringDark, "C:/Users/1/eclipse-workspace/earthquakes/data/blankDark-1-3.mbtiles");
		fileNames.put(FileNames.mbTilesString2, "C:/Users/1/eclipse-workspace/earthquakes/data/key");
		fileNames.put(FileNames.earthquakesURL,
				"https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom");
		fileNames.put(FileNames.earthquakesURLpastHour,"https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.atom");

		fileNames.put(FileNames.earthquakesURLpastDay,"https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.atom");
		fileNames.put(FileNames.earthquakesURLoffline,
				"C:/Users/1/eclipse-workspace/earthquakes/data/2.5_week.atom");
		fileNames.put(FileNames.URLearthquakesFile, "C:/Users/1/eclipse-workspace/earthquakes/data/URL.txt");
		fileNames.put(FileNames.cityFile, "C:/Users/1/eclipse-workspace/earthquakes/data/city-data.json");
		fileNames.put(FileNames.airFile, "C:/Users/1/eclipse-workspace/earthquakes/data/airports.dat");
		fileNames.put(FileNames.routFile, "C:/Users/1/eclipse-workspace/earthquakes/data/routes.dat");
		fileNames.put(FileNames.menuFile, "C:/Users/1/eclipse-workspace/earthquakes/data/MenuMark.JSON");
		fileNames.put(FileNames.countryFile, "C:/Users/1/eclipse-workspace/earthquakes/data/countries.geo.json");

	}

	public void changeFileName(FileNames typeFile, String newName) {
		fileNames.put(typeFile, newName);
	}

	public String getFileName(FileNames typeFile) {
		return fileNames.get(typeFile);
	}

}
