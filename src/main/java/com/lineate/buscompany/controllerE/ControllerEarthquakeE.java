package com.lineate.buscompany.controllerE;

import com.lineate.buscompany.dtoE.requestE.EarthquakeRequest;
import com.lineate.buscompany.dtoE.requestE.EarthquakeRequestCD;
import com.lineate.buscompany.dtoE.requestE.RequestRequest;
import com.lineate.buscompany.dtoE.responseE.EarthquakeResponse;
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
public class ControllerEarthquakeE {
    private final EarthquakeTraderE earthquakeTrader;
    @Autowired
    public ControllerEarthquakeE(EarthquakeTraderE earthquakeTrader) {
        this.earthquakeTrader = earthquakeTrader;
    }

    @PostMapping(value = "/earthquakeE", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public EarthquakeResponse createRequest(@RequestBody @Valid EarthquakeRequest request) throws ServerException {
        return earthquakeTrader.createRequest(request);
    }

    @GetMapping(value = "/earthquakeE/{id}")
    public EarthquakeResponse getById(@PathVariable("id") int id) throws ServerException {
        return earthquakeTrader.getById(id);
    }

    @GetMapping(value = "/AllEarthquakesE")
    public List<EarthquakeResponse> getAll() throws ServerException {

        return earthquakeTrader.getAll();
    }

    @DeleteMapping(value = "/delete/earthquakeE/{id}")
    public ResponseEntity<HttpStatus> deleteRequests(@PathVariable("id") int id) throws ServerException {
        EarthquakeResponse response= earthquakeTrader.getById(id);
        earthquakeTrader.deleteEarthquake(response);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping(value = "/earthquakeE/data")
    public List<EarthquakeResponse> getByData(@RequestBody @Valid LocalDate data) throws ServerException {
        return earthquakeTrader.getByData(data);
    }

    @PostMapping(value = "/earthquakeE/CountryData")
    public List<EarthquakeResponse> getByCounrtyData(@RequestBody @Valid EarthquakeRequestCD request) throws ServerException {
        return earthquakeTrader.getByCounrtyData(request.getCountry(),request.getData());
    }

    @DeleteMapping(value = "/earthquakeE/deleteAll")
    public ResponseEntity<HttpStatus> deleteAll() throws ServerException {
        earthquakeTrader.deleteAllEarthquakes();
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
