package com.lineate.buscompany;

import com.lineate.buscompany.Handler.*;
import com.lineate.buscompany.dtoE.responseE.AdministratorResponseE;
import com.lineate.buscompany.dtoE.responseE.ClientResponseE;
import com.lineate.buscompany.dtoE.responseE.SessionResponseE;
import com.lineate.buscompany.interactiveMap.*;
import com.lineate.buscompany.interactiveMap.GUI.*;
import com.lineate.buscompany.model.ButtonModel;
import com.lineate.buscompany.model.CityMarker;
import com.lineate.buscompany.model.CommonMarker;
import com.lineate.buscompany.parsing.Parser;
import com.lineate.buscompany.secondHandler.ConcreteHandlerFirst;
import com.lineate.buscompany.secondHandler.ConcreteHandlerSecond;
import com.lineate.buscompany.secondHandler.ConcreteHandlerThird;
import com.lineate.buscompany.secondHandler.SecondHalderContainer;
import com.lineate.buscompany.services.*;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication

public class MainApplication
        extends PApplet {

    private final MBTilesMapProvider mbTilesMapProvider= new MBTilesMapProvider(Parser.getmbTilesString());
    private final GeneralInfo generalInfo=new GeneralInfo();
    private final RatingStar ratingStar=new RatingStar();
    private final ClockInFrame clockInFrame=new ClockInFrame();
    private final OnlineButton onlineButton=new OnlineButton();
    private final CalendarButton calendarButton=new CalendarButton();
    private final RatingButton ratingButton=new RatingButton();
    private final TipsButton tipsButton=new TipsButton();
    private final SingUpButton singUpButton=new SingUpButton();
    private final SingInButton singInButton=new SingInButton();
    private final SingOutButton singOutButton=new SingOutButton();
    private final RequestButton requestButton=new RequestButton();
    private final ChatButton chatButton=new ChatButton();
    private final CardsButton cardsButton=new CardsButton();
    private final ProfileButton profileButton=new ProfileButton();
    private final KeyFrame keyFrame=new KeyFrame();
    private final SideBlock sideBlock=new SideBlock();

    private final ResourseService resourseService=new ResourseService();
    private final Google.GoogleMapProvider mapProvider=new Google.GoogleMapProvider();
    private final Location consoleLocation=new Location(83.44f, 23f + 10.5f);

    private static SqlSessionFactory sqlSessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);
    private static final long serialVersionUID = 1L;
    private static boolean offline = true;

    private final HalderContainer halderNoLastClicks = new HalderContainer();
    private final SecondHalderContainer halderWithLastClicks = new SecondHalderContainer();
    private final ConcreteHandlerAirports concreteHandlerAirports= new ConcreteHandlerAirports();
    private final ConcreteHandlerCities concreteHandlerCities=new ConcreteHandlerCities();
    private final ConcreteHandlerEarthquakes concreteHandlerEarthquakes=new ConcreteHandlerEarthquakes();
    private final ConcreteHandlerMenu concreteHandlerMenu=new ConcreteHandlerMenu();
    private final ConcreteHandlerFirst concreteHandlerFirst=new ConcreteHandlerFirst();
    private final ConcreteHandlerSecond concreteHandlerSecond=new ConcreteHandlerSecond();
    private final ConcreteHandlerThird concreteHandlerThird=new ConcreteHandlerThird();

    private boolean overButtonLinkFlight = false;
    private boolean overButtonOnline = false;
    private boolean overButtonCalendar = false;
    private boolean overButtonTips = false;
    private boolean overRatingStars = false;
    private boolean overSignUp = false;
    private boolean overSignIn = false;
    private boolean overSignOut = false;
    private boolean overRequest = false;
    private boolean overChat = false;
    private boolean overCards = false;
    private boolean overMyProfile = false;
    private boolean overButtonWeb = false;
    private boolean overButtonGit = false;
    private boolean overButtonGitMobile = false;
    private boolean overButtonGitWeb = false;

    private final Star star = new Star();
    private PGraphics buffer;

    private int button_y;

    private List<Marker> markersCity;
    private static List<Marker> markersQuake;
    private List<Marker> markersAirport;
    private List<Marker> markersMenu;

    private int width;
    private int height;
    private static final RestTemplate template = new RestTemplate();


    public static boolean initSqlSessionFactory() {
        try (Reader reader = Resources.getResourceAsReader("application.properties")) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

            return true;
        } catch (Exception e) {
            LOGGER.error("Error loading mybatis-config.xml", e);
            return false;
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public static void main(final String[] args) {
        PApplet.main("com.lineate.buscompany.MainApplication");
        splash();
    }

    public void setup() {
        size(1893, 939, P2D);
        System.out.print("\n\n\n start!! \n");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("./data/mouse7.png");
        Cursor c = toolkit.createCustomCursor(image, new Point(getX(), getY()), "img");
        setCursor(c);

        SpringApplication.run(MainApplication.class);
        System.out.println("Start application");
        startApp();
    }


    public void startApp() {
        setAllMapSettings();

        Service.setCurrentToken();
        if (Service.getToken() != null ) {
            if (!Service.getToken().isBlank()){
            MenuService.getInstance().setProfileLogo();
            Service.setinSystem();
            SessionResponseE sessionResponseE2 = template.getForObject(
                    "http://localhost:8080/api/sessionsE/token/{token}",
                    SessionResponseE.class,
                    Service.getToken());
            System.out.println("\n\n\n  id!!   " + sessionResponseE2.getUserId());

            ClientResponseE userById = template.getForObject(
                    "http://localhost:8080/api/clientsE/{id}",
                    ClientResponseE.class,
                    sessionResponseE2.getUserId());

            int age = LocalDate.now().getYear() - userById.getBirthday().getYear();
            if (userById.getMail() != null) {
                Service.setProfile(userById.getLogin(), userById.getFirstName(),
                        userById.getLastName() + " " + userById.getPatronymic(), userById.getMail(),
                        userById.getCountry(), userById.getSex(), String.valueOf(userById.getBirthday()), age, false, "");
            } else {
                AdministratorResponseE adminById = template.getForObject(
                        "http://localhost:8080/api/adminsE/{id}",
                        AdministratorResponseE.class,
                        sessionResponseE2.getUserId());

                Service.setProfile(adminById.getLogin(), adminById.getFirstName(),
                        adminById.getLastName() + " " + adminById.getPatronymic(), "earthquakes@list.ru",
                        adminById.getCountry(), userById.getSex(), String.valueOf(userById.getBirthday()), age, true, adminById.getPosition());
            }
        }
        }else{
            Service.setoutSystem();
        }
//        try {
//            EarthquakeService.parseToBD();
//        } catch (Exception ignored) {
//        }
    }

    private void setAllMapSettings() {
        if (offline) {
            MarkerService.getInstance()
                    .setMap(new UnfoldingMap(this, 0, 0, 1893, 939, mbTilesMapProvider));
            MarkerService.getInstance().getMap().setZoomRange(1.7f, 3);
        } else {

            System.setProperty("http.agent", "Chrome");
            MarkerService.getInstance().setMap(new UnfoldingMap(this, 0, 0, 1893, 939, mapProvider));
            MarkerService.getInstance().getMap().setZoomRange(1.7f, 6);

        }
        MarkerService.getInstance().getMap().setTweening(true);
        MarkerService.getInstance().setSecMap(
                new UnfoldingMap(this, 360, 20, 495 + 115, 69, mbTilesMapProvider));

        MarkerService.getInstance().getConsole().zoomAndPanTo(3,consoleLocation);

        MarkerService.getInstance().getConsole().setTweening(false);
        MarkerService.getInstance().getConsole().setPanningRestriction(consoleLocation, 0);

        MarkerService.getInstance().getConsole().setZoomRange(3, 3);
        MarkerService.getInstance().getMap().zoom(1.7f);

        MapUtils.createDefaultEventDispatcher(this, MarkerService.getInstance().getMap());
        MapUtils.createDefaultEventDispatcher(this, MarkerService.getInstance().getConsole());

        AirportService.getInstance().parse();
        CityService.getInstance().parse();
        MenuService.getInstance().parse();
        EarthquakeService.parse(offline, false);

        Service.setFields();
        Service.setRating(star);
        Service.setSignIn();
        Service.setRegistrationAdmin();
        Service.setCalendar();

        MarkerService.getInstance().addMarkersToMap();
        MarkerService.getInstance().addMenuMarkersToMap();

        buffer = createGraphics(1893, 939);

        halderNoLastClicks.addHandler(concreteHandlerAirports);
        halderNoLastClicks.addHandler(concreteHandlerCities);
        halderNoLastClicks.addHandler(concreteHandlerEarthquakes);
        halderNoLastClicks.addHandler(concreteHandlerMenu);
        halderWithLastClicks.addHandler(concreteHandlerFirst);
        halderWithLastClicks.addHandler(concreteHandlerSecond);
        halderWithLastClicks.addHandler(concreteHandlerThird);

        markersCity = CityService.getInstance().getCityMarkers();
        markersQuake = EarthquakeService.getQuakeMarkers();
        markersAirport = AirportService.getInstance().getAirportList();
        markersMenu = MenuService.getInstance().getMenuMarkers();

        button_y = getSize().height - 110;
        width = getSize().width;
        height = getSize().height;
        printQuakes();
        clockInFrame.addClock();

    }

    private static void splash() {
        Thread splashThread = new Thread() {
            public void run()
            {
                new SplashScreenMap();
            }
        };
        splashThread.start();
    }

    public void draw() {

        if (offline) {
            background(215, 215, 215);
        } else {
            background(138, 179, 246);
        }
        buffer.beginDraw();
        MarkerService.getInstance().getMap().draw();
        buffer.endDraw();
        image(buffer, 0, 0);

        image(resourseService.getMenuFrame(), 352, 3, resourseService.getMenuFrame().width - 15, resourseService.getMenuFrame().height - 15);

        if (!Service.isHiddenKey()) {
            sideBlock.show(this,resourseService);
            keyFrame.show(this,resourseService);
            clockInFrame.drawClock(this);
        }

        if (!MenuService.getInstance().isClickenSixMarket()) {
            generalInfo.show(this);
            CityService.getInstance().hideCityInfo();
        }

        MarkerService.getInstance().getConsole().draw();

        onlineButton.show(this,resourseService,overButtonOnline,offline, button_y);
        calendarButton.show(this,resourseService,overButtonCalendar,offline, button_y);
        tipsButton.show(this,resourseService,overButtonTips,offline, button_y);
        ratingButton.show(this);
        requestButton.show(this,resourseService,overRequest,offline, button_y);
        chatButton.show(this,resourseService,overChat,offline, button_y);
        if (TextFieldProfile.isAdmn()) {
            cardsButton.show(this,resourseService,overCards,offline, button_y);
        }

        if (Service.inSystem()) {
            profileButton.show(this,overMyProfile,offline, button_y);
            singOutButton.show(this,resourseService,overSignOut,offline, button_y);
        } else {
            singUpButton.show(this,resourseService,overSignUp,offline, button_y);
            singInButton.show(this,resourseService,overSignIn,offline, button_y);
        }

        ratingStar.show(this);
        buffer.clear();

        if (MarkerService.getInstance().getLastSelected() != null) {
            MarkerService.getInstance().drawTitleOnTop(buffer, mouseX, mouseY);
        }
    }

    @Override
    public void mouseMoved() {

        if (MarkerService.getInstance().getLastSelected() != null) {
            MarkerService.getInstance().unselected();
        }

        selectMarkerIfHover(markersCity);
        selectMarkerIfHover(markersQuake);
        selectMarkerIfHover(markersAirport);
        selectMarkerIfHover(markersMenu);
        checkButtons();

    }

    private void selectMarkerIfHover(List<Marker> markers) {

        if (MarkerService.getInstance().getLastSelected() != null) {
            return;
        }

        for (Marker m : markers) {
            CommonMarker marker = (CommonMarker) m;
            if (marker.isInside(MarkerService.getInstance().getConsole(), mouseX, mouseY)) {
                MarkerService.getInstance().selectMarkerIfHover(marker);
                return;
            }
            if (marker.isInside(MarkerService.getInstance().getMap(), mouseX, mouseY)) {
                MarkerService.getInstance().selectMarkerIfHover(marker);
                return;
            }
        }

    }

    @Override
    public void mouseClicked() {
        if (MarkerService.getInstance().getLastClicked() != null) {
            AirportService.getInstance().hideRouteByAir();

            if (MarkerService.getInstance().getLastClicked().isInside(MarkerService.getInstance().getConsole(), mouseX,
                    mouseY)) {
                halderWithLastClicks.markerSetClick();
            }
            MarkerService.getInstance().setLastClickedNull();
        } else if (MarkerService.getInstance().getLastClicked() == null) {
            checkMarkerForClick();
        }
    }

    private void checkMarkerForClick() {
        if (MarkerService.getInstance().getLastClicked() != null)
            return;
        halderNoLastClicks.markerCheckClick(mouseX, mouseY);
    }

    public void selectByCountry(String country) {
        MarkerService.selectByCountry(country);
    }

    private void printQuakes() {
        EarthquakeService.printQuakes();
    }

    public void mousePressed() {

        if (overButtonLinkFlight && !offline) {
            link(ButtonModel.getUrl());
        } else if (overButtonLinkFlight && offline) {
            JOptionPane.showMessageDialog(MainApplication.this, "You are offline. Please switch the mode", "Warning",
                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./data/warning-PhotoRoom.png"));
        } else if (overButtonCalendar && !offline) {
            Service.getTextFieldCalendar().setVisible(true);
            Service.showDisclaimer();
        } else if (overButtonCalendar && offline) {
            JOptionPane.showMessageDialog(MainApplication.this, "You are offline. Please switch the mode", "Warning",
                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./data/warning-PhotoRoom.png"));
        } else if (overButtonTips) {
            Service.getTextFieldTips().setVisible(true);
        } else if (overSignUp) {
            Service.setRegistration();
            Service.getTextFieldReg().setVisible(true);
        } else if (overSignIn) {
            Service.getTextFieldSignIn().setVisible(true);

        } else if (overSignOut) {

            Service.setOut();
            Service.setoutSystem();
            TextFieldProfile.notAdmn();
            Service.getOut().setVisible(true);
            File oldfile = new File("./data/combined.png");
            oldfile.delete();

        } else if (overRequest) {
            if (!Service.inSystem()) {
                JOptionPane.showMessageDialog(MainApplication.this, "You are not logged in, please login or register to rate the project", "Warning",
                        JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./data/warning-profile-PhotoRoom.png"));
            } else {
                Service.getTextFieldRequest().setVisible(true);
            }
        } else if (overRatingStars) {
            if (!Service.inSystem()) {
                JOptionPane.showMessageDialog(MainApplication.this, "You are not logged in, please login or register to rate the project", "Warning",
                        JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./data/warning-profile-PhotoRoom.png"));
            } else {
                if (TextFieldProfile.isAdmn() || TextFieldProfile.isModer()) {
                    Service.getRatingAdm().setVisible(true);
                } else {
                    Service.getTextFieldRating().setVisible(true);
                }

            }
        } else if (overButtonOnline && offline) {
            offline = false;
            splash();
            startApp();
        } else if (overButtonOnline && !offline) {
            offline = true;
            splash();
            startApp();

        } else if (overChat) {
            if (!Service.inSystem()) {
                JOptionPane.showMessageDialog(MainApplication.this, "You are not logged in, please login or register to rate the project", "Warning",
                        JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./data/warning-profile-PhotoRoom.png"));
            } else {
                if (TextFieldProfile.isAdmn() || TextFieldProfile.isModer()) {
                    Service.seChatAdm();
                    Service.getChatAdm().setVisible(true);
                } else {
                    Service.seChat();
                    Service.getChat().setVisible(true);
                }
            }

        } else if (overCards) {
            if (TextFieldAdminCards.getAllCards() > 0) {
                Service.getCards().setVisible(true);
            }
        } else if (overMyProfile) {
            Service.getProfile().setVisible(true);
        }else if(overButtonWeb&&!Service.isHiddenKey()){
            link("https://www.skyscanner.com.sa/sa/en-gb/sar/hotels/united-states/adamstown-hotels/ci-46877540?previousCultureSource=URL&redirectedFrom=www.skyscanner.net");
        }
        else if(overButtonGit&&!Service.isHiddenKey()){
            link("https://github.com/AnnaLaptiy/EarthquakeMap");

        }
        else if(overButtonGitMobile&&!Service.isHiddenKey()){
            link("https://github.com/AnnaLaptiy/Earthquake-Android-Application");

        }
        else if(overButtonGitWeb&&!Service.isHiddenKey()){
            link("https://github.com/AnnaLaptiy/Earthquake-Application-Website");

        }

    }

    public void mouseDragged() {
        checkButtons();
    }

    void checkButtons() {
        overButtonLinkFlight = false;
        overButtonOnline = false;
        overButtonCalendar = false;
        overButtonTips = false;
        overRatingStars = false;
        overSignUp = false;
        overSignIn = false;
        overSignOut = false;
        overRequest = false;
        overChat = false;
        overCards = false;
        overMyProfile = false;
        overButtonWeb=false;
        overButtonGit = false;
        overButtonGitMobile = false;
        overButtonGitWeb = false;
        CityMarker.clickForLink(false);

        if (mouseX > width - 100 && mouseX < width - 70 && mouseY > 182 && mouseY < 212) {
            overButtonLinkFlight = true;
            CityMarker.clickForLink(true);
        } else if (mouseX > 15 && mouseX < 50 && mouseY > 60 + height - 110 && mouseY < 60 + height - 75) {
            overButtonOnline = true;
        } else if (mouseX > 55 && mouseX < 90 && mouseY > height - 50 && mouseY < height - 15) {
            overButtonCalendar = true;
        } else if (mouseX > 95 && mouseX < 130 && mouseY > height - 50 && mouseY < height - 15) {
            overButtonTips = true;
        } else if (mouseX > 150 && mouseX < 210 && mouseY > 675 && mouseY < 735) {
            overButtonWeb = true;
        }  else if (mouseX > 180 && mouseX < 205 && mouseY > 750 && mouseY < 775) {
            overButtonGit = true;
        } else if (mouseX > 150 && mouseX < 210 && mouseY > 775 && mouseY < 800) {
            overButtonGitMobile = true;
        } else if (mouseX > 150 && mouseX < 210 && mouseY > 800 && mouseY < 825) {
            overButtonGitWeb = true;
        }else if (mouseX > 135 && mouseX < 170 && mouseY > height - 50 && mouseY < height - 15) {
            overRequest = true;
        } else if (mouseX > 175 && mouseX < 210 && mouseY > height - 50 && mouseY < height - 15) {
            overChat = true;
        } else if (mouseX > 215 && mouseX < 250 && mouseY > height - 50 && mouseY < height - 15) {
            if (Service.inSystem()) {
                overSignOut = true;
            } else {
                overSignIn = true;
            }
        } else if (mouseX > 255 && mouseX < 290 && mouseY > height - 50 && mouseY < height - 15) {
            if (!Service.inSystem()) {
                overSignUp = true;
            } else {
                overCards = true;
            }
        } else if (mouseX > 1565 + 270 && mouseX < 1565 + 270 + 40 && mouseY > height - 55 && mouseY < height - 15) {
            overMyProfile = true;
        } else if (mouseX > 1100 && mouseX < 1300 && mouseY > 10 && mouseY < 50) {
            overRatingStars = true;
        }
    }

    public static void refreshMarkers() {
        markersQuake = EarthquakeService.getQuakeMarkers();
    }

    private String getCurrentToken() {
        String currentLine = "";
        String file = "./data/tokenfile.txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            currentLine = reader.readLine();
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("\n\n    Token!!   " + currentLine);
        return currentLine;
    }

}
