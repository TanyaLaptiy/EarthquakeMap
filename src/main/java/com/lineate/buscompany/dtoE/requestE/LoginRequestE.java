package com.lineate.buscompany.dtoE.requestE;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode()
public class LoginRequestE {
    private String login;
    private String password;

    public LoginRequestE(String login, String password) {
        this.login = login;
        this.password = password;

    }

}
