package com.lineate.buscompany.interactiveMap;

import javax.swing.*;

import com.lineate.buscompany.dtoE.requestE.RatingRequestE;
import com.lineate.buscompany.dtoE.responseE.RatingResponseE;
import com.lineate.buscompany.dtoE.responseE.SessionResponseE;
import de.fhpotsdam.unfolding.marker.Marker;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


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
import java.awt.Dimension;
import java.awt.FlowLayout;

public class TextFieldRating extends JFrame implements ITextField {

    private JComboBox<String> box;
    private JPanel contents;
    private JButton b1;
    private static String rate;
    private static RestTemplate template = new RestTemplate();

    public TextFieldRating(Star star) {
        super("Rating");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image imageM = toolkit.getImage("./data/mouse7.png");
        Cursor c = toolkit.createCustomCursor(imageM, new Point(getX(), getY()), "img");
        setCursor(c);

        JTextField textField = new JTextField("           Please rate this project ");
        textField.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 12));
        textField.setForeground(Color.RED);
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        textField.setBackground(new Color(255, 250, 240));

        JTextField shortField = new JTextField("Write your wishes here:   ");
        shortField.setFont(new java.awt.Font("Arial", Font.ITALIC, 12));
        shortField.setForeground(Color.BLACK);
        shortField.setEditable(false);
        shortField.setHorizontalAlignment(JTextField.CENTER);
        shortField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        shortField.setBackground(new Color(255, 250, 240));

        JTextField secondField = new JTextField("Choose your rating:                     ");
        secondField.setFont(new java.awt.Font("Arial", Font.ITALIC, 12));
        secondField.setForeground(Color.BLACK);
        secondField.setEditable(false);
        secondField.setHorizontalAlignment(JTextField.CENTER);
        secondField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        secondField.setBackground(new Color(255, 250, 240));

        String[] array = {"0", "1", "2", "3", "4", "5"};

        b1 = new JButton("Rate");
        box = new JComboBox<>(array);
        box.setBackground(Color.WHITE);
        box.setEditable(false);

        JTextArea area2 = new JTextArea(5, 19);

        area2.setEditable(true);
        area2.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        area2.setLineWrap(true);

        b1.setBackground(new Color(253, 224, 195));
        rate = String.valueOf(template.getForObject("http://localhost:8080/api/averages", int.class));

        System.out.println("\n\n\n    " + rate);

        switch (rate) {
            case "1":
                star.setStar1();
                break;
            case "2":
                star.setStar2();
                break;
            case "3":
                star.setStar3();
                break;
            case "4":
                star.setStar4();
                break;
            case "5":
                star.setStar5();
                break;
            default:
                star.setStar0();
        }
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Service.setCurrentToken();
                System.out.println("\n\n token ");
                System.out.println("\n\n token " + Service.getToken());

                SessionResponseE sessionResponseE2 = template.getForObject(
                        "http://localhost:8080/api/sessionsE/token/{token}",
                        SessionResponseE.class,
                        Service.getToken());
                System.out.println("\n\n\n  id!!   " + sessionResponseE2.getUserId());

                int r = Integer.parseInt(box.getSelectedItem().toString());
                System.out.println("\n\n\n           " + sessionResponseE2.getUserId());
                System.out.println("           " + r);
                System.out.println("           " + area2.getText());

                RatingRequestE ratingRequest = new RatingRequestE(
                        sessionResponseE2.getUserId(),
                        r,
                        area2.getText()
                );


                HttpHeaders headersRequest = new HttpHeaders();
                headersRequest.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<Object> requestRequest = new HttpEntity<>(ratingRequest, headersRequest);
                try {
                    RatingResponseE ratingResponse = template.postForObject("http://localhost:8080/api/ratingsE", requestRequest, RatingResponseE.class);


                    rate = String.valueOf(template.getForObject("http://localhost:8080/api/averages", int.class));

                    System.out.println("\n\n\n    " + rate);
                    switch (rate) {
                        case "1":
                            star.setStar1();
                            break;
                        case "2":
                            star.setStar2();
                            break;
                        case "3":
                            star.setStar3();
                            break;
                        case "4":
                            star.setStar4();
                            break;
                        case "5":
                            star.setStar5();
                            break;
                        default:
                            star.setStar0();
                    }

                    JOptionPane.showMessageDialog(TextFieldRating.this,
                            "Спасибо, ваша оценка (" + box.getSelectedItem().toString() + ")  будет учтена! ");
                } catch (HttpClientErrorException httpEx) {
                    JOptionPane.showMessageDialog(TextFieldRating.this, "Everyone can only rate the project once", "Warning",
                            JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./data/sqlEx-PhotoRoom.png"));
                }
                dispose();
            }
        });

        ImageIcon image = new ImageIcon("./data/Rating2-PhotoRoom.png");
        JLabel pic = new JLabel(image);
        contents = new JPanel(new FlowLayout(FlowLayout.LEFT));

        contents.add(textField);
        contents.add(pic);
        contents.add(secondField);
        contents.add(box);
        contents.add(shortField);
        contents.add(new JScrollPane(area2, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        contents.add(b1);

        contents.setBackground(new Color(255, 250, 240));

        setContentPane(contents);
        setSize(238, 365);

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