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
public class EarthquakeRequestCD {
    private String country;
    private LocalDate data;


    public EarthquakeRequestCD(String country, LocalDate data) {
        this.country = country;
        this.data = data;

    }

}
