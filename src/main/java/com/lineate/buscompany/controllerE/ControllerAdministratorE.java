package com.lineate.buscompany.controllerE;


import com.lineate.buscompany.dtoE.requestE.AdministratorRequestE;
import com.lineate.buscompany.dtoE.requestE.LoginRequestE;
import com.lineate.buscompany.dtoE.responseE.AdministratorResponseE;
import com.lineate.buscompany.dtoE.responseE.UserResponseE;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.traderE.AdministratorTreaderE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ControllerAdministratorE {
    private final AdministratorTreaderE administratorTreader;

    @Autowired
    public ControllerAdministratorE( AdministratorTreaderE administratorTreader) {
        this.administratorTreader = administratorTreader;
    }

    @PostMapping(value = "/adminsE", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public AdministratorResponseE registerAdmin(
            @Valid @RequestBody AdministratorRequestE registerUserDtoRequest,
            HttpServletResponse httpServletResponse) throws ServerException {
        return administratorTreader.registerAdmin(registerUserDtoRequest, httpServletResponse);
    }

    @GetMapping(value = "/adminsE/{id}", produces = MediaType.ALL_VALUE, consumes = MediaType.ALL_VALUE)
    public AdministratorResponseE getById(@PathVariable("id") int id) throws ServerException {
        return administratorTreader.getById(id);
    }
    @PostMapping(value = "/loginUser", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public <T extends UserResponseE> T loginUser(@RequestBody LoginRequestE loginUserRequest,
                                                 HttpServletResponse httpServletResponse) throws ServerException {
        return (T) administratorTreader.loginUser(loginUserRequest, httpServletResponse);
    }

    @GetMapping(value = "/AllAdminsE")
    public List<AdministratorResponseE> getAll() throws ServerException {
        return administratorTreader.getAll();
    }
}
