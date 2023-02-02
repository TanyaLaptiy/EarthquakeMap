package com.lineate.buscompany.mappers;

import com.lineate.buscompany.dtoE.requestE.ClientRequestE;
import com.lineate.buscompany.dtoE.requestE.SmsRequest;
import com.lineate.buscompany.dtoE.responseE.ClientResponseE;
import com.lineate.buscompany.dtoE.responseE.RequestResponse;
import com.lineate.buscompany.dtoE.responseE.SmsResponse;
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

public class TemplateSmsTests {
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

//------------------------ create sms ------------------------------------------------------------

        LocalDate data = LocalDate.of(2022,07,23);

        SmsRequest smsRequest = new SmsRequest(
                clientResponseE.getId(),
                data,
                "message"
        );

        HttpHeaders headersRequest = new HttpHeaders();
        headersRequest.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestRequest = new HttpEntity<>(smsRequest, headersRequest);
        SmsResponse smsResponse = template.postForObject("http://localhost:8080/api/smsE", requestRequest, SmsResponse.class);


        assertEquals(clientResponseE.getId(),smsResponse.getClientId());
        assertEquals(data, smsResponse.getData());
        assertEquals("message", smsResponse.getMessage());

        System.out.println(" ~~ smsId ~~ " + smsResponse.getId());
        System.out.println(" ~~ requestClientId ~~ " + smsResponse.getClientId());


        List<SmsResponse> responseList = template.getForObject("http://localhost:8080/api/AllsmsE", List.class);

        assertEquals(1, responseList.size());

        //------------------------ get sms by Id ------------------------------------------------------------

        SmsResponse smsById = template.getForObject(
                "http://localhost:8080/api/smsE/{id}",
                SmsResponse.class,
                smsResponse.getId());


        assertEquals(clientResponseE.getId(),smsById.getClientId());
        assertEquals(data, smsById.getData());
        assertEquals("message", smsById.getMessage());

        System.out.println(" ~~ smsId ~~ " + smsById.getId());
        System.out.println(" ~~ requestClientId ~~ " + smsById.getClientId());

        // --------------------------- get sms By ClientId -------------------------------------------------------

        List<SmsResponse> responseListByClientId = template.getForObject("http://localhost:8080/api/smsE/client/{id}",
                List.class,
                smsById.getClientId());

        System.out.println("@@@@@@ "+responseListByClientId.get(0));

        // --------------------------- get requests By Data -------------------------------------------------------


        HttpHeaders headerslogin = new HttpHeaders();
        headerslogin.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestlogin = new HttpEntity<>(smsById.getData(), headerslogin);
        List<RequestResponse> responseListByData = template.postForObject("http://localhost:8080/api/smsE/data",  requestlogin, List.class );
        System.out.println("@@@@@@ "+responseListByData.get(0));

        // --------------------------- delete -------------------------------------------------------

        template.delete(
                "http://localhost:8080/api/smsE/{id}",
                smsById.getId());

        List<RequestResponse> responseList22 = template.getForObject("http://localhost:8080/api/AllsmsE", List.class);

        assertEquals(0, responseList22.size());

        template.delete("http://localhost:8080/api/deleteAll/smsE");

        List<RequestResponse> responseList2 = template.getForObject("http://localhost:8080/api/AllsmsE", List.class);

        assertEquals(0, responseList2.size());
    }

}
