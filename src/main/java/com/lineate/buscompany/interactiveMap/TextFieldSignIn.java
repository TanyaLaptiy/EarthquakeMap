package com.lineate.buscompany.interactiveMap;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
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
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.lineate.buscompany.dtoE.requestE.LoginRequestE;
import com.lineate.buscompany.dtoE.responseE.AdministratorResponseE;
import com.lineate.buscompany.dtoE.responseE.ClientResponseE;
import com.lineate.buscompany.dtoE.responseE.SessionResponseE;
import com.lineate.buscompany.dtoE.responseE.UserResponseE;
import de.fhpotsdam.unfolding.marker.Marker;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class TextFieldSignIn extends JFrame implements ActionListener {

    private Container c;
    private JLabel title;


    private JLabel login;
    private JTextField tlogin;

    private JPasswordField value;
    private JLabel l1;

    private JLabel add;
    private JTextArea tadd;
    private JCheckBox term;
    private JTextField logOn;
    private JTextField logOn2;
    private JButton admin;

    private JButton log;
    private JButton sub;
    private JButton reset;
    private JLabel tout;
    private JLabel res;

    public TextFieldSignIn() {

        setTitle("Sign In");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("./data/mouse7.png");
        Cursor cur = toolkit.createCustomCursor(image, new Point(getX(), getY()), "img");
        setCursor(cur);

        setBounds(300, 90, 650, 650);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);


        c = getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(255, 250, 240));

        title = new JLabel("Sign In");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setForeground(new Color(64, 192, 255));
        title.setSize(300, 30);
        title.setLocation(250, 30);

        c.add(title);


        login = new JLabel("Login");
        login.setFont(new Font("Arial", Font.PLAIN, 20));
        login.setSize(100, 20);
        login.setLocation(200, 310);
        c.add(login);

        tlogin = new JTextField();
        tlogin.setFont(new Font("Arial", Font.PLAIN, 15));
        tlogin.setSize(190, 20);
        tlogin.setLocation(300, 310);
        c.add(tlogin);

        l1 = new JLabel("Password:");
        l1.setFont(new Font("Arial", Font.PLAIN, 20));
        l1.setSize(100, 20);
        l1.setLocation(200, 340);
        c.add(l1);

        value = new JPasswordField();
        value.setFont(new Font("Arial", Font.PLAIN, 15));
        value.setSize(190, 20);
        value.setLocation(300, 340);
        c.add(value);


        term = new JCheckBox("Accept Terms And Conditions.");
        term.setBackground(new Color(255, 250, 240));
        term.setFont(new Font("Arial", Font.PLAIN, 15));
        term.setSize(250, 20);
        term.setLocation(150, 450);
        c.add(term);

        sub = new JButton("Submit");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(100, 20);
        sub.setBackground(new Color(253, 224, 195));
        sub.setLocation(150, 480);
        sub.addActionListener(this);
        c.add(sub);

        reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(100, 20);
        reset.setBackground(new Color(253, 224, 195));
        reset.setLocation(270, 480);
        reset.addActionListener(this);
        c.add(reset);

        logOn = new JTextField("If you are not registered yet,");
        logOn.setEditable(false);
        logOn.setBackground(new Color(255, 250, 240));
        logOn.setFont(new Font("Arial", Font.PLAIN, 15));
        logOn.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        logOn.setSize(250, 20);
        logOn.setLocation(150, 510);
        c.add(logOn);
        logOn2 = new JTextField("please register.");
        logOn2.setEditable(false);
        logOn2.setBackground(new Color(255, 250, 240));
        logOn2.setFont(new Font("Arial", Font.PLAIN, 15));
        logOn2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        logOn2.setSize(250, 20);
        logOn2.setLocation(150, 530);
        c.add(logOn2);

        log = new JButton("Sign up");
        log.setFont(new Font("Arial", Font.PLAIN, 15));
        log.setSize(100, 20);
        log.setBackground(new Color(253, 224, 195));
        log.setLocation(150, 560);
        log.addActionListener(this);
        c.add(log);

        admin = new JButton("I'm admin");
        admin.setFont(new Font("Arial", Font.PLAIN, 15));
        admin.setSize(100, 20);
        admin.setBackground(new Color(253, 224, 195));
        admin.setLocation(270, 560);
        admin.addActionListener(this);
        c.add(admin);

        ImageIcon ii = new ImageIcon("./data/SignIn-PhotoRoom1.png");
        tout = new JLabel(ii);
        tout.setSize(600, 600);
        tout.setLocation(0, 0);
        c.add(tout);

        res = new JLabel("");
        res.setFont(new Font("Arial", Font.PLAIN, 20));
        res.setSize(500, 25);
        res.setLocation(100, 590);
        c.add(res);

        setAlwaysOnTop(true);
        setVisible(false);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sub) {
            HttpHeaders headerslogin = new HttpHeaders();
            headerslogin.setContentType(MediaType.APPLICATION_JSON);
            LoginRequestE loginRequestE = new LoginRequestE(tlogin.getText(), value.getText());
            HttpEntity<Object> requestlogin = new HttpEntity<>(loginRequestE, headerslogin);
            RestTemplate template = new RestTemplate();
            UserResponseE clientResponseELogin = template.postForObject("http://localhost:8080/api/loginUser", requestlogin, UserResponseE.class);
            List<SessionResponseE> sessionResponseEList3 = template.getForObject("http://localhost:8080/api/sessionsE", List.class);

            System.out.println("\n\n\n login:    " + clientResponseELogin.getLogin() + " " + clientResponseELogin.getFirstName());


            ClientResponseE userById = template.getForObject(
                    "http://localhost:8080/api/clientsE/{id}",
                    ClientResponseE.class,
                    clientResponseELogin.getId());

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("./data/tokenfile.txt"));

                writer.write(userById.getToken());
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("  userById:    " + userById.getFirstName());
            int yearCur = Year.now().getValue();
            int age = yearCur - clientResponseELogin.getBirthday().getYear();
            if (userById.getMail() != null) {
                Service.setProfile(clientResponseELogin.getLogin(), clientResponseELogin.getFirstName(),
                        clientResponseELogin.getLastName() + " " + clientResponseELogin.getPatronymic(), userById.getMail(),
                        clientResponseELogin.getCountry(), clientResponseELogin.getSex(), clientResponseELogin.getBirthday().toString(), age, false, "");
            } else {
                AdministratorResponseE adminById = template.getForObject(
                        "http://localhost:8080/api/adminsE/{id}",
                        AdministratorResponseE.class,
                        clientResponseELogin.getId());

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("./data/tokenfile.txt"));

                    writer.write(adminById.getToken());
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                System.out.println("  userById:    " + adminById.getFirstName());

                Service.setProfile(clientResponseELogin.getLogin(), clientResponseELogin.getFirstName(),
                        clientResponseELogin.getLastName() + " " + clientResponseELogin.getPatronymic(), "earthquakes@list.ru",
                        clientResponseELogin.getCountry(), clientResponseELogin.getSex(), clientResponseELogin.getBirthday().toString(), age, true, adminById.getPosition());
            }
            Service.getProfile().setVisible(true);

            dispose();

        } else if (e.getSource() == reset) {
            String def = "";
            tadd.setText(def);
            res.setText(def);
            tout.setText(def);
            term.setSelected(false);
            value.setText(def);
            tlogin.setText(def);
        } else if (e.getSource() == log) {
            dispose();
            Service.getTextFieldReg().setVisible(true);
        } else if (e.getSource() == admin) {
            dispose();
            Service.getTextFieldRegAdm().setVisible(true);
        }
    }

    public void showRegisterPanel() {
        setVisible(true);

    }

}
