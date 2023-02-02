package com.lineate.buscompany.parsing;

import java.awt.Dimension;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.lineate.buscompany.dto.AirportMarkerRequest;
import com.lineate.buscompany.dtoE.requestE.EarthquakeRequest;
import com.lineate.buscompany.dtoE.responseE.ClientResponseE;
import com.lineate.buscompany.dtoE.responseE.EarthquakeResponse;
import com.lineate.buscompany.dtoE.responseE.EarthquakeResponseStr;
import com.lineate.buscompany.model.*;
import com.lineate.buscompany.services.AirportService;
import com.lineate.buscompany.services.CityService;
import com.lineate.buscompany.services.EarthquakeService;
import com.lineate.buscompany.services.MenuService;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;


public class ProcessingParse extends PApplet {

    private Map<String, PImage> cityLogo = new HashMap<>();
    private interactiveMapDAO parseDAO = new Parser();
    private static PImage profileLogo;
    private RestTemplate template = new RestTemplate();

    int count = 0;

    public void parseEatrhBD() {
        PImage img = loadImage("C:/Users/1/eclipse-workspace/earthquakes/data/clock-benzin-preview.png");
          LocalDate date = LocalDate.now();
        /////////////////
         LocalDate yesterdaydate=LocalDate.of(date.getYear(),date.getMonth(),date.getDayOfMonth()-1);
        ////////////
        System.out.println("\n\n yesterday "+yesterdaydate);

        List<PointFeature> earthquakes2 = parseDAO.parseEarthquakeToday(this);
        //EarthquakeService.setQuakeMarkers(new ArrayList<Marker>());
        EarthquakeRequest request1;
        for (PointFeature feature : earthquakes2) {
            if (isLand(feature)) {
                LandQuakeMarker mark = new LandQuakeMarker(feature, img);

                java.util.HashMap<String, Object> properties = mark.getProperties();

                if ("Past Hour".equals((String) properties.get("age")) || "Past Day".equals((String) properties.get("age"))) {
                    request1 = new EarthquakeRequest(
                            mark.getLocation().getLat(),
                            mark.getLocation().getLon(),
                            mark.getMagnitude(),
                            (String) properties.get("title"),
                            (String) properties.get("age"),
                            mark.getDepth(),
                            "onland",
                            mark.getCountry(),
                            yesterdaydate);

                    count += 1;

                    HttpHeaders headersRequest = new HttpHeaders();
                    headersRequest.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<Object> requestRequest = new HttpEntity<>(request1, headersRequest);
                    EarthquakeResponse earthquakeResponse = template.postForObject("http://localhost:8080/api/earthquakeE", requestRequest, EarthquakeResponse.class);
                }


                //EarthquakeService.addQuakeMarker(new LandQuakeMarker(feature, img));
            } else {
                OceanQuakeMarker mark = new OceanQuakeMarker(feature, img);
                java.util.HashMap<String, Object> properties = mark.getProperties();
                if ("Past Hour".equals((String) properties.get("age")) || "Past Day".equals((String) properties.get("age"))) {
                    request1 = new EarthquakeRequest(
                            mark.getLocation().getLat(),
                            mark.getLocation().getLon(),
                            mark.getMagnitude(),
                            (String) properties.get("title"),
                            (String) properties.get("age"),
                            mark.getDepth(),
                            "water",
                            "no",
                            yesterdaydate);
                    count += 1;

                    HttpHeaders headersRequest = new HttpHeaders();
                    headersRequest.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<Object> requestRequest = new HttpEntity<>(request1, headersRequest);
                    EarthquakeResponse earthquakeResponse = template.postForObject("http://localhost:8080/api/earthquakeE", requestRequest, EarthquakeResponse.class);
                }

                //EarthquakeService.addQuakeMarker(new OceanQuakeMarker(feature, img));
            }

        }
        System.out.println("*  " + count);
    }

    public void parseEarthquakesByDate(LocalDate localDate) {
        PImage img = loadImage("C:/Users/1/eclipse-workspace/earthquakes/data/clock-benzin-preview.png");
        EarthquakeService.setQuakeMarkers(new ArrayList<Marker>());
        HttpHeaders headerslogin = new HttpHeaders();
        headerslogin.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestlogin = new HttpEntity<>(localDate, headerslogin);
        String administratorDtos3 = template.postForObject("http://localhost:8080/api/earthquakeE/data", requestlogin, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType =
                objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, EarthquakeResponseStr.class);
//        List<EarthquakeResponseStr> bookList = objectMapper.readValue(responseListByData3, listType);
//        CollectionType listType =
//                objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, EarthquakeResponse.class);
        List<EarthquakeResponseStr> bookList = null;
        try {
            bookList = objectMapper.readValue(administratorDtos3, listType);
            System.out.println("\n\n\n bookList: "+bookList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }



        for (EarthquakeResponseStr feature : bookList) {
            if (feature.getOnland().equals("onland")) {
                //float latitude, float longitude, float magnitude, String location, String age, float depth, PImage logo
                EarthquakeService.addQuakeMarker(new LandQuakeMarker((float)feature.getLatitude(),(float)feature.getLongitude(),(float)feature.getMagnitude(),feature.getLocation(),feature.getAge(),(float)feature.getDepth(),feature.getCountry(), img));

            } else {
                EarthquakeService.addQuakeMarker(new OceanQuakeMarker((float)feature.getLatitude(),(float)feature.getLongitude(),(float)feature.getMagnitude(),feature.getLocation(),feature.getAge(),(float)feature.getDepth(),feature.getCountry(), img));
            }
        }


    }

    public void parseEarthquakes(boolean offline, boolean today) {
        PImage img = loadImage("C:/Users/1/eclipse-workspace/earthquakes/data/clock-benzin-preview.png");

        if (!today) {
            System.out.println("\n\n not today");

//


            List<PointFeature> earthquakes = parseDAO.parseEarthquake(this, offline);
            EarthquakeService.setQuakeMarkers(new ArrayList<Marker>());

            for (PointFeature feature : earthquakes) {
                if (isLand(feature)) {
                    EarthquakeService.addQuakeMarker(new LandQuakeMarker(feature, img));

                } else {
                    EarthquakeService.addQuakeMarker(new OceanQuakeMarker(feature, img));
                }
            }
        } else {
            System.out.println("\n\n  today");

            ////////////////////////////////////////
            List<PointFeature> earthquakes2 = parseDAO.parseEarthquake(this, false);
            EarthquakeService.setQuakeMarkers(new ArrayList<Marker>());

            for (PointFeature feature : earthquakes2) {
                if (isLand(feature)) {
                    EarthquakeService.addQuakeMarker(new LandQuakeMarker(feature, img));
                } else {
                    EarthquakeService.addQuakeMarker(new OceanQuakeMarker(feature, img));
                }
            }
        }

    }

    public void parseMenuMarkers() {
        List<Feature> menu = GeoJSONReader.loadData(this, Parser.getmenuFile());
        MenuService.getInstance().setMenuMarkers(new ArrayList<Marker>());

        for (Feature city : menu) {
            MenuService.getInstance().addMenuMarker(new menuMarker(city));

        }
    }

    public void parseCountries() {
        PImage img = loadImage("C:/Users/1/eclipse-workspace/earthquakes/data/countrymarker-PhotoRoom.png");
        Map<String, String> cityPhoto = parsePhotosCity();
        for (String key : cityPhoto.keySet().stream().collect(Collectors.toList())) {
            cityLogo.put(key, loadImage(cityPhoto.get(key)));
        }

        List<Feature> countries = GeoJSONReader.loadData(this, Parser.getcountryFile());
        CityService.getInstance().setCountryMarkers(MapUtils.createSimpleMarkers(countries));

        List<Feature> cities = GeoJSONReader.loadData(this, Parser.getcityFile()); // �������� ���������� �� Parser
        CityService.getInstance().setCityMarkers();
        Map<String, String> cityUrl = new HashMap<>();
        for (Feature city : cities) {
            String cityN = city.getStringProperty("name");
            String url = city.getStringProperty("url");
            cityUrl.put(cityN, url);
        }
        for (Feature city : cities) {
            String cityName = city.getStringProperty("name");

            PImage logoInfo = cityLogo.get(cityName);
            String url = cityUrl.get(cityName);
            // System.out.print("\n\n url^ "+url);
            CityService.getInstance().addCityMarker(new CityMarker(city, img, logoInfo, url, new Dimension(1893, 939)));
        }
    }

    public Map<String, String> parsePhotosCity() {
        List<Feature> cities = GeoJSONReader.loadData(this, Parser.getcityFile()); // �������� ���������� �� Parser
        Map<String, String> cityLogos = new HashMap<>();
        for (Feature city : cities) {
            String cityName = city.getStringProperty("name");
            String photo = "C:/Users/1/eclipse-workspace/earthquakes/data/" + city.getStringProperty("photo");
            cityLogos.put(cityName, photo);
        }
        return cityLogos;
    }

    public void parseAirports() {
        PImage img = loadImage("C:/Users/1/eclipse-workspace/earthquakes/data/airportmarker-PhotoRoom.png");
        List<PointFeature> features = parseDAO.parseAirports(this);
        AirportService.getInstance().setAirportList(new ArrayList<Marker>());
        HashMap<Integer, Location> airports = new HashMap<Integer, Location>();

        // ������� ������� �� feature, ������� �� �������� �� Parser
        for (PointFeature feature : features) {
            AirportMarkerRequest a = new AirportMarkerRequest(feature);
            AirportMarker m = new AirportMarker(feature, img);

            m.setRadius(5);
            AirportService.getInstance().addAirport(m);

            airports.put(Integer.parseInt(feature.getId()), feature.getLocation());

        }

        // ������ ���������� ��� route
        List<ShapeFeature> routes = parseDAO.parseRoutes(this);
        AirportService.getInstance().setRouteList(new ArrayList<Marker>());
        for (ShapeFeature route : routes) {

            // ������� ����� ������ � ����� �������
            int source = Integer.parseInt((String) route.getProperty("source"));
            int dest = Integer.parseInt((String) route.getProperty("destination"));
            String code = (String) route.getProperty("code");
            // ���� ������� ����������, ������ ��������
            if (airports.containsKey(source) && airports.containsKey(dest)) {
                route.addLocation(airports.get(source));
                route.addLocation(airports.get(dest));

            }
            // �����, ������������ �������
            SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());
            AirportService.getInstance().addRoute(sl);
        }

        AirportService.getInstance().hideAirRouts();
    }

    private boolean isLand(PointFeature earthquake) {
        return EarthquakeService.isLand(earthquake);

    }

    public void setProfileLogo() {
        profileLogo = loadImage("C:/Users/1/eclipse-workspace/earthquakes/data/combined.png");
    }

    public PImage getProfileLogo() {
        return profileLogo;
    }

}
