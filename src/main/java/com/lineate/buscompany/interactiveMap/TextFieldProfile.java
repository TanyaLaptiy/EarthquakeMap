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

import com.lineate.buscompany.services.MenuService;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import de.fhpotsdam.unfolding.marker.Marker;
import processing.core.PImage;


public class TextFieldProfile extends JFrame implements ActionListener {

    private JPanel photoPanel = new JPanel();

    private Container c;
    private JLabel login, fullName, fullName2, email, Country, Gender, DOB;
    private static boolean isAdmn = false;
    private static boolean isModer = false;

    public TextFieldProfile(String login, String Name, String Name2, String email, String Country, String Gender,
                            String DOB, int age, int index, boolean isAdmn, String status) {

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

        this.login = new JLabel();
        this.login.setFont(new Font("Arial", Font.PLAIN, 15));
        this.login.setText(login);
        this.login.setSize(100, 20);
        this.login.setLocation(50, 130 - 5);
        c.add(this.login);

        this.fullName = new JLabel();
        this.fullName.setFont(new Font("Arial", Font.PLAIN, 15));
        this.fullName.setText(Name);
        this.fullName.setSize(150, 20);
        this.fullName.setLocation(50, 150 - 5 + 20);
        c.add(this.fullName);

        this.fullName2 = new JLabel();
        this.fullName2.setFont(new Font("Arial", Font.PLAIN, 15));
        this.fullName2.setText(Name2);
        this.fullName2.setSize(200, 20);
        this.fullName2.setLocation(50, 150 - 5 + 40);
        c.add(this.fullName2);

        this.email = new JLabel();
        this.email.setFont(new Font("Arial", Font.PLAIN, 15));
        this.email.setText(email);
        this.email.setSize(200, 20);
        this.email.setLocation(100 + 10, 160 + 20 - 7 + 50);
        c.add(this.email);

        this.Country = new JLabel();
        this.Country.setFont(new Font("Arial", Font.PLAIN, 15));
        this.Country.setText(Country);
        this.Country.setSize(100, 20);
        this.Country.setLocation(100 + 20, 160 + 20 - 5 + 30 + 40);
        c.add(this.Country);

        this.Gender = new JLabel();
        this.Gender.setFont(new Font("Arial", Font.PLAIN, 15));
        this.Gender.setText(Gender);
        this.Gender.setSize(100, 20);
        this.Gender.setLocation(100 + 20, 160 + 40 - 5 + 20 + 30 + 20);
        c.add(this.Gender);

        this.DOB = new JLabel();
        this.DOB.setFont(new Font("Arial", Font.PLAIN, 15));
        this.DOB.setText(DOB);
        this.DOB.setSize(100, 20);
        this.DOB.setLocation(100, 160 + 40 - 5 + 20 + 20 + 20 + 30);
        c.add(this.DOB);
        BufferedImage imageBuf = null;
        BufferedImage overlay = null;
        try {

            index++;
            String flag = "C:/Users/1/eclipse-workspace/earthquakes/data/flag" + index + ".png";
            imageBuf = ImageIO.read(new File(flag));
            if (isAdmn) {
                if (Gender.equals("Male")) {
                    if (status.contains("Administrator")) {
                        overlay = ImageIO.read(new File("C:/Users/1/eclipse-workspace/earthquakes/data/menAdmin-PhotoRoom.png"));

                    } else {
                        overlay = ImageIO.read(new File("C:/Users/1/eclipse-workspace/earthquakes/data/menModer-PhotoRoom.png"));
                    }
                } else {
                    if (status.contains("Administrator")) {
                        overlay = ImageIO.read(new File("C:/Users/1/eclipse-workspace/earthquakes/data/womenAdmin-PhotoRoom.png"));

                    } else {
                        overlay = ImageIO.read(new File("C:/Users/1/eclipse-workspace/earthquakes/data/womenModer-PhotoRoom.png"));
                    }
                }
            } else {
                if (Gender.equals("Male")) {
                    if (age <= 18) {
                        overlay = ImageIO.read(new File("C:/Users/1/eclipse-workspace/earthquakes/data/menyoung.png"));
                    } else if (age > 18 && age < 55) {
                        overlay = ImageIO.read(new File("C:/Users/1/eclipse-workspace/earthquakes/data/men.png"));
                    } else {
                        overlay = ImageIO.read(new File("C:/Users/1/eclipse-workspace/earthquakes/data/menold.png"));
                    }
                } else {
                    if (age <= 18) {
                        overlay = ImageIO.read(new File("C:/Users/1/eclipse-workspace/earthquakes/data/womenyoung.png"));
                    } else if (age > 18 && age < 55) {
                        overlay = ImageIO.read(new File("C:/Users/1/eclipse-workspace/earthquakes/data/women.png"));
                    } else {
                        overlay = ImageIO.read(new File("C:/Users/1/eclipse-workspace/earthquakes/data/womenold.png"));
                    }
                }
            }

        } catch (IOException e2) {
            e2.printStackTrace();
        }

        // create the new image, canvas size is the max. of both image sizes
        int w = Math.max(imageBuf.getWidth(), overlay.getWidth());
        int h = Math.max(imageBuf.getHeight(), overlay.getHeight());
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // paint both images, preserving the alpha channels
        Graphics g = combined.getGraphics();
        g.drawImage(imageBuf, 0, 0, null);
        g.drawImage(overlay, 0, 0, null);

        g.dispose();

        // Save as new image
        String path = "./data/combined.png";
        if (status.contains("sender")) {
            path = "./data/combined_sender.png";
        }
        try {

            ImageIO.write(combined, "PNG", new File(path));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        photoPanel.setBounds(195, 88, 63, 85);
        photoPanel.setBackground(new Color(255, 178, 102));
        ImageIcon photo = new ImageIcon(path);
        Image img = photo.getImage();
        Image newimg = img.getScaledInstance(65, 90, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        photo = new ImageIcon(newimg);
        JLabel pic = new JLabel(photo);
        pic.setSize(50, 50);
        photoPanel.add(pic);
        add(photoPanel);

        if (!status.contains("sender")) {
            if (status.contains("Administrator")) {
                this.isAdmn = isAdmn;
            } else {
                this.isModer = isAdmn;
            }

        }
        Service.setinSystem();
        Service.setCurrentToken();
        System.out.println("\n\n\n  Set curr token!  " + Service.getToken());
        MenuService.getInstance().setProfileLogo();

        ImageIcon ii = new ImageIcon("./data/profile.png");
        JLabel tout = new JLabel(ii);
        tout.setSize(300, 400);
        tout.setLocation(0, 0);
        c.add(tout);

        setAlwaysOnTop(true);
        setVisible(false);

    }

    public void actionPerformed(ActionEvent e) {

    }

    public static boolean isAdmn() {
        return isAdmn;
    }

    public static boolean isModer() {
        return isModer;
    }

    public static void notAdmn() {
        isAdmn = false;
        isModer = false;
    }

    public void showRegisterPanel() {
        setVisible(true);

    }


}
