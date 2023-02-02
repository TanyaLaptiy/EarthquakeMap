package com.lineate.buscompany.traderE;

import com.lineate.buscompany.Config;
import com.lineate.buscompany.daoE.*;
import com.lineate.buscompany.dtoE.requestE.ClientRequestE;
import com.lineate.buscompany.dtoE.requestE.LoginRequestE;
import com.lineate.buscompany.dtoE.responseE.ClientResponseE;
import com.lineate.buscompany.errors.ErrorCodeApplication;
import com.lineate.buscompany.errors.ServerException;

import com.lineate.buscompany.modelsE.ClientE;
import com.lineate.buscompany.modelsE.SessionE;
import com.lineate.buscompany.modelsE.UserE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Collectors;

@Service

public class ClientTraderE extends ServiceBaseE {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientTraderE.class);
    private static Connection connection;
    ConnectionE connectionE = new ConnectionE();

    public ClientTraderE(UserDaoE userDao,
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

    public ClientResponseE registerClient(ClientRequestE client,
                                          HttpServletResponse httpServletResponse) throws ServerException {
        ClientResponseE clientDto = new ClientResponseE();
        connectionE.setConnection();
        try {
            connection = DriverManager.getConnection(connectionE.getURL(), connectionE.getUSER(), connectionE.getPASSWORD());
            connection.setAutoCommit(false);
            LOGGER.info("Create user " + client.toString());

            ClientE client1 = new ClientE(
                    client.getLastName(),
                    client.getFirstName(),
                    client.getPatronymic(),
                    client.getLogin(),
                    client.getPassword(),
                    client.getCountry(),
                    client.getSex(),
                    client.getBirthday(),
                    client.getMail());

            ClientE res = getClientDao().insert(client1);

            String token = addCookie(res, httpServletResponse);
            clientDto = new ClientResponseE(
                    res.getLastName(),
                    res.getFirstName(),
                    res.getPatronymic(),
                    res.getLogin(),
                    res.getPassword(),
                    res.getCountry(),
                    res.getSex(),
                    res.getBirthday(),
                    res.getMail());

            clientDto.setId(res.getId());
            clientDto.setToken(token);

            List<String> mails = new ArrayList<>();
            mails.add(client.getMail());
            Mail mail = new Mail();
            String text = "Welcome " + client.getFirstName() + "! You have registered on the Earthquakes website and now new features are available to you.\n" +
                    "Your nickname: " + client.getLogin() + "\n" +
                    "Name: " + client.getFirstName() + " " + client.getLastName() + " " + client.getPatronymic() + "\n" +
                    "Password: " + client.getPassword() + "\n";
            mail.sendMail(mails, text);
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientDto;
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

    public ClientResponseE loginUser(LoginRequestE loginUserRequest,
                                     HttpServletResponse httpServletResponse) throws ServerException {
        ClientResponseE clientDto = new ClientResponseE();
        connectionE.setConnection();
        try {
            connection = DriverManager.getConnection(connectionE.getURL(), connectionE.getUSER(), connectionE.getPASSWORD());
            connection.setAutoCommit(false);
            ClientE client = getClientDao().getByLogin(loginUserRequest);

            if (client == null) {
                throw new ServerException(ErrorCodeApplication.WRONG_LOGIN);
            }

            String token = addCookie(client, httpServletResponse);
            clientDto = new ClientResponseE(
                    client.getLastName(),
                    client.getFirstName(),
                    client.getPatronymic(),
                    client.getLogin(),
                    client.getPassword(),
                    client.getCountry(),
                    client.getSex(),
                    client.getBirthday(),
                    client.getMail());
            clientDto.setId(client.getId());
            clientDto.setToken(token);
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientDto;

    }

    public void logoutUser(String token) {
        getClientDao().deleteSessionByToken(token);

    }

    public ClientResponseE getById(int client) throws ServerException {
        LOGGER.info("get client by id " + client);
        ClientResponseE clientDto = new ClientResponseE();
        connectionE.setConnection();
        try {
            connection = DriverManager.getConnection(connectionE.getURL(), connectionE.getUSER(), connectionE.getPASSWORD());
            connection.setAutoCommit(false);
            ClientE client1 = getClientDao().getById(client);
            clientDto = new ClientResponseE(
                    client1.getLastName(),
                    client1.getFirstName(),
                    client1.getPatronymic(),
                    client1.getLogin(),
                    client1.getPassword(),
                    client1.getCountry(),
                    client1.getSex(),
                    client1.getBirthday(),
                    client1.getMail());
            clientDto.setId(client1.getId());
            SessionE sessionE = getSessionDao().getById(client);
            if(sessionE!=null) {
                clientDto.setToken(sessionE.getToken());
            }
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientDto;
    }

    public List<ClientResponseE> getAll() throws ServerException {
        LOGGER.info("update all clients ");
        List<ClientResponseE> r = new ArrayList<>();
        connectionE.setConnection();
        try {
            connection = DriverManager.getConnection(connectionE.getURL(), connectionE.getUSER(), connectionE.getPASSWORD());
            connection.setAutoCommit(false);
            List<ClientE> list = getClientDao().getAll();
            r = list.stream().map(a -> new ClientResponseE(
                    a.getId(),
                    a.getLastName(),
                    a.getFirstName(),
                    a.getPatronymic(),
                    a.getLogin(),
                    a.getPassword(),
                    a.getCountry(),
                    a.getSex(),
                    a.getBirthday(),
                    a.getMail())).collect(Collectors.toList());
            for (ClientResponseE clientResponseE : r) {
                SessionE sessionE = getSessionDao().getById(clientResponseE.getId());
                clientResponseE.setToken(sessionE.getToken());
            }
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }


    public void deleteAllClient() throws ServerException {
        LOGGER.info("Delete All clients ");
        getClientDao().deleteAll();
    }
}
