package com.lineate.buscompany.mappers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.lineate.buscompany.daoE.UserDaoE;
import com.lineate.buscompany.dtoE.requestE.ClientRequestE;
import com.lineate.buscompany.dtoE.requestE.LoginRequestE;
import com.lineate.buscompany.dtoE.responseE.ClientResponseE;
import com.lineate.buscompany.dtoE.responseE.SessionResponseE;
import com.lineate.buscompany.dtoE.responseE.UserResponseE;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.UserE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TemplateClientTests {


    private RestTemplate template = new RestTemplate();

    @Test
    public void postClientOkTest() throws JsonProcessingException {
        template.delete("http://localhost:8080/api/deleteDB");
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


        assertEquals("firstName", clientResponseE.getFirstName());
        assertEquals("lastName", clientResponseE.getLastName());
        assertEquals("patronymic", clientResponseE.getPatronymic());
        assertEquals("Lodin", clientResponseE.getLogin());
        assertEquals("password", clientResponseE.getPassword());
        assertEquals("Russia", clientResponseE.getCountry());
        assertEquals("mail10234", clientResponseE.getMail());
        assertEquals("male", clientResponseE.getSex());
        assertEquals(birthday, clientResponseE.getBirthday());
        System.out.println(" ~~ ClientId ~~ " + clientResponseE.getId());

        List<UserResponseE> user = template.getForObject("http://localhost:8080/api/usersE", List.class);

        List<ClientResponseE> administratorDtos = template.getForObject("http://localhost:8080/api/AllClientsE", List.class);
        assertEquals(1, administratorDtos.size());
        assertEquals(1, user.size());
        // ------------------------------------- get All to Obj -----------------------------------------------------
        String administratorDtos3 = template.getForObject("http://localhost:8080/api/AllClientsE", String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType =
                objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, ClientResponseE.class);
        List<ClientResponseE> bookList = objectMapper.readValue(administratorDtos3, listType);

        ClientResponseE bookList2 = bookList.get(0);
        System.out.println("AAAAAAAAAAAAaa " +bookList2.getMail());

        System.out.println("AAAAAAAAAAAAaa" +administratorDtos.get(0));
        // ------------------------------- Session -------------------------------------------------
        List<SessionResponseE> sessionResponseEList = template.getForObject("http://localhost:8080/api/sessionsE", List.class);
        assertEquals(1, sessionResponseEList.size());

        SessionResponseE sessionResponseE = template.getForObject(
                "http://localhost:8080/api/sessionsE/{id}",
                SessionResponseE.class,
                clientResponseE.getId());
        assertEquals(clientResponseE.getId(), sessionResponseE.getUserId());
        assertEquals(clientResponseE.getToken(), sessionResponseE.getToken());

        System.out.println("!!! SS " + sessionResponseE.getToken());
        System.out.println("!!! SS " + sessionResponseE.getId());

// ----------------------------- get client by id -------------------------------------------

        ClientResponseE userById = template.getForObject(
                "http://localhost:8080/api/clientsE/{id}",
                ClientResponseE.class,
                clientResponseE.getId());
        assertEquals("firstName", userById.getFirstName());
        assertEquals("lastName", userById.getLastName());
        assertEquals("patronymic", userById.getPatronymic());
        assertEquals("Lodin", userById.getLogin());
        assertEquals("password", userById.getPassword());
        assertEquals("Russia", userById.getCountry());
        assertEquals("mail10234", userById.getMail());
        assertEquals("male", userById.getSex());
        assertEquals(birthday, userById.getBirthday());
        System.out.println(" TTTT "+ userById.getToken());
// ------------------------------  Logout -----------------------------------------------------------



        template.delete(
                "http://localhost:8080/api/clientsE/logout/{token}",
                sessionResponseE.getToken());


        List<SessionResponseE> sessionResponseEList2 = template.getForObject("http://localhost:8080/api/sessionsE", List.class);
        assertEquals(0, sessionResponseEList2.size());
// ------------------------------- login ---------------------------------------------------------------
        HttpHeaders headerslogin = new HttpHeaders();
        headerslogin.setContentType(MediaType.APPLICATION_JSON);
        LoginRequestE loginRequestE = new LoginRequestE(userById.getLogin(),userById.getPassword());
        HttpEntity<Object> requestlogin = new HttpEntity<>(loginRequestE, headerslogin);

        ClientResponseE clientResponseELogin = template.postForObject("http://localhost:8080/api/loginUser", requestlogin, ClientResponseE.class);
        List<SessionResponseE> sessionResponseEList3 = template.getForObject("http://localhost:8080/api/sessionsE", List.class);
        assertEquals(1, sessionResponseEList3.size());

        assertEquals("firstName", clientResponseELogin.getFirstName());
        assertEquals("lastName", clientResponseELogin.getLastName());
        assertEquals("patronymic", clientResponseELogin.getPatronymic());
        assertEquals("Lodin", clientResponseELogin.getLogin());
        assertEquals("password", clientResponseELogin.getPassword());
        assertEquals("Russia", clientResponseELogin.getCountry());
        assertEquals("male", clientResponseELogin.getSex());
        assertEquals(birthday, clientResponseELogin.getBirthday());
//        assertEquals("mail10234", clientResponseELogin.getMail());
// ---------------------------------------------------------------------------------------

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<ClientResponseE> request1 = new HttpEntity<ClientResponseE>(clientResponseELogin, httpHeaders);
//        String cookie = request1.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
//        System.out.println("!@#$$$$ "+ cookie);
//
//        headers.add(HttpHeaders.COOKIE, cookie);
//        HttpEntity<Object> request2 = new HttpEntity<>(headers);
//
//        ResponseEntity<HttpStatus>  settingsClientDto = template.exchange(
//                "http://localhost:8080/api/clientsE/logout",
//                HttpMethod.DELETE,
//                request2,
//                HttpStatus.class);

        template.delete("http://localhost:8080/api/deleteDB");
        List<ClientResponseE> administratorDtos66 = template.getForObject("http://localhost:8080/api/AllClientsE", List.class);
        assertEquals(0, administratorDtos66.size());

    }
}
