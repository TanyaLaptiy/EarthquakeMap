package com.lineate.buscompany.interactiveMap.GUI;

import com.lineate.buscompany.services.EarthquakeService;
import processing.core.PApplet;
import processing.core.PFont;

import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.RIGHT;

public class GeneralInfo {
    public void show(PApplet pApplet) {
        pApplet.fill(255, 250, 240);
        pApplet.stroke(0);
        int xbase = pApplet.getSize().width - 180;
        int ybase = 30;

        int stringsCount = 0;
        for (String str : EarthquakeService.getCountriesWithQuakes()) {
            stringsCount++;
        }
        pApplet.stroke(204, 102, 0);

        pApplet.rect(xbase - 75, ybase, 220, stringsCount * 17 + 30);

        PFont font;
        font = pApplet.createFont("Georgia", 18);
        pApplet.textFont(font);

        pApplet.fill(255, 0, 0);
        pApplet.textAlign(RIGHT, CENTER);
        pApplet.textSize(12);
        pApplet.text("Information", xbase + 85, ybase + 25);

        pApplet.fill(150, 30, 30);

        pApplet.fill(0, 0, 0);
        pApplet.textAlign(RIGHT, CENTER);
        int x = 15;

        for (String str : EarthquakeService.getCountriesWithQuakes()) {
            pApplet.text(str, xbase + 140, ybase + 25 + x);
            x += 15;
        }

    }
}
