package com.lineate.buscompany.interactiveMap;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.crypto.Data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.lineate.buscompany.dtoE.responseE.*;
import com.lineate.buscompany.services.AirportService;
import de.fhpotsdam.unfolding.geo.Location;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import de.fhpotsdam.unfolding.marker.Marker;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;


public class TextFieldAdminCards extends JFrame implements ActionListener {

    JPanel rightPanel = new JPanel();
    EarthMap chinaMap = new EarthMap();
    private Container c;
    private JLabel title, title2;
    private JLabel latitude;
    private static JTextField tlat;
    private JLabel longiude;
    private static JTextField tlon;
    private JLabel depth;
    private JLabel country;
    private JTextField tdepth;
    private JLabel magnitude;
    private JTextField tmag;
    private JLabel radius;
    private JTextField trad;
    private static JTextField loc = new JTextField();
    ButtonGroup gengp = new ButtonGroup();

    private static JComboBox tcountry2;
    private static JComboBox points;
    private JButton reject;
    private JButton next;
    private JButton previous;
    private JButton approve;
    private JButton from;
    private JLabel tout;
    private JTextField dat = new JTextField();
    private JLabel today = new JLabel();
    private JLabel location2 = new JLabel();
    private JLabel location3 = new JLabel();
    private static RestTemplate template = new RestTemplate();
    private static List<RequestResponseStr> responseList = null;
    private int index = 0;

    private String locCity = " ";

    public TextFieldAdminCards() {

        setTitle("Requests");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("./data/mouse7.png");
        Cursor cur = toolkit.createCustomCursor(image, new Point(getX(), getY()), "img");
        setCursor(cur);

        setBounds(300, 90, 760, 680);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(255, 250, 240));

        getAllCards();

        latitude = new JLabel("Latitude");
        latitude.setFont(new Font("Arial", Font.PLAIN, 15));
        latitude.setSize(100, 20);
        latitude.setLocation(100 + 40, 160 + 40 + 20 - 5);
        c.add(latitude);

        tlat = new JTextField();
        tlat.setFont(new Font("Arial", Font.PLAIN, 13));
        tlat.setSize(50, 20);
        tlat.setBackground(new Color(255, 250, 240));
        tlat.setEditable(false);
        tlat.setText(String.valueOf(responseList.get(index).getLatitude()).substring(0, 6));
        tlat.setLocation(220 - 5, 160 + 40 + 20 - 5);
        c.add(tlat);

        longiude = new JLabel("Longiude");
        longiude.setFont(new Font("Arial", Font.PLAIN, 15));
        longiude.setSize(100, 20);

        longiude.setLocation(100 + 40, 190 + 30 + 5 + 20);
        c.add(longiude);

        tlon = new JTextField();
        tlon.setFont(new Font("Arial", Font.PLAIN, 13));
        tlon.setSize(50, 20);
        tlon.setBackground(new Color(255, 250, 240));
        tlon.setEditable(false);
        tlon.setText(String.valueOf(responseList.get(index).getLongitude()).substring(0, 6));
        tlon.setLocation(220, 190 + 30 + 5 + 20);
        c.add(tlon);

        depth = new JLabel("Depth");
        depth.setFont(new Font("Arial", Font.PLAIN, 15));
        depth.setSize(100, 20);
        depth.setLocation(100 + 20 + 20, 240 + 40);
        c.add(depth);

        tdepth = new JTextField();
        tdepth.setFont(new Font("Arial", Font.PLAIN, 13));
        tdepth.setSize(40, 20);
        tdepth.setBackground(new Color(255, 250, 240));
        tdepth.setEditable(false);
        tdepth.setText(String.valueOf(new DecimalFormat("#0.00").format(responseList.get(index).getDepth())));
        tdepth.setLocation(150 + 20 + 20, 240 + 40);
        c.add(tdepth);

        magnitude = new JLabel("Magnitude");
        magnitude.setFont(new Font("Arial", Font.PLAIN, 15));
        magnitude.setSize(100, 20);
        magnitude.setLocation(240, 240 + 40);
        c.add(magnitude);

        tmag = new JTextField();
        tmag.setFont(new Font("Arial", Font.PLAIN, 13));
        tmag.setSize(40, 20);
        tmag.setBackground(new Color(255, 250, 240));
        tmag.setEditable(false);
        tmag.setText(String.valueOf(new DecimalFormat("#0.00").format(responseList.get(index).getMagnitude())));
        tmag.setLocation(320 - 5, 240 + 40);
        c.add(tmag);

        dat.setFont(new Font("Arial", Font.PLAIN, 13));
        dat.setSize(80, 20);
        dat.setEditable(false);
        dat.setBackground(new Color(255, 250, 240));
        dat.setText(String.valueOf(responseList.get(index).getData()));
        dat.setLocation(140 + 20 + 40, 310 + 30);
        c.add(dat);

        JLabel data = new JLabel("Data: ");
        data.setFont(new Font("Arial", Font.PLAIN, 15));
        data.setSize(100, 20);
        data.setLocation(100 + 20 + 40, 310 + 30);
        c.add(data);

        today.setFont(new Font("Arial", Font.PLAIN, 15));
        today.setSize(100, 20);
        today.setText(String.valueOf(responseList.get(index).getAge()));
        today.setLocation(140 + 40 + 20, 310 + 50);
        c.add(today);

        from = new JButton("From");
        from.setFont(new Font("Arial", Font.PLAIN, 15));
        from.setSize(70, 20);
        from.setBackground(new Color(255, 250, 240));
        from.setLocation(590, 350 - 60 - 40);
        from.addActionListener(this);
        c.add(from);

        country = new JLabel("Earthquake type: ");
        country.setFont(new Font("Arial", Font.PLAIN, 15));
        country.setSize(200, 20);
        country.setLocation(440 + 20, 350 - 60);
        c.add(country);

        country = new JLabel();
        country.setFont(new Font("Arial", Font.PLAIN, 15));
        country.setSize(180, 20);
        country.setText(String.valueOf(responseList.get(index).getOnland()));
        country.setLocation(440 + 40, 370 - 60);
        c.add(country);

        JLabel location = new JLabel("Location: ");
        location.setFont(new Font("Arial", Font.PLAIN, 15));
        location.setSize(100, 20);
        location.setLocation(390 + 40 + 20, 400 - 45);
        c.add(location);

        location2.setFont(new Font("Arial", Font.PLAIN, 15));
        location2.setText(getTitleFirst(responseList.get(index).getLatitude(), responseList.get(index).getLongitude()));
        location2.setSize(200, 20);
        location2.setLocation(390 + 50 + 10, 430 - 40);
        c.add(location2);

        location3.setFont(new Font("Arial", Font.PLAIN, 15));
        location3.setText(locCity);
        location3.setSize(300, 20);
        location3.setLocation(390 + 50 + 10, 460 - 40);
        c.add(location3);

        approve = new JButton("Approve");
        approve.setFont(new Font("Arial", Font.PLAIN, 15));
        approve.setSize(100, 20);
        approve.setBackground(new Color(253, 224, 195));
        approve.setLocation(500 - 40, 480);
        approve.addActionListener(this);
        c.add(approve);

        reject = new JButton("Reject");
        reject.setFont(new Font("Arial", Font.PLAIN, 15));
        reject.setSize(100, 20);
        reject.setBackground(new Color(253, 224, 195));
        reject.setLocation(580 - 10, 480);
        reject.addActionListener(this);
        c.add(reject);

        next = new JButton("Next");
        next.setFont(new Font("Arial", Font.PLAIN, 15));
        next.setSize(100, 20);
        next.setBackground(new Color(253, 224, 195));
        next.setLocation(450 - 20, 570 - 20);
        next.addActionListener(this);
        c.add(next);

        previous = new JButton("Previous");
        previous.setFont(new Font("Arial", Font.PLAIN, 15));
        previous.setSize(100, 20);
        previous.setBackground(new Color(253, 224, 195));
        previous.setLocation(300 - 20, 570 - 20);
        previous.addActionListener(this);
        c.add(previous);

        ImageIcon ii = new ImageIcon("./data/AdminCard.png");
        tout = new JLabel(ii);
        tout.setSize(750, 600);
        tout.setLocation(0, 0);
        c.add(tout);

        setAlwaysOnTop(true);
        setVisible(false);
    }

    private String getTitleFirst(double latitude, double longitude) {
        String title;
        String loc = " ";
        Location location = new Location(latitude, longitude);

        Location second = null;

        double dist = AirportService.getInstance().getAirportList().get(0).getDistanceTo(location);
        for (Marker r : AirportService.getInstance().getAirportList()) {

            if (dist > location.getDistance(r.getLocation())) {
                second = r.getLocation();
                dist = location.getDistance(r.getLocation());

                loc = r.getStringProperty("city").toString() + "; " + r.getStringProperty("country").toString();
            }

        }
        title = String.valueOf(dist).substring(0, 7) + " km";
        double PI = Math.PI;
        double dTeta = Math
                .log(Math.tan((second.getLat() / 2) + (PI / 4)) / Math.tan((location.getLat() / 2) + (PI / 4)));
        double dLon = Math.abs(location.getLon() - second.getLon());
        double teta = Math.atan2(dLon, dTeta);
        double direction = Math.round(Math.toDegrees(teta));

        String res = null;
        if (direction >= 350 || direction >= 0 && direction < 10) {
            res = "N";
        } else if (direction >= 10 && direction < 35) {
            res = "NNE";
        } else if (direction >= 35 && direction < 60) {
            res = "NE";
        } else if (direction >= 60 && direction < 80) {
            res = "ENE";
        } else if (direction >= 80 && direction < 100) {
            res = "E";
        } else if (direction >= 100 && direction < 125) {
            res = "ESE";
        } else if (direction >= 125 && direction < 150) {
            res = "SE";
        } else if (direction >= 150 && direction < 170) {
            res = "SSE";
        } else if (direction >= 170 && direction < 190) {
            res = "S";
        } else if (direction >= 190 && direction < 215) {
            res = "SSW";
        } else if (direction >= 215 && direction < 235) {
            res = "SW";
        } else if (direction >= 235 && direction < 260) {
            res = "WSW";
        } else if (direction >= 260 && direction < 280) {
            res = "W";
        } else if (direction >= 280 && direction < 305) {
            res = "WNW";
        } else if (direction >= 305 && direction < 325) {
            res = "NW";
        } else if (direction >= 325 && direction < 350) {
            res = "NW";
        }
        title += " " + res + " of ";
        this.locCity = loc;
        return title;
    }


    private void setSender() {

        ClientResponseE userById = template.getForObject(
                "http://localhost:8080/api/clientsE/{id}",
                ClientResponseE.class,
                responseList.get(index).getClientId());


        System.out.println("\n\n Sender!!!   " + responseList.get(index).getClientId());
        int age = LocalDate.now().getYear() - userById.getBirthday().getYear();

        if (userById.getMail() != null) {
            Service.setProfileSender(userById.getLogin(), userById.getFirstName(),
                    userById.getLastName() + " " + userById.getPatronymic(), userById.getMail(),
                    userById.getCountry(), userById.getSex(), String.valueOf(userById.getBirthday()), age, false, "sender");
        } else {
            AdministratorResponseE adminById = template.getForObject(
                    "http://localhost:8080/api/adminsE/{id}",
                    AdministratorResponseE.class,
                    responseList.get(index).getClientId());

            Service.setProfileSender(adminById.getLogin(), adminById.getFirstName(),
                    adminById.getLastName() + " " + adminById.getPatronymic(), "",
                    adminById.getCountry(), userById.getSex(), String.valueOf(userById.getBirthday()), age, true, adminById.getPosition() + "+sender");
        }
    }

    public static int getAllCards() {
        int res = 0;
        HttpHeaders headerslogin = new HttpHeaders();
        headerslogin.setContentType(MediaType.APPLICATION_JSON);

        String administratorDtos3 = template.getForObject("http://localhost:8080/api/requestsE/status/{status}",
                String.class,
                "false");
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType =
                objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, RequestResponseStr.class);

        try {
            responseList = objectMapper.readValue(administratorDtos3, listType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (responseList != null || responseList.size() > 0) {
            res = responseList.size();
        }
        return res;
    }

    // method actionPerformed()
// to get the action performed
// by the user and act accordingly
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == reject) {
            HttpHeaders headersapprove = new HttpHeaders();
            headersapprove.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestapprove = new HttpEntity<>(responseList.get(index), headersapprove);
            template.put("http://localhost:8080/api/requestsE/reject", requestapprove, HttpStatus.class);
            JOptionPane.showMessageDialog(TextFieldAdminCards.this, "This marker has not been added.");


        } else if (e.getSource() == approve) {
            HttpHeaders headersapprove = new HttpHeaders();
            headersapprove.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestapprove = new HttpEntity<>(responseList.get(index), headersapprove);
            template.put("http://localhost:8080/api/requestsE/approve", requestapprove, HttpStatus.class);
            JOptionPane.showMessageDialog(TextFieldAdminCards.this, "This marker has been added successfully.");


        } else if (e.getSource() == next) {
            getInfo(1);
            tlat.setText(String.valueOf(responseList.get(index).getLatitude()).substring(0, 6));
            tlon.setText(String.valueOf(responseList.get(index).getLongitude()).substring(0, 6));
            tdepth.setText(String.valueOf(new DecimalFormat("#0.00").format(responseList.get(index).getDepth())));
            tmag.setText(String.valueOf(new DecimalFormat("#0.00").format(responseList.get(index).getMagnitude())));
            dat.setText(String.valueOf(responseList.get(index).getData()));
            today.setText(String.valueOf(responseList.get(index).getAge()));
            country.setText(String.valueOf(responseList.get(index).getOnland()));
            location2.setText(getTitleFirst(responseList.get(index).getLatitude(), responseList.get(index).getLongitude()));
            location3.setText(locCity);
            setSender();
        } else if (e.getSource() == previous) {
            getInfo(-1);
            tlat.setText(String.valueOf(responseList.get(index).getLatitude()).substring(0, 6));
            tlon.setText(String.valueOf(responseList.get(index).getLongitude()).substring(0, 6));
            tdepth.setText(String.valueOf(new DecimalFormat("#0.00").format(responseList.get(index).getDepth())));
            tmag.setText(String.valueOf(new DecimalFormat("#0.00").format(responseList.get(index).getMagnitude())));
            dat.setText(String.valueOf(responseList.get(index).getData()));
            today.setText(String.valueOf(responseList.get(index).getAge()));
            country.setText(String.valueOf(responseList.get(index).getOnland()));
            location2.setText(getTitleFirst(responseList.get(index).getLatitude(), responseList.get(index).getLongitude()));
            location3.setText(locCity);
            setSender();
        } else if (e.getSource() == from) {
            setSender();
            Service.getProfileSender().setVisible(true);
        }
    }

    private void getInfo(int num) {
        index += num;
        if (index >= responseList.size()) {
            index = 0;
        }
        if (index < 0) {
            index = responseList.size() - 1;
        }
    }

    public void showRegisterPanel() {
        setVisible(true);

    }

}
