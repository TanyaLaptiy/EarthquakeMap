package com.lineate.buscompany.traderE;

import com.lineate.buscompany.Config;
import com.lineate.buscompany.daoE.*;
import com.lineate.buscompany.errors.ErrorCodeApplication;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.SessionE;
import com.lineate.buscompany.modelsE.UserE;
import lombok.Data;
import org.springframework.core.NestedRuntimeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Data
@Service
@Transactional(rollbackFor = {NestedRuntimeException.class, ServerException.class})
public class ServiceBaseE {
    protected final static String COOKIE_NAME = "JAVASESSIONID";
    private final UserDaoE userDao;
    private final ClientDaoE clientDao;
    private final AdministratorDaoE administratorDao;
    private final RequestDaoE requestDao;
    private final EarthquakeDaoE earthquakeDao;
    private final SmsDaoE smsDao;
    private final RatingDaoE ratingDao;
    private final SessionDaoE sessionDao;
    private final Config config;

    public ServiceBaseE(
            UserDaoE userDao,
            ClientDaoE clientDao,
            AdministratorDaoE administratorDao,
            RequestDaoE requestDao,
            EarthquakeDaoE earthquakeDao,
            SmsDaoE smsDao,
            RatingDaoE ratingDao,
            SessionDaoE sessionDao,
            Config config) {
        this.userDao = userDao;
        this.clientDao = clientDao;
        this.administratorDao = administratorDao;
        this.requestDao = requestDao;
        this.earthquakeDao = earthquakeDao;
        this.smsDao = smsDao;
        this.ratingDao = ratingDao;
        this.sessionDao = sessionDao;
        this.config = config;
    }

    public SessionE getSession(String token) throws ServerException {
        SessionE session = userDao.getSessionByToken(token);

        if (session == null) {
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }

        return session;
    }

    public UserE getUserById(int id) throws ServerException {
        UserE user = userDao.getById(id);

        if (user == null) {
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }

        return user;
    }
}
