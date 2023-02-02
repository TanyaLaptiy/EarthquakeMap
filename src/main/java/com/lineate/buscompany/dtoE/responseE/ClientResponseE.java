package com.lineate.buscompany.dtoE.responseE;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode()
@AllArgsConstructor
public class ClientResponseE extends UserResponseE{
    private int id;
    private String mail;
    private String token;
    private String userType;

    public ClientResponseE(String lastName, String firstName, String patronymic, String login, String password, String country, String sex, LocalDate birthday,String mail) {
        super(lastName, firstName, patronymic, login, password, country,sex,birthday);
        this.mail = mail;

    }

    public ClientResponseE(int id, String lastName, String firstName, String patronymic, String login, String password, String country, String sex,LocalDate birthday, String mail) {
        super(lastName, firstName, patronymic, login, password, country,sex,birthday);
        this.mail = mail;
        this.id=id;

    }
}
