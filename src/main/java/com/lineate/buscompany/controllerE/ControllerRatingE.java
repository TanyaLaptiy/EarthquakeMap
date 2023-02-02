package com.lineate.buscompany.controllerE;

import com.lineate.buscompany.dtoE.requestE.RatingRequestE;
import com.lineate.buscompany.dtoE.requestE.UserRequestE;
import com.lineate.buscompany.dtoE.responseE.RatingResponseE;
import com.lineate.buscompany.dtoE.responseE.SessionResponseE;
import com.lineate.buscompany.dtoE.responseE.UserResponseE;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.traderE.RatingTraderE;
import com.lineate.buscompany.traderE.UserTraderE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ControllerRatingE {
    private final RatingTraderE ratingTrader;

    @Autowired
    public ControllerRatingE(RatingTraderE ratingTrader) {
        this.ratingTrader = ratingTrader;
    }

    @PostMapping(value = "/ratingsE", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RatingResponseE createRating(@RequestBody @Valid RatingRequestE ratingRequestE) throws ServerException {
        return ratingTrader.createRating(ratingRequestE);
    }
    @GetMapping(value = "/averages")
    public int getAverage() throws ServerException {
        return ratingTrader.getAverage();
    }
    @GetMapping(value = "/ratingsE/{id}")
    public RatingResponseE getById(@PathVariable("id") int id) throws ServerException {
        return ratingTrader.getById(id);
    }
    @GetMapping(value = "/client/ratingsE/{id}")
    public RatingResponseE getByClirntId(@PathVariable("id") int id) throws ServerException {
        return ratingTrader.getByClirntId(id);
    }

    @GetMapping(value = "/AllratingsE")
    public List<RatingResponseE> getAllRatings() throws ServerException {
        return ratingTrader.getAll();
    }

    @DeleteMapping(value = "/deleteAll/ratingsE")
    public ResponseEntity<HttpStatus> deleteAllRatings() throws ServerException {
        ratingTrader.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
