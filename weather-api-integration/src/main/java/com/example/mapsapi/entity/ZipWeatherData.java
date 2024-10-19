package com.example.mapsapi.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "zip_weather_data")
public class ZipWeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "precipitation")
    private Double precipitation;

    @Column(name = "wind_speed")
    private Double windSpeed;

    @Column(name = "temperature_unit")
    private String temperatureUnit;

    @Column(name = "precipitation_unit")
    private String precipitationUnit;

    @Column(name = "wind_speed_unit")
    private String windSpeedUnit;

}
