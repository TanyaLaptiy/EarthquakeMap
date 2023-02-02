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
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.google.gson.Gson;
import com.lineate.buscompany.MainApplication;
import com.lineate.buscompany.dtoE.requestE.ClientRequestE;
import com.lineate.buscompany.dtoE.responseE.ClientResponseE;
import de.fhpotsdam.unfolding.marker.Marker;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;


public class TextFieldRegistration extends JFrame implements ActionListener {

    private Container c;
    private JLabel title;

    private JLabel name;
    private JTextField tname;

    private JLabel lastName;
    private JTextField tLastName;

    private JLabel middleName;
    private JTextField tmiddleName;

    private JLabel country;
    private JComboBox<String> tcountry;

    private JLabel login;
    private JTextField tlogin;

    private JPasswordField value;
    private JLabel l1;

    private JLabel mno;
    private JTextField tmno;

    private JLabel gender;
    private JRadioButton male;
    private JRadioButton female;
    private ButtonGroup gengp;

    private JLabel dob;
    private JComboBox<String> date;
    private JComboBox<String> month;
    private JComboBox<String> year;
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
    private List<String> curList;
    private String dates[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
            "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String months[] = {"Jan", "feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sup", "Oct", "Nov", "Dec"};
    private String years[] = {"1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970",
            "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983",
            "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996",
            "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009",
            "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022"};
    int age;

    public TextFieldRegistration(List<Marker> airportList, List<Marker> cityMarkers) {

        setTitle("Registration Form");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("./data/mouse7.png");
        Cursor cur = toolkit.createCustomCursor(image, new Point(getX(), getY()), "img");
        setCursor(cur);

        setBounds(300, 90, 650, 650);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);

        List<String> list = new ArrayList<>();

        for (Marker r : airportList) {

            String countryName = r.getProperty("country").toString();
            String airportCountry = countryName.substring(1, countryName.length() - 1);
            list.add(airportCountry);

        }

        for (Marker r : cityMarkers) {
            list.add(r.getProperty("country").toString());

        }

        curList = list.stream().distinct().sorted().collect(Collectors.toList());
        String[] array = new String[curList.size()];

        int i = 0;
        for (String country : curList) {
            array[i] = country;
            i++;
        }

        c = getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(255, 250, 240));

        title = new JLabel("Registration Form");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setForeground(new Color(64, 192, 255));
        title.setSize(300, 30);
        title.setLocation(200, 30);

        c.add(title);

        name = new JLabel("Name");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(100, 20);
        name.setLocation(100 - 50, 100);
        c.add(name);

        tname = new JTextField();
        tname.setFont(new Font("Arial", Font.PLAIN, 15));
        tname.setSize(190, 20);
        tname.setLocation(250, 100);
        c.add(tname);

        lastName = new JLabel("Last Name");
        lastName.setFont(new Font("Arial", Font.PLAIN, 20));
        lastName.setSize(100, 20);
        lastName.setLocation(100 - 50, 130);
        c.add(lastName);

        tLastName = new JTextField();
        tLastName.setFont(new Font("Arial", Font.PLAIN, 15));
        tLastName.setSize(190, 20);
        tLastName.setLocation(250, 130);
        c.add(tLastName);

        middleName = new JLabel("Middle name*");
        middleName.setFont(new Font("Arial", Font.PLAIN, 20));
        middleName.setSize(150, 20);
        middleName.setLocation(100 - 50, 160);
        c.add(middleName);

        tmiddleName = new JTextField();
        tmiddleName.setFont(new Font("Arial", Font.PLAIN, 15));
        tmiddleName.setSize(190, 20);
        tmiddleName.setLocation(250, 160);
        c.add(tmiddleName);

        country = new JLabel("Country");
        country.setBackground(new Color(255, 250, 240));
        country.setFont(new Font("Arial", Font.PLAIN, 20));
        country.setSize(100, 20);
        country.setLocation(100 - 50, 190);
        c.add(country);

        tcountry = new JComboBox(array);
        tcountry.setFont(new Font("Arial", Font.PLAIN, 15));
        tcountry.setSize(190, 20);
        tcountry.setBackground(new Color(255, 250, 240));
        tcountry.setLocation(250, 190);
        c.add(tcountry);

        login = new JLabel("Login");
        login.setFont(new Font("Arial", Font.PLAIN, 20));
        login.setSize(100, 20);
        login.setLocation(100 - 50, 220);
        c.add(login);

        tlogin = new JTextField();
        tlogin.setFont(new Font("Arial", Font.PLAIN, 15));
        tlogin.setSize(190, 20);
        tlogin.setLocation(250, 220);
        c.add(tlogin);

        l1 = new JLabel("Password:");
        l1.setFont(new Font("Arial", Font.PLAIN, 20));
        l1.setSize(100, 20);
        l1.setLocation(100 - 50, 250);
        c.add(l1);

        value = new JPasswordField();
        value.setFont(new Font("Arial", Font.PLAIN, 15));
        value.setSize(190, 20);
        value.setLocation(250, 250);
        c.add(value);

        mno = new JLabel("Email address");
        mno.setFont(new Font("Arial", Font.PLAIN, 20));
        mno.setSize(150, 20);
        mno.setLocation(100 - 50, 280);
        c.add(mno);

        tmno = new JTextField();
        tmno.setFont(new Font("Arial", Font.PLAIN, 15));
        tmno.setSize(150, 20);
        tmno.setLocation(250, 280);
        c.add(tmno);

        gender = new JLabel("Gender");
        gender.setFont(new Font("Arial", Font.PLAIN, 20));
        gender.setSize(100, 20);
        gender.setLocation(100 - 50, 310);
        c.add(gender);

        male = new JRadioButton("Male");
        male.setBackground(new Color(255, 250, 240));
        male.setFont(new Font("Arial", Font.PLAIN, 15));
        male.setSelected(true);
        male.setSize(75, 20);
        male.setLocation(250, 310);
        c.add(male);

        female = new JRadioButton("Female");
        female.setBackground(new Color(255, 250, 240));
        female.setFont(new Font("Arial", Font.PLAIN, 15));
        female.setSelected(false);
        female.setSize(80, 20);
        female.setLocation(320, 310);
        c.add(female);

        gengp = new ButtonGroup();
        gengp.add(male);
        gengp.add(female);

        dob = new JLabel("DOB");
        dob.setBackground(new Color(255, 250, 240));
        dob.setFont(new Font("Arial", Font.PLAIN, 20));
        dob.setSize(100, 20);
        dob.setLocation(100 - 50, 340);
        c.add(dob);

        date = new JComboBox(dates);
        date.setFont(new Font("Arial", Font.PLAIN, 15));
        date.setSize(50, 20);
        date.setBackground(new Color(255, 250, 240));
        date.setLocation(200 + 50, 340);
        c.add(date);

        month = new JComboBox(months);
        month.setFont(new Font("Arial", Font.PLAIN, 15));
        month.setSize(60, 20);
        month.setBackground(new Color(255, 250, 240));
        month.setLocation(250 + 50, 340);
        c.add(month);

        year = new JComboBox(years);
        year.setFont(new Font("Arial", Font.PLAIN, 15));
        year.setSize(60, 20);
        year.setBackground(new Color(255, 250, 240));
        year.setLocation(320 + 50, 340);
        c.add(year);

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

        logOn = new JTextField("If you are already registered,");
        logOn.setEditable(false);
        logOn.setBackground(new Color(255, 250, 240));
        logOn.setFont(new Font("Arial", Font.PLAIN, 15));
        logOn.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        logOn.setSize(250, 20);
        logOn.setLocation(150, 510);
        c.add(logOn);
        logOn2 = new JTextField("please login to your account.");
        logOn2.setEditable(false);
        logOn2.setBackground(new Color(255, 250, 240));
        logOn2.setFont(new Font("Arial", Font.PLAIN, 15));
        logOn2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        logOn2.setSize(250, 20);
        logOn2.setLocation(150, 530);
        c.add(logOn2);

        log = new JButton("Sign in");
        log.setFont(new Font("Arial", Font.PLAIN, 15));
        log.setSize(100, 20);
        log.setBackground(new Color(253, 224, 195));
        log.setLocation(150, 560);
        log.addActionListener(this);
        c.add(log);

        // admin
        admin = new JButton("I'm admin");
        admin.setFont(new Font("Arial", Font.PLAIN, 15));
        admin.setSize(100, 20);
        admin.setBackground(new Color(253, 224, 195));
        admin.setLocation(270, 560);
        admin.addActionListener(this);
        c.add(admin);

        ImageIcon ii = new ImageIcon("./data/reg-PhotoRoom4.png");
        tout = new JLabel(ii);
        tout.setSize(400, 500);
        tout.setLocation(350, 100);
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
            if (tname.getText().isEmpty() || tname.getText().matches(".*\\d+.*")) {
                res.setText("Wrong name..");
            } else if (tLastName.getText().isEmpty() || tLastName.getText().matches(".*\\d+.*")) {
                res.setText("Wrong last name..");
            } else if (tlogin.getText().isEmpty()) {
                res.setText("Wrong login..");
            } else if (tmno.getText().isEmpty()
                    || !tmno.getText().contains("@mail.ru") && !tmno.getText().contains("@gmail.ru")) {
                res.setText("Wrong mail..");
            } else if (!term.isSelected()) {
                res.setText("Please accept the" + " terms & conditions..");
            } else if (value.getText().isEmpty() || value.getText() == null || value.getText().length() <= 5) {
                res.setText("Password not secure");
            } else {
                int yearCur = Year.now().getValue();
                age = yearCur - Integer.parseInt(year.getSelectedItem().toString());
                System.out.print("\n  " + age);
                String gender;
                if (male.isSelected()) {
                    gender = "Male";
                } else {
                    gender = "Female";
                }

                int mounthInt = Arrays.asList(months).indexOf((String) month.getSelectedItem()) + 1;

                LocalDate localDate = LocalDate.of(Integer.parseInt((String) year.getSelectedItem()), mounthInt, Integer.parseInt((String) date.getSelectedItem()));


                ClientRequestE clientDto = new ClientRequestE(tLastName.getText(), tname.getText(),
                        tmiddleName.getText(), tlogin.getText(), value.getText(), tcountry.getSelectedItem().toString(), gender, localDate,
                        tmno.getText());
                RestTemplate template = new RestTemplate();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<Object> request = new HttpEntity<>(clientDto, headers);
                ClientResponseE clientResponseE = null;
                try {
                    clientResponseE = template.postForObject("http://localhost:8080/api/clientsE", request, ClientResponseE.class);

                    String data2 = (String) date.getSelectedItem() + "/" + (String) month.getSelectedItem() + "/"
                            + (String) year.getSelectedItem() + "\n";
                    Service.setProfile(tlogin.getText(), tname.getText(),
                            tLastName.getText() + " " + tmiddleName.getText(), tmno.getText(),
                            tcountry.getSelectedItem().toString(), gender, data2, age, false, "");

                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("./data/tokenfile.txt"));

                        writer.write(clientResponseE.getToken());
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    Service.getProfile().setVisible(true);
                    System.out.println("\n\n\n user registrated:    " + clientResponseE.getLogin());


                } catch (HttpClientErrorException httpEx) {
                    JOptionPane.showMessageDialog(TextFieldRegistration.this, "Login must be unique", "Warning",
                            JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./data/sqlEx-PhotoRoom.png"));
                }

                dispose();

            }
        } else if (e.getSource() == reset) {
            String def = "";
            tname.setText(def);
            tadd.setText(def);
            tmno.setText(def);
            res.setText(def);
            tout.setText(def);
            term.setSelected(false);
            date.setSelectedIndex(0);
            month.setSelectedIndex(0);
            year.setSelectedIndex(0);
        } else if (e.getSource() == log) {
            dispose();
            Service.getTextFieldSignIn().setVisible(true);
        } else if (e.getSource() == admin) {
            dispose();
            Service.getTextFieldRegAdm().setVisible(true);
        }
    }

    public void setPhoto() {
        BufferedImage image = null;
        BufferedImage overlay = null;
        try {

            int index = curList.indexOf(tcountry.getSelectedItem().toString());

            index++;
            String flag = "./data/flag" + index + ".png";

            image = ImageIO.read(new File(flag));
            if (male.isSelected()) {
                if (age <= 18) {
                    overlay = ImageIO.read(new File("./data/menyoung.png"));
                } else if (age > 18 && age < 55) {
                    overlay = ImageIO.read(new File("./data/men.png"));
                } else {
                    overlay = ImageIO.read(new File("./data/menold.png"));
                }
            } else {
                if (age <= 18) {
                    overlay = ImageIO.read(new File("./data/womenyoung.png"));
                } else if (age > 18 && age < 55) {
                    overlay = ImageIO.read(new File("./data/women.png"));
                } else {
                    overlay = ImageIO.read(new File("./data/womenold.png"));
                }
            }

        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        int w = Math.max(image.getWidth(), overlay.getWidth());
        int h = Math.max(image.getHeight(), overlay.getHeight());
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics g = combined.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.drawImage(overlay, 0, 0, null);

        g.dispose();
    }

    public void showRegisterPanel() {
        setVisible(true);

    }

}
