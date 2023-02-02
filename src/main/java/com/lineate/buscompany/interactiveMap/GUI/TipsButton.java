package com.lineate.buscompany.interactiveMap.GUI;

import com.lineate.buscompany.ResourseService;
import processing.core.PApplet;

public class TipsButton {
    private int tipsButon_x = 90 - 40 - 40;

    public void show(PApplet pApplet, ResourseService resourseService, boolean overButtonTips, boolean offline, int buton_y) {

        pApplet.noStroke();
        if (overButtonTips) {
            pApplet.fill(255);
        } else if (!overButtonTips && offline) {
            pApplet.fill(215, 215, 215);
        } else if (!overButtonTips && !offline) {
            pApplet.fill(138, 179, 246);
        }

        pApplet.rect(105 - tipsButon_x, 60 + buton_y, 35, 35);
        pApplet.image(resourseService.getFactsLogo(), 106 - tipsButon_x, 61 + buton_y, 35, 35);
    }
}
