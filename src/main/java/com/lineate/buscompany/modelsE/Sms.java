package com.lineate.buscompany.modelsE;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode()
public class Sms {
    private int id;
    private int clientId;
    private LocalDate data;
    private String message;

    public Sms(int clientId, LocalDate data, String message) {
        this.clientId=clientId;
        this.data = data;
        this.message = message;

    }
}
