package com.lineate.buscompany.daoimplE;

import com.lineate.buscompany.daoE.UserDaoE;
import com.lineate.buscompany.database.mappers.SessionMapperE;
import com.lineate.buscompany.database.mappers.UserMapperE;
import com.lineate.buscompany.errors.ErrorCodeApplication;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.SessionE;
import com.lineate.buscompany.modelsE.UserE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoImplE extends DaoImplBaseE implements UserDaoE {
    private static final Logger LOGGER = LoggerFactory.getLogger(DaoImplBaseE.class);
    private final UserMapperE userMapper;
    private final SessionMapperE sessionMapper;

    public UserDaoImplE(UserMapperE userMapper, SessionMapperE sessionMapper) {
        this.userMapper = userMapper;
        this.sessionMapper = sessionMapper;
    }

    @Override
    public UserE insert(UserE user) throws ServerException {
        LOGGER.debug("DAO insert User {}", user);
        try {
            userMapper.insert(user);

        } catch (RuntimeException ex) {
            LOGGER.info("Can't insert  User {} {}", user, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_INSETR);
        }
        return user;
    }

    @Override
    public void deleteSessionByToken(String token) {
        LOGGER.debug("DAO delete session by token: {}", token);
        sessionMapper.deleteByToken(token);
    }

    @Override
    public UserE getById(int id) throws ServerException {
        LOGGER.debug("DAO get by id {}", id);
        try {
            return userMapper.getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get by id {} {}", id, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public UserE getByLog(String log) throws ServerException {
        LOGGER.debug("DAO get by log {}", log);
        try {
            return userMapper.getByLog(log);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get by id {} {}", log, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }


    @Override
    public void deleteAll() throws ServerException {
        LOGGER.debug("DAO delete all Trainee {}");
        try {
            userMapper.deleteAll();
            sessionMapper.deleteAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't delete all Trainee {}", ex);
            throw new ServerException(ErrorCodeApplication.ERROR_DELETE);
        }

    }

    @Override
    public void delete(UserE user) throws ServerException {
        LOGGER.debug("DAO delete User {}", user);
        try {
            userMapper.delete(user);
            sessionMapper.deleteByUserId(user.getId());
        } catch (RuntimeException ex) {
            LOGGER.info("Can't delete User {} {}", user, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_DELETE);
        }

    }

    @Override
    public List<UserE> getAll() throws ServerException {
        LOGGER.debug("DAO get all ");
        try {
            return userMapper.getAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get all  {}", ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }


    @Override
    public SessionE getSessionByToken(String token) {
        LOGGER.debug("DAO get session by token: {}", token);
        return userMapper.getByToken(token);
    }

}
