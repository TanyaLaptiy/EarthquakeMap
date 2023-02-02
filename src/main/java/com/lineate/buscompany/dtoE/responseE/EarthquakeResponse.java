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
public class EarthquakeResponse {
    private int id;
    private double latitude;
    private double longitude;
    private double magnitude;
    private String location;
    private String age;
    private double depth;
    private String onland;
    private String country;
    private LocalDate data;


    public EarthquakeResponse(int id, double latitude, double longitude, double magnitude, String location, String age, double depth, String onland, String country, LocalDate data) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.magnitude = magnitude;
        this.location = location;
        this.age = age;
        this.depth = depth;
        this.onland = onland;
        this.country = country;
        this.data = data;
    }

}
