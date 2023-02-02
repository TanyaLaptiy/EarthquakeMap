package com.lineate.buscompany.dtoE.requestE;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode()
public class ClientRequestE extends UserRequestE{
    private String mail;

    public ClientRequestE(String lastName, String firstName, String patronymic, String login, String password, String country, String sex, LocalDate birthday, String mail) {
        super(lastName, firstName, patronymic, login, password, country,sex,birthday);
        this.mail = mail;

    }
}
