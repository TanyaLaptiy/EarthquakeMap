package com.lineate.buscompany.daoimplE;

import com.lineate.buscompany.daoE.RequestDaoE;
import com.lineate.buscompany.database.mappers.RequestMapperE;
import com.lineate.buscompany.errors.ErrorCodeApplication;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component

public class RequestDaoImplE extends DaoImplBaseE implements RequestDaoE {
    private static final Logger LOGGER = LoggerFactory.getLogger(DaoImplBaseE.class);
    private final RequestMapperE requestMapper;

    public RequestDaoImplE(RequestMapperE requestMapper) {
        this.requestMapper = requestMapper;
    }

    @Override
    public Request insert(Request request) throws ServerException {
        LOGGER.debug("DAO insert request {}", request);
        try {
            requestMapper.insert(request);

        } catch (RuntimeException ex) {
            LOGGER.info("Can't insert  User {} {}", request, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_INSETR);
        }
        return request;
    }

    @Override
    public Request getById(int id) throws ServerException {
        LOGGER.debug("DAO get by id {}", id);
        try {
            return requestMapper.getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get by id {} {}", id, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public List<Request> getByClientId(int id) throws ServerException {
        LOGGER.debug("DAO get by client id {}", id);
        try {
            System.out.println("######### DAO "+id);

            List<Request> res= requestMapper.getByClientId(id);
            System.out.println("######### DAO "+res.size());

            return res;
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get by client id {} {}", id, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public List<Request> getByStatus(String status) throws ServerException {
        LOGGER.debug("DAO get by status id {}", status);
        try {
            return requestMapper.getByStatus(status);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get by status id {} {}", status, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public List<Request> getByData(LocalDate data) throws ServerException {
        LOGGER.debug("DAO get by data id {}", data);
        try {
            return requestMapper.getByData(data);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get by client id {} {}", data, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public String getStatusByClientId(int id) throws ServerException {
        LOGGER.debug("DAO get status by client id {}", id);
        try {
            return requestMapper.getStatusByClientId(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get status by client id {} {}", id, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public void approve(Request request) throws ServerException {
        LOGGER.debug("DAO approve {}", request);
        try {
            System.out.println("******88 "+request.getId());

            System.out.println("******88 "+request.getStatus());
            requestMapper.update(request);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't approve {} {}", request, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public Request getClone(Request earthquake) throws ServerException {
        LOGGER.debug("DAO get Clone  {}", earthquake);
        try {
            return requestMapper.getClone(earthquake);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Clone  {} {}", earthquake, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public void delete(Request request) throws ServerException {
        LOGGER.debug("DAO delete request {}", request);
        try {
            requestMapper.delete(request);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't delete request {} {}", request, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_DELETE);
        }
    }

    @Override
    public List<Request> getAll() throws ServerException {
        LOGGER.debug("DAO get all ");
        try {
            return requestMapper.getAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get all  {}", ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public void deleteAll() throws ServerException {
        LOGGER.debug("DAO delete all Trainee {}");
        try {
            requestMapper.deleteAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't delete all Trainee {}", ex);
            throw new ServerException(ErrorCodeApplication.ERROR_DELETE);
        }
    }
}
