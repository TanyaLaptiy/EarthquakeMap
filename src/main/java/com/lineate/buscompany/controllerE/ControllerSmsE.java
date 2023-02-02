package com.lineate.buscompany.controllerE;

import com.lineate.buscompany.dtoE.requestE.SmsRequest;
import com.lineate.buscompany.dtoE.responseE.SmsResponse;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.traderE.SmsTraderE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ControllerSmsE {
    private final SmsTraderE smsTrader;

    @Autowired
    public ControllerSmsE(SmsTraderE smsTrader) {
        this.smsTrader = smsTrader;
    }

    @PostMapping(value = "/smsE", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public SmsResponse createUser(@RequestBody @Valid SmsRequest sms) throws ServerException {
        return smsTrader.createSms(sms);
    }
    @GetMapping(value = "/smsE/{id}")
    public SmsResponse getById(@PathVariable("id") int id) throws ServerException {
        return smsTrader.getById(id);
    }
    @GetMapping(value = "/smsE/client/{id}")
    public List<SmsResponse> getByClientId(@PathVariable("id") int id) throws ServerException {
        return smsTrader.getByClientId(id);
    }

    @PostMapping(value = "/smsE/data")
    public List<SmsResponse> getByData(@RequestBody @Valid LocalDate data) throws ServerException {
        return smsTrader.getByData(data);
    }

    @DeleteMapping(value = "/smsE/{id}")
    public ResponseEntity<HttpStatus> deleteSms(@PathVariable("id") int id) throws ServerException {
        smsTrader.deleteSms(smsTrader.getById(id));
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping(value = "/deleteAll/smsE")
    public ResponseEntity<HttpStatus> deleteAllSms() throws ServerException {
        smsTrader.deleteAllSms();
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @GetMapping(value = "/AllsmsE")
    public List<SmsResponse> getAllSms() throws ServerException {
        return smsTrader.getAll();
    }


}
