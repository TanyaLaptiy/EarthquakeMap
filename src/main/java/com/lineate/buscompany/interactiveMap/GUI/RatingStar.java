package com.lineate.buscompany.interactiveMap.GUI;

import com.lineate.buscompany.interactiveMap.Star;
import processing.core.PApplet;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;
import static processing.core.PConstants.*;

public class RatingStar {
    private final Star star = new Star();

    public void show(PApplet pApplet) {
        addStar(pApplet,0, star.getStar1());
        addStar(pApplet,40, star.getStar2());
        addStar(pApplet,80, star.getStar3());
        addStar(pApplet,120, star.getStar4());
        addStar(pApplet,160, star.getStar5());

    }
    public void addStar(PApplet pApplet,int x, int color) {
        pApplet.pushMatrix();
        pApplet.translate(1120 + x, 30);
        pApplet.rotate((float) (pApplet.frameCount / -100.0));
        showStar(pApplet,0, 0, 10, 20, 5, color);
        pApplet.popMatrix();

    }
    public void showStar(PApplet pApplet,float x, float y, float radius1, float radius2, int npoints, int c) {
        float angle = TWO_PI / npoints;
        float halfAngle = (float) (angle / 2.0);
        pApplet.beginShape();
        int color = pApplet.color(255, 99, 41);
        pApplet.stroke(color);
        pApplet.fill(c);
        for (float a = 0; a < TWO_PI; a += angle) {
            float sx = x + cos(a) * radius2;
            float sy = y + sin(a) * radius2;
            pApplet.vertex(sx, sy);
            sx = x + cos(a + halfAngle) * radius1;
            sy = y + sin(a + halfAngle) * radius1;
            pApplet.vertex(sx, sy);
        }
        pApplet.endShape(CLOSE);
    }
}
