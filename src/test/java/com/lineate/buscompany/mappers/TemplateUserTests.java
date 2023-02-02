package com.lineate.buscompany.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.lineate.buscompany.dtoE.requestE.UserRequestE;
import com.lineate.buscompany.dtoE.responseE.SessionResponseE;
import com.lineate.buscompany.dtoE.responseE.UserResponseE;
import com.lineate.buscompany.errors.ServerException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class TemplateUserTests {
    private RestTemplate template = new RestTemplate();

    @Test
    public void postUserOkTestE() throws ServerException, JsonProcessingException {
        LocalDate birthday = LocalDate.of(2000,5,14);

        template.delete("http://localhost:8080/api/deleteDB");
        UserRequestE dto = new UserRequestE(
                "lastName",
                "firstName",
                "patronymic",
                "login1",
                "password",
                "Russia",
                "male",
                birthday);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(dto, headers);

        UserResponseE dto2 = template.postForObject("http://localhost:8080/api/usersE", request, UserResponseE.class);
        System.out.println(" ~~ UserId ~~ "+ dto2.getId());
        assertEquals("firstName", dto2.getFirstName());
        assertEquals("lastName", dto2.getLastName());
        assertEquals("login1", dto2.getLogin());
        assertEquals("patronymic", dto2.getPatronymic());
        assertEquals("password", dto2.getPassword());
        assertEquals("Russia", dto2.getCountry());
        assertEquals("male", dto2.getSex());
        assertEquals(birthday, dto2.getBirthday());

        List<UserResponseE> user = template.getForObject("http://localhost:8080/api/usersE", List.class);
        List<SessionResponseE> sessionResponseEList = template.getForObject("http://localhost:8080/api/sessionsE", List.class);

        assertEquals(1, user.size());
        assertEquals(0, sessionResponseEList.size());

// -------------------------------- get by id ---------------------------------------------------------------

        UserResponseE userById = template.getForObject(
                "http://localhost:8080/api/usersE/{id}",
                UserResponseE.class,
                dto2.getId());

        assertEquals("firstName", userById.getFirstName());
        assertEquals("lastName", userById.getLastName());
        assertEquals("login1", userById.getLogin());
        assertEquals("patronymic", userById.getPatronymic());
        assertEquals("password", userById.getPassword());
        assertEquals("Russia", userById.getCountry());
        assertEquals("male", userById.getSex());
        assertEquals(birthday, userById.getBirthday());
        // -------------------------------- get by log ---------------------------------------------------------------

        UserResponseE userBylog = template.getForObject(
                "http://localhost:8080/api/usersByLogE/{log}",
                UserResponseE.class,
                dto2.getLogin());

        assertEquals("firstName", userBylog.getFirstName());
        assertEquals("lastName", userBylog.getLastName());
        assertEquals("login1", userBylog.getLogin());
        assertEquals("patronymic", userBylog.getPatronymic());
        assertEquals("password", userBylog.getPassword());
        assertEquals("Russia", userBylog.getCountry());
        assertEquals("male", userBylog.getSex());
        assertEquals(birthday, userBylog.getBirthday());
// ------------------------------- add User № 2 -----------------------------------------------------------------

        UserRequestE userRequestE = new UserRequestE(
                "lastName",
                "firstName",
                "patronymic",
                "login2",
                "password",
                "Russia",
                "male",
                birthday);
        HttpHeaders headers2 = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request2 = new HttpEntity<>(userRequestE, headers2);

        UserResponseE userResponseE = template.postForObject("http://localhost:8080/api/usersE", request2, UserResponseE.class);

        System.out.println(" ~~ UserId ~~ "+ userResponseE.getId());
        assertEquals("firstName", userResponseE.getFirstName());
        assertEquals("lastName", userResponseE.getLastName());
        assertEquals("login2", userResponseE.getLogin());
        assertEquals("patronymic", userResponseE.getPatronymic());
        assertEquals("password", userResponseE.getPassword());
        assertEquals("Russia", userResponseE.getCountry());
        assertEquals("male", userResponseE.getSex());
        assertEquals(birthday, userResponseE.getBirthday());
        System.out.println(" ~~ DATE ~~ "+ userResponseE.getBirthday());
        System.out.println(" ~~ SEX ~~ "+ userResponseE.getSex());

        List<UserResponseE> user2 = template.getForObject("http://localhost:8080/api/usersE", List.class);
        assertEquals(2, user2.size());

// ------------------------------- delete User № 1 -----------------------------------------------------------------
        template.delete(
                "http://localhost:8080/api/usersE/{id}",
                dto2.getId());

        List<UserResponseE> user3 = template.getForObject("http://localhost:8080/api/usersE", List.class);
        assertEquals(1, user3.size());

        template.delete("http://localhost:8080/api/deleteDB");
        List<UserResponseE> user4 = template.getForObject("http://localhost:8080/api/usersE", List.class);
        assertEquals(0, user4.size());


    }


}
