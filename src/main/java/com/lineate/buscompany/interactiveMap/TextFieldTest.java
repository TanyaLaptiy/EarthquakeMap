package com.lineate.buscompany.interactiveMap;

import javax.swing.*;

import com.lineate.buscompany.services.AirportService;
import com.lineate.buscompany.services.MarkerService;
import de.fhpotsdam.unfolding.marker.Marker;

import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;

public class TextFieldTest extends JFrame implements ITextField {

    private JComboBox<String> box;
    private JPanel contents;
    private JButton b1;
    private Map<String, List<Marker>> map = new HashMap<>();
    private static String country;

    public TextFieldTest(List<Marker> airportList, List<Marker> quakeMarkers, List<Marker> cityMarkers) {
        super("Country");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image imageM = toolkit.getImage("./data/mouse7.png");
        Cursor c = toolkit.createCustomCursor(imageM, new Point(getX(),
                getY()), "img");
        setCursor(c);


        JTextField textField = new JTextField("                  Select country");
        textField.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 12));
        textField.setForeground(Color.RED);
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        textField.setBackground(new Color(255, 250, 240));

        for (Marker r : airportList) {

            String countryName = r.getProperty("country").toString();
            String airportCountry = countryName.substring(1, countryName.length() - 1);

            List<Marker> list = map.get(airportCountry);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(r);
            map.put(airportCountry, list);
        }

        for (Marker r : cityMarkers) {

            String st = r.getProperty("country").toString();
            List<Marker> list = map.get(st);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(r);
            map.put(r.getProperty("country").toString(), list);
        }

        List<String> curList = map.keySet().stream().sorted().collect(Collectors.toList());
        String[] array = new String[curList.size()];
        int i = 0;
        for (String country : curList) {
            array[i] = country;
            i++;
        }
        b1 = new JButton("Select");
        box = new JComboBox<>(array);
        box.setBackground(Color.WHITE);
        box.setEditable(true);
        b1.setBackground(new Color(253, 224, 195));

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                country = box.getSelectedItem().toString();
                System.out.println("\n\n !! " + country + "\n\n");
                quakeMarkers.stream().forEach(a -> a.setHidden(true));
                map.values().stream().flatMap(List::stream).forEach(a -> a.setHidden(true));
                AirportService.getInstance().hideAirRouts();
                List<Marker> l = map.get(country);
                if (l != null) {
                    Marker last = null;
                    for (Marker m : map.get(country)) {
                        m.setHidden(false);
                        last = m;
                    }
                    MarkerService.getInstance().getMap().panTo(last.getLocation());
                    MarkerService.getInstance().getMap().zoom(3);
                }
                JOptionPane.showMessageDialog(TextFieldTest.this,
                        "Все маркеры в данной стране отмечены: " + box.getSelectedItem().toString());
            }
        });

        ImageIcon image = new ImageIcon("./data/countryIcon-PhotoRoom.png");
        JLabel pic = new JLabel(image);
        contents = new JPanel(new FlowLayout(FlowLayout.LEFT));

        contents.add(textField);

        contents.add(pic);

        contents.add(box);
        contents.add(b1);

        contents.setBackground(new Color(255, 250, 240));

        setContentPane(contents);
        setSize(300, 210);

        setResizable(false);
        setAlwaysOnTop(true);
        setVisible(false);

    }

    public void showTextPanel() {
        setVisible(true);

    }

    public void hideTextPanel() {
        setVisible(false);

    }

}