package com.example.mapsapi.service;

import com.example.mapsapi.entity.ZipCodeCoordinates;
import com.example.mapsapi.entity.ZipWeatherData;
import com.example.mapsapi.repository.ZipCodeCoordinatesRepository;
import com.example.mapsapi.repository.ZipWeatherDataRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WeatherServiceImplTest {
    @Mock
    private ZipWeatherDataRepository weatherDataRepository;

    @Mock
    private ZipCodeCoordinatesRepository coordinatesRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    private static final String ZIP_CODE = "12345";
    private static final LocalDate DATE = LocalDate.of(2024, 10, 19);

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetWeatherData_Success() throws Exception {
        String locationJson = "[{\"lat\": \"40.7128\", \"lon\": \"-74.0060\"}]";
        JsonNode locationResponse = objectMapper.readTree(locationJson);
        when(restTemplate.getForObject(anyString(), eq(JsonNode.class))).thenReturn(locationResponse);

        String weatherJson = "{\"hourly\": {\"temperature_2m\": [20.0], \"precipitation\": [0.5], \"windspeed_10m\": [5.0]}, \"hourly_units\": {\"temperature_2m\": \"C\", \"precipitation\": \"mm\", \"windspeed_10m\": \"km/h\"}}";
        JsonNode weatherResponse = objectMapper.readTree(weatherJson);
        when(restTemplate.getForObject(anyString(), eq(JsonNode.class))).thenReturn(weatherResponse);

        when(weatherDataRepository.findByZipCodeAndDate(anyString(), any(LocalDate.class))).thenReturn(null);
        when(coordinatesRepository.findByZipCode(anyString())).thenReturn(null);

        ZipWeatherData result = weatherService.getWeatherData(ZIP_CODE, DATE);

        assertNotNull(result);
        assertEquals(ZIP_CODE, result.getZipCode());
        assertEquals(15.4, result.getTemperature());
        assertEquals(2.8, result.getWindSpeed());

        verify(coordinatesRepository, times(1)).save(any(ZipCodeCoordinates.class));
        verify(weatherDataRepository, times(1)).save(any(ZipWeatherData.class));
    }

}