package com.lineate.buscompany.database;

import java.util.List;

import de.fhpotsdam.unfolding.marker.Marker;

public interface RepInterface {
	void writeToFile(List<Marker> list, String fileName);
}
