package com.lineate.buscompany.modelsE;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionE {
    private int id;
    private int userId;
    private String token;

    public SessionE(int userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}
