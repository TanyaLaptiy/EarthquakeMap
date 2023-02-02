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
import java.util.*;
import java.time.Year;
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

import com.lineate.buscompany.dtoE.requestE.RequestRequest;
import com.lineate.buscompany.dtoE.responseE.RequestResponse;
import com.lineate.buscompany.dtoE.responseE.SessionResponseE;
import com.lineate.buscompany.services.AirportService;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import de.fhpotsdam.unfolding.marker.Marker;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class TextFieldRequest extends JFrame implements ActionListener {
	private RestTemplate template = new RestTemplate();

	JPanel rightPanel = new JPanel();
	EarthMap chinaMap = new EarthMap();
	private Container c;
	private JLabel title, title2;
	private JLabel latitude;
	private static JTextField tlat;
	private JLabel longiude;
	private static JTextField tlon;
	private JLabel depth;
	private JLabel country;
	private JTextField tdepth;
	private JLabel magnitude;
	private JTextField tmag;
	private JLabel radius;
	private JTextField trad;
	private static JTextField loc = new JTextField();
	private ButtonGroup gengp = new ButtonGroup();
	private JDatePickerImpl datePicker;
	private static JComboBox tcountry2;
	private static JComboBox points;
	JRadioButton underwater;
	JCheckBox term;
	private JButton log;
	private JLabel tout;
	private JLabel today;
	private List<String> curList;
	private List<String> ciyList = new ArrayList<>();
	private String compas[] = { "N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW",
			"NW", "NNW" };


	public TextFieldRequest(List<Marker> airportList, List<Marker> cityMarkers) {

		setTitle("Registration Form");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("./data/mouse7.png");
		Cursor cur = toolkit.createCustomCursor(image, new Point(getX(), getY()), "img");
		setCursor(cur);

		setBounds(300, 90, 760, 680);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);

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

		for (Marker r : AirportService.getInstance().getAirportList()) {
			ciyList.add(r.getStringProperty("city").toString() + "; " + r.getStringProperty("country").toString());

		}
		List<String> ciyList2 = ciyList.stream().distinct().sorted().collect(Collectors.toList());
		String[] cities = new String[ciyList2.size()];
		int iin = 0;
		for (String country : ciyList2) {
			cities[iin] = country;
			iin++;
		}

		rightPanel.setBounds(500 - 10, 95 - 2, 200, 230);
		chinaMap.setPreferredSize(new Dimension(200, 230));
		chinaMap.init();
		chinaMap.start();
		rightPanel.add(chinaMap);
		rightPanel.setForeground(new Color(215, 215, 215));

		add(rightPanel);

		c = getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(255, 250, 240));

		title = new JLabel("Request to add an earthquake marker");
		title.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 15));
		title.setForeground(Color.RED);
		title.setSize(300, 30);
		title.setLocation(200, 30 - 20);

		c.add(title);

		title2 = new JLabel("to the map");
		title2.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 15));
		title2.setForeground(Color.RED);
		title2.setSize(300, 30);
		title2.setLocation(300, 30);

		c.add(title2);

		JLabel message = new JLabel("   Enter  coordinates,  or  point  on  the  map:");
		message.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 15));
		message.setForeground(new Color(64, 192, 255));
		message.setSize(400, 20);
		message.setLocation(100, 160 - 20 - 10 - 10 - 10);
		c.add(message);

		latitude = new JLabel("Latitude");
		latitude.setFont(new Font("Arial", Font.PLAIN, 15));
		latitude.setSize(100, 20);
		latitude.setLocation(100 - 20, 160 - 20);
		c.add(latitude);

		tlat = new JTextField();
		tlat.setFont(new Font("Arial", Font.PLAIN, 13));
		tlat.setSize(80, 20);
		tlat.setLocation(200 - 45, 160 - 20);
		c.add(tlat);

		longiude = new JLabel("Longiude");
		longiude.setFont(new Font("Arial", Font.PLAIN, 15));
		longiude.setSize(100, 20);
		longiude.setLocation(100 - 20, 190 - 20 - 10 + 5);
		c.add(longiude);

		tlon = new JTextField();
		tlon.setFont(new Font("Arial", Font.PLAIN, 13));
		tlon.setSize(80, 20);
		tlon.setLocation(200 - 40, 190 - 20 - 10 + 5);
		c.add(tlon);

		depth = new JLabel("Depth");
		depth.setFont(new Font("Arial", Font.PLAIN, 15));
		depth.setSize(100, 20);
		depth.setLocation(100 - 20, 220 - 10);
		c.add(depth);

		tdepth = new JTextField();
		tdepth.setFont(new Font("Arial", Font.PLAIN, 13));
		tdepth.setSize(80, 20);
		tdepth.setLocation(130, 220 - 10);
		c.add(tdepth);

		magnitude = new JLabel("Magnitude");
		magnitude.setFont(new Font("Arial", Font.PLAIN, 15));
		magnitude.setSize(100, 20);
		magnitude.setLocation(245, 220 - 10);
		c.add(magnitude);


		tmag = new JTextField();
		tmag.setFont(new Font("Arial", Font.PLAIN, 13));
		tmag.setSize(80, 20);
		tmag.setLocation(320, 220 - 10);
		c.add(tmag);

		JLabel message2 = new JLabel("   Did  the  earthquake  happen  today?");
		message2.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 15));
		message2.setForeground(new Color(64, 192, 255));
		message2.setSize(800, 20);
		message2.setLocation(100, 240 + 20);
		c.add(message2);

		JLabel message3 = new JLabel("If  today,  then  fill  in  the  \"Today\"  field,\r\n");
		message3.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 15));
		message3.setForeground(new Color(64, 192, 255));
		message3.setSize(800, 20);
		message3.setLocation(100, 260 + 20);
		c.add(message3);

		JLabel message4 = new JLabel("otherwise  fill  in  the  \"Date\"  field.\r\n");
		message4.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 15));
		message4.setForeground(new Color(64, 192, 255));
		message4.setSize(800, 20);
		message4.setLocation(100, 280 + 20);
		c.add(message4);
		Date d = new Date();
		LocalDate localDate=LocalDate.now();
		UtilDateModel model = new UtilDateModel();
		model.setSelected(true);
		model.setDate(localDate.getYear(), localDate.getMonth().getValue()-1, localDate.getDayOfMonth());
		// Need this...
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");

		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		 datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

		JPanel contents = new JPanel();
		contents.setBackground(new Color(255, 250, 240));
		contents.setBounds(120, 330, 200, 30);
		contents.add(datePicker);
		add(contents);

		JLabel data = new JLabel("Data: ");
		data.setFont(new Font("Arial", Font.PLAIN, 15));
		data.setSize(100, 20);
		data.setLocation(100 - 20, 310 + 30);
		c.add(data);

		 today = new JLabel("Today: ");
		today.setFont(new Font("Arial", Font.PLAIN, 15));
		today.setSize(100, 20);
		today.setLocation(330, 310 + 30);
		c.add(today);

		 term = new JCheckBox("Past Hour");
		term.setBackground(new Color(255, 250, 240));
		term.setFont(new Font("Arial", Font.PLAIN, 15));
		term.setSize(250, 20);
		term.setLocation(380, 310 + 30);
		c.add(term);
		term.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setSelected(true);
				model.setDate(localDate.getYear(), localDate.getMonth().getValue()-1, localDate.getDayOfMonth());

			}
		});

		country = new JLabel("Earthquake type: ");
		country.setFont(new Font("Arial", Font.PLAIN, 15));
		country.setSize(200, 20);
		country.setLocation(390, 410);
		c.add(country);

		underwater = new JRadioButton("Underwater earthquake");
		underwater.setBackground(new Color(255, 250, 240));
		underwater.setFont(new Font("Arial", Font.PLAIN, 15));
		underwater.setSize(180, 20);
		underwater.setLocation(420, 430);
		c.add(underwater);
		JRadioButton ground = new JRadioButton("Earthquake on ground");
		ground.setBackground(new Color(255, 250, 240));
		ground.setFont(new Font("Arial", Font.PLAIN, 15));
		ground.setSize(170, 20);
		ground.setLocation(420, 450);
		c.add(ground);

		gengp.add(underwater);
		gengp.add(ground);

		JLabel location = new JLabel("Location: ");
		location.setFont(new Font("Arial", Font.PLAIN, 15));
		location.setSize(100, 20);

		location.setLocation(390, 500);
		c.add(location);

		loc.setFont(new Font("Arial", Font.PLAIN, 13));
		loc.setSize(40, 20);
		loc.setLocation(390, 530);
		loc.setOpaque(true);
		loc.setBackground(Color.WHITE);
		c.add(loc);

		JLabel km = new JLabel("km ");
		km.setFont(new Font("Arial", Font.PLAIN, 15));
		km.setOpaque(true);
		km.setBackground(Color.WHITE);
		km.setSize(40, 20);
		km.setLocation(430, 530);
		c.add(km);

		points = new JComboBox(compas);
		points.setFont(new Font("Arial", Font.PLAIN, 15));
		points.setSize(70, 20);
		points.setBackground(Color.WHITE);
		points.setLocation(520 - 50, 530);
		c.add(points);

		tcountry2 = new JComboBox(cities);
		tcountry2.setFont(new Font("Arial", Font.PLAIN, 15));
		tcountry2.setSize(230, 20);
		tcountry2.setBackground(Color.WHITE);
		tcountry2.setLocation(410, 560);
		c.add(tcountry2);

		JLabel of = new JLabel("of ");
		of.setFont(new Font("Arial", Font.PLAIN, 15));
		of.setOpaque(true);
		of.setSize(25, 20);

		of.setBackground(Color.WHITE);
		of.setLocation(390, 560);
		c.add(of);

		log = new JButton("Send ~>");
		log.setFont(new Font("Arial", Font.PLAIN, 15));
		log.setSize(100, 20);
		log.setBackground(new Color(253, 224, 195));
		log.setLocation(580, 590);
		log.addActionListener(this);
		c.add(log);

		ImageIcon ii = new ImageIcon("./data/Request.png");
		tout = new JLabel(ii);
		tout.setSize(700, 600);
		tout.setLocation(50, 50);
		c.add(tout);

		setAlwaysOnTop(true);
		setVisible(false);

	}

	public static void setloc(int km) {
		loc.setText(String.valueOf(km));
	}

	public static void setloc(String loc) {
		tcountry2.setSelectedItem(loc);
	}

	public static void setDirection(String loc) {
		points.setSelectedItem(loc);
	}

	public static void settlat(float lat) {
		tlat.setText(String.valueOf(lat));
	}

	public static void settlon(float lon) {
		tlon.setText(String.valueOf(lon));
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == log) {
			boolean isOk = true;
			try {
				Float.parseFloat(tlat.getText());
			} catch (Exception err) {
				tlat.setForeground(Color.RED);
				isOk = false;
			}
			if (tlat.getText().isEmpty() || tlat.getText() == null) {
				tlat.setText("  !");
				tlat.setForeground(Color.RED);
			}

			try {
				Float.parseFloat(tlon.getText());
			} catch (Exception err) {
				tlon.setForeground(Color.RED);
				isOk = false;
			}
			if (tlon.getText().isEmpty() || tlon.getText() == null) {
				tlon.setText("  !");
				tlon.setForeground(Color.RED);
			}

			try {// tdepth
				Float.parseFloat(tdepth.getText());
			} catch (Exception err) {
				tdepth.setForeground(Color.RED);
				isOk = false;
			}
			if (tdepth.getText().isEmpty() || tdepth.getText() == null) {
				tdepth.setText("  !");
				tdepth.setForeground(Color.RED);
			}

			try {
				Float.parseFloat(tmag.getText());
			} catch (Exception err) {
				tmag.setForeground(Color.RED);
				isOk = false;
			}
			if (tmag.getText().isEmpty() || tmag.getText() == null) {
				tmag.setText("  !");
				tmag.setForeground(Color.RED);
			}

			try {
				Float.parseFloat(loc.getText());
			} catch (Exception err) {
				loc.setForeground(Color.RED);
				isOk = false;
			}
			if (loc.getText().isEmpty() || loc.getText() == null) {
				loc.setText("  !");
				loc.setForeground(Color.RED);
			}
			if (gengp.getSelection() == null) {
				country.setForeground(Color.RED);
			}

			else if (isOk) {
				Service.setCurrentToken();
				SessionResponseE sessionResponseE2 = template.getForObject(
						"http://localhost:8080/api/sessionsE/token/{token}",
						SessionResponseE.class,
						Service.getToken());

				int month=datePicker.getJDateInstantPanel().getModel().getMonth() + 1;
				LocalDate currentdate = LocalDate.of(datePicker.getJDateInstantPanel().getModel().getYear(),month,datePicker.getJDateInstantPanel().getModel().getDay());

				String land;
				if(underwater.isSelected()){
					land="water";
				}else{
					land="onland";
				}

				String title=loc.getText().toString()+" km "+points.getSelectedItem().toString()+" of "+tcountry2.getSelectedItem().toString();
				RequestRequest request1 = new RequestRequest(sessionResponseE2.getUserId(),
						currentdate,
						title,
						"message",
						Double.parseDouble(tlat.getText()),
						Double.parseDouble(tlon.getText()),
						Double.parseDouble(tmag.getText()),
						Double.parseDouble(tdepth.getText()),
						term.getText(),
						land,
						Objects.requireNonNull(tcountry2.getSelectedItem()).toString().substring(tcountry2.getSelectedItem().toString().indexOf(';')+3,tcountry2.getSelectedItem().toString().length()-1)
				);

				System.out.println("\n\n               "+request1);
				System.out.println("\n\n\n          "+tcountry2.getSelectedItem().toString().substring(tcountry2.getSelectedItem().toString().indexOf(';')+3,tcountry2.getSelectedItem().toString().length()-1)
				);
				HttpHeaders headersRequest = new HttpHeaders();
				headersRequest.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<Object> requestRequest = new HttpEntity<>(request1, headersRequest);
				RequestResponse requestResponse = template.postForObject("http://localhost:8080/api/requestsE", requestRequest, RequestResponse.class);

				JOptionPane.showMessageDialog(TextFieldRequest.this, "Your email has been successfully sent", "Message",
						JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./data/email-PhotoRoom.png"));

				dispose();
			}

		}

	}

	public void showRegisterPanel() {
		setVisible(true);

	}

}
