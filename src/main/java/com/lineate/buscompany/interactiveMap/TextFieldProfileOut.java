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
import java.io.*;
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

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.springframework.web.client.RestTemplate;


public class TextFieldProfileOut extends JFrame implements ActionListener {

    JPanel photoPanel = new JPanel();
    private static String token;
    private Container c;

    public TextFieldProfileOut() {

        setTitle("Profile");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("./data/mouse7.png");
        Cursor cur = toolkit.createCustomCursor(image, new Point(getX(), getY()), "img");
        setCursor(cur);

        setBounds(300, 90, 320, 440);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(255, 250, 240));

        photoPanel.setBounds(195, 88, 63, 85);
        photoPanel.setBackground(new Color(255, 178, 102));
        ImageIcon photo = new ImageIcon("./data/combined.png");
        Image img = photo.getImage();
        Image newimg = img.getScaledInstance(65, 90, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        photo = new ImageIcon(newimg);
        JLabel pic = new JLabel(photo);
        pic.setSize(50, 50);
        photoPanel.add(pic);
        add(photoPanel);
        RestTemplate template = new RestTemplate();

        String currentLine="";
        String file = "./data/tokenfile.txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
             currentLine = reader.readLine();
            reader.close();

            template.delete(
                    "http://localhost:8080/api/clientsE/logout/{token}",
                    currentLine);

            BufferedWriter writer = new BufferedWriter(new FileWriter("./data/tokenfile.txt"));
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        ImageIcon ii = new ImageIcon("./data/profileOut.png");
        JLabel tout = new JLabel(ii);
        tout.setSize(300, 400);
        tout.setLocation(0, 0);
        c.add(tout);

        setAlwaysOnTop(true);
        setVisible(false);

    }


    public void actionPerformed(ActionEvent e) {

    }

    public void showRegisterPanel() {
        setVisible(true);

    }

}
