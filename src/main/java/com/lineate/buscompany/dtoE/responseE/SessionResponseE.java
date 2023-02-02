package com.lineate.buscompany.dtoE.responseE;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionResponseE {
    private int id;
    private int userId;
    private String token;

    public SessionResponseE(int userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}
