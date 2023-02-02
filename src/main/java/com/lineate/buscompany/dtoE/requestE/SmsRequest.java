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
public class SmsRequest {
    private int clientId;
    private LocalDate data;
    private String message;

    public SmsRequest(int clientId,LocalDate data, String message) {
        this.clientId=clientId;
        this.data = data;
        this.message = message;

    }
}
