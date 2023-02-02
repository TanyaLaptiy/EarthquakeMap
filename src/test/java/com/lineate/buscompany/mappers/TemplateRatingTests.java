package com.lineate.buscompany.mappers;

import com.lineate.buscompany.dtoE.requestE.ClientRequestE;
import com.lineate.buscompany.dtoE.requestE.RatingRequestE;
import com.lineate.buscompany.dtoE.responseE.ClientResponseE;
import com.lineate.buscompany.dtoE.responseE.RatingResponseE;
import com.lineate.buscompany.dtoE.responseE.RequestResponse;
import com.lineate.buscompany.errors.ServerException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TemplateRatingTests {
    private RestTemplate template = new RestTemplate();
    @Test
    public void postRatingOkTest() throws ServerException {

        template.delete("http://localhost:8080/api/deleteDB");
        LocalDate birthday = LocalDate.of(2000,5,14);

// -------------------------- create client ------------------------------------------------------------------
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

//------------------------ create rating ------------------------------------------------------------

        RatingRequestE ratingRequest = new RatingRequestE(
                clientResponseE.getId(),
                4,
                "message"
                );


        HttpHeaders headersRequest = new HttpHeaders();
        headersRequest.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestRequest = new HttpEntity<>(ratingRequest, headersRequest);
        RatingResponseE ratingResponse = template.postForObject("http://localhost:8080/api/ratingsE", requestRequest, RatingResponseE.class);


        assertEquals(clientResponseE.getId(),ratingResponse.getClientId());
        assertEquals(4, ratingResponse.getMark());
        assertEquals("message", ratingResponse.getMessage());

        System.out.println(" ~~ ratindId ~~ " + ratingResponse.getId());


        List<RatingResponseE> responseList = template.getForObject("http://localhost:8080/api/AllratingsE", List.class);

        assertEquals(1, responseList.size());

        //------------------------ get rating by Id ------------------------------------------------------------

        RatingResponseE ratingById = template.getForObject(
                "http://localhost:8080/api/ratingsE/{id}",
                RatingResponseE.class,
                ratingResponse.getId());


        assertEquals(clientResponseE.getId(),ratingById.getClientId());
        assertEquals(4, ratingById.getMark());
        assertEquals("message", ratingById.getMessage());

        System.out.println(" ~~ ratindId ~~ " + ratingById.getId());

        // --------------------------- get rating By ClientId -------------------------------------------------------

        RatingResponseE responseListByClientId = template.getForObject("http://localhost:8080/api/client/ratingsE/{id}",
                RatingResponseE.class,
                ratingById.getClientId());

        System.out.println("@@@@@@ "+responseListByClientId.getClientId());

        // --------------------------- get rating average -------------------------------------------------------

        int aver = template.getForObject("http://localhost:8080/api/averages", int.class);

        System.out.println("aver "+aver);



        // --------------------------- delete -------------------------------------------------------

        template.delete("http://localhost:8080/api/deleteAll/ratingsE");

        List<RequestResponse> responseList22 = template.getForObject("http://localhost:8080/api/AllratingsE", List.class);

        assertEquals(0, responseList22.size());

        template.delete("http://localhost:8080/api/deleteDB");

        List<RequestResponse> responseList2 = template.getForObject("http://localhost:8080/api/AllsmsE", List.class);

        assertEquals(0, responseList2.size());
    }

}
