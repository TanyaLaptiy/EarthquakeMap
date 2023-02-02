package com.lineate.buscompany.traderE;

import com.lineate.buscompany.Config;
import com.lineate.buscompany.daoE.*;
import com.lineate.buscompany.dtoE.requestE.AdministratorRequestE;
import com.lineate.buscompany.dtoE.requestE.LoginRequestE;
import com.lineate.buscompany.dtoE.responseE.AdministratorResponseE;
import com.lineate.buscompany.dtoE.responseE.ClientResponseE;
import com.lineate.buscompany.dtoE.responseE.UserResponseE;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.AdministratorE;
import com.lineate.buscompany.modelsE.ClientE;
import com.lineate.buscompany.modelsE.SessionE;
import com.lineate.buscompany.modelsE.UserE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service

public class AdministratorTreaderE extends ServiceBaseE {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientTraderE.class);
    private static Connection connection;
    ConnectionE connectionE = new ConnectionE();

    public AdministratorTreaderE(UserDaoE userDao,
                                 ClientDaoE clientDao,
                                 AdministratorDaoE administratorDao,
                                 RequestDaoE requestDao,
                                 EarthquakeDaoE earthquakeDao,
                                 SmsDaoE smsDao,
                                 RatingDaoE ratingDao,
                                 SessionDaoE sessionDao,
                                 Config config) {
        super(userDao,
                clientDao,
                administratorDao,
                requestDao,
                earthquakeDao,
                smsDao,
                ratingDao,
                sessionDao,
                config);
    }


    public AdministratorResponseE registerAdmin(AdministratorRequestE administrator,
                                                HttpServletResponse httpServletResponse) throws ServerException {
        AdministratorResponseE administratorDto = new AdministratorResponseE();
        connectionE.setConnection();
        try {
            connection = DriverManager.getConnection(connectionE.getURL(), connectionE.getUSER(), connectionE.getPASSWORD());
            connection.setAutoCommit(false);
            LOGGER.info("Create administrator " + administrator.toString());
            AdministratorE administrator1 = new AdministratorE(
                    administrator.getLastName(),
                    administrator.getFirstName(),
                    administrator.getPatronymic(),
                    administrator.getLogin(),
                    administrator.getPassword(),
                    administrator.getCountry(),
                    administrator.getSex(),
                    administrator.getBirthday(),
                    administrator.getPosition());

            AdministratorE res = getAdministratorDao().insert(administrator1);

            String token = addCookie(res, httpServletResponse);
            administratorDto = new AdministratorResponseE(
                    res.getLastName(),
                    res.getFirstName(),
                    res.getPatronymic(),
                    res.getLogin(),
                    res.getPassword(),
                    res.getCountry(),
                    res.getSex(),
                    res.getBirthday(),
                    res.getPosition());
            administratorDto.setId(res.getId());
            administratorDto.setToken(token);
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return administratorDto;
    }

    private String addCookie(UserE user, HttpServletResponse httpServletResponse) throws ServerException {
        Cookie cookie = createCookie();
        SessionE session = new SessionE(user.getId(), cookie.getValue());
        getSessionDao().insert(session);

        httpServletResponse.addCookie(cookie);
        return cookie.getValue();
    }

    private Cookie createCookie() {
        return new Cookie(COOKIE_NAME, UUID.randomUUID().toString());
    }

    public <T extends UserResponseE> T loginUser(LoginRequestE loginUserRequest,
                                                 HttpServletResponse httpServletResponse) throws ServerException {
        AdministratorResponseE administratorDto = new AdministratorResponseE();
        UserResponseE userResponseE = new UserResponseE();
        connectionE.setConnection();
        try {
            connection = DriverManager.getConnection(connectionE.getURL(), connectionE.getUSER(), connectionE.getPASSWORD());
            connection.setAutoCommit(false);
            boolean administrator = true;
            try {
                getAdministratorDao().getByLogin(loginUserRequest).getId();
            } catch (NullPointerException ex) {
                administrator = false;
            }
            if (!administrator) {
                ClientE client = getClientDao().getByLogin(loginUserRequest);

                String token = addCookie(client, httpServletResponse);
//                ClientResponseE clientDto = new ClientResponseE(
//                        client.getLastName(),
//                        client.getFirstName(),
//                        client.getPatronymic(),
//                        client.getLogin(),
//                        client.getPassword(),
//                        client.getCountry(),
//                        client.getMail());
//                clientDto.setId(client.getId());
//                clientDto.setToken(token);
                userResponseE.setId(client.getId());
                userResponseE.setLastName(client.getLastName());
                userResponseE.setFirstName(client.getFirstName());

                userResponseE.setPatronymic(client.getPatronymic());
                userResponseE.setLogin(client.getLogin());
                userResponseE.setPassword(client.getPassword());
                userResponseE.setCountry(client.getCountry());
                userResponseE.setSex(client.getSex());
                userResponseE.setBirthday(client.getBirthday());

                return (T) userResponseE;
            }
            AdministratorE admin = getAdministratorDao().getByLogin(loginUserRequest);
            String token = addCookie(admin, httpServletResponse);
            userResponseE.setId(admin.getId());
            userResponseE.setLastName(admin.getLastName());
            userResponseE.setFirstName(admin.getFirstName());

            userResponseE.setPatronymic(admin.getPatronymic());
            userResponseE.setLogin(admin.getLogin());
            userResponseE.setPassword(admin.getPassword());
            userResponseE.setCountry(admin.getCountry());
            userResponseE.setSex(admin.getSex());
            userResponseE.setBirthday(admin.getBirthday());

            administratorDto.setId(admin.getId());
            administratorDto.setToken(token);
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (T) userResponseE;

    }

    public void logoutUser(String token) {

        getUserDao().deleteSessionByToken(token);

    }

    public AdministratorResponseE getById(int administrator) throws ServerException {
        AdministratorResponseE administratorDto = new AdministratorResponseE();
        connectionE.setConnection();
        try {
            connection = DriverManager.getConnection(connectionE.getURL(), connectionE.getUSER(), connectionE.getPASSWORD());
            connection.setAutoCommit(false);
            LOGGER.info("get client by id " + administrator);
            AdministratorE administrator1 = getAdministratorDao().getById(administrator);
            administratorDto = new AdministratorResponseE(
                    administrator1.getLastName(),
                    administrator1.getFirstName(),
                    administrator1.getPatronymic(),
                    administrator1.getLogin(),
                    administrator1.getPassword(),
                    administrator1.getCountry(),
                    administrator1.getSex(),
                    administrator1.getBirthday(),
                    administrator1.getPosition());
            administratorDto.setId(administrator1.getId());
            SessionE sessionE = getSessionDao().getById(administrator);
            administratorDto.setToken(sessionE.getToken());
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return administratorDto;
    }

    public List<AdministratorResponseE> getAll() throws ServerException {
        List<AdministratorResponseE> res = new ArrayList<>();
        connectionE.setConnection();
        try {
            connection = DriverManager.getConnection(connectionE.getURL(), connectionE.getUSER(), connectionE.getPASSWORD());
            connection.setAutoCommit(false);
            LOGGER.info("update all admins ");
            List<AdministratorE> list = getAdministratorDao().getAll();
            res = list.stream().map(a -> new AdministratorResponseE(
                    a.getId(),
                    a.getLastName(),
                    a.getFirstName(),
                    a.getPatronymic(),
                    a.getLogin(),
                    a.getPassword(),
                    a.getCountry(),
                    a.getSex(),
                    a.getBirthday(),
                    a.getPosition())).collect(Collectors.toList());
            for (AdministratorResponseE administratorResponseE : res) {
                SessionE sessionE = getSessionDao().getById(administratorResponseE.getId());
                administratorResponseE.setToken(sessionE.getToken());
            }
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void deleteAllAdmin() throws ServerException {
        LOGGER.info("Delete All administrators ");
        getAdministratorDao().deleteAll();
    }
}
