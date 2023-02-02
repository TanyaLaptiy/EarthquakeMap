package com.lineate.buscompany.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.lineate.buscompany.dtoE.requestE.EarthquakeRequest;
import com.lineate.buscompany.dtoE.requestE.EarthquakeRequestCD;
import com.lineate.buscompany.dtoE.responseE.ClientResponseE;
import com.lineate.buscompany.dtoE.responseE.EarthquakeResponse;
import com.lineate.buscompany.dtoE.responseE.EarthquakeResponseStr;
import com.lineate.buscompany.errors.ServerException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TemplateEarthquakeTests {
    private RestTemplate template = new RestTemplate();

    @Test
    public void postEarthquakeOkTest() throws ServerException, JsonProcessingException {

        template.delete("http://localhost:8080/api/deleteDB");


        LocalDate data = LocalDate.of(2022,07,23);
        double latitude = 123.4;
        double longitude = 13.4;
        double magnitude = 5.676;
        double depth = 45.65;

        EarthquakeRequest request1 = new EarthquakeRequest(
                latitude,
                longitude,
                magnitude,
                "location",
                "age",
                depth,
                "onland",
                "country",
                data);


        HttpHeaders headersRequest = new HttpHeaders();
        headersRequest.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestRequest = new HttpEntity<>(request1, headersRequest);
        EarthquakeResponse earthquakeResponse = template.postForObject("http://localhost:8080/api/earthquakeE", requestRequest, EarthquakeResponse.class);


        assertEquals(data, earthquakeResponse.getData());

        assertEquals(latitude, earthquakeResponse.getLatitude());
        assertEquals(longitude, earthquakeResponse.getLongitude());
        assertEquals(magnitude, earthquakeResponse.getMagnitude());
        assertEquals(depth, earthquakeResponse.getDepth());
        assertEquals("age", earthquakeResponse.getAge());
        assertEquals("onland", earthquakeResponse.getOnland());
        assertEquals("country", earthquakeResponse.getCountry());

        System.out.println(" ~~ earthquakeId ~~ " + earthquakeResponse.getId());


        List<EarthquakeResponse> responseList = template.getForObject("http://localhost:8080/api/AllEarthquakesE", List.class);

        assertEquals(1, responseList.size());

        //------------------------ get Earthquake by Id ------------------------------------------------------------

        EarthquakeResponse requestById = template.getForObject(
                "http://localhost:8080/api/earthquakeE/{id}",
                EarthquakeResponse.class,
                earthquakeResponse.getId());


        assertEquals(data, requestById.getData());

        assertEquals(latitude, requestById.getLatitude());
        assertEquals(longitude, requestById.getLongitude());
        assertEquals(magnitude, requestById.getMagnitude());
        assertEquals(depth, requestById.getDepth());
        assertEquals("age", requestById.getAge());
        assertEquals("onland", requestById.getOnland());
        assertEquals("country", requestById.getCountry());

        System.out.println(" ~~ earthquakeId ~~ " + requestById.getId());

        //------------------------ get Earthquake by data ------------------------------------------------------------

        HttpHeaders headerslogin = new HttpHeaders();
        headerslogin.setContentType(MediaType.APPLICATION_JSON);
        LocalDate localDate = requestById.getData();

        HttpEntity<Object> requestlogin = new HttpEntity<>(localDate, headerslogin);
        List<EarthquakeResponse> responseListByData = template.postForObject("http://localhost:8080/api/earthquakeE/data",  requestlogin, List.class );
//        EarthquakeResponse earthquakeResponse1 = responseListByData.get(0);
        assertEquals(1, responseListByData.size());

        System.out.println(" ~~  ~~ " + responseListByData.get(0));
//        System.out.println(" ~~ get Earthquake by data  ~~ " + earthquakeResponse1.toString());

        //------------------------ get Earthquake by data country ------------------------------------------------------------

        HttpHeaders headerslogin2 = new HttpHeaders();
        headerslogin2.setContentType(MediaType.APPLICATION_JSON);
        EarthquakeRequestCD earthquakeRequestCD = new EarthquakeRequestCD(requestById.getCountry(),requestById.getData());
        HttpEntity<Object> requestlogin2 = new HttpEntity<>(earthquakeRequestCD, headerslogin2);
        List<EarthquakeResponse> responseListByData2 = template.postForObject("http://localhost:8080/api/earthquakeE/CountryData",  requestlogin2, List.class );

        assertEquals(1, responseListByData2.size());

        System.out.println(" ~~  ~~ " + responseListByData2.get(0));
        String responseListByData3 = template.postForObject("http://localhost:8080/api/earthquakeE/CountryData",  requestlogin2, String.class );

        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType =
                objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, EarthquakeResponseStr.class);
        List<EarthquakeResponseStr> bookList = objectMapper.readValue(responseListByData3, listType);

        EarthquakeResponseStr bookList2 = bookList.get(0);
        System.out.println(" ~~ get Earthquake by data country  ~~ " + bookList2.getData());

        // --------------------------- delete -------------------------------------------------------

        template.delete(
                "http://localhost:8080/api/delete/earthquakeE/{id}",
                requestById.getId());

        List<EarthquakeResponse> responseList3 = template.getForObject("http://localhost:8080/api/AllEarthquakesE", List.class);

        assertEquals(0, responseList3.size());


        template.delete("http://localhost:8080/api/deleteDB");

        List<EarthquakeResponse> responseList2 = template.getForObject("http://localhost:8080/api/AllEarthquakesE", List.class);

        assertEquals(0, responseList2.size());
    }

}
