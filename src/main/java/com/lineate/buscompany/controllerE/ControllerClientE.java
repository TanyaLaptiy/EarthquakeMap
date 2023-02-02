package com.lineate.buscompany.controllerE;


import com.lineate.buscompany.dtoE.requestE.ClientRequestE;
import com.lineate.buscompany.dtoE.responseE.ClientResponseE;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.traderE.ClientTraderE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ControllerClientE {
    private final ClientTraderE clientTrader;
    private final static String COOKIE_NAME = "JAVASESSIONID";

    @Autowired
    public ControllerClientE( ClientTraderE clientTrader) {
        this.clientTrader = clientTrader;
    }


    @PostMapping(value = "/clientsE", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ClientResponseE registerClient(
            @Valid @RequestBody ClientRequestE registerUserDtoRequest,
            HttpServletResponse httpServletResponse) throws ServerException {
        return clientTrader.registerClient(registerUserDtoRequest, httpServletResponse);
    }

    @GetMapping(value = "/clientsE/{id}", produces = MediaType.ALL_VALUE, consumes = MediaType.ALL_VALUE)
    public ClientResponseE getById(@PathVariable("id") int id) throws ServerException {
        return clientTrader.getById(id);
    }

    @DeleteMapping(value = "/clientsE/logout/{token}", produces = MediaType.ALL_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<HttpStatus> logout (@PathVariable("token") String token) throws ServerException {
        clientTrader.logoutUser(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping(value = "/clientsE/logout", produces = MediaType.ALL_VALUE)
//    public ClientResponseE logoutCl (@CookieValue(value = COOKIE_NAME) String token) throws ServerException {
//        System.out.println("!!!!(( "+token);
//        clientTrader.logoutUser(token);
//        return new ClientResponseE();
//    }
//

    @GetMapping(value = "/AllClientsE")
    public List<ClientResponseE> getAll() throws ServerException {
        return clientTrader.getAll();
    }

}
