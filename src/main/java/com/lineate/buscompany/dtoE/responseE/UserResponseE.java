package com.lineate.buscompany.dtoE.responseE;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode()
@AllArgsConstructor
public class UserResponseE {
    private int id;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String login;
    private String password;
    private String country;
    private String sex;
    private LocalDate birthday;

    public UserResponseE(String lastName,String firstName, String patronymic, String login, String password, String country,String sex,LocalDate birthday){
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
