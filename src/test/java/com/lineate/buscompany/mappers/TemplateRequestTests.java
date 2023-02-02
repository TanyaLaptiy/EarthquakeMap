package com.lineate.buscompany.mappers;


import com.lineate.buscompany.dtoE.requestE.ClientRequestE;
import com.lineate.buscompany.dtoE.requestE.RequestRequest;
import com.lineate.buscompany.dtoE.responseE.ClientResponseE;
import com.lineate.buscompany.dtoE.responseE.RequestResponse;
import com.lineate.buscompany.errors.ServerException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TemplateRequestTests {
    private RestTemplate template = new RestTemplate();
    @Test
    public void postRequestOkTest() throws ServerException {

        template.delete("http://localhost:8080/api/deleteDB");

// -------------------------- create client ------------------------------------------------------------------
        LocalDate birthday = LocalDate.of(2000,5,14);

        ClientRequestE clientDto = new ClientRequestE(
                "lastName",
                "firstName",
                "patronymic",
                "Lodin",
                "password",
                "Russia",
                "male",
                birthday,
                "mail10234"
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(clientDto, headers);
        ClientResponseE clientResponseE = template.postForObject("http://localhost:8080/api/clientsE", request, ClientResponseE.class);

//------------------------ create Request ------------------------------------------------------------

        LocalDate data = LocalDate.of(2022,07,23);
        double latitude = 123.4;
        double longitude = 13.4;
        double magnitude = 5.676;
        double depth = 45.65;
        RequestRequest request1 = new RequestRequest(clientResponseE.getId(),
         data,
        "title",
         "message",
         latitude,
         longitude,
         magnitude,
        depth,
         "age",
         "onland",
         "country"
        );

        HttpHeaders headersRequest = new HttpHeaders();
        headersRequest.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestRequest = new HttpEntity<>(request1, headersRequest);
        RequestResponse requestResponse = template.postForObject("http://localhost:8080/api/requestsE", requestRequest, RequestResponse.class);


        assertEquals(data, requestResponse.getData());
        assertEquals("title", requestResponse.getTitle());
        assertEquals("message", requestResponse.getMessage());
        assertEquals(latitude, requestResponse.getLatitude());
        assertEquals(longitude, requestResponse.getLongitude());
        assertEquals(magnitude, requestResponse.getMagnitude());
        assertEquals(depth, requestResponse.getDepth());
        assertEquals("age", requestResponse.getAge());
        assertEquals("onland", requestResponse.getOnland());
        assertEquals("country", requestResponse.getCountry());

        System.out.println(" ~~ requestId ~~ " + requestResponse.getId());
        System.out.println(" ~~ requestClientId ~~ " + requestResponse.getClientId());
        System.out.println(" ~~ requestData ~~ " + requestResponse.getRequestData());


        List<RequestResponse> responseList = template.getForObject("http://localhost:8080/api/AllRequestsE", List.class);

        assertEquals(1, responseList.size());

        template.delete("http://localhost:8080/api/deleteDB");

        List<RequestResponse> responseList2 = template.getForObject("http://localhost:8080/api/AllRequestsE", List.class);

        assertEquals(0, responseList2.size());
    }

    @Test
    public void getByIdRequestOkTest() throws ServerException {

        template.delete("http://localhost:8080/api/deleteDB");

// -------------------------- create client ------------------------------------------------------------------
        LocalDate birthday = LocalDate.of(2000,5,14);

        ClientRequestE clientDto = new ClientRequestE(
                "lastName",
                "firstName",
                "patronymic",
                "Lodin",
                "password",
                "Russia",
                "male",
                birthday,
                "pochtaperepochtanedopochta@mail.ru"
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(clientDto, headers);
        ClientResponseE clientResponseE = template.postForObject("http://localhost:8080/api/clientsE", request, ClientResponseE.class);

//------------------------ create Request ------------------------------------------------------------

        LocalDate data = LocalDate.of(2022,07,23);
        double latitude = 123.4;
        double longitude = 13.4;
        double magnitude = 5.676;
        double depth = 45.65;
        RequestRequest request1 = new RequestRequest(clientResponseE.getId(),
                data,
                "title",
                "message",
                latitude,
                longitude,
                magnitude,
                depth,
                "age",
                "onland",
                "country"
        );

        HttpHeaders headersRequest = new HttpHeaders();
        headersRequest.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestRequest = new HttpEntity<>(request1, headersRequest);
        RequestResponse requestResponse = template.postForObject("http://localhost:8080/api/requestsE", requestRequest, RequestResponse.class);


        assertEquals(data, requestResponse.getData());
        assertEquals("title", requestResponse.getTitle());
        assertEquals("message", requestResponse.getMessage());
        assertEquals(latitude, requestResponse.getLatitude());
        assertEquals(longitude, requestResponse.getLongitude());
        assertEquals(magnitude, requestResponse.getMagnitude());
        assertEquals(depth, requestResponse.getDepth());
        assertEquals("age", requestResponse.getAge());
        assertEquals("onland", requestResponse.getOnland());
        assertEquals("country", requestResponse.getCountry());

        System.out.println(" ~~ requestId ~~ " + requestResponse.getId());
        System.out.println(" ~~ requestClientId ~~ " + requestResponse.getClientId());
        System.out.println(" ~~ requestData ~~ " + requestResponse.getRequestData());

        List<RequestResponse> responseList = template.getForObject("http://localhost:8080/api/AllRequestsE", List.class);

        assertEquals(1, responseList.size());

        //------------------------ get Request by Id ------------------------------------------------------------

        RequestResponse requestById = template.getForObject(
                "http://localhost:8080/api/requestsE/{id}",
                RequestResponse.class,
                requestResponse.getId());


        assertEquals(data, requestById.getData());
        assertEquals("title", requestById.getTitle());
        assertEquals("message", requestById.getMessage());
        assertEquals(latitude, requestById.getLatitude());
        assertEquals(longitude, requestById.getLongitude());
        assertEquals(magnitude, requestById.getMagnitude());
        assertEquals(depth, requestById.getDepth());
        assertEquals("age", requestById.getAge());
        assertEquals("onland", requestById.getOnland());
        assertEquals("country", requestById.getCountry());

        System.out.println(" ~~ requestId ~~ " + requestById.getId());
        System.out.println(" ~~ requestClientId ~~ " + requestById.getClientId());
        System.out.println(" ~~ requestData ~~ " + requestById.getRequestData());


        // --------------------------- get requests By ClientId -------------------------------------------------------

        List<RequestResponse> responseListByClientId = template.getForObject("http://localhost:8080/api/requestsE/client/{id}",
                List.class,
                requestById.getClientId());
        System.out.println("@@@@@@ "+responseListByClientId.get(0));

        // --------------------------- get requests By Data -------------------------------------------------------


        HttpHeaders headerslogin = new HttpHeaders();
        headerslogin.setContentType(MediaType.APPLICATION_JSON);
        LocalDate localDate = requestById.getRequestData();
        HttpEntity<Object> requestlogin = new HttpEntity<>(localDate, headerslogin);
        List<RequestResponse> responseListByData = template.postForObject("http://localhost:8080/api/requestsE/data",  requestlogin, List.class );

        // --------------------------- get requests By status -------------------------------------------------------

        List<RequestResponse> responseListByStatus = template.getForObject("http://localhost:8080/api/requestsE/status/{status}",
                List.class,
                requestById.getStatus());
        System.out.println("@@@@@@ "+responseListByClientId.get(0));

        // --------------------------- get status By Cl id -------------------------------------------------------

        String responseListByClId = template.getForObject("http://localhost:8080/api/requestsE/status/id/{id}",
                String.class,
                requestById.getClientId());
        System.out.println("@@@@@@ "+responseListByClId);

        // --------------------------- approve -------------------------------------------------------

        HttpHeaders headersapprove = new HttpHeaders();
        headersapprove.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestapprove = new HttpEntity<>(requestById, headersapprove);
//        template.put("http://localhost:8080/api/requestsE/approve",  requestapprove, HttpStatus.class );
//          template.put("http://localhost:8080/api/requestsE/reject",  requestapprove, HttpStatus.class );

        // --------------------------- get status By Cl id -------------------------------------------------------

        String responseListByClId2 = template.getForObject("http://localhost:8080/api/requestsE/status/id/{id}",
                String.class,
                requestById.getClientId());
        System.out.println("get status By Cl id  -- "+responseListByClId2);

        RequestResponse requestById2 = template.getForObject(
                "http://localhost:8080/api/requestsE/{id}",
                RequestResponse.class,
                requestResponse.getId());
        System.out.println("@@@@44@@ "+requestById2.getStatus());


        // --------------------------- delete -------------------------------------------------------

        template.delete(
                "http://localhost:8080/api/delete/requestsE/{id}",
                requestById.getId());

        List<RequestResponse> responseList2 = template.getForObject("http://localhost:8080/api/AllRequestsE", List.class);

        assertEquals(0, responseList2.size());

        template.delete("http://localhost:8080/api/deleteDB");


    }

}
