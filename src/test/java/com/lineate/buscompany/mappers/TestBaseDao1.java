package com.lineate.buscompany.mappers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.lineate.buscompany.daoE.UserDaoE;
import com.lineate.buscompany.dtoE.requestE.AdministratorRequestE;
import com.lineate.buscompany.dtoE.requestE.ClientRequestE;
import com.lineate.buscompany.dtoE.requestE.LoginRequestE;
import com.lineate.buscompany.dtoE.responseE.AdministratorResponseE;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestBaseDao1 {
    @Autowired
    UserDaoE userDao;

    public void clear() throws ServerException {
        if (userDao != null) {
            userDao.deleteAll();
        }
    }

    @Test
    public void testInsertAndGetUserByLogin() throws ServerException {
        userDao.deleteAll();
        LocalDate birthday = LocalDate.of(2000,5,14);

        UserE user = new UserE("lastName",
                "firstName",
                "patronymic",
                "login1",
                "password",
                "Russia",
                "male",
                birthday);
        userDao.insert(user);

        UserE userFromDb = userDao.getById(user.getId());

        Assertions.assertEquals(user, userFromDb);
        userDao.deleteAll();
    }
    private RestTemplate template = new RestTemplate();

    @Test
    public void postAdminOkTest() throws JsonProcessingException {
        template.delete("http://localhost:8080/api/deleteDB");
        LocalDate birthday = LocalDate.of(2000,5,14);
        AdministratorRequestE adminDto = new AdministratorRequestE(
                "lastName",
                "firstName",
                "patronymic",
                "Lodin",
                "password",
                "Russia",
                "male",
                birthday,
                "position"
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(adminDto, headers);
        AdministratorResponseE adminResponseE = template.postForObject("http://localhost:8080/api/adminsE", request, AdministratorResponseE.class);


        assertEquals("firstName", adminResponseE.getFirstName());
        assertEquals("lastName", adminResponseE.getLastName());
        assertEquals("patronymic", adminResponseE.getPatronymic());
        assertEquals("Lodin", adminResponseE.getLogin());
        assertEquals("password", adminResponseE.getPassword());
        assertEquals("Russia", adminResponseE.getCountry());
        assertEquals("position", adminResponseE.getPosition());
        assertEquals("male", adminResponseE.getSex());
        assertEquals(birthday, adminResponseE.getBirthday());
        System.out.println(" ~~ AdminId ~~ " + adminResponseE.getId());

        List<UserResponseE> user = template.getForObject("http://localhost:8080/api/usersE", List.class);

        List<AdministratorResponseE> administratorDtos = template.getForObject("http://localhost:8080/api/AllAdminsE", List.class);
        assertEquals(1, administratorDtos.size());
        assertEquals(1, user.size());

        // ------------------------------- Session -------------------------------------------------
        List<SessionResponseE> sessionResponseEList = template.getForObject("http://localhost:8080/api/sessionsE", List.class);
        assertEquals(1, sessionResponseEList.size());

        SessionResponseE sessionResponseE = template.getForObject(
                "http://localhost:8080/api/sessionsE/{id}",
                SessionResponseE.class,
                adminResponseE.getId());
        assertEquals(adminResponseE.getId(), sessionResponseE.getUserId());
        assertEquals(adminResponseE.getToken(), sessionResponseE.getToken());

        System.out.println("!!! SS " + sessionResponseE.getToken());
        System.out.println("!!! SS " + sessionResponseE.getId());

// ----------------------------- get admin by id -------------------------------------------

        AdministratorResponseE userById = template.getForObject(
                "http://localhost:8080/api/adminsE/{id}",
                AdministratorResponseE.class,
                adminResponseE.getId());
        assertEquals("firstName", userById.getFirstName());
        assertEquals("lastName", userById.getLastName());
        assertEquals("patronymic", userById.getPatronymic());
        assertEquals("Lodin", userById.getLogin());
        assertEquals("password", userById.getPassword());
        assertEquals("Russia", userById.getCountry());
        assertEquals("position", userById.getPosition());
        assertEquals("male", userById.getSex());
        assertEquals(birthday, userById.getBirthday());
        System.out.println(" TTTT "+ userById.getToken());
// ------------------------------  Logout -----------------------------------------------------------
        template.delete(
                "http://localhost:8080/api/clientsE/logout/{token}",
                sessionResponseE.getToken());


        List<SessionResponseE> sessionResponseEList2 = template.getForObject("http://localhost:8080/api/sessionsE", List.class);
        assertEquals(0, sessionResponseEList2.size());
        // ------------------------------- red Clietn --------------------------------------------------------

        ClientRequestE clientDto = new ClientRequestE(
                "lastName",
                "firstName",
                "patronymic",
                "LodinClietn",
                "password",
                "Russia",
                "male",
                birthday,
                "mail10234"
        );

        HttpHeaders Clietnheaders = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> Clietnrequest = new HttpEntity<>(clientDto, Clietnheaders);
        ClientResponseE clientResponseE = template.postForObject("http://localhost:8080/api/clientsE", Clietnrequest, ClientResponseE.class);


        assertEquals("firstName", clientResponseE.getFirstName());
        assertEquals("lastName", clientResponseE.getLastName());
        assertEquals("patronymic", clientResponseE.getPatronymic());
        assertEquals("LodinClietn", clientResponseE.getLogin());
        assertEquals("password", clientResponseE.getPassword());
        assertEquals("Russia", clientResponseE.getCountry());
        assertEquals("mail10234", clientResponseE.getMail());
        assertEquals("male", clientResponseE.getSex());
        assertEquals(birthday, clientResponseE.getBirthday());
        System.out.println(" ~~ ClientId ~~ " + clientResponseE.getId());

        List<UserResponseE> Clietnuser = template.getForObject("http://localhost:8080/api/usersE", List.class);

        List<ClientResponseE> ClietnadministratorDtos = template.getForObject("http://localhost:8080/api/AllClientsE", List.class);
        assertEquals(1, ClietnadministratorDtos.size());
        assertEquals(2, Clietnuser.size());

        // -------------------------------- logout cl ----------------------------------------------
        SessionResponseE ClsessionResponseE = template.getForObject(
                "http://localhost:8080/api/sessionsE/{id}",
                SessionResponseE.class,
                clientResponseE.getId());

        template.delete(
                "http://localhost:8080/api/clientsE/logout/{token}",
                ClsessionResponseE.getToken());
        List<SessionResponseE> sessionResponseELis = template.getForObject("http://localhost:8080/api/sessionsE", List.class);
        assertEquals(0, sessionResponseELis.size());

// ------------------------------- login ---------------------------------------------------------------
        HttpHeaders headerslogin = new HttpHeaders();
        headerslogin.setContentType(MediaType.APPLICATION_JSON);
        LoginRequestE loginRequestE = new LoginRequestE(userById.getLogin(),userById.getPassword());
        HttpEntity<Object> requestlogin = new HttpEntity<>(loginRequestE, headerslogin);

        AdministratorResponseE adminResponseELogin = template.postForObject("http://localhost:8080/api/loginUser", requestlogin, AdministratorResponseE.class);
        List<SessionResponseE> sessionResponseEList3 = template.getForObject("http://localhost:8080/api/sessionsE", List.class);
        assertEquals(1, sessionResponseEList3.size());

        assertEquals("firstName", adminResponseELogin.getFirstName());
        assertEquals("lastName", adminResponseELogin.getLastName());
        assertEquals("patronymic", adminResponseELogin.getPatronymic());
        assertEquals("Lodin", adminResponseELogin.getLogin());
        assertEquals("password", adminResponseELogin.getPassword());
        assertEquals("Russia", adminResponseELogin.getCountry());
        assertEquals("male", adminResponseELogin.getSex());
        assertEquals(birthday, adminResponseELogin.getBirthday());
//        assertEquals("position", adminResponseELogin.getPosition());
// -------------------------------- login Cl ------------------------------------------------------------
        HttpHeaders Cheaderslogin = new HttpHeaders();
        Cheaderslogin.setContentType(MediaType.APPLICATION_JSON);

        LoginRequestE CloginRequestE = new LoginRequestE(clientDto.getLogin(),clientDto.getPassword());
        HttpEntity<Object> Crequestlogin = new HttpEntity<>(CloginRequestE, Cheaderslogin);


        ClientResponseE ResponseELogin = template.postForObject("http://localhost:8080/api/loginUser", Crequestlogin, ClientResponseE.class);
        assertEquals("firstName", ResponseELogin.getFirstName());
        assertEquals("lastName", ResponseELogin.getLastName());
        assertEquals("patronymic", ResponseELogin.getPatronymic());
        assertEquals("LodinClietn", ResponseELogin.getLogin());
        assertEquals("password", ResponseELogin.getPassword());
        assertEquals("Russia", ResponseELogin.getCountry());
//        assertEquals("mail10234", ResponseELogin.getMail());
        assertEquals("male", ResponseELogin.getSex());
        assertEquals(birthday, ResponseELogin.getBirthday());
        System.out.println(" ~~ ClientId ~~ " + ResponseELogin.getId());

        List<SessionResponseE> sessionResponseEList33 = template.getForObject("http://localhost:8080/api/sessionsE", List.class);
        assertEquals(2, sessionResponseEList33.size());

        template.delete("http://localhost:8080/api/deleteDB");
        List<ClientResponseE> administratorDtos66 = template.getForObject("http://localhost:8080/api/AllAdminsE", List.class);
        assertEquals(0, administratorDtos66.size());

    }

}
