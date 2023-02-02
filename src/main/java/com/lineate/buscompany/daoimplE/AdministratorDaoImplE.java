package com.lineate.buscompany.daoimplE;

import com.lineate.buscompany.daoE.AdministratorDaoE;
import com.lineate.buscompany.database.mappers.AdministratorMapperE;
import com.lineate.buscompany.database.mappers.SessionMapperE;
import com.lineate.buscompany.database.mappers.UserMapperE;
import com.lineate.buscompany.dtoE.requestE.LoginRequestE;
import com.lineate.buscompany.errors.ErrorCodeApplication;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.AdministratorE;
import com.lineate.buscompany.modelsE.UserE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class AdministratorDaoImplE implements AdministratorDaoE {
    private static final Logger LOGGER = LoggerFactory.getLogger(DaoImplBaseE.class);
    private final AdministratorMapperE administratorMapper;
    private final UserMapperE userMapper;
    private final SessionMapperE sessionMapper;
    public AdministratorDaoImplE(AdministratorMapperE administratorMapper, UserMapperE userMapper, SessionMapperE sessionMapper) {
        this.administratorMapper = administratorMapper;
        this.userMapper = userMapper;
        this.sessionMapper = sessionMapper;
    }

    @Override
    public AdministratorE insert(AdministratorE administrator) throws ServerException {
        LOGGER.debug("DAO insert  Admin {}", administrator);
        try {
            UserE user = new UserE(administrator.getLastName(),
                    administrator.getFirstName(),
                    administrator.getPatronymic(),
                    administrator.getLogin(),
                    administrator.getPassword(),
                    administrator.getCountry(),
                    administrator.getSex(),
                    administrator.getBirthday());

            userMapper.insert(user);
            administrator.setId(user.getId());
            administratorMapper.insert(administrator);


        } catch (RuntimeException ex) {
            LOGGER.info("Can't insert Admin {} {}", administrator, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_INSETR);
        }

        return administrator;
    }

    @Override
    public AdministratorE getByLogin(LoginRequestE login) {
        LOGGER.debug("DAO select user by login: {}", login);
        UserE user1 = userMapper.getByLogin(login.getLogin(), login.getPassword());
        AdministratorE admin = administratorMapper.getById(user1.getId());

        admin.setId(user1.getId());
        admin.setLastName(user1.getLastName());
        admin.setFirstName(user1.getFirstName());
        admin.setLogin(user1.getLogin());
        admin.setPassword(user1.getPassword());
        admin.setPatronymic(user1.getPatronymic());
        admin.setSex(user1.getSex());
        admin.setBirthday(user1.getBirthday());
        admin.setCountry(user1.getCountry());
        return admin;
    }

    @Override
    public void deleteSessionByToken(String token) {
        LOGGER.debug("DAO delete session by token: {}", token);
        sessionMapper.deleteByToken(token);
    }

    @Override
    public AdministratorE getById(int id) throws ServerException {
        LOGGER.debug("DAO get by id {}", id);
        try {
            UserE user = new UserE();
            AdministratorE administrator = new AdministratorE();
            user = userMapper.getById(id);
            String pos = administratorMapper.getPositionById(id);
            administrator.setLastName(user.getLastName());
            administrator.setFirstName(user.getFirstName());
            administrator.setLogin(user.getLogin());
            administrator.setPassword(user.getPassword());
            administrator.setPatronymic(user.getPatronymic());
            administrator.setCountry(user.getCountry());
            administrator.setSex(user.getSex());
            administrator.setBirthday(user.getBirthday());
            administrator.setPosition(pos);

            return administrator;
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get by id {} {}", id, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public void deleteAll() throws ServerException {
        LOGGER.debug("DAO delete all administrator {}");
        try {
            administratorMapper.deleteAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't delete all administrator {}", ex);
            throw new ServerException(ErrorCodeApplication.ERROR_DELETE);
        }

    }


    @Override
    public List<AdministratorE> getAll() throws ServerException {
        LOGGER.debug("DAO get all ");
        try {

            return administratorMapper.getAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get all  {}", ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }
}
