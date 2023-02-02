package com.lineate.buscompany.interactiveMap;

import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;

public class SplashScreenMap {
    JDialog frame;
    JLabel image = new JLabel(new ImageIcon("./data/loader.gif"));

    JLabel text = new JLabel("Earthquake and Air Travel Map");
    JProgressBar progressBar = new JProgressBar();
    JLabel message = new JLabel();// Crating a JLabel for displaying the message

    public SplashScreenMap() {

        createGUI();
        addImage();
        addText();
        addProgressBar();
        addMessage();
        runningPBar();

    }

    public void createGUI() {
        frame = new JDialog();
        frame.setAlwaysOnTop(true);

        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setLayout(null);
        frame.setModal(false);
        frame.setUndecorated(true);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);
        frame.setVisible(true);

    }

    public void addImage() {
        image.setSize(600, 200);
        frame.add(image);
    }

    public void addText() {
        text.setFont(new Font("serif", Font.BOLD, 30));
        text.setBounds(100, 220, 600, 40);
        text.setForeground(new Color(204, 102, 0));
        frame.add(text);
    }

    public void addMessage() {
        message.setBounds(250, 320, 200, 40);// Setting the size and location of the label
        message.setForeground(new Color(64, 192, 255));// Setting foreground Color
        message.setFont(new Font("arial", Font.BOLD, 15));// Setting font properties
        frame.add(message);// adding label to the frame
    }

    public void addProgressBar() {
        progressBar.setBounds(100, 280, 400, 30);

        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        progressBar.setBackground(Color.WHITE);
        progressBar.setForeground(new Color(204, 102, 0));
        progressBar.setValue(0);
        frame.add(progressBar);
    }

    public void runningPBar() {
        int i = 0;

        while (i <= 100) {
            try {
                progressBar.setValue(i);
                message.setText("LOADING " + Integer.toString(i) + "%");
                i++;
                if (i == 100) {

                    Thread.sleep(30000);
                    frame.dispose();

                }
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
