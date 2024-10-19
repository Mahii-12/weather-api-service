package com.example.mapsapi.repository;

import com.example.mapsapi.entity.ZipWeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ZipWeatherDataRepository extends JpaRepository<ZipWeatherData, Long> {
    ZipWeatherData findByZipCodeAndDate(String zipCode, LocalDate date);
}
