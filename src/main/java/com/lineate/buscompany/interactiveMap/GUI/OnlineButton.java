package com.lineate.buscompany.interactiveMap.GUI;

import com.lineate.buscompany.ResourseService;
import processing.core.PApplet;

public class OnlineButton {
    private int onlineButon_x = 90;
    public void show(PApplet pApplet,ResourseService resourseService,boolean overButtonOnline, boolean offline, int buton_y) {
        pApplet.noStroke();
        if (overButtonOnline) {
            pApplet.fill(255);
        } else if (!overButtonOnline && offline) {
            pApplet.fill(215, 215, 215);
        } else if (!overButtonOnline && !offline) {
            pApplet.fill(138, 179, 246);
        }

        pApplet.rect(105 - onlineButon_x, 60 + buton_y, 35, 35);

        if (offline) {
            pApplet.image(resourseService.getOfflineBack(), 106 - onlineButon_x, 61 + buton_y, 35, 30);
        } else {
            pApplet.image(resourseService.getOnlineBack(), 105 - onlineButon_x, 61 + buton_y, 35, 30);
        }

    }
}
