package com.lineate.buscompany.interactiveMap;

import javax.swing.*;

import com.lineate.buscompany.MainApplication;
import com.lineate.buscompany.services.CityService;
import com.lineate.buscompany.services.EarthquakeService;
import com.lineate.buscompany.services.MarkerService;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;


import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.Properties;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;

public class TextFieldEarthQuake extends JFrame implements ITextField {
    private JButton b1;

    public TextFieldEarthQuake() {
        super("Show earthquakes");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image imageM = toolkit.getImage("./data/mouse7.png");
        Cursor c = toolkit.createCustomCursor(imageM, new Point(getX(), getY()), "img");
        setCursor(c);

        JTextField textField = new JTextField(" Show earthquakes on a specific date");
        textField.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 12));
        textField.setForeground(Color.RED);
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        textField.setBackground(new Color(255, 250, 240));

        JTextField tittleField = new JTextField("Choose a date: ");
        tittleField.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 12));
        tittleField.setForeground(Color.BLACK);
        tittleField.setEditable(false);
        tittleField.setHorizontalAlignment(JTextField.CENTER);
        tittleField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        tittleField.setBackground(new Color(255, 250, 240));

        b1 = new JButton("Show");
        b1.setBackground(new Color(253, 224, 195));

        ImageIcon image = new ImageIcon("./data/earth.png");
        JLabel pic = new JLabel(image);
        JPanel contents = new JPanel(new FlowLayout(FlowLayout.LEFT));

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EarthquakeService.hideQuakeMarkers();
                int month = datePicker.getJDateInstantPanel().getModel().getMonth() + 1;

                LocalDate currentdate = LocalDate.of(datePicker.getJDateInstantPanel().getModel().getYear(),
                        month, datePicker.getJDateInstantPanel().getModel().getDay());

                EarthquakeService.parseByDate(currentdate);
                MarkerService.getInstance().addEarthQuakeToMap();
                EarthquakeService.printQuakes();
                CityService.getInstance().hideCityInfo();
                MainApplication.refreshMarkers();

                JOptionPane.showMessageDialog(TextFieldEarthQuake.this,
                        "Вы можете видеть все землятресения, которые \n " + "происходили в указанный год: "
                                + datePicker.getJDateInstantPanel().getModel().getYear() + "\nВ месяц: "
                                + month + "\n " + "В день: "
                                + datePicker.getJDateInstantPanel().getModel().getDay());
            }
        });

        contents.add(textField);

        contents.add(pic);
        contents.add(tittleField);
        contents.add(datePicker);
        contents.add(b1);

        contents.setBackground(new Color(255, 250, 240));

        setContentPane(contents);
        setSize(250, 290);
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