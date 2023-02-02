package com.lineate.buscompany.traderE;

import com.lineate.buscompany.Config;
import com.lineate.buscompany.daoE.*;
import com.lineate.buscompany.dtoE.requestE.RequestRequest;
import com.lineate.buscompany.dtoE.responseE.RequestResponse;
import com.lineate.buscompany.errors.ErrorCodeApplication;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.ClientE;
import com.lineate.buscompany.modelsE.Earthquake;
import com.lineate.buscompany.modelsE.Request;
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
public class RequestTraderE extends ServiceBaseE {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestTraderE.class);
    private static Connection connection;
    ConnectionE connectionE = new ConnectionE();

    public RequestTraderE(UserDaoE userDao, ClientDaoE clientDao, AdministratorDaoE administratorDao, RequestDaoE requestDao, EarthquakeDaoE earthquakeDao, SmsDaoE smsDao, RatingDaoE ratingDao, SessionDaoE sessionDao, Config config) {
        super(userDao, clientDao, administratorDao, requestDao, earthquakeDao, smsDao, ratingDao, sessionDao, config);
    }

    public RequestResponse createRequest(RequestRequest request) throws ServerException {
        RequestResponse response = new RequestResponse();
        connectionE.setConnection();
        try {
            connection = DriverManager.getConnection(connectionE.getURL(), connectionE.getUSER(), connectionE.getPASSWORD());
            connection.setAutoCommit(false);
            LOGGER.info("Create request " + request.toString());

            Request request1 = new Request(
                    request.getClientId(),
                    request.getData(),
                    request.getTitle(),
                    request.getMessage(),
                    request.getLatitude(),
                    request.getLongitude(),
                    request.getMagnitude(),
                    request.getDepth(),
                    request.getAge(),
                    request.getOnland(),
                    request.getCountry(),
                    request.getStatus(),
                    request.getRequestData());
            if (check(request1)) {
                throw new ServerException(ErrorCodeApplication.ERROR_INSETR);
            }
            Request res = getRequestDao().insert(request1);

            response = new RequestResponse(
                    res.getId(),
                    res.getClientId(),
                    res.getData(),
                    res.getTitle(),
                    res.getMessage(),
                    res.getLatitude(),
                    res.getLongitude(),
                    res.getMagnitude(),
                    res.getDepth(),
                    res.getAge(),
                    res.getOnland(),
                    res.getCountry(),
                    res.getStatus(),
                    res.getRequestData()
            );
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;

    }

    public boolean check(Request request) throws ServerException {
        LOGGER.info("Create request " + request.toString());

        boolean check = true;
        try {
            getRequestDao().getClone(request).getId();
        } catch (NullPointerException ex) {
            check = false;
        }

        return check;

    }

    public RequestResponse getById(int id) throws ServerException {
        LOGGER.info("get user by id " + id);
        Request res = getRequestDao().getById(id);
        RequestResponse response = new RequestResponse(
                id,
                res.getClientId(),
                res.getData(),
                res.getTitle(),
                res.getMessage(),
                res.getLatitude(),
                res.getLongitude(),
                res.getMagnitude(),
                res.getDepth(),
                res.getAge(),
                res.getOnland(),
                res.getCountry(),
                res.getStatus(),
                res.getRequestData()
        );
        return response;
    }


    public List<RequestResponse> getAll() throws ServerException {
        LOGGER.info("update all users ");
        List<Request> list = getRequestDao().getAll();
        return list.stream().map(a -> new RequestResponse(
                a.getId(),
                a.getClientId(),
                a.getData(),
                a.getTitle(),
                a.getMessage(),
                a.getLatitude(),
                a.getLongitude(),
                a.getMagnitude(),
                a.getDepth(),
                a.getAge(),
                a.getOnland(),
                a.getCountry(),
                a.getStatus(),
                a.getRequestData())).collect(Collectors.toList());
    }

    public void deleteRequest(RequestResponse request) throws ServerException {
        LOGGER.info("Delete user " + request);
        Request request1 = new Request(
                request.getClientId(),
                request.getData(),
                request.getTitle(),
                request.getMessage(),
                request.getLatitude(),
                request.getLongitude(),
                request.getMagnitude(),
                request.getDepth(),
                request.getAge(),
                request.getOnland(),
                request.getCountry(),
                request.getStatus(),
                request.getRequestData());
        request1.setId(request.getId());
        getRequestDao().delete(request1);
    }

    public List<RequestResponse> getByClientId(int id) throws ServerException {
        LOGGER.info("get by client ");
        List<Request> list = getRequestDao().getByClientId(id);

        return list.stream().map(a -> new RequestResponse(
                a.getId(),
                a.getClientId(),
                a.getData(),
                a.getTitle(),
                a.getMessage(),
                a.getLatitude(),
                a.getLongitude(),
                a.getMagnitude(),
                a.getDepth(),
                a.getAge(),
                a.getOnland(),
                a.getCountry(),
                a.getStatus(),
                a.getRequestData())).collect(Collectors.toList());
    }

    public List<RequestResponse> getByStatus(String status) throws ServerException {
        LOGGER.info("get by status ");
        List<Request> list = getRequestDao().getByStatus(status);

        return list.stream().map(a -> new RequestResponse(
                a.getId(),
                a.getClientId(),
                a.getData(),
                a.getTitle(),
                a.getMessage(),
                a.getLatitude(),
                a.getLongitude(),
                a.getMagnitude(),
                a.getDepth(),
                a.getAge(),
                a.getOnland(),
                a.getCountry(),
                a.getStatus(),
                a.getRequestData()
        )).collect(Collectors.toList());
    }

    public List<RequestResponse> getByData(LocalDate data) throws ServerException {
        LOGGER.info("get by data ");
        List<Request> list = getRequestDao().getByData(data);

        return list.stream().map(a -> new RequestResponse(
                a.getId(),
                a.getClientId(),
                a.getData(),
                a.getTitle(),
                a.getMessage(),
                a.getLatitude(),
                a.getLongitude(),
                a.getMagnitude(),
                a.getDepth(),
                a.getAge(),
                a.getOnland(),
                a.getCountry(),
                a.getStatus(),
                a.getRequestData()
        )).collect(Collectors.toList());
    }

    public String getStatusByClientId(int id) throws ServerException {
        LOGGER.info("get by data ");
        String res = getRequestDao().getStatusByClientId(id);
        return res;
    }

    public void approve(RequestResponse request) throws ServerException {
        connectionE.setConnection();
        try {
            connection = DriverManager.getConnection(connectionE.getURL(), connectionE.getUSER(), connectionE.getPASSWORD());
            connection.setAutoCommit(false);
            LOGGER.info("approve");
            Request request1 = new Request(
                    request.getClientId(),
                    request.getData(),
                    request.getTitle(),
                    request.getMessage(),
                    request.getLatitude(),
                    request.getLongitude(),
                    request.getMagnitude(),
                    request.getDepth(),
                    request.getAge(),
                    request.getOnland(),
                    request.getCountry(),
                    "approve",
                    request.getRequestData());
            request1.setId(request.getId());
            Earthquake earthquake = new Earthquake(
                    request.getLatitude(),
                    request.getLongitude(),
                    request.getMagnitude(),
                    null,
                    request.getAge(),
                    request.getDepth(),
                    request.getOnland(),
                    request.getCountry(),
                    request.getData()
            );
            getRequestDao().approve(request1);
            getEarthquakeDao().insert(earthquake);
            ClientE client = getClientDao().getById(request.getClientId());
            List<String> mails = new ArrayList<>();
            mails.add(client.getMail());
            Mail mail = new Mail();
            String text = "Your request has been approved";
            mail.sendMail(mails, text);
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reject(RequestResponse request) throws ServerException {
        connectionE.setConnection();
        try {
            connection = DriverManager.getConnection(connectionE.getURL(), connectionE.getUSER(), connectionE.getPASSWORD());
            connection.setAutoCommit(false);
        LOGGER.info("reject");
        Request request1 = new Request(
                request.getClientId(),
                request.getData(),
                request.getTitle(),
                request.getMessage(),
                request.getLatitude(),
                request.getLongitude(),
                request.getMagnitude(),
                request.getDepth(),
                request.getAge(),
                request.getOnland(),
                request.getCountry(),
                "reject",
                request.getRequestData());
        request1.setId(request.getId());
        getRequestDao().approve(request1);
        ClientE client = getClientDao().getById(request.getClientId());
        List<String> mails = new ArrayList<>();
        mails.add(client.getMail());
        Mail mail = new Mail();
        String text = "Your request has been rejected";
        mail.sendMail(mails, text);
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteAllRequests() throws ServerException {
        LOGGER.info("Delete All Requests ");
        getRequestDao().deleteAll();
    }

}
