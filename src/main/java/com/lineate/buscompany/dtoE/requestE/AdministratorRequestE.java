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
public class AdministratorRequestE extends UserRequestE{
    private String position;

    public AdministratorRequestE(String lastName, String firstName, String patronymic, String login, String password, String country, String sex, LocalDate birthday, String position) {
        super(lastName, firstName, patronymic, login, password, country,sex,birthday);
        this.position = position;

    }
}
