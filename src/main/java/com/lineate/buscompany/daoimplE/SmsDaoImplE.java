package com.lineate.buscompany.daoimplE;

import com.lineate.buscompany.daoE.SmsDaoE;
import com.lineate.buscompany.database.mappers.SmsMapperE;
import com.lineate.buscompany.errors.ErrorCodeApplication;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.Sms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component

public class SmsDaoImplE extends DaoImplBaseE implements SmsDaoE {
    private static final Logger LOGGER = LoggerFactory.getLogger(DaoImplBaseE.class);
    private final SmsMapperE smsMapper;

    public SmsDaoImplE(SmsMapperE smsMapper) {
        this.smsMapper = smsMapper;
    }

    @Override
    public Sms insert(Sms sms) throws ServerException {
        LOGGER.debug("DAO insert sms {}", sms);
        try {
            smsMapper.insert(sms);

        } catch (RuntimeException ex) {
            LOGGER.info("Can't insert  sms {} {}", sms, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_INSETR);
        }
        return sms;
    }

    @Override
    public Sms getById(int id) throws ServerException {
        LOGGER.debug("DAO get by id {}", id);
        try {
            return smsMapper.getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get by id {} {}", id, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public void delete(Sms sms) throws ServerException {
        LOGGER.debug("DAO delete Sms {}", sms);
        try {
            smsMapper.delete(sms);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't delete sms {} {}", sms, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_DELETE);
        }
    }

    @Override
    public List<Sms> getAll() throws ServerException {
        LOGGER.debug("DAO get all ");
        try {
            return smsMapper.getAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get all  {}", ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public List<Sms> getByClientId(int id) throws ServerException {
        LOGGER.debug("DAO get by id {}", id);
        try {
            return smsMapper.getByClientId(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get by id {} {}", id, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public List<Sms> getByData(LocalDate data) throws ServerException {
        LOGGER.debug("DAO get by data {}", data);
        try {
            return smsMapper.getByData(data);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get by data {} {}", data, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public void deleteAll() throws ServerException {
        LOGGER.debug("DAO delete all Sms {}");
        try {
            smsMapper.deleteAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't delete all Sms {}", ex);
            throw new ServerException(ErrorCodeApplication.ERROR_DELETE);
        }
    }
}
