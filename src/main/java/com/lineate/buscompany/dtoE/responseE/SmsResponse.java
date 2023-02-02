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
public class SmsResponse {
    private int id;
    private int clientId;
    private LocalDate data;
    private String message;

    public SmsResponse(int id, int clientId, LocalDate data, String message) {
        this.id=id;
        this.clientId=clientId;
        this.data = data;
        this.message = message;

    }
}
