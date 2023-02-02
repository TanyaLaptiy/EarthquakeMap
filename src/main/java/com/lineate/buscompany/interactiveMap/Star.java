package com.lineate.buscompany.interactiveMap;

import processing.core.PApplet;

public class Star extends PApplet {

    private int star1 = color(255, 255, 204);
    private int star2 = color(255, 255, 204);
    private int star3 = color(255, 255, 204);
    private int star4 = color(255, 255, 255);
    private int star5 = color(255, 255, 255);

    public void setup() {
        size(640, 360);
    }

    public void draw() {
        float a = (float) (width * 0.8);
        float b = (float) (height * 0.5);

        pushMatrix();
        translate(a, b);
        rotate((float) (frameCount / -100.0));
        star(0, 0, 30, 70, 5);
        popMatrix();
    }

    public void star(float x, float y, float radius1, float radius2, int npoints) {
        float angle = TWO_PI / npoints;
        float halfAngle = (float) (angle / 2.0);
        beginShape();
        for (float a = 0; a < TWO_PI; a += angle) {
            float sx = x + cos(a) * radius2;
            float sy = y + sin(a) * radius2;
            vertex(sx, sy);
            sx = x + cos(a + halfAngle) * radius1;
            sy = y + sin(a + halfAngle) * radius1;
            vertex(sx, sy);
        }
        endShape(CLOSE);
    }

    public int getStar1() {
        return star1;
    }

    public void setStar0() {
        star1 = color(255, 255, 255);
        star2 = color(255, 255, 255);
        star3 = color(255, 255, 255);
        star4 = color(255, 255, 255);
        star5 = color(255, 255, 255);

    }

    public void setStar1() {
        star1 = color(255, 255, 204);
        star2 = color(255, 255, 255);
        star3 = color(255, 255, 255);
        star4 = color(255, 255, 255);
        star5 = color(255, 255, 255);

    }

    public int getStar2() {
        return star2;
    }

    public void setStar2() {
        star1 = color(255, 255, 204);
        star2 = color(255, 255, 204);
        star3 = color(255, 255, 255);
        star4 = color(255, 255, 255);
        star5 = color(255, 255, 255);
    }

    public int getStar3() {
        return star3;
    }

    public void setStar3() {
        star1 = color(255, 255, 204);
        star2 = color(255, 255, 204);
        star3 = color(255, 255, 204);
        star4 = color(255, 255, 255);
        star5 = color(255, 255, 255);
    }

    public int getStar4() {
        return star4;
    }

    public void setStar4() {
        star1 = color(255, 255, 204);
        star2 = color(255, 255, 204);
        star3 = color(255, 255, 204);
        star4 = color(255, 255, 204);
        star5 = color(255, 255, 255);
    }

    public int getStar5() {
        return star5;
    }

    public void setStar5() {
        star1 = color(255, 255, 204);
        star2 = color(255, 255, 204);
        star3 = color(255, 255, 204);
        star4 = color(255, 255, 204);
        star5 = color(255, 255, 204);
    }
}
