package com.lineate.buscompany.interactiveMap.GUI;

import com.lineate.buscompany.ResourseService;
import processing.core.PApplet;

public class ChatButton {
    private int chatButon_x = 90 - 40 - 40 - 40 - 40;

    public void show(PApplet pApplet, ResourseService resourseService, boolean overChat, boolean offline, int buton_y) {

        pApplet.noStroke();
        if (overChat) {
            pApplet.fill(255);
        } else if (!overChat && offline) {
            pApplet.fill(215, 215, 215);
        } else if (!overChat && !offline) {
            pApplet.fill(138, 179, 246);
        }

        pApplet.rect(105 - chatButon_x, 60 + buton_y, 35, 35);

        pApplet.image(resourseService.getChatLogo(), 106 - chatButon_x, 61 + buton_y, 35, 30);
    }
}
