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
public class Earthquake {
   private int id;

    @Override
    public String toString() {
        return "Earthquake{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", magnitude=" + magnitude +
                ", location='" + location + '\'' +
                ", age='" + age + '\'' +
                ", depth=" + depth +
                ", onland='" + onland + '\'' +
                ", country='" + country + '\'' +
                ", data=" + data +
                '}';
    }

    private double latitude;
    private double longitude;
    private double magnitude;
    private String location;
    private String age;
    private double depth;
    private String onland;
    private String country;
    private LocalDate data;


    public Earthquake(double latitude, double longitude, double magnitude, String location, String age, double depth, String onland, String country, LocalDate data){
        this.latitude=latitude;
        this.longitude=longitude;
        this.magnitude=magnitude;
        this.location=location;
        this.age=age;
        this.depth=depth;
        this.onland=onland;
        this.country=country;
        this.data=data;
    }
    public Earthquake(double latitude, double longitude, double magnitude, String age, double depth, String onland, String country, LocalDate data){
        this.latitude=latitude;
        this.longitude=longitude;
        this.magnitude=magnitude;
        this.age=age;
        this.depth=depth;
        this.onland=onland;
        this.country=country;
        this.data=data;
    }
}
