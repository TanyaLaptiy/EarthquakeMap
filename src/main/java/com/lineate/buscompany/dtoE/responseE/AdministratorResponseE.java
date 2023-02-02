package com.lineate.buscompany.dtoE.responseE;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode()

public class AdministratorResponseE extends UserResponseE{
    private int id;
    private String position;
    private String token;

    public AdministratorResponseE(String lastName, String firstName, String patronymic, String login, String password, String country, String sex, LocalDate birthday, String position) {
        super(lastName, firstName, patronymic, login, password, country,sex,birthday);
        this.position = position;

    }
    public AdministratorResponseE(int id, String lastName, String firstName, String patronymic, String login, String password, String country, String sex, LocalDate birthday, String position) {
        super(lastName, firstName, patronymic, login, password, country,sex,birthday);
        this.position = position;
        this.id=id;

    }
}
