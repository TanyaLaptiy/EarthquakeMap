package com.lineate.buscompany.interactiveMap.GUI;

import com.lineate.buscompany.interactiveMap.Clock;
import processing.core.PApplet;

import static processing.core.PConstants.*;

public class ClockInFrame {
    private Clock clock;

    public void drawClock(PApplet pApplet) {

        pApplet.beginShape();
        pApplet.fill(160, 160, 150);
        pApplet.noStroke();
        pApplet.ellipse(clock.getCX(), clock.getCY(), clock.getClockDiameter(), clock.getClockDiameter());

        float s = pApplet.map( pApplet.second(), 0, 60, 0, TWO_PI) - HALF_PI;
        float m =  pApplet.map( pApplet.minute() +  pApplet.norm( pApplet.second(), 0, 60), 0, 60, 0, TWO_PI) - HALF_PI;
        float h =  pApplet.map( pApplet.hour() +  pApplet.norm( pApplet.minute(), 0, 60), 0, 24, 0, TWO_PI * 2) - HALF_PI;

        pApplet.stroke(255);
        pApplet.strokeWeight(1);
        pApplet.stroke(205, 249, 255);
        pApplet.line(clock.getCX(), clock.getCY(), clock.getCX() +  pApplet.cos(s) * clock.getSecondsRadius(),
                clock.getCY() +  pApplet.sin(s) * clock.getSecondsRadius());
        pApplet.strokeWeight(2);
        pApplet.line(clock.getCX(), clock.getCY(), clock.getCX() +  pApplet.cos(m) * clock.getMinutesRadius(),
                clock.getCY() +  pApplet.sin(m) * clock.getMinutesRadius());
        pApplet.strokeWeight(4);
        pApplet.line(clock.getCX(), clock.getCY(), clock.getCX() +  pApplet.cos(h) * clock.getHoursRadius(),
                clock.getCY() +  pApplet.sin(h) * clock.getHoursRadius());

        pApplet.strokeWeight(2);
        pApplet.endShape();
        pApplet.beginShape(POINTS);
        pApplet.stroke(255);
        for (int a = 0; a < 360; a += 6) {
            float angle =  pApplet.radians(a);
            float x = clock.getCX() +  pApplet.cos(angle) * clock.getSecondsRadius();
            float y = clock.getCY() +  pApplet.sin(angle) * clock.getSecondsRadius();
            pApplet.vertex(x, y);
        }
        pApplet.endShape();

    }

    public void addClock() {
        clock = new Clock();

        int radius = 55;

        clock.setSecondsRadius((float) (radius * 0.72));
        clock.setMinutesRadius((float) (radius * 0.60));
        clock.setHoursRadius((float) (radius * 0.50));
        clock.setClockDiameter((float) (radius * 1.8));

        clock.setCX(255);
        clock.setCY(306);
    }
}
