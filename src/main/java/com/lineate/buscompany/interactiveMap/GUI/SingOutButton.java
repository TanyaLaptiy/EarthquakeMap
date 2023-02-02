package com.lineate.buscompany.interactiveMap.GUI;

import com.lineate.buscompany.ResourseService;
import processing.core.PApplet;

public class SingOutButton {
    private int signOutButon_x = 90 - 40 - 40 - 40 - 40 - 40;
    public void show(PApplet pApplet, ResourseService resourseService, boolean overSignOut, boolean offline, int buton_y) {

        pApplet.noStroke();
        if (overSignOut) {
            pApplet.fill(255);
        } else if (!overSignOut && offline) {
            pApplet.fill(215, 215, 215);
        } else if (!overSignOut && !offline) {
            pApplet.fill(138, 179, 246);
        }

        pApplet.rect(105 - signOutButon_x, 60 + buton_y, 35, 35);
        pApplet.image(resourseService.getSignOutLogo(), 106 - signOutButon_x, 61 + buton_y, 35, 30);
    }
}
