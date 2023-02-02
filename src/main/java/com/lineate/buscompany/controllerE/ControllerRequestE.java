package com.lineate.buscompany.controllerE;

import com.lineate.buscompany.dtoE.requestE.EarthquakeRequest;
import com.lineate.buscompany.dtoE.requestE.RequestRequest;
import com.lineate.buscompany.dtoE.responseE.RequestResponse;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.traderE.EarthquakeTraderE;
import com.lineate.buscompany.traderE.RequestTraderE;
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
public class ControllerRequestE {
    private final RequestTraderE requestTrader;
    private final EarthquakeTraderE earthquakeTrader;


    @Autowired
    public ControllerRequestE(RequestTraderE requestTrader, EarthquakeTraderE earthquakeTrader) {
        this.requestTrader = requestTrader;
        this.earthquakeTrader = earthquakeTrader;
    }

    @PostMapping(value = "/requestsE", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RequestResponse createRequest(@RequestBody @Valid RequestRequest request) throws ServerException {
        return requestTrader.createRequest(request);
    }

    @GetMapping(value = "/requestsE/{id}")
    public RequestResponse getById(@PathVariable("id") int id) throws ServerException {
        return requestTrader.getById(id);
    }

    @GetMapping(value = "/AllRequestsE")
    public List<RequestResponse> getAll() throws ServerException {

        return requestTrader.getAll();
    }

    @DeleteMapping(value = "/delete/requestsE/{id}")
    public ResponseEntity<HttpStatus> deleteRequests(@PathVariable("id") int id) throws ServerException {
        RequestResponse response= requestTrader.getById(id);
        requestTrader.deleteRequest(response);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping(value = "/requestsE/client/{id}")
    public List<RequestResponse> getByClientId(@PathVariable("id") int id) throws ServerException {
        return requestTrader.getByClientId(id);
    }

    @PostMapping(value = "/requestsE/data")
    public List<RequestResponse> getByData(@RequestBody @Valid LocalDate data) throws ServerException {
        return requestTrader.getByData(data);
    }

    @GetMapping(value = "/requestsE/status/{status}")
    public List<RequestResponse> getByStatus(@PathVariable("status") String status) throws ServerException {
        return requestTrader.getByStatus(status);
    }

    @GetMapping(value = "/requestsE/status/id/{id}")
    public String getStatusByClientId(@PathVariable("id") int id) throws ServerException {
        return requestTrader.getStatusByClientId(id);
    }

    @PutMapping(value = "/requestsE/approve")
    public ResponseEntity<HttpStatus> approve(@RequestBody RequestResponse request) throws ServerException {
        requestTrader.approve(request);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @PutMapping(value = "/requestsE/reject")
    public ResponseEntity<HttpStatus> reject(@RequestBody RequestResponse request) throws ServerException {
        requestTrader.reject(request);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @DeleteMapping(value = "/requestsE/deleteAll")
    public ResponseEntity<HttpStatus> deleteAll() throws ServerException {
        requestTrader.deleteAllRequests();
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
