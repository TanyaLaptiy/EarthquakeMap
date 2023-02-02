package com.lineate.buscompany.interactiveMap.GUI;

import com.lineate.buscompany.ResourseService;
import processing.core.PApplet;

public class RequestButton {
    private int requestButon_x = 90 - 40 - 40 - 40;
    public void show(PApplet pApplet, ResourseService resourseService, boolean overRequest, boolean offline, int buton_y) {

        pApplet.noStroke();
        if (overRequest) {
            pApplet.fill(255);
        } else if (!overRequest && offline) {
            pApplet.fill(215, 215, 215);
        } else if (!overRequest && !offline) {
            pApplet.fill(138, 179, 246);
        }

        pApplet.rect(105 - requestButon_x, 60 + buton_y, 35, 35);
        pApplet.image(resourseService.getNewPointLogo(), 106 - requestButon_x, 61 + buton_y, 35, 30);
    }
}
