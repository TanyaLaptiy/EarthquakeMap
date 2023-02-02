package com.lineate.buscompany.traderE;

import com.lineate.buscompany.Config;
import com.lineate.buscompany.daoE.*;
import com.lineate.buscompany.dtoE.requestE.SmsRequest;
import com.lineate.buscompany.dtoE.responseE.SmsResponse;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.Sms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class SmsTraderE  extends ServiceBaseE{
    private static final Logger LOGGER = LoggerFactory.getLogger(SmsTraderE.class);
    public SmsTraderE(
            UserDaoE userDao,
            ClientDaoE clientDao,
            AdministratorDaoE administratorDao,
            RequestDaoE requestDao,
            EarthquakeDaoE earthquakeDao,
            SmsDaoE smsDao,
            RatingDaoE ratingDao,
            SessionDaoE sessionDao,
            Config config) {
        super(userDao,
                clientDao,
                administratorDao,
                requestDao,
                earthquakeDao,
                smsDao,
                ratingDao, sessionDao,
                config);
    }

    public SmsResponse createSms(SmsRequest sms) throws ServerException {
        LOGGER.info("Create sms " + sms.toString());

        Sms sms1 = new Sms(
                sms.getClientId(),
                sms.getData(),
                sms.getMessage()
        );

        Sms res = getSmsDao().insert(sms1);
        SmsResponse smsResponse = new SmsResponse(
                res.getId(),
                res.getClientId(),
                res.getData(),
                res.getMessage());

        return smsResponse;

    }

    public SmsResponse getById(int id) throws ServerException {
        LOGGER.info("get sms by id " + id);
        Sms sms = getSmsDao().getById(id);
        SmsResponse smsResponse = new SmsResponse(
                sms.getId(),
                sms.getClientId(),
                sms.getData(),
                sms.getMessage());
        return smsResponse;
    }
    public List<SmsResponse> getByClientId( int id) throws ServerException{
        LOGGER.info("get by client id sms ");
        List<Sms> list = getSmsDao().getByClientId(id);
        return list.stream().map(a -> new SmsResponse(
                a.getId(),
                a.getClientId(),
                a.getData(),
                a.getMessage())).collect(Collectors.toList());
    }

    public List<SmsResponse> getByData(LocalDate data) throws ServerException{
        LOGGER.info("get by data sms ");
        List<Sms> list = getSmsDao().getByData(data);
        return list.stream().map(a -> new SmsResponse(
                a.getId(),
                a.getClientId(),
                a.getData(),
                a.getMessage())).collect(Collectors.toList());
    }


    public List<SmsResponse> getAll() throws ServerException {
        LOGGER.info("get all sms ");
        List<Sms> list = getSmsDao().getAll();
        return list.stream().map(a -> new SmsResponse(
                a.getId(),
                a.getClientId(),
                a.getData(),
                a.getMessage())).collect(Collectors.toList());
    }

    public void deleteSms(SmsResponse sms) throws ServerException {
        LOGGER.info("Delete user " + sms);
        Sms sms1 = new Sms(
                sms.getClientId(),
                sms.getData(),
                sms.getMessage()
        );
        sms1.setId(sms.getId());
        getSmsDao().delete(sms1);
    }
    public void deleteAllSms() throws ServerException {
        LOGGER.info("Delete All sms ");
        getSmsDao().deleteAll();
    }

}
