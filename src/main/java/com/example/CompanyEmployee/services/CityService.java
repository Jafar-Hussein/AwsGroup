package com.example.CompanyEmployee.services;

import com.example.CompanyEmployee.models.City;
import com.example.CompanyEmployee.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * CityService class
 * Den här klassen är en serviceklass som innehåller metoder för att hantera städer.
 * Den står för att utföra affärslogik och hantera CRUD-operationer.
 * @Author Clara Brorson
 */
@Service
public class CityService {

    @Autowired
    CityRepository repository;

    public CityService(CityRepository cityRepository) {
        this.repository = cityRepository;
    }

    public City addCity (City city) {
        if (city == null) {
            throw new IllegalArgumentException("City cannot be null");
        }
        return repository.save(city);
    }

    public List <City> addCities (List<City> cities) {
        if (cities == null || cities.isEmpty()) {
            throw new IllegalArgumentException("Cities list cannot be null or empty");
        }
        return repository.saveAll(cities);
    }

    public List <City> findAllCities () {
        List<City> cities = repository.findAll();
        if (cities.isEmpty()) {
            throw new NoSuchElementException("No cities found");
        }
        return cities;
    }

    public City findCityById (long cityId) {
        return repository.findById(cityId).orElseThrow(() -> new NoSuchElementException("City with id " + cityId + " not found"));
    }

    public City updateCity (City city) {
        if (city == null) {
            throw new IllegalArgumentException("City cannot be null");
        }
        City existingCity = repository.findById(city.getCityId()).orElseThrow(() -> new NoSuchElementException("City with id " + city.getCityId() + " not found"));
        existingCity.setCityName(city.getCityName());
        return repository.save(existingCity);
    }

    public String deleteCity (long cityId) {
        if (!repository.existsById(cityId)) {
            throw new NoSuchElementException("City with id " + cityId + " not found");
        }
        repository.deleteById(cityId);
        return "City removed:"+cityId;
    }
}
