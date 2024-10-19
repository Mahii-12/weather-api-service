package com.example.mapsapi.controller;

import com.example.mapsapi.entity.ZipWeatherData;
import com.example.mapsapi.service.WeatherServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WeatherControllerTest {


    @Mock
    private WeatherServiceImpl weatherService;


    @Test
    public void test_retrieve_weather_data_success() throws Exception {
        WeatherServiceImpl weatherService = mock(WeatherServiceImpl.class);
        WeatherController weatherController = new WeatherController(weatherService);
        String zipCode = "12345";
        String date = "2023-10-01";
        LocalDate parsedDate = LocalDate.parse(date);
        ZipWeatherData expectedData = new ZipWeatherData();
        when(weatherService.getWeatherData(zipCode, parsedDate)).thenReturn(expectedData);

        ResponseEntity<ZipWeatherData> response = weatherController.getWeatherData(zipCode, date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedData, response.getBody());
    }

}