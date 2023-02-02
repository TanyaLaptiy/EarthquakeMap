package com.lineate.buscompany.interactiveMap;

import processing.core.PApplet;

public class Clock extends PApplet {
    private int cx, cy;
    private float secondsRadius;
    private float minutesRadius;
    private float hoursRadius;
    private float clockDiameter;

    public void setup() {
        size(640, 360);
        stroke(255);

        int radius = min(width, height) / 2;
        secondsRadius = (float) (radius * 0.72);
        minutesRadius = (float) (radius * 0.60);
        hoursRadius = (float) (radius * 0.50);
        clockDiameter = (float) (radius * 1.8);

        cx = width / 2;
        cy = height / 2;
    }

    public void draw() {

        fill(80);
        noStroke();
        ellipse(cx, cy, clockDiameter, clockDiameter);

        float s = map(second(), 0, 60, 0, TWO_PI) - HALF_PI;
        float m = map(minute() + norm(second(), 0, 60), 0, 60, 0, TWO_PI) - HALF_PI;
        float h = map(hour() + norm(minute(), 0, 60), 0, 24, 0, TWO_PI * 2) - HALF_PI;

        stroke(255);
        strokeWeight(1);
        line(cx, cy, cx + cos(s) * secondsRadius, cy + sin(s) * secondsRadius);
        strokeWeight(2);
        line(cx, cy, cx + cos(m) * minutesRadius, cy + sin(m) * minutesRadius);
        strokeWeight(4);
        line(cx, cy, cx + cos(h) * hoursRadius, cy + sin(h) * hoursRadius);

        strokeWeight(2);
        beginShape(POINTS);
        for (int a = 0; a < 360; a += 6) {
            float angle = radians(a);
            float x = cx + cos(angle) * secondsRadius;
            float y = cy + sin(angle) * secondsRadius;
            vertex(x, y);
        }
        endShape();
    }

    public void setSecondsRadius(float f) {
        secondsRadius = f;
    }

    public void setMinutesRadius(float f) {
        minutesRadius = f;
    }

    public void setHoursRadius(float f) {
        hoursRadius = f;
    }

    public void setClockDiameter(float f) {
        clockDiameter = f;
    }

    public void setCX(int f) {
        cx = f;
    }

    public void setCY(int f) {
        cy = f;
    }

    public float getSecondsRadius() {
        return secondsRadius;
    }

    public float getMinutesRadius() {
        return minutesRadius;
    }

    public float getHoursRadius() {
        return hoursRadius;
    }

    public float getClockDiameter() {
        return clockDiameter;
    }

    public int getCX() {
        return cx;
    }

    public int getCY() {
        return cy;
    }
}
