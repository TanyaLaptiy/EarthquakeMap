package com.lineate.buscompany.daoimplE;

import com.lineate.buscompany.daoE.RatingDaoE;
import com.lineate.buscompany.database.mappers.RatingMapperE;

import com.lineate.buscompany.errors.ErrorCodeApplication;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.Rating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class RatingDaoImplE extends DaoImplBaseE implements RatingDaoE {
    private static final Logger LOGGER = LoggerFactory.getLogger(DaoImplBaseE.class);
    private final RatingMapperE ratingMapper;

    public RatingDaoImplE(RatingMapperE ratingMapper) {
        this.ratingMapper = ratingMapper;
    }


    @Override
    public Rating insert(Rating rating) throws ServerException {
        LOGGER.debug("DAO insert rating {}", rating);
        try {
            ratingMapper.insert(rating);

        } catch (RuntimeException ex) {
            LOGGER.info("Can't insert  rating {} {}", rating, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_INSETR);
        }
        return rating;
    }

    @Override
    public Rating getById(int id) throws ServerException {
        LOGGER.debug("DAO get by id {}", id);
        try {
            return ratingMapper.getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get by id {} {}", id, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public Rating getByClientId(int id) throws ServerException {
        LOGGER.debug("DAO get by client id {}", id);
        try {
            return ratingMapper.getByClientId(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get by client id {} {}", id, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public List<Rating> getAll() throws ServerException {
        LOGGER.debug("DAO get all ");
        try {
            return ratingMapper.getAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get all  {}", ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public void deleteAll() throws ServerException {
        LOGGER.debug("DAO delete all  {}");
        try {
            ratingMapper.deleteAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't delete all  {}", ex);
            throw new ServerException(ErrorCodeApplication.ERROR_DELETE);
        }
    }
}
