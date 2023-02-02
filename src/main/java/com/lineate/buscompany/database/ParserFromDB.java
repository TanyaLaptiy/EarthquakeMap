package com.lineate.buscompany.database;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import de.fhpotsdam.unfolding.marker.Marker;

public class ParserFromDB implements RepInterface {
	public void writeToFile(List<Marker> list, String fileName) {
		try (FileWriter writer = new FileWriter(fileName, false)) {
			for (Marker marker : list) {
				writer.write(marker.toString());
				writer.append('\n');
			}
			writer.flush();
		} catch (IOException ex) {

			System.out.println(ex.getMessage());
		}
	}

}
