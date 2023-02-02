package com.lineate.buscompany.traderE;

import com.lineate.buscompany.Config;
import com.lineate.buscompany.daoE.*;
import com.lineate.buscompany.dtoE.requestE.RatingRequestE;
import com.lineate.buscompany.dtoE.requestE.UserRequestE;
import com.lineate.buscompany.dtoE.responseE.RatingResponseE;
import com.lineate.buscompany.dtoE.responseE.UserResponseE;
import com.lineate.buscompany.errors.ErrorCodeApplication;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.Rating;
import com.lineate.buscompany.modelsE.UserE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service

public class RatingTraderE extends ServiceBaseE {
    private static final Logger LOGGER = LoggerFactory.getLogger(RatingTraderE.class);

    public RatingTraderE(
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
                ratingDao,
                sessionDao,
                config);
    }

    public RatingResponseE createRating(RatingRequestE rating) throws ServerException {
        LOGGER.info("Create rating " + rating.toString());
        if (rating.getMark() > 5 && rating.getMark() < 0) {
            throw new ServerException(ErrorCodeApplication.ERROR_INSETR);
        }
        Rating rating1 = new Rating(
                rating.getClientId(),
                rating.getMark(),
                rating.getMessage()
        );

        Rating res = getRatingDao().insert(rating1);
        RatingResponseE ratingResponseE = new RatingResponseE(
                res.getId(),
                res.getClientId(),
                res.getMark(),
                res.getMessage());

        return ratingResponseE;

    }


    public RatingResponseE getById(int id) throws ServerException {
        LOGGER.info("get rating by id " + id);
        Rating rating = getRatingDao().getById(id);
        RatingResponseE ratingResponseE = new RatingResponseE(
                rating.getId(),
                rating.getClientId(),
                rating.getMark(),
                rating.getMessage());
        return ratingResponseE;
    }

    public int getAverage() throws ServerException {
        LOGGER.info("get rating average " );
        List<Rating> list = getRatingDao().getAll();
        int sum=0;
        int count=0;
        for(Rating rating: list){
            sum+=rating.getMark();
            count++;
        }
        int res=0;
        if(count!=0) {
             res = sum / count;
        }
        return res;
    }
    public RatingResponseE getByClirntId(int id) throws ServerException {
        LOGGER.info("get rating by id " + id);
        Rating rating = getRatingDao().getByClientId(id);
        RatingResponseE ratingResponseE = new RatingResponseE(
                rating.getId(),
                rating.getClientId(),
                rating.getMark(),
                rating.getMessage());
        return ratingResponseE;
    }
    public List<RatingResponseE> getAll() throws ServerException {
        LOGGER.info("get all Rating ");
        List<Rating> list = getRatingDao().getAll();
        return list.stream().map(a -> new RatingResponseE(
                a.getId(),
                a.getClientId(),
                a.getMark(),
                a.getMessage())).collect(Collectors.toList());
    }

    public void deleteAll() throws ServerException {
        LOGGER.info("Delete All Rating ");
        getRatingDao().deleteAll();
    }
}
