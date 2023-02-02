package com.lineate.buscompany.interactiveMap.GUI;

import com.lineate.buscompany.ResourseService;
import processing.core.PApplet;

public class CalendarButton {
    private int calendarButon_x = 90 - 40;
    public void show(PApplet pApplet, ResourseService resourseService, boolean overButtonCalendar, boolean offline, int buton_y) {
        pApplet.noStroke();
        if (overButtonCalendar) {
            pApplet.fill(255);
        } else if (!overButtonCalendar && offline) {
            pApplet.fill(215, 215, 215);
        } else if (!overButtonCalendar && !offline) {
            pApplet.fill(138, 179, 246);
        }

        pApplet.rect(105 - calendarButon_x, 60 + buton_y, 35, 35);

        pApplet.image(resourseService.getCalendarLogo(), 106 - calendarButon_x, 61 + buton_y, 35, 30);

    }
}
