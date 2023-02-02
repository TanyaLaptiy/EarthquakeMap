package com.lineate.buscompany.interactiveMap;

import java.awt.Color;
import java.awt.Cursor;
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
import java.util.ArrayList;
import java.util.List;

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
import javax.swing.UIManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TextFieldCalendar extends JFrame implements ITextField {
	private static JButton b1, b2;
	private int size;
	private int currentPossition = 0;

	public TextFieldCalendar() {
		super("Today in Earthquake History");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image imageM = toolkit.getImage("./data/mouse7.png");
		Cursor c = toolkit.createCustomCursor(imageM, new Point(getX(), getY()), "img");
		setCursor(c);

		StringBuilder info = new StringBuilder();
		StringBuilder subTitle = new StringBuilder();

		List<String> h4 = new ArrayList<>();
		List<String> p = new ArrayList<>();

		JTextField tittleField = new JTextField("Today in Earthquake History");
		tittleField.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 12));
		tittleField.setForeground(Color.BLACK);
		tittleField.setEditable(false);
		tittleField.setHorizontalAlignment(JTextField.CENTER);
		JTextField textField = null;

		try {
			Document doc = Jsoup.connect("https://earthquake.usgs.gov/learn/today/index.php").get();

			textField = new JTextField(doc.getElementsByTag("h2").first().text());
			textField.setFont(new java.awt.Font("Arial", Font.ITALIC | Font.BOLD, 12));
			textField.setForeground(Color.RED);
			textField.setEditable(false);
			textField.setHorizontalAlignment(JTextField.CENTER);
			textField.setBorder(javax.swing.BorderFactory.createEmptyBorder());

			Elements paragraphs = doc.getElementsByTag("h4");
			for (Element paragraph : paragraphs) {
				h4.add(paragraph.text());
			}
			Elements paragraphs2 = doc.getElementsByTag("li");
			StringBuilder s=new StringBuilder(); 
			for (Element paragraph : paragraphs2) {
				s=new StringBuilder(); 
				Elements paragraphs3 = paragraph.getElementsByTag("p");

				for(Element secondParagraph : paragraphs3) {
					s.append(secondParagraph.text()+"\n");
				} 
				p.add(s.toString());

			}
			for (int i = 0; i < h4.size(); i++) {
				List<String> subTitles = new ArrayList<>();
				for (String str : h4.get(i).split("-|,")) {
					subTitles.add(str);
				}
				info.append("\t " + subTitles.get(subTitles.size() - 1));
				info.append("\n");
				info.append("\n");
				for (int index = 1; index < subTitles.size() - 1; index++) {
					subTitle.append(subTitles.get(index) + ", ");
				}
				info.append(cutText(subTitle.toString()));
				subTitle = new StringBuilder();
				info.append("\n");
				info.append("\t " + subTitles.get(0));
				info.append("\n");
				info.append("   " + cutText(p.get(i)));
				info.append(">");
				size++;
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String[] earthQuaqes = info.toString().split(">");

		b1 = new JButton("Next");
		b2 = new JButton("Previous");

		JTextArea area2 = new JTextArea(15, 20);
		area2.setEditable(false);

		area2.setText((earthQuaqes[currentPossition]));

		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getInfo(1);
				area2.setText((earthQuaqes[currentPossition]));
			}
		});
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getInfo(-1);
				area2.setText((earthQuaqes[currentPossition]));
			}
		});

		JPanel contents = new JPanel();

		ImageIcon image = new ImageIcon("./data/calendar.png");
		JLabel pic = new JLabel(image);
		tittleField.setBackground(new Color(255, 250, 240));

		textField.setBackground(new Color(255, 250, 240));
		contents.add(tittleField);

		contents.add(textField);

		contents.add(pic);

		contents.add(new JScrollPane(area2));
		contents.add(b2);

		contents.add(b1);
		setContentPane(contents);
		contents.setBackground(new Color(255, 250, 240));

		setSize(270, 500);
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
			currentInfo.append(part);
			currentInfo.append("\n  ");

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

	public void showDisclaimer() {

		UIManager.put("OptionPane.background", new Color(255, 250, 240));
		UIManager.put("Panel.background", new Color(255, 250, 240));
		JOptionPane.showMessageDialog(TextFieldCalendar.this,
				"\t                                Disclaimer\r\n"
						+ "All dates and times are displayed in UTC and not your local time\n "
						+ "or the local time near the epicenter. \n"
						+ "Also, the history displayed on this page defaults to the current date at UTC; \n"
						+ "please take this into consideration if you view this page and the day \n"
						+ "is either ahead or behind your local date.",
				"Coordinated Universal Time", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./data/UTC-PhotoRoom.png"));

	}
}
