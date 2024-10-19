package com.example.mapsapi.repository;

import com.example.mapsapi.entity.ZipCodeCoordinates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZipCodeCoordinatesRepository extends JpaRepository<ZipCodeCoordinates, Long> {
    ZipCodeCoordinates findByZipCode(String zipCode);
}