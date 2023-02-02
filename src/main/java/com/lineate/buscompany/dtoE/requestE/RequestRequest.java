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
public class RequestRequest {
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
    private LocalDate requestData ;


    public RequestRequest(int clientId, LocalDate data, String title, String message, double latitude, double longitude, double magnitude, double depth, String age, String onland, String country, String status) {
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
        requestData=LocalDate.now();

    }
    public RequestRequest(int clientId, LocalDate data, String title, String message, double latitude, double longitude, double magnitude, double depth, String age, String onland, String country) {
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
        status = "false";
        requestData=LocalDate.now();


    }

    @Override
    public String toString() {
        return "RequestRequest{" +
                "clientId=" + clientId +
                ", data=" + data +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", magnitude=" + magnitude +
                ", depth=" + depth +
                ", age='" + age + '\'' +
                ", onland='" + onland + '\'' +
                ", country='" + country + '\'' +
                ", status='" + status + '\'' +
                ", requestData=" + requestData +
                '}';
    }
}
