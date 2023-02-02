package com.lineate.buscompany.interactiveMap.GUI;

import com.lineate.buscompany.ResourseService;
import processing.core.PApplet;

public class SingUpButton {
    private int signUpButon_x = 90 - 40 - 40 - 40 - 40 - 40 - 40;

    public void show(PApplet pApplet, ResourseService resourseService, boolean overSignUp, boolean offline, int buton_y) {

        pApplet.noStroke();
        if (overSignUp) {
            pApplet.fill(255);
        } else if (!overSignUp && offline) {
            pApplet.fill(215, 215, 215);
        } else if (!overSignUp && !offline) {
            pApplet.fill(138, 179, 246);
        }

        pApplet.rect(105 - signUpButon_x, 60 + buton_y, 35, 35);
        pApplet.image(resourseService.getSignUpLogo(), 106 - signUpButon_x, 61 + buton_y, 35, 30);
    }
}
