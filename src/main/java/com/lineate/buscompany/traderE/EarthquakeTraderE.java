package com.lineate.buscompany.traderE;

import com.lineate.buscompany.Config;
import com.lineate.buscompany.daoE.*;
import com.lineate.buscompany.dtoE.requestE.EarthquakeRequest;
import com.lineate.buscompany.dtoE.responseE.EarthquakeResponse;
import com.lineate.buscompany.errors.ErrorCodeApplication;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.Earthquake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class EarthquakeTraderE extends ServiceBaseE {
    private static final Logger LOGGER = LoggerFactory.getLogger(EarthquakeTraderE.class);
    private static Connection connection;
    ConnectionE connectionE = new ConnectionE();

    public EarthquakeTraderE(UserDaoE userDao, ClientDaoE clientDao, AdministratorDaoE administratorDao, RequestDaoE requestDao, EarthquakeDaoE earthquakeDao, SmsDaoE smsDao, RatingDaoE ratingDao, SessionDaoE sessionDao, Config config) {
        super(userDao, clientDao, administratorDao, requestDao, earthquakeDao, smsDao, ratingDao, sessionDao, config);
    }

    public EarthquakeResponse createRequest(EarthquakeRequest request) throws ServerException {
        EarthquakeResponse response = new EarthquakeResponse();
        connectionE.setConnection();
        try {
            connection = DriverManager.getConnection(connectionE.getURL(), connectionE.getUSER(), connectionE.getPASSWORD());
            connection.setAutoCommit(false);
            LOGGER.info("Create request " + request.toString());


            Earthquake earthquake = new Earthquake(
                    request.getLatitude(),
                    request.getLongitude(),
                    request.getMagnitude(),
                    request.getLocation(),
                    request.getAge(),
                    request.getDepth(),
                    request.getOnland(),
                    request.getCountry(),
                    request.getData()
            );
            if (check(earthquake)) {
                throw new ServerException(ErrorCodeApplication.ERROR_INSETR);
            }

            Earthquake res = getEarthquakeDao().insert(earthquake);

            response = new EarthquakeResponse(
                    res.getId(),
                    res.getLatitude(),
                    res.getLongitude(),
                    res.getMagnitude(),
                    res.getLocation(),
                    res.getAge(),
                    res.getDepth(),
                    res.getOnland(),
                    res.getCountry(),
                    res.getData()
            );
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;
    }

    public boolean check(Earthquake request) throws ServerException {
        LOGGER.info("Create request " + request.toString());

        boolean check = true;

        try {
            getEarthquakeDao().getClone(request).getId();
        } catch (NullPointerException ex) {
            check = false;
        }

        return check;

    }

    public EarthquakeResponse getById(int id) throws ServerException {
        LOGGER.info("get Earthquake by id " + id);
        Earthquake res = getEarthquakeDao().getById(id);
        EarthquakeResponse response = new EarthquakeResponse(
                id,
                res.getLatitude(),
                res.getLongitude(),
                res.getMagnitude(),
                res.getLocation(),
                res.getAge(),
                res.getDepth(),
                res.getOnland(),
                res.getCountry(),
                res.getData()
        );
        return response;
    }

    public List<EarthquakeResponse> getAll() throws ServerException {
        LOGGER.info("update all users ");
        List<Earthquake> list = getEarthquakeDao().getAll();
        return list.stream().map(a -> new EarthquakeResponse(
                a.getId(),
                a.getLatitude(),
                a.getLongitude(),
                a.getMagnitude(),
                a.getLocation(),
                a.getAge(),
                a.getDepth(),
                a.getOnland(),
                a.getCountry(),
                a.getData())).collect(Collectors.toList());
    }

    public void deleteEarthquake(EarthquakeResponse request) throws ServerException {
        LOGGER.info("Delete Earthquake " + request);
        Earthquake earthquake = new Earthquake(
                request.getLatitude(),
                request.getLongitude(),
                request.getMagnitude(),
                request.getLocation(),
                request.getAge(),
                request.getDepth(),
                request.getOnland(),
                request.getCountry(),
                request.getData()
        );
        earthquake.setId(request.getId());
        getEarthquakeDao().delete(earthquake);
    }

    public List<EarthquakeResponse> getByData(LocalDate data) throws ServerException {
        List<Earthquake> list = new ArrayList<>();
        connectionE.setConnection();
        try {
            connection = DriverManager.getConnection(connectionE.getURL(), connectionE.getUSER(), connectionE.getPASSWORD());
            connection.setAutoCommit(false);
            LOGGER.info("get by data ");
            list = getEarthquakeDao().getByData(data);
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.stream().map(a -> new EarthquakeResponse(
                a.getId(),
                a.getLatitude(),
                a.getLongitude(),
                a.getMagnitude(),
                a.getLocation(),
                a.getAge(),
                a.getDepth(),
                a.getOnland(),
                a.getCountry(),
                a.getData())).collect(Collectors.toList());
    }

    public List<EarthquakeResponse> getByCounrtyData(String country, LocalDate data) throws ServerException {
        LOGGER.info("get by country data ");
        List<Earthquake> list = getEarthquakeDao().getByCounrtyData(country, data);

        return list.stream().map(a -> new EarthquakeResponse(
                a.getId(),
                a.getLatitude(),
                a.getLongitude(),
                a.getMagnitude(),
                a.getLocation(),
                a.getAge(),
                a.getDepth(),
                a.getOnland(),
                a.getCountry(),
                a.getData())).collect(Collectors.toList());
    }

    public void deleteAllEarthquakes() throws ServerException {
        LOGGER.info("Delete All Earthquakes ");
        getEarthquakeDao().deleteAll();
    }

}
