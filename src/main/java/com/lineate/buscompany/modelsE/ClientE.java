package com.lineate.buscompany.modelsE;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode()
public class ClientE extends UserE{
    private int id;
    private String mail;

    public ClientE(String lastName, String firstName, String patronymic, String login, String password, String country, String sex, LocalDate birthday, String mail) {
        super(lastName, firstName, patronymic, login, password, country,sex,birthday);
        this.mail = mail;

    }

}
