package com.lineate.buscompany.controllerE;

import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.traderE.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DebugControllerE {
    private final UserTraderE userTrader;
    private final RequestTraderE requestTrader;
    private final EarthquakeTraderE earthquakeTrader;
    private final SmsTraderE smsTrader;
    private final RatingTraderE ratingTrader;


    @Autowired
    public DebugControllerE(UserTraderE userTrader, RequestTraderE requestTrader, EarthquakeTraderE earthquakeTrader, SmsTraderE smsTrader, RatingTraderE ratingTrader) {
        this.userTrader = userTrader;
        this.requestTrader = requestTrader;
        this.earthquakeTrader = earthquakeTrader;
        this.smsTrader = smsTrader;
        this.ratingTrader = ratingTrader;
    }
    @DeleteMapping(value = "/deleteDB")
    public void deleteAll() throws ServerException {
        userTrader.deleteAllUser();
        userTrader.deleteAllSessions();
        requestTrader.deleteAllRequests();
        earthquakeTrader.deleteAllEarthquakes();
        smsTrader.deleteAllSms();
        ratingTrader.deleteAll();

    }
}
