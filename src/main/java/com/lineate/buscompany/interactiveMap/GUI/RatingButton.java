package com.lineate.buscompany.interactiveMap.GUI;

import com.lineate.buscompany.ResourseService;
import processing.core.PApplet;

public class RatingButton {
    private int ratingButon_x = 1100;
    private int ratingButon_y = 10;
    public void show(PApplet pApplet) {
        pApplet.noStroke();
        pApplet.noFill();
        pApplet.rect(ratingButon_x, ratingButon_y, 200, ratingButon_y + 30);
    }
}
