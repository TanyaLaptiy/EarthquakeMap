package com.lineate.buscompany.interactiveMap;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.lineate.buscompany.model.AirportMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;

public class TextFieldRoute extends JFrame implements ITextField {

    private JTextField smallField, bigField;
    JPanel contents;
    private JButton b1;
    private JComboBox<String> box1, box2;

    private static String startCounty, endCountry;
    private Map<String, List<Marker>> airToCounry = new HashMap<>();
    private Map<String, Marker> airToId = new HashMap<>();
    private Map<String, List<Marker>> routToDestination = new HashMap<>();

    private List<Marker> way = new ArrayList<>();
    private List<Marker> routes = new ArrayList<>();

    public TextFieldRoute(List<Marker> airportList, List<Marker> quakeMarkers, List<Marker> cityMarkers,
                          List<Marker> routeList) {
        super("Build air routes");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image imageM = toolkit.getImage("./data/mouse7.png");
        Cursor c = toolkit.createCustomCursor(imageM, new Point(getX(),
                getY()), "img");
        setCursor(c);
        for (Marker currentAir : airportList) {
            AirportMarker airportInStrartCountry = (AirportMarker) currentAir;
            airToId.put(airportInStrartCountry.getFeature().getId().toString(), currentAir);
            String countryName;
            countryName = currentAir.getProperty("country").toString();

            String airportCountry = countryName.substring(1, countryName.length() - 1);
            List<Marker> list = airToCounry.get(airportCountry);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(currentAir);
            airToCounry.put(airportCountry, list);
        }

        for (Marker currentRout : routeList) {
            String destination = currentRout.getProperty("destination").toString();
            String source = currentRout.getProperty("source").toString();

            List<Marker> list = routToDestination.get(destination);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(currentRout);
            routToDestination.put(destination, list);

            list = routToDestination.get(source);
            if (list == null) {
                list = new ArrayList<>();
            }

            list.add(currentRout);
            routToDestination.put(source, list);

        }


        List<String> curList = airToCounry.keySet().stream().sorted().collect(Collectors.toList());
        String[] array = new String[curList.size()];
        int i = 0;
        for (String country : curList) {
            array[i] = country;
            i++;
        }
        b1 = new JButton("Build");
        b1.setBackground(new Color(253, 224, 195));
        box1 = new JComboBox<>(array);
        box1.setBackground(Color.WHITE);
        box1.setEditable(true);
        box2 = new JComboBox<>(array);
        box2.setBackground(Color.WHITE);
        box2.setEditable(true);
        smallField = new JTextField(15);
        bigField = new JTextField(15);
        JTextField textField = new JTextField("                Build air routes");
        textField.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 12));
        textField.setForeground(Color.RED);
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        textField.setBackground(new Color(255, 250, 240));

        smallField.setToolTipText("Введите страну вылета");
        UIManager.put("ToolTip.background", new Color(255, 250, 240));

        bigField.setToolTipText("Введите страну назначения");

        bigField.setHorizontalAlignment(JTextField.RIGHT);


        b1.addActionListener(new ActionListener() {

            private void findNeighbors(Marker currentAir) {

                way = new ArrayList<>();
                routes = new ArrayList<>();


                AirportMarker airportInStrartCountry = (AirportMarker) currentAir;
                String airStr = airportInStrartCountry.getFeature().getId().toString();

                List<Marker> l = routToDestination.get(airStr);
                if (l != null) {
                    for (Marker currentRout : l) {
                        if (currentRout == null) {
                            System.out.println("\n Exception! " + airStr);
                        } else {
                            String routSecondStr = currentRout.getProperty("destination").toString();
                            String sourceSecondStr = currentRout.getProperty("source").toString();

                            way.add(airToId.get(routSecondStr));
                            way.add(airToId.get(sourceSecondStr));
                            routes.add(currentRout);
                        }
                    }
                }

            }

            public void actionPerformed(ActionEvent e) {

                int count = 0;
                int countRout = 0;
                Set<String> countries = new HashSet<>();

                startCounty = box1.getSelectedItem().toString();
                System.out.print("\n startCounty " + startCounty);
                endCountry = box2.getSelectedItem().toString();
                System.out.print("\n endCountry " + endCountry);

                if (startCounty != null && !startCounty.isEmpty() && endCountry != null && !endCountry.isEmpty()) {
                    hideOtherMarkers(airportList, routeList);
                    cityMarkers.stream().forEach(a -> a.setHidden(true));
                    cityMarkers.stream().filter(a -> a.getProperty("country").toString().equals(startCounty) || a.getProperty("country").toString().equals(endCountry)).forEach(a -> a.setHidden(false));
                    List<Marker> airs = airToCounry.get(startCounty);
                    if (airs != null) {
                        for (Marker currentAir : airToCounry.get(startCounty)) {
                            findNeighbors(currentAir);

                            int i = 0;
                            for (; i < way.size(); i++) {
                                AirportMarker otherAiroport = (AirportMarker) way.get(i);
                                String airID = otherAiroport.getFeature().getId().toString();
                                String countryOfAir = way.get(i).getProperty("country").toString();

                                String country = countryOfAir.substring(1, countryOfAir.length() - 1);
                                if (country.equals(endCountry)) {
                                    count++;
                                    for (Marker route : routes) {
                                        String routSecondStr = route.getProperty("destination").toString();
                                        String sourceSecondStr = route.getProperty("source").toString();

                                        if (airID.equals(routSecondStr) || airID.equals(sourceSecondStr)) {
                                            airToId.get(routSecondStr).setHidden(false);
                                            airToId.get(sourceSecondStr).setHidden(false);
                                            route.setHidden(false);

                                        }

                                    }

                                }

                            }

                        }


                        if (count == 0) {
                            hideOtherMarkers(airportList, routeList);

                            for (Marker currentAir : airToCounry.get(startCounty)) {
                                findNeighbors(currentAir);

                                int i = 0;
                                for (; i < way.size(); i++) {
                                    AirportMarker otherAiroport = (AirportMarker) way.get(i);
                                    String airID = otherAiroport.getFeature().getId().toString();
                                    String countryOfAir = way.get(i).getProperty("country").toString();

                                    String country = countryOfAir.substring(1, countryOfAir.length() - 1);
                                    if (country.equals(endCountry)) {
                                        for (Marker route : routes) {
                                            String routSecondStr = route.getProperty("destination").toString();
                                            String sourceSecondStr = route.getProperty("source").toString();

                                            if (airID.equals(routSecondStr) || airID.equals(sourceSecondStr)) {
                                                airToId.get(routSecondStr).setHidden(false);
                                                airToId.get(sourceSecondStr).setHidden(false);
                                                route.setHidden(false);
                                            }

                                        }

                                    } else {

                                        for (Marker currentSecondRout : routToDestination.get(airID)) {

                                            String routSecondStr = currentSecondRout.getProperty("destination").toString();
                                            String sourceSecondStr = currentSecondRout.getProperty("source").toString();

                                            String countryOfFinishAir = airToId.get(routSecondStr).getProperty("country")
                                                    .toString();

                                            String countryFinish = countryOfFinishAir.substring(1,
                                                    countryOfFinishAir.length() - 1);

                                            if (countryFinish.equals(endCountry)) {
                                                countRout++;
                                                countries.add(country);

                                                way.get(i).setHidden(false);
                                                currentSecondRout.setHidden(false);
                                                airToId.get(routSecondStr).setHidden(false);
                                                for (Marker route : routes) {
                                                    String routSecondS = route.getProperty("destination").toString();
                                                    String sourceSecondS = route.getProperty("source").toString();

                                                    if (airID.equals(routSecondS) || airID.equals(sourceSecondS)) {
                                                        airToId.get(routSecondS).setHidden(false);
                                                        airToId.get(sourceSecondS).setHidden(false);
                                                        route.setHidden(false);
                                                    }

                                                }
                                            }

                                            String countryOfFinishAir2 = airToId.get(sourceSecondStr).getProperty("country")
                                                    .toString();

                                            String countryFinish2 = countryOfFinishAir2.substring(1,
                                                    countryOfFinishAir2.length() - 1);

                                            if (countryFinish2.equals(endCountry)) {
                                                countRout++;
                                                countries.add(country);

                                                way.get(i).setHidden(false);
                                                currentSecondRout.setHidden(false);
                                                airToId.get(sourceSecondStr).setHidden(false);
                                                for (Marker route : routes) {
                                                    String routSecondSt = route.getProperty("destination").toString();
                                                    String sourceSecondSt = route.getProperty("source").toString();

                                                    if (airID.equals(routSecondSt) || airID.equals(sourceSecondSt)) {
                                                        airToId.get(routSecondSt).setHidden(false);
                                                        airToId.get(sourceSecondSt).setHidden(false);
                                                        route.setHidden(false);
                                                    }

                                                }
                                            }

                                        }

                                    }

                                }

                            }

                        }
                    }
                }


                if (count > 0) {
                    JOptionPane.showMessageDialog(TextFieldRoute.this,
                            "Были найдны ПРЯМЫЕ рейсы \n"
                                    + " из страны: " + box1.getSelectedItem().toString()
                                    + "\n в страну: " + box2.getSelectedItem().toString()
                                    + "\n\nКоличество рейсов: " + count / 2 + "\n\n ", "Information",
                            JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon("routesLogo-PhotoRoom.png"));

                } else if (countRout > 0) {
                    List<String> ll = countries.stream().collect(Collectors.toList());
                    StringBuilder info = new StringBuilder();
                    info.append("Были найдны КРАТЧАЙШИЕ рейсы \n"
                            + " из страны: " + box1.getSelectedItem().toString()
                            + "\n в страну: " + box2.getSelectedItem().toString()
                            + "\n\n "
                            + box1.getSelectedItem().toString());
                    info.append(" ~> " + ll.get(0));

                    for (int i = 1; i < countries.size(); i++) {
                        info.append("\n");

                        for (int k = 0; k < box1.getSelectedItem().toString().length(); k++) {
                            info.append("  ");
                        }
                        info.append("   ~> " + ll.get(i));

                    }
                    info.append("        ~> " + box2.getSelectedItem().toString());

                    JOptionPane.showMessageDialog(TextFieldRoute.this,
                            info.toString(), "Information",
                            JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon("routesLogo-PhotoRoom.png"));
                } else {
                    JOptionPane.showMessageDialog(TextFieldRoute.this,
                            "К сожалению КРАТЧАЙШИЕ рейсы \n"
                                    + " из страны: " + box1.getSelectedItem().toString()
                                    + "\n в страну: " + box2.getSelectedItem().toString()
                                    + "\n не найдены ", "Information",
                            JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon("routesLogo-PhotoRoom.png"));
                }
            }


        });
        ImageIcon image = new ImageIcon("./data/airIco.png");
        JLabel pic = new JLabel(image);

        contents = new JPanel(new FlowLayout(FlowLayout.LEFT));

        contents.add(textField);

        contents.add(pic);
        contents.add(box1);
        contents.add(box2);

        contents.add(b1);

        contents.setBackground(new Color(255, 250, 240));
        setContentPane(contents);

        setSize(250, 220);

        setResizable(false);
        setAlwaysOnTop(true);
        setVisible(false);

    }

    private void hideOtherMarkers(List<Marker> airportList, List<Marker> routeList) {
        for (Marker marker : airportList) {
            marker.setHidden(true);
        }
        for (Marker marker : routeList) {
            marker.setHidden(true);
        }
    }

    public void showTextPanel() {
        setVisible(true);

    }

    public void hideTextPanel() {
        setVisible(false);

    }

}