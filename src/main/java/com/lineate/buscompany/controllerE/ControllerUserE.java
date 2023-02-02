package com.lineate.buscompany.controllerE;


import com.lineate.buscompany.dtoE.requestE.UserRequestE;
import com.lineate.buscompany.dtoE.responseE.SessionResponseE;
import com.lineate.buscompany.dtoE.responseE.UserResponseE;
import com.lineate.buscompany.errors.ServerException;
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
public class ControllerUserE {
    private final UserTraderE userTrader;
    @Autowired
    public ControllerUserE(UserTraderE userTrader) {
        this.userTrader = userTrader;
    }

    @PostMapping(value = "/usersE", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseE createUser(@RequestBody @Valid UserRequestE userDTO) throws ServerException {
        return userTrader.createUser(userDTO);
    }
    @GetMapping(value = "/usersE/{id}")
    public UserResponseE getByIdUser(@PathVariable("id") int userId) throws ServerException {
        return userTrader.getById(userId);
    }

    @GetMapping(value = "/usersByLogE/{log}")
    public UserResponseE getByLog(@PathVariable("log") String log) throws ServerException {
        return userTrader.getByLog(log);
    }
    @DeleteMapping(value = "/usersE/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int userId) throws ServerException {
        userTrader.deleteUser(userTrader.getById(userId));
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @GetMapping(value = "/usersE")
    public List<UserResponseE> getAllUser() throws ServerException {
        return userTrader.getAll();
    }

    @GetMapping(value = "/sessionsE")
    public List<SessionResponseE> getAllSessions() throws ServerException {
        return userTrader.getAllSession();
    }

    @GetMapping(value = "/sessionsE/{id}")
    public SessionResponseE getSessionById(@PathVariable("id") int id) throws ServerException {
        return userTrader.getSessionById(id);
    }
    @GetMapping(value = "/sessionsE/token/{token}")
    public SessionResponseE getSessionByToken(@PathVariable("token") String token) throws ServerException {
        return userTrader.getSessionByToken(token);
    }
}
