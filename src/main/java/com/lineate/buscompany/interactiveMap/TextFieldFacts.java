package com.lineate.buscompany.interactiveMap;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class TextFieldFacts extends JFrame implements ITextField {
    static JButton b1, b2;
    int size;
    int currentPossition = 0;

    int rows = 0;

    public TextFieldFacts() {
        super("Cool Earthquake Facts");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image imageM = toolkit.getImage("./data/mouse7.png");
        Cursor c = toolkit.createCustomCursor(imageM, new Point(getX(),
                getY()), "img");
        setCursor(c);

        String res = null;
        try {
            res = new String(Files.readAllBytes(Paths.get("./data/Cool_Earthquake_Facts.txt")));

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        String[] columns = res.split(">");
        size = columns.length;

        JTextField tittleField = new JTextField("                     Cool Earthquake Facts                     ");
        tittleField.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 12));
        tittleField.setForeground(Color.BLACK);
        tittleField.setEditable(false);
        tittleField.setHorizontalAlignment(JTextField.CENTER);
        JTextField textField = new JTextField("         Find some interesting facts about earthquakes.                    ");
        textField.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 12));
        textField.setForeground(Color.BLACK);
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        b1 = new JButton("Next");
        b2 = new JButton("Previous");

        JTextArea area2 = new JTextArea(15, 20);
        area2.setEditable(false);

        area2.setText(cutText(columns[currentPossition]));

        ImageIcon ii = new ImageIcon("./data/history1.png");
        String filename = "./data/Facts-PhotoRoom.png";
        JLabel label = new JLabel(ii);

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getInfo(1);
                area2.setText(cutText(columns[currentPossition]));

                label.setEnabled(false);
                int index = currentPossition + 1;
                ImageIcon i = new ImageIcon("./data/history" + index + ".png");
                label.setDisabledIcon(i);
            }
        });

        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getInfo(-1);
                area2.setText(cutText(columns[currentPossition]));

                label.setEnabled(false);
                int index = currentPossition + 1;
                ImageIcon i = new ImageIcon("./data/history" + index + ".png");
                label.setDisabledIcon(i);
            }
        });

        JPanel contents = new JPanel();

        ImageIcon image = new ImageIcon(filename);
        JLabel pic = new JLabel(image);

        tittleField.setBackground(new Color(255, 250, 240));
        textField.setBackground(new Color(255, 250, 240));

        contents.add(tittleField);
        contents.add(textField);

        contents.add(pic);

        contents.add(new JScrollPane(area2,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));


        JScrollPane pictureScrollPane = new JScrollPane(label,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pictureScrollPane.setPreferredSize(new Dimension(200, 249));
        pictureScrollPane.getViewport().setBackground(Color.WHITE);

        contents.add(pictureScrollPane);

        contents.add(b2);

        contents.add(b1);
        setContentPane(contents);
        contents.setBackground(new Color(255, 250, 240));

        setSize(460, 465);
        setResizable(false);
        setAlwaysOnTop(true);
        setVisible(false);
    }

    private void getInfo(int num) {
        currentPossition += num;
        if (currentPossition >= size) {
            currentPossition = 0;
        }
        if (currentPossition < 0) {
            currentPossition = size - 1;
        }
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
            currentInfo.append("  " + part);
            currentInfo.append("\n");

        }

        return currentInfo.toString();

    }

    @Override
    public void showTextPanel() {
        setVisible(true);

    }

    @Override
    public void hideTextPanel() {
        setVisible(false);

    }

}
