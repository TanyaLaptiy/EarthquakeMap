package com.lineate.buscompany.traderE;

import com.lineate.buscompany.Config;
import com.lineate.buscompany.daoE.*;
import com.lineate.buscompany.dtoE.requestE.UserRequestE;
import com.lineate.buscompany.dtoE.responseE.SessionResponseE;
import com.lineate.buscompany.dtoE.responseE.UserResponseE;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.SessionE;
import com.lineate.buscompany.modelsE.UserE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class UserTraderE extends ServiceBaseE {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserTraderE.class);

    public UserTraderE(
            UserDaoE userDao,
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

    public UserResponseE createUser(UserRequestE user) throws ServerException {
        LOGGER.info("Create user " + user.toString());
        UserE user1 = new UserE(
                user.getLastName(),
                user.getFirstName(),
                user.getPatronymic(),
                user.getLogin(),
                user.getPassword(),
                user.getCountry(),
                user.getSex(),
                user.getBirthday());

        UserE res = getUserDao().insert(user1);
        UserResponseE userDto = new UserResponseE(
                res.getId(),
                res.getLastName(),
                res.getFirstName(),
                res.getPatronymic(),
                res.getLogin(),
                res.getPassword(),
                res.getCountry(),
                res.getSex(),
                res.getBirthday());

        return userDto;

    }


    public UserResponseE getById(int user) throws ServerException {
        LOGGER.info("get user by id " + user);
        UserE user1 = getUserDao().getById(user);
        UserResponseE userDto = new UserResponseE(
                user1.getId(),
                user1.getLastName(),
                user1.getFirstName(),
                user1.getPatronymic(),
                user1.getLogin(),
                user1.getPassword(),
                user1.getCountry(),
                user1.getSex(),
                user1.getBirthday());
        return userDto;
    }

    public UserResponseE getByLog(String log) throws ServerException {
        LOGGER.info("get user by log " + log);
        UserE user1 = getUserDao().getByLog(log);
        UserResponseE userDto = new UserResponseE(
                user1.getId(),
                user1.getLastName(),
                user1.getFirstName(),
                user1.getPatronymic(),
                user1.getLogin(),
                user1.getPassword(),
                user1.getCountry(),
                user1.getSex(),
                user1.getBirthday());
        return userDto;
    }

    public List<UserResponseE> getAll() throws ServerException {
        LOGGER.info("update all users ");
        List<UserE> list = getUserDao().getAll();
        return list.stream().map(a -> new UserResponseE(
                a.getId(),
                a.getLastName(),
                a.getFirstName(),
                a.getPatronymic(),
                a.getLogin(),
                a.getPassword(),
                a.getCountry(),
                a.getSex(),
                a.getBirthday())).collect(Collectors.toList());
    }

    public List<SessionResponseE> getAllSession() throws ServerException {
        LOGGER.info("update all users ");
        List<SessionE> list = getSessionDao().getAll();
        return list.stream().map(a -> new SessionResponseE(a.getUserId(), a.getToken())).collect(Collectors.toList());
    }

    public SessionResponseE getSessionById(int id) throws ServerException {
        SessionE user1 = getSessionDao().getById(id);
        SessionResponseE sessionDto = new SessionResponseE(user1.getUserId(), user1.getToken());
        sessionDto.setId(user1.getId());
        return sessionDto;
    }
    public SessionResponseE getSessionByToken(String token) throws ServerException {
        SessionE user1 = getSessionDao().getByToken(token);
        SessionResponseE sessionDto = new SessionResponseE(user1.getUserId(), user1.getToken());
        sessionDto.setId(user1.getId());
        return sessionDto;
    }
    public void deleteUser(UserResponseE user) throws ServerException {
        LOGGER.info("Delete user " + user);
        UserE use = new UserE( user.getLastName(), user.getFirstName(), user.getPatronymic(), user.getLogin(), user.getPassword(), user.getCountry(),user.getSex(),user.getBirthday());
        use.setId(user.getId());
        getUserDao().delete(use);
    }

    public void deleteAllUser() throws ServerException {
        LOGGER.info("Delete All users ");
        getUserDao().deleteAll();
    }

    public void deleteAllSessions() throws ServerException {
        LOGGER.info("Delete All sessions ");
        getSessionDao().deleteAll();
    }

}
