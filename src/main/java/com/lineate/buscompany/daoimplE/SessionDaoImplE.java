package com.lineate.buscompany.daoimplE;

import com.lineate.buscompany.daoE.SessionDaoE;
import com.lineate.buscompany.database.mappers.SessionMapperE;
import com.lineate.buscompany.errors.ErrorCodeApplication;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.SessionE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class SessionDaoImplE extends DaoImplBaseE implements SessionDaoE {
    private static final Logger LOGGER = LoggerFactory.getLogger(DaoImplBaseE.class);
    private final SessionMapperE sessionMapper;

    public SessionDaoImplE(SessionMapperE sessionMapper) {
        this.sessionMapper = sessionMapper;
    }

    @Override
    public SessionE insert(SessionE session) throws ServerException {
        LOGGER.debug("DAO insert session {}", session);
        try {
            System.out.println("!!!!! insert "+session.getUserId() );

            sessionMapper.insert(session);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't insert  session {} {}", session, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_INSETR);
        }
        return session;
    }

    @Override
    public SessionE update(SessionE session) throws ServerException {
        LOGGER.debug("DAO update session {}", session);
        try {
            sessionMapper.update(session);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't change session {} {}", session, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_UPDATE);
        }
        List<SessionE> l = getAll();
        SessionE session1 = l.get(l.size() - 1);
        return session1;
    }

    @Override
    public void delete(SessionE session) throws ServerException {
        LOGGER.debug("DAO delete session {}", session);
        try {
            sessionMapper.delete(session);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't delete session {} {}", session, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_DELETE);
        }
    }

    @Override
    public void deleteByUserId(int session) throws ServerException {
        LOGGER.debug("DAO delete session {}", session);
        try {
            sessionMapper.deleteByUserId(session);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't delete session {} {}", session, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_DELETE);
        }
    }

    @Override
    public List<SessionE> getAll() throws ServerException {
        LOGGER.debug("DAO get all ");
        try {
            return sessionMapper.getAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get all  {}", ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public SessionE getById(int id) throws ServerException {
        LOGGER.debug("DAO get by id {}", id);
        try {
            return sessionMapper.getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get by id {} {}", id, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public SessionE getByToken(String token) throws ServerException {
        LOGGER.debug("DAO get by token {}", token);
        try {
            return sessionMapper.getByToken(token);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get by id {} {}", token, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }    }

    @Override
    public void deleteAll() throws ServerException {
        LOGGER.debug("DAO delete all sessions {}");
        try {
            sessionMapper.deleteAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't delete all sessions {}", ex);
            throw new ServerException(ErrorCodeApplication.ERROR_DELETE);
        }

    }
}
