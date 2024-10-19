package com.example.mapsapi.controller;

import com.example.mapsapi.entity.ZipWeatherData;
import com.example.mapsapi.service.WeatherServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherServiceImpl weatherService;

    @PostMapping("/getWeatherData")
    public ResponseEntity<ZipWeatherData> getWeatherData(
            @RequestParam String zipCode,
            @RequestParam String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            ZipWeatherData weatherData = weatherService.getWeatherData(zipCode, parsedDate);
            return ResponseEntity.ok(weatherData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
