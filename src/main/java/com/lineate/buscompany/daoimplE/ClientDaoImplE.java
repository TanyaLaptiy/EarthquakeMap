package com.lineate.buscompany.daoimplE;

import com.lineate.buscompany.daoE.ClientDaoE;

import com.lineate.buscompany.database.mappers.ClientMapperE;
import com.lineate.buscompany.database.mappers.SessionMapperE;
import com.lineate.buscompany.database.mappers.UserMapperE;
import com.lineate.buscompany.dtoE.requestE.LoginRequestE;
import com.lineate.buscompany.errors.ErrorCodeApplication;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.ClientE;
import com.lineate.buscompany.modelsE.UserE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientDaoImplE implements ClientDaoE {
    private static final Logger LOGGER = LoggerFactory.getLogger(DaoImplBaseE.class);
    private final ClientMapperE clientMapper;
    private final UserMapperE userMapper;
    private final SessionMapperE sessionMapper;

    public ClientDaoImplE(ClientMapperE clientMapper,
                         UserMapperE userMapper,
                        SessionMapperE sessionMapper) {
        this.clientMapper = clientMapper;
        this.userMapper = userMapper;
        this.sessionMapper = sessionMapper;
    }

    @Override
    public ClientE insert(ClientE client) throws ServerException {
        LOGGER.debug("DAO insert  Client {}", client);
        try {
            UserE user = new UserE(client.getLastName(),
                    client.getFirstName(),
                    client.getPatronymic(),
                    client.getLogin(),
                    client.getPassword(),
                    client.getCountry(),
                    client.getSex(),
                    client.getBirthday());

            userMapper.insert(user);
            client.setId(user.getId());
            clientMapper.insert(client);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't insert Clent {} {}", client, ex);

            throw new ServerException(ErrorCodeApplication.ERROR_INSETR);

        }
        return client;
    }

    @Override
    public ClientE getByLogin(LoginRequestE login) {
        LOGGER.debug("DAO select user by login: {}", login);
        UserE user1 = userMapper.getByLogin(login.getLogin(), login.getPassword());

        ClientE client = clientMapper.getById(user1.getId());
        String mail = clientMapper.getMailById(user1.getId());

        client.setId(user1.getId());
        client.setLastName(user1.getLastName());
        client.setFirstName(user1.getFirstName());
        client.setLogin(user1.getLogin());
        client.setPassword(user1.getPassword());
        client.setPatronymic(user1.getPatronymic());
        client.setCountry(user1.getCountry());
        client.setSex(user1.getSex());
        client.setBirthday(user1.getBirthday());
        client.setMail(mail);

        return client;
    }


    @Override
    public ClientE getById(int id) throws ServerException {
        LOGGER.debug("DAO get by id {}", id);
        try {
            UserE user = new UserE();
            ClientE client = new ClientE();
            user = userMapper.getById(id);
            String mail = clientMapper.getMailById(id);
            client.setLastName(user.getLastName());
            client.setFirstName(user.getFirstName());
            client.setLogin(user.getLogin());
            client.setPassword(user.getPassword());
            client.setPatronymic(user.getPatronymic());
            client.setCountry(user.getCountry());
            client.setSex(user.getSex());
            client.setBirthday(user.getBirthday());
            client.setMail(mail);

            return client;
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get by id {} {}", id, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public void deleteSessionByToken(String token) {
        LOGGER.debug("DAO delete session by token: {}", token);
        sessionMapper.deleteByToken(token);
    }

    @Override
    public void deleteAll() throws ServerException {
        LOGGER.debug("DAO delete all Client {}");
        try {
            clientMapper.deleteAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't delete all Client {}", ex);
            throw new ServerException(ErrorCodeApplication.ERROR_DELETE);
        }

    }

    @Override
    public List<ClientE> getAll() throws ServerException {
        LOGGER.debug("DAO get all ");
        try {
            return clientMapper.getAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get all  {}", ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

}
