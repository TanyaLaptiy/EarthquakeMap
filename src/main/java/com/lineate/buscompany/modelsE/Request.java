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
public class Request {
    private int id;
    private int clientId;
    private LocalDate data;
    private String title;
    private String message;
    private double latitude;
    private double longitude;
    private double magnitude;
    private double depth;
    private String age;
    private String onland;
    private String country;
    private String status;
    private LocalDate requestData;


    public Request(int clientId, LocalDate data, String title, String message, double latitude, double longitude, double magnitude, double depth, String age, String onland, String country, String status, LocalDate requestData) {
        this.clientId = clientId;
        this.data = data;
        this.title = title;
        this.message = message;
        this.latitude = latitude;
        this.longitude = longitude;
        this.magnitude = magnitude;
        this.depth = depth;
        this.age = age;
        this.onland = onland;
        this.country = country;
        this.status = status;
        this.requestData = requestData;
    }

}
