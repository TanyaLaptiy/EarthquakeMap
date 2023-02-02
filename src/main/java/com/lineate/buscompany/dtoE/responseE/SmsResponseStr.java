package com.lineate.buscompany.dtoE.responseE;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode()
public class SmsResponseStr {
    private int id;
    private int clientId;
    private String data;
    private String message;

    public SmsResponseStr(int id, int clientId, String data, String message) {
        this.id=id;
        this.clientId=clientId;
        this.data = data;
        this.message = message;

    }
}
