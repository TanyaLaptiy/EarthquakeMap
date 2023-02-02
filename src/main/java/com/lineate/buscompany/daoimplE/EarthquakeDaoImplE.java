package com.lineate.buscompany.daoimplE;

import com.lineate.buscompany.daoE.EarthquakeDaoE;
import com.lineate.buscompany.database.mappers.EarthquakeMapperE;
import com.lineate.buscompany.errors.ErrorCodeApplication;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.Earthquake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component

public class EarthquakeDaoImplE extends DaoImplBaseE implements EarthquakeDaoE {
    private static final Logger LOGGER = LoggerFactory.getLogger(DaoImplBaseE.class);
    private final EarthquakeMapperE earthquakeMapper;

    public EarthquakeDaoImplE(EarthquakeMapperE earthquakeMapper) {
        this.earthquakeMapper = earthquakeMapper;
    }


    @Override
    public Earthquake insert(Earthquake earthquake) throws ServerException {
        LOGGER.debug("DAO insert earthquake {}", earthquake);
        try {
            earthquakeMapper.insert(earthquake);

        } catch (RuntimeException ex) {
            LOGGER.info("Can't insert  earthquake {} {}", earthquake, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_INSETR);
        }
        return earthquake;
    }

    @Override
    public Earthquake getById(int id) throws ServerException {
        LOGGER.debug("DAO get by id {}", id);
        try {
            return earthquakeMapper.getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get by id {} {}", id, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public List<Earthquake> getByData(LocalDate data) throws ServerException {
        LOGGER.debug("DAO get by data  {}", data);
        try {
            return earthquakeMapper.getByData(data);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get by data  {} {}", data, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public List<Earthquake> getByCounrtyData(String country, LocalDate data) throws ServerException {
        LOGGER.debug("DAO get by country data  {}", data);
        try {
            return earthquakeMapper.getByCounrtyData(country, data);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get by country data  {} {}", data, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public Earthquake getClone(Earthquake earthquake) throws ServerException {
        LOGGER.debug("DAO get Clone  {}", earthquake);
        try {
            return earthquakeMapper.getClone(earthquake);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Clone  {} {}", earthquake, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public void delete(Earthquake earthquake) throws ServerException {
        LOGGER.debug("DAO delete earthquake {}", earthquake);
        try {
            earthquakeMapper.delete(earthquake);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't delete request {} {}", earthquake, ex);
            throw new ServerException(ErrorCodeApplication.ERROR_DELETE);
        }
    }

    @Override
    public List<Earthquake> getAll() throws ServerException {
        LOGGER.debug("DAO get all ");
        try {
            return earthquakeMapper.getAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get all  {}", ex);
            throw new ServerException(ErrorCodeApplication.ERROR_GET);
        }
    }

    @Override
    public void deleteAll() throws ServerException {
        LOGGER.debug("DAO delete all Earthquake {}");
        try {
            earthquakeMapper.deleteAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't delete all Earthquake {}", ex);
            throw new ServerException(ErrorCodeApplication.ERROR_DELETE);
        }
    }
}
