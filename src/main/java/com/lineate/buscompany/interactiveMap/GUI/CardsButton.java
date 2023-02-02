package com.lineate.buscompany.interactiveMap.GUI;

import com.lineate.buscompany.ResourseService;
import processing.core.PApplet;

public class CardsButton {
    private int cardsButon_x = 90 - 40 - 40 - 40 - 40 - 40 - 40;
    public void show(PApplet pApplet, ResourseService resourseService, boolean overCards, boolean offline, int buton_y) {

        pApplet.noStroke();
        if (overCards) {
            pApplet.fill(255);
        } else if (!overCards && offline) {
            pApplet.fill(215, 215, 215);
        } else if (!overCards && !offline) {
            pApplet.fill(138, 179, 246);
        }

        pApplet.rect(105 - cardsButon_x, 60 + buton_y, 35, 35);

        pApplet.image(resourseService.getCardsLogo(), 106 - cardsButon_x, 61 + buton_y, 35, 30);

    }
}
