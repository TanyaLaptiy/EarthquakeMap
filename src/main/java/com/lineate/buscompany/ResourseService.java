package com.lineate.buscompany;

import processing.core.PApplet;
import processing.core.PImage;

public class ResourseService extends PApplet {
    private final PImage offlineBack = loadImage("./data/offnline-PhotoRoom.png");
    private final PImage onlineBack = loadImage("./data/online-PhotoRoom.png");
    private final PImage calendarLogo = loadImage("./data/calen-PhotoRoom.png");
    private final PImage factsLogo = loadImage("./data/FactsLogo-PhotoRoom.png");
    private final PImage signUpLogo = loadImage("./data/signUp-PhotoRoom.png");
    private final PImage signInLogo = loadImage("./data/signinBut-PhotoRoom.png");
    private final PImage signOutLogo = loadImage("./data/signoutBut-PhotoRoom.png");
    private final PImage newPointLogo = loadImage("./data/newPoint-PhotoRoom.png");
    private final PImage airLogo = loadImage("./data/airportmarker-PhotoRoom.png");
    private final PImage gitLogo = loadImage("./data/gitHubLogo.png");
    private final PImage webLogo = loadImage("./data/webLogo.png");
    private final PImage countryLogo = loadImage("./data/countrymarker-PhotoRoom.png");
    private final PImage clockLogo = loadImage("./data/clock-benzin-preview.png");
    private final PImage chatLogo = loadImage("./data/chat-PhotoRoom.png");
    private final PImage cardsLogo = loadImage("./data/postbox.png");
    private final PImage photo = loadImage("./data/unnamed.png");
    private final PImage menuFrame = loadImage("./data/menuFrame.png");



    public PImage getOfflineBack() {
        return offlineBack;
    }

    public PImage getOnlineBack() {
        return onlineBack;
    }

    public PImage getCalendarLogo() {
        return calendarLogo;
    }

    public PImage getFactsLogo() {
        return factsLogo;
    }

    public PImage getSignUpLogo() {
        return signUpLogo;
    }

    public PImage getSignInLogo() {
        return signInLogo;
    }

    public PImage getSignOutLogo() {
        return signOutLogo;
    }

    public PImage getNewPointLogo() {
        return newPointLogo;
    }

    public PImage getAirLogo() {
        return airLogo;
    }

    public PImage getCountryLogo() {
        return countryLogo;
    }

    public PImage getClockLogo() {
        return clockLogo;
    }

    public PImage getChatLogo() {
        return chatLogo;
    }

    public PImage getCardsLogo() {
        return cardsLogo;
    }

    public PImage getPhoto() {
        return photo;
    }

    public PImage getMenuFrame() {
        return menuFrame;
    }

    public PImage getGitLogo() {
        return gitLogo;
    }

    public PImage getWebLogo() {
        return webLogo;
    }
}
