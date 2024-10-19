package com.example.mapsapi.service;

import com.example.mapsapi.entity.ZipCodeCoordinates;
import com.example.mapsapi.entity.ZipWeatherData;
import com.example.mapsapi.repository.ZipCodeCoordinatesRepository;
import com.example.mapsapi.repository.ZipWeatherDataRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Log4j2
@Service
@AllArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private ZipWeatherDataRepository repository;
    private ZipCodeCoordinatesRepository zipCodeCoordinatesRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public ZipWeatherData getWeatherData(String zipCode, LocalDate date) throws Exception {
        log.info("Fetching location data for ZIP code: {}", zipCode);

        String locationApiUrl = "https://nominatim.openstreetmap.org/search?postalcode=" + zipCode + "&format=json";
        JsonNode locationResponse;

        try {
            locationResponse = restTemplate.getForObject(locationApiUrl, JsonNode.class);
        } catch (Exception e) {
            log.error("Error occurred while fetching location data: {}", e.getMessage());
            throw new Exception("Error fetching location data: " + e.getMessage(), e);
        }

        if (locationResponse == null || locationResponse.isEmpty()) {
            log.warn("Location not found for ZIP code: {}", zipCode);
            throw new Exception("Location not found for ZIP code: " + zipCode);
        }

        double latitude = locationResponse.get(0).get("lat").asDouble();
        double longitude = locationResponse.get(0).get("lon").asDouble();
        log.info("Retrieved coordinates - Latitude: {}, Longitude: {}", latitude, longitude);

        ZipCodeCoordinates coordinates = zipCodeCoordinatesRepository.findByZipCode(zipCode);
        if (coordinates == null) {
            coordinates = new ZipCodeCoordinates();
            coordinates.setZipCode(zipCode);
            coordinates.setLatitude(latitude);
            coordinates.setLongitude(longitude);
            zipCodeCoordinatesRepository.save(coordinates);
            log.info("Stored new coordinates for ZIP code: {}", zipCode);
        } else {
            log.info("Coordinates already exist for ZIP code: {}", zipCode);
        }

        ZipWeatherData zipWeatherData = repository.findByZipCodeAndDate(zipCode, date);
        if (zipWeatherData == null) {
            zipWeatherData = new ZipWeatherData();
            zipWeatherData.setZipCode(zipCode);
            zipWeatherData.setLatitude(latitude);
            zipWeatherData.setLongitude(longitude);
            log.info("Creating new weather data entry for ZIP code: {}", zipCode);
        } else {
            log.info("Weather data already exists for ZIP code: {} on date: {}", zipCode, date);
        }

        String weatherApiUrl = String.format(
                "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&start_date=%s&end_date=%s&hourly=temperature_2m,precipitation,windspeed_10m",
                latitude, longitude, date, date);
        log.info("Fetching weather data from API: {}", weatherApiUrl);

        JsonNode weatherResponse;
        try {
            weatherResponse = restTemplate.getForObject(weatherApiUrl, JsonNode.class);
        } catch (Exception e) {
            log.error("Error occurred while fetching weather data: {}", e.getMessage());
            throw new Exception("Error fetching weather data: " + e.getMessage(), e);
        }

        if (weatherResponse == null || weatherResponse.get("hourly").isEmpty()) {
            log.warn("Weather data not found for the specified date: {} for ZIP code: {}", date, zipCode);
            throw new Exception("Weather data not found for the specified date: " + date);
        }

        JsonNode hourlyData = weatherResponse.get("hourly");

        zipWeatherData.setDate(date);
        zipWeatherData.setTemperature(hourlyData.get("temperature_2m").get(0).asDouble());
        zipWeatherData.setPrecipitation(hourlyData.get("precipitation").get(0).asDouble());
        zipWeatherData.setWindSpeed(hourlyData.get("windspeed_10m").get(0).asDouble());
        zipWeatherData.setTemperatureUnit(weatherResponse.get("hourly_units").get("temperature_2m").asText());
        zipWeatherData.setPrecipitationUnit(weatherResponse.get("hourly_units").get("precipitation").asText());
        zipWeatherData.setWindSpeedUnit(weatherResponse.get("hourly_units").get("windspeed_10m").asText());

        repository.save(zipWeatherData);
        log.info("Weather data saved successfully for ZIP code: {} on date: {}", zipCode, date);

        return zipWeatherData;
    }

}
