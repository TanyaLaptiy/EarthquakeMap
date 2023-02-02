package com.lineate.buscompany.interactiveMap.GUI;

import com.lineate.buscompany.ResourseService;
import com.lineate.buscompany.services.EarthquakeService;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import static processing.core.PConstants.*;

public class KeyFrame {

    public void show(PApplet pApplet, ResourseService resourseService) {
        pApplet.fill(255, 250, 240);
        pApplet.stroke(0);

        pApplet.image(resourseService.getPhoto(), 10, 10, 300, 350);
        PFont font;
        font =  pApplet.createFont("Georgia", 18);
        pApplet.textFont(font);

        pApplet.fill(194, 92, 0);
        pApplet.textAlign(LEFT, CENTER);
        pApplet.textSize(12);

        pApplet.text("Shapes", 145, 75);

        pApplet.image(resourseService.getAirLogo(), 120, 88, 20, 20);
        pApplet.image(resourseService.getCountryLogo(), 120, 110, 20, 25);

        pApplet.fill(204, 102, 0);

        pApplet.fill(0, 0, 0);
        pApplet.textAlign(LEFT, CENTER);
        pApplet.text("Airport Marker", 145, 100);

        pApplet.text("City Marker", 145, 120);

        pApplet.text("Land Quake", 145, 140);
        pApplet.text("Ocean Quake", 145, 160);
        pApplet.fill(194, 92, 0);
        pApplet.textAlign(LEFT, CENTER);
        pApplet.textSize(12);
        pApplet.text("Color", 125, 180);

        pApplet.fill(255, 255, 255);
        pApplet.ellipse(130, 140, 10, 10);

        pApplet.fill(194, 92, 0);
        pApplet.fill(255, 255, 255);
        pApplet.rect(125, 155, 10, 10);

        pApplet.fill( pApplet.color(153, 255, 255));
        pApplet.ellipse(90, 200, 12, 12);
        pApplet.fill( pApplet.color(0, 128, 255));
        pApplet.ellipse(90, 220, 12, 12);
        pApplet.fill( pApplet.color(0, 0, 255));
        pApplet.ellipse(90, 240, 12, 12);

        pApplet.textAlign(LEFT, CENTER);
        pApplet.fill(0, 0, 0);
        pApplet.text("Shallow", 105, 200);
        pApplet.text("Intermediate", 105, 220);
        pApplet.text("Deep", 105, 240);

        pApplet.text("Past hour", 105, 260);
        pApplet.image(resourseService.getClockLogo(), 80, 250, 20, 20);

        pApplet.fill(255, 255, 255);

        pApplet.fill(194, 92, 0);
        pApplet.textAlign(LEFT, CENTER);
        pApplet.textSize(12);
        pApplet.text("Size", 125, 280);
        pApplet.fill(0, 0, 0);
        pApplet.textAlign(LEFT, CENTER);

        pApplet.text("~ Magnitude", 80, 300);
    }
}
