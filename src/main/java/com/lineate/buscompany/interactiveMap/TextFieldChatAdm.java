package com.lineate.buscompany.interactiveMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.lineate.buscompany.dtoE.requestE.SmsRequest;
import com.lineate.buscompany.dtoE.responseE.ClientResponseE;
import com.lineate.buscompany.dtoE.responseE.SessionResponseE;
import com.lineate.buscompany.dtoE.responseE.SmsResponse;
import com.lineate.buscompany.dtoE.responseE.SmsResponseStr;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TextFieldChatAdm extends JFrame implements ActionListener {

    JPanel rightPanel = new JPanel();
    JPanel leftPanel = new JPanel();
    JPanel photoPanel = new JPanel();
    JTextArea area2;

    private Container c;
    private static RestTemplate template = new RestTemplate();
    JTextArea area;
    private JButton next, delete,prev;
    private JLabel tout;
    List<SmsResponseStr> responseList = null;
    ClientResponseE userById;
    int index = 0;


    public TextFieldChatAdm() {

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

        area.setEditable(false);
        area.setText(getAllMessage().toString());
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
        area2.setEditable(false);
        area2.setText(getMessage(index).toString());
        area2.setBorder(null);
        area2.setLocation(65, 100);
        area2.setLineWrap(true);
        JScrollPane scroll2 = new JScrollPane(area2);
        scroll2.setBorder(null);
        leftPanel.add(scroll2);
        add(leftPanel);

        next = new JButton("Next");
        next.setFont(new Font("Arial", Font.PLAIN, 15));
        next.setSize(100, 20);
        next.setBackground(new Color(253, 224, 195));
        next.setLocation(450 + 20 + 20 + 70, 570 + 70 + 55);
        next.addActionListener(this);
        c.add(next);

        delete = new JButton("Delete");
        delete.setFont(new Font("Arial", Font.PLAIN, 15));
        delete.setSize(100, 20);
        delete.setBackground(new Color(253, 224, 195));
        delete.setLocation(450, 570 + 70 + 55);
        delete.addActionListener(this);
        c.add(delete);

        prev = new JButton("Previous");
        prev.setFont(new Font("Arial", Font.PLAIN, 15));
        prev.setSize(100, 20);
        prev.setBackground(new Color(253, 224, 195));
        prev.setLocation(340, 570 + 70 + 55);
        prev.addActionListener(this);
        c.add(prev);


        ImageIcon ii = new ImageIcon("./data/chatt.png");
        tout = new JLabel(ii);
        tout.setSize(700, 750);
        tout.setLocation(0, 00);
        c.add(tout);

        photoPanel.setBounds(10, 582, 100, 110);
        photoPanel.setBackground(Color.white);
        ImageIcon photo = new ImageIcon("./data/combined.png");
        Image img = photo.getImage();
        Image newimg = img.getScaledInstance(85, 100, Image.SCALE_SMOOTH);
        photo = new ImageIcon(newimg);
        JLabel pic = new JLabel(photo);
        pic.setSize(50, 50);
        photoPanel.add(pic);
        add(photoPanel);

        setAlwaysOnTop(true);
        setVisible(false);

    }
    private String cutCurrentText(String user, String text,String data) {
        StringBuilder currentInfo = new StringBuilder();

        String arrWords[] = text.split("[ ]+");
        ArrayList<String> arrPhrases = new ArrayList<String>();

        StringBuilder stringBuffer = new StringBuilder();
        int cnt = 0;
        int index = 0;
        int length = arrWords.length;

        while (index != length) {
            if (cnt + arrWords[index].length() <= 75) {

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
        currentInfo.append(" " + user + ":\n\n");
        for (String part : arrPhrases) {
            currentInfo.append(part);

        }
        currentInfo.append("\n\n" +data);
        return currentInfo.toString();

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
        String res = cutCurrentText(userById.getLogin()+" wrote", "\""+responseList.get(i).getMessage()+"\"" , "on "+responseList.get(i).getData().toString() );
        return res;
    }

    private String getAllMessage() {
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
            getInfo(1);
            area.setText(getAllMessage().toString());
            area2.setText(getMessage(index).toString());

        } else if (e.getSource() == delete) {
            template.delete(
                    "http://localhost:8080/api/smsE/{id}",
                    responseList.get(index).getId());
            area.setText(getAllMessage().toString());
            area2.setText(getMessage(index).toString());

        }else if (e.getSource() == prev) {
            getInfo(-1);
            area.setText(getAllMessage().toString());
            area2.setText(getMessage(index).toString());
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

    public void setPhoto() {
        photoPanel.setBounds(20, 595, 80, 90);
        photoPanel.setBackground(Color.white);
        ImageIcon photo = new ImageIcon("./data/combined.png");
        Image img = photo.getImage();
        Image newimg = img.getScaledInstance(70, 80, Image.SCALE_SMOOTH);
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
