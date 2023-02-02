package com.lineate.buscompany.interactiveMap;

import com.lineate.buscompany.services.AirportService;
import com.lineate.buscompany.services.CityService;
import com.lineate.buscompany.services.EarthquakeService;
import de.fhpotsdam.unfolding.marker.Marker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Service {

    private static boolean keyIsHidden = false;
    private static boolean infoIsHidden = false;
    private static boolean fieldHidden = false;
    private static boolean secondFieldHidden = false;
    private static boolean quakeFieldHidden = false;
    private static boolean inSystem = false;

    private static TextFieldTest textFieldTest;
    private static TextFieldRoute textFieldRoute;
    private static TextFieldCalendar textFieldCalendar;
    private static TextFieldFacts textFieldFacts;
    private static TextFieldEarthQuake textFieldEarthQuake;
    private static TextFieldRating textFieldRating;
    private static TextFieldRegistration textFieldRegistration;
    private static TextFieldSignIn textFieldSignIn;
    private static TextFieldRegistrationAdmin textFieldRegistrationAdmin;
    private static TextFieldRequest textFieldRequest;
    private static TextFieldAdminCards textFieldAdminCards;
    private static TextFieldChat textFieldChat;
    private static TextFieldChatAdm textFieldChatAdm;
    private static TextFieldProfileOut textFieldProfileOut;
    private static TextFieldProfile profile;
    private static TextFieldProfile sender;
    private static TextFieldRatingAdm textFieldRatingAdm;
    private static String token = null;

    public static void setFields() {
        textFieldCalendar = new TextFieldCalendar();
        textFieldEarthQuake = new TextFieldEarthQuake();
        textFieldFacts = new TextFieldFacts();
        textFieldTest = new TextFieldTest(AirportService.getInstance().getAirportList(), EarthquakeService.getQuakeMarkers(),
                CityService.getInstance().getCityMarkers());
        textFieldRoute = new TextFieldRoute(AirportService.getInstance().getAirportList(), EarthquakeService.getQuakeMarkers(),
                CityService.getInstance().getCityMarkers(), AirportService.getInstance().getRouteList());
        textFieldRequest = new TextFieldRequest(AirportService.getInstance().getAirportList(),
                CityService.getInstance().getCityMarkers());
        textFieldAdminCards = new TextFieldAdminCards();
        textFieldRatingAdm = new TextFieldRatingAdm();

    }

    public static void setProfile(String login, String Name, String Name2, String email, String Country, String Gender,
                                  String DOB, int age, boolean isAdmn, String status) {
        int index = getIndexOfCountry(Country);
        profile = new TextFieldProfile(login, Name, Name2, email, Country, Gender, DOB, age, index, isAdmn, status);
    }

    public static void setProfileSender(String login, String Name, String Name2, String email, String Country, String Gender,
                                        String DOB, int age, boolean isAdmn, String status) {
        int index = getIndexOfCountry(Country);
        sender = new TextFieldProfile(login, Name, Name2, email, Country, Gender, DOB, age, index, isAdmn, status);
    }

    public static TextFieldProfile getProfileSender() {
        return sender;
    }

    public static TextFieldRatingAdm getRatingAdm() {
        return textFieldRatingAdm;
    }

    public static TextFieldProfile getProfile() {
        return profile;
    }

    public static void setOut() {
        textFieldProfileOut = new TextFieldProfileOut();
        Service.setoutSystem();
    }

    public static void seChat() {
        textFieldChat = new TextFieldChat();
    }

    public static void seChatAdm() {
        textFieldChatAdm = new TextFieldChatAdm();
    }

    public static void setRegistrationAdmin() {
        textFieldRegistrationAdmin = new TextFieldRegistrationAdmin(AirportService.getInstance().getAirportList(),
                CityService.getInstance().getCityMarkers());
    }

    public static void setRegistration() {
        textFieldRegistration = new TextFieldRegistration(AirportService.getInstance().getAirportList(),
                CityService.getInstance().getCityMarkers());
    }

    public static void setSignIn() {
        textFieldSignIn = new TextFieldSignIn();
    }

    public static void setRating(Star star) {
        textFieldRating = new TextFieldRating(star);
    }

    public static void setCalendar() {
        textFieldCalendar = new TextFieldCalendar();
    }

    public static void setKeyHidden(boolean hidden) {
        keyIsHidden = hidden;
    }

    public static void setInfoHidden(boolean hidden) {
        infoIsHidden = hidden;
    }

    public static void setFieldHidden(boolean hidden) {
        fieldHidden = hidden;
    }

    public static void setSecondFieldHidden(boolean hidden) {
        secondFieldHidden = hidden;
    }

    public static void setQuakeFieldHidden(boolean hidden) {
        quakeFieldHidden = hidden;
    }

    public static boolean isHiddenKey() {
        return keyIsHidden;
    }

    public static boolean isHiddenInfo() {
        return infoIsHidden;
    }

    public static boolean isHiddenField() {
        return fieldHidden;
    }

    public static boolean isHiddenSecondField() {
        return secondFieldHidden;
    }

    public static boolean isHiddenQuakeField() {
        return quakeFieldHidden;
    }

    public static TextFieldTest getTextField() {
        return textFieldTest;
    }

    public static TextFieldRoute getTextFieldRoute() {
        return textFieldRoute;
    }

    public static TextFieldRating getTextFieldRating() {
        return textFieldRating;
    }

    public static TextFieldCalendar getTextFieldCalendar() {
        return textFieldCalendar;
    }

    public static TextFieldFacts getTextFieldTips() {
        return textFieldFacts;
    }

    public static TextFieldRegistration getTextFieldReg() {
        return textFieldRegistration;
    }

    public static TextFieldRegistrationAdmin getTextFieldRegAdm() {
        return textFieldRegistrationAdmin;
    }

    public static TextFieldRequest getTextFieldRequest() {
        return textFieldRequest;
    }

    public static TextFieldSignIn getTextFieldSignIn() {
        return textFieldSignIn;
    }

    public static TextFieldEarthQuake getEarthQuakes() {
        return textFieldEarthQuake;
    }

    public static TextFieldAdminCards getCards() {
        return textFieldAdminCards;
    }

    public static TextFieldChat getChat() {
        return textFieldChat;
    }

    public static TextFieldChatAdm getChatAdm() {
        return textFieldChatAdm;
    }

    public static TextFieldProfileOut getOut() {
        return textFieldProfileOut;
    }


    public static void showDisclaimer() {
        textFieldCalendar.showDisclaimer();
    }

    public static boolean inSystem() {
        return inSystem;
    }

    public static void setinSystem() {
        inSystem = true;
    }

    public static void setoutSystem() {
        inSystem = false;
        token = null;
    }

    private static int getIndexOfCountry(String country) {
        List<String> list = new ArrayList<>();

        for (Marker r : AirportService.getInstance().getAirportList()) {
            String countryName = r.getProperty("country").toString();
            String airportCountry = countryName.substring(1, countryName.length() - 1);
            list.add(airportCountry);

        }

        for (Marker r : CityService.getInstance().getCityMarkers()) {
            list.add(r.getProperty("country").toString());

        }

        return list.stream().distinct().sorted().collect(Collectors.toList()).indexOf(country);
    }

    public static void setCurrentToken() {
        String currentLine = "";
        String file = "./data/tokenFile.txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            currentLine = reader.readLine();
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("\n\n    Token!!   " + currentLine);
        token = currentLine;

    }

    public static String getToken() {
        return token;
    }
}
