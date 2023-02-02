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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.xml.crypto.Data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.lineate.buscompany.dtoE.requestE.SmsRequest;
import com.lineate.buscompany.dtoE.responseE.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import de.fhpotsdam.unfolding.marker.Marker;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class TextFieldChat extends JFrame implements ActionListener {

    JPanel rightPanel = new JPanel();
    JPanel leftPanel = new JPanel();
    JPanel photoPanel = new JPanel();
    JTextArea area2;
    private Container c;
    private static RestTemplate template = new RestTemplate();
    JTextArea area;
    private JButton next;
    private JLabel tout;


    public TextFieldChat() {

        setTitle("Chat");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("./data/mouse7.png");
        Cursor cur = toolkit.createCustomCursor(image, new Point(getX(), getY()), "img");
        setCursor(cur);

        setBounds(300, 90, 715, 760);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(255, 250, 240));

        rightPanel.setBounds(65, 285, 450, 284);
        rightPanel.setBackground(Color.WHITE);
        area = new JTextArea(17, 39);

        getMessage();

        area.setEditable(false);
        area.setText(getMessage().toString());
        area.setLineWrap(false);
        area.setLocation(80, 100);
        area.setBorder(null);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(null);
        rightPanel.add(scroll);
        add(rightPanel);

        leftPanel.setBounds(125, 595, 550, 90);
        leftPanel.setBackground(Color.white);
        area2 = new JTextArea(5, 49);
        area2.setEditable(true);
        area2.setBorder(null);
        area2.setLocation(65, 100);
        area2.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        area2.setLineWrap(true);
        leftPanel.add(area2);
        add(leftPanel);


        next = new JButton("Send");
        next.setFont(new Font("Arial", Font.PLAIN, 15));
        next.setSize(100, 20);
        next.setBackground(new Color(253, 224, 195));
        next.setLocation(450 + 20 + 20 + 70, 570 + 70 + 55);
        next.addActionListener(this);
        c.add(next);


        ImageIcon ii = new ImageIcon("./data/chatt.png");
        tout = new JLabel(ii);
        tout.setSize(700, 750);
        tout.setLocation(0, 00);
        c.add(tout);

        photoPanel.setBounds(10, 582, 100, 110);
        photoPanel.setBackground(Color.white);
        ImageIcon photo = new ImageIcon("./data/combined.png");
        Image img = photo.getImage();
        Image newimg = img.getScaledInstance(85, 100, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        photo = new ImageIcon(newimg);
        JLabel pic = new JLabel(photo);
        pic.setSize(50, 50);
        photoPanel.add(pic);
        add(photoPanel);

        setAlwaysOnTop(true);
        setVisible(false);

    }

    private String cutText(String user, String text) {
        StringBuilder currentInfo = new StringBuilder();

        String arrWords[] = text.split("[ ]+");
        ArrayList<String> arrPhrases = new ArrayList<String>();

        StringBuilder stringBuffer = new StringBuilder();
        int cnt = 0;
        int index = 0;
        int length = arrWords.length;

        while (index != length) {
            if (cnt + arrWords[index].length() <= 50) {

                cnt += arrWords[index].length() + 1;
                stringBuffer.append(arrWords[index]).append(" ");
                index++;
            } else {
                arrPhrases.add(stringBuffer.toString());
                stringBuffer = new StringBuilder();
                cnt = 0;
            }
        }

        if (stringBuffer.length() > 0) {
            arrPhrases.add(stringBuffer.toString());
        }
        currentInfo.append(" " + user + ":");
        for (String part : arrPhrases) {
            currentInfo.append("             " + part);
            currentInfo.append("\n               ");
        }
        currentInfo.append("\n");
        return currentInfo.toString();
    }

    private String getMessage() {
        HttpHeaders headerslogin = new HttpHeaders();
        headerslogin.setContentType(MediaType.APPLICATION_JSON);

        String administratorDtos3 = template.getForObject("http://localhost:8080/api/AllsmsE", String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType =
                objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, SmsResponseStr.class);
        List<SmsResponseStr> responseList = null;
        try {
            responseList = objectMapper.readValue(administratorDtos3, listType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        StringBuilder res = new StringBuilder();
        if (responseList != null) {
            for (SmsResponseStr sms : responseList) {
                ClientResponseE userById = template.getForObject(
                        "http://localhost:8080/api/clientsE/{id}",
                        ClientResponseE.class,
                        sms.getClientId());
                res.append(cutText(userById.getLogin(), sms.getMessage() + "  (" + sms.getData().toString() + ");"));
            }
        }

        return res.toString();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == next) {
            Service.setCurrentToken();

            LocalDate data = LocalDate.now();
            SessionResponseE sessionResponseE2 = template.getForObject(
                    "http://localhost:8080/api/sessionsE/token/{token}",
                    SessionResponseE.class,
                    Service.getToken());
            System.out.println("\n\n\n  id!!   " + sessionResponseE2.getUserId());
            SmsRequest smsRequest = new SmsRequest(
                    sessionResponseE2.getUserId(),
                    data,
                    area2.getText()
            );

            HttpHeaders headersRequest = new HttpHeaders();
            headersRequest.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> requestRequest = new HttpEntity<>(smsRequest, headersRequest);
            SmsResponse smsResponse = template.postForObject("http://localhost:8080/api/smsE", requestRequest, SmsResponse.class);
            area2.setText("");
            area.setText(getMessage().toString());
        }

    }

    public void setPhoto() {
        photoPanel.setBounds(20, 595, 80, 90);
        photoPanel.setBackground(Color.white);
        ImageIcon photo = new ImageIcon("./data/combined.png");
        Image img = photo.getImage();
        Image newimg = img.getScaledInstance(70, 80, java.awt.Image.SCALE_SMOOTH);
        photo = new ImageIcon(newimg);
        JLabel pic = new JLabel(photo);
        pic.setSize(50, 50);
        photoPanel.add(pic);
        add(photoPanel);
    }

    public void showRegisterPanel() {
        setVisible(true);

    }

}
