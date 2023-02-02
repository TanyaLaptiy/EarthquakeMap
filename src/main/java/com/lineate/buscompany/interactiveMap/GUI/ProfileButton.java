package com.lineate.buscompany.interactiveMap.GUI;

import com.lineate.buscompany.services.MenuService;
import processing.core.PApplet;

public class ProfileButton {
    private int profileButon_x = 90 - 40 - 40 - 40 - 40 - 40 - 40 - 40 - 40 - 40;
    public void show(PApplet pApplet,  boolean overMyProfile, boolean offline, int buton_y) {

        pApplet.noStroke();
        if (overMyProfile) {
            pApplet.fill(255);
        } else if (!overMyProfile && offline) {
            pApplet.fill(215, 215, 215);
        } else if (!overMyProfile && !offline) {
            pApplet.fill(138, 179, 246);
        }

        pApplet.rect(1565 - profileButon_x, 60 + buton_y - 10, 50, 55);
        pApplet.image(MenuService.getInstance().getProfileLogo(), 1565 - profileButon_x, 61 + buton_y - 10, 50, 55);

    }
}
