package com.lineate.buscompany.interactiveMap.GUI;

import com.lineate.buscompany.ResourseService;
import processing.core.PApplet;

public class SingInButton {
    private int signInButon_x = 90 - 40 - 40 - 40 - 40 - 40;
    public void show(PApplet pApplet, ResourseService resourseService, boolean overSignIn, boolean offline, int buton_y) {

        pApplet.noStroke();
        if (overSignIn) {
            pApplet.fill(255);
        } else if (!overSignIn && offline) {
            pApplet.fill(215, 215, 215);
        } else if (!overSignIn && !offline) {
            pApplet.fill(138, 179, 246);
        }

        pApplet.rect(105 - signInButon_x, 60 + buton_y, 35, 35);
        pApplet.image(resourseService.getSignInLogo(), 106 - signInButon_x, 61 + buton_y, 35, 30);
    }
}
