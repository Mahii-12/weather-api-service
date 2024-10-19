package com.example.mapsapi.service;

import com.example.mapsapi.entity.ZipWeatherData;

import java.time.LocalDate;

public interface WeatherService {
    public ZipWeatherData getWeatherData(String zipCode, LocalDate date) throws Exception;
}
