package com.lineate.buscompany.interactiveMap.GUI;

import com.lineate.buscompany.ResourseService;
import com.lineate.buscompany.services.EarthquakeService;
import processing.core.PApplet;
import processing.core.PFont;

import static processing.core.PConstants.*;

public class SideBlock {
    public void show(PApplet pApplet, ResourseService resourseService) {
        pApplet.fill(255, 250, 240);
        pApplet.stroke(0);
        int xbase = 40;
        int ybase = 650;

        int stringsCount = 10;

        pApplet.stroke(204, 102, 0);

        pApplet.rect(xbase - 15, ybase, 220, stringsCount * 17 + 30);

        PFont font;
        font = pApplet.createFont("Georgia", 18);
        pApplet.textFont(font);

        pApplet.fill(255, 0, 0);
        pApplet.textAlign(LEFT, CENTER);
        pApplet.textSize(13);
        pApplet.text("Welcome!", xbase + 55, ybase + 25);

        pApplet.fill(150, 30, 30);

        pApplet.fill(0, 0, 0);
        pApplet.textAlign(LEFT, CENTER);
        int x = 15;
        pApplet.image(resourseService.getWebLogo(), xbase+110, ybase + 10 + x, 60, 60);
        pApplet.text("Visit our website!", xbase , ybase + 35 + x);x += 25;

        pApplet.image(resourseService.getGitLogo(), xbase+140, ybase + 60 + x, 25, 25);
        pApplet.text("Check out our git repositories!", xbase , ybase + 45 + x);x += 25;

        pApplet.image(resourseService.getGitLogo(), xbase+140, ybase + 60 + x, 25, 25);
        pApplet.text("desktop application:", xbase+10 , ybase + 45 + x); x += 25;

        pApplet.image(resourseService.getGitLogo(), xbase+140, ybase + 60 + x, 25, 25);
        pApplet.text("mobile application:", xbase+10 , ybase + 45 + x); x += 25;

        pApplet.text("website:", xbase+10 , ybase + 45 + x);


    }
}
