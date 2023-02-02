package com.lineate.buscompany.interactiveMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.lineate.buscompany.dtoE.responseE.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class TextFieldRatingAdm extends JFrame implements ActionListener {

    private Container c;
    private JLabel comment;
    JTextArea area2;
    JPanel leftPanel = new JPanel();
    private JLabel rating;
    private JLabel count;
    private static JTextField trating;
    private static JTextField tcount;
    private ClientResponseE userById;
    private JButton next;
    private JButton previous;
    private JButton from;
    private JLabel tout;
    private JTextField dat = new JTextField();
    private JLabel today = new JLabel();
    private static RestTemplate template = new RestTemplate();
    private static List<RatingResponseE> responseList = null;
    private int index = 0;

    public TextFieldRatingAdm() {

        setTitle("Rating");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("./data/mouse7.png");
        Cursor cur = toolkit.createCustomCursor(image, new Point(getX(), getY()), "img");
        setCursor(cur);

        setBounds(0, 50, 400, 430);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(false);

        c = getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(255, 250, 240));


        getAllRates();
        setSender();
        comment = new JLabel("This user's comment:   ");
        comment.setFont(new java.awt.Font("Arial", Font.ITALIC, 12));
        comment.setForeground(Color.BLACK);
        comment.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        comment.setBackground(new Color(255, 250, 240));
        comment.setSize(150, 20);
        comment.setLocation(180, 160);
        c.add(comment);

        leftPanel.setBounds(180, 180, 200, 90);
        leftPanel.setBackground(Color.white);
        area2 = new JTextArea(5, 16);
        area2.setEditable(false);
        area2.setLocation(180, 180);
        area2.setLineWrap(true);
        JScrollPane scroll2 = new JScrollPane(area2);
        scroll2.setBorder(null);
        leftPanel.add(scroll2);
        add(leftPanel);
        area2.setText(cutText(index + ".  " + userById.getLogin() + " (" + userById.getFirstName() + ", " + userById.getLastName() + "): \" " + responseList.get(index).getMessage() + "\""));

        rating = new JLabel("User's rating: ");
        rating.setFont(new java.awt.Font("Arial", Font.ITALIC, 12));
        rating.setForeground(Color.BLACK);
        rating.setSize(150, 20);
        rating.setHorizontalAlignment(JTextField.CENTER);
        rating.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        rating.setBackground(new Color(255, 250, 240));
        rating.setLocation(140, 140);
        c.add(rating);

        trating = new JTextField();
        trating.setFont(new Font("Arial", Font.PLAIN, 13));
        trating.setSize(30, 20);
        trating.setBackground(Color.WHITE);
        trating.setEditable(false);
        trating.setText(String.valueOf(responseList.get(index).getMark()));
        trating.setLocation(330, 140);
        c.add(trating);

        from = new JButton("From");
        from.setFont(new Font("Arial", Font.PLAIN, 15));
        from.setSize(70, 20);
        from.setBackground(new Color(255, 250, 240));
        from.setLocation(100, 140);
        from.addActionListener(this);
        c.add(from);

        next = new JButton("Next");
        next.setFont(new Font("Arial", Font.PLAIN, 15));
        next.setSize(100, 20);
        next.setBackground(new Color(253, 224, 195));
        next.setLocation(220, 280);
        next.addActionListener(this);
        c.add(next);

        previous = new JButton("Previous");
        previous.setFont(new Font("Arial", Font.PLAIN, 15));
        previous.setSize(100, 20);
        previous.setBackground(new Color(253, 224, 195));
        previous.setLocation(100, 280);
        previous.addActionListener(this);
        c.add(previous);

        count = new JLabel("' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' '");
        count.setFont(new java.awt.Font("Arial", Font.ITALIC, 12));
        count.setForeground(Color.BLACK);
        count.setSize(350, 20);
        count.setHorizontalAlignment(JTextField.CENTER);
        count.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        count.setBackground(new Color(255, 250, 240));
        count.setLocation(10, 310);
        c.add(count);
        count = new JLabel("Number of users who have evaluated the project: ");
        count.setFont(new java.awt.Font("Arial", Font.ITALIC, 12));
        count.setForeground(Color.BLACK);
        count.setSize(350, 20);
        count.setHorizontalAlignment(JTextField.CENTER);
        count.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        count.setBackground(new Color(255, 250, 240));
        count.setLocation(10, 330);
        c.add(count);

        tcount = new JTextField();
        tcount.setFont(new Font("Arial", Font.PLAIN, 13));
        tcount.setSize(30, 20);
        tcount.setBackground(Color.WHITE);
        tcount.setEditable(false);
        tcount.setText(String.valueOf(responseList.size()));
        tcount.setLocation(330, 329);
        c.add(tcount);

        count = new JLabel("Project average score: ");
        count.setFont(new java.awt.Font("Arial", Font.ITALIC, 12));
        count.setForeground(Color.BLACK);
        count.setSize(200, 20);
        count.setHorizontalAlignment(JTextField.CENTER);
        count.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        count.setBackground(new Color(255, 250, 240));
        count.setLocation(10, 350);
        c.add(count);

        tcount = new JTextField();
        tcount.setFont(new Font("Arial", Font.PLAIN, 13));
        tcount.setSize(30, 20);
        tcount.setBackground(Color.WHITE);
        tcount.setEditable(false);
        int aver = 0;
        try {
            aver = template.getForObject("http://localhost:8080/api/averages", int.class);
        } catch (NullPointerException ignored) {
        }
        tcount.setText(String.valueOf(aver));
        tcount.setLocation(330, 353);
        c.add(tcount);

        ImageIcon ii = new ImageIcon("./data/RatingAdm.png");
        tout = new JLabel(ii);
        tout.setSize(400, 300);
        tout.setLocation(-5, 8);
        c.add(tout);

        setAlwaysOnTop(true);
    }

    private void setSender() {
        userById = template.getForObject(
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

            Service.setProfileSender(userById.getLogin(), userById.getFirstName(),
                    userById.getLastName() + " " + userById.getPatronymic(), "earthquakes@list.ru",
                    userById.getCountry(), userById.getSex(), String.valueOf(userById.getBirthday()), age, false, "sender");
        }
    }

    public static int getAllRates() {
        int res = 0;
        HttpHeaders headerslogin = new HttpHeaders();
        headerslogin.setContentType(MediaType.APPLICATION_JSON);

        String administratorDtos3 = template.getForObject("http://localhost:8080/api/AllratingsE", String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType =
                objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, RatingResponseE.class);

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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == next) {
            getInfo(1);
            setSender();
            area2.setText(cutText(index + ".  " + userById.getLogin() + " (" + userById.getFirstName() + ", " + userById.getLastName() + "): \" " + responseList.get(index).getMessage() + "\""));
            trating.setText(String.valueOf(responseList.get(index).getMark()));

        } else if (e.getSource() == previous) {
            getInfo(-1);
            setSender();
            area2.setText(cutText(index + ".  " + userById.getLogin() + " (" + userById.getFirstName() + ", " + userById.getLastName() + "): \" " + responseList.get(index).getMessage() + "\""));
            trating.setText(String.valueOf(responseList.get(index).getMark()));


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

    private String getMessage(int i) {
        HttpHeaders headerslogin = new HttpHeaders();
        headerslogin.setContentType(MediaType.APPLICATION_JSON);

        String administratorDtos3 = template.getForObject("http://localhost:8080/api/AllsmsE", String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType =
                objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, SmsResponseStr.class);
        try {
            responseList = objectMapper.readValue(administratorDtos3, listType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        userById = template.getForObject(
                "http://localhost:8080/api/clientsE/{id}",
                ClientResponseE.class,
                responseList.get(i).getClientId());
        String res = cutText("\"" + responseList.get(i).getMessage() + "\"");
        return res;
    }

    private String cutText(String text) {
        StringBuilder currentInfo = new StringBuilder();

        String arrWords[] = text.split("[ ]+");
        ArrayList<String> arrPhrases = new ArrayList<String>();

        StringBuilder stringBuffer = new StringBuilder();
        int cnt = 0;
        int index = 0;
        int length = arrWords.length;

        while (index != length) {
            if (cnt + arrWords[index].length() <= 30) {

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
        for (String part : arrPhrases) {
            currentInfo.append(part);
            currentInfo.append("\n");
        }
        currentInfo.append("\n");
        return currentInfo.toString();
    }
}
