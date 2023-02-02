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
public class UserRequestE {
    private String lastName;
    private String firstName;
    private String patronymic;
    private String login;
    private String password;
    private String country;
    private String sex;
    private LocalDate birthday;

    public UserRequestE(String lastName,String firstName, String patronymic, String login, String password, String country,String sex,LocalDate birthday){
        this.lastName=lastName;
        this.firstName=firstName;
        this.patronymic = patronymic;
        this.login=login;
        this.password=password;
        this.country=country;
        this.sex=sex;
        this.birthday=birthday;
    }
}
