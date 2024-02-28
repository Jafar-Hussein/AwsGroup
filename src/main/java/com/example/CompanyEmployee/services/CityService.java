package com.example.CompanyEmployee.services;

import com.example.CompanyEmployee.models.City;
import com.example.CompanyEmployee.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * CityService class
 * Den här klassen är en serviceklass som innehåller metoder för att hantera städer.
 * Den står för att utföra affärslogik och hantera CRUD-operationer.
 */
@Service
public class CityService {

    @Autowired
    CityRepository repository;
    public CityService(CityRepository cityRepository) {
        this.repository = cityRepository;
    }
    public City addCity (City city) {
        return repository.save(city);
    }

    public List <City> addCities (List<City> cities) {
        return repository.saveAll(cities);
    }

    public List <City> findAllCities () {
        return repository.findAll();
    }

    public City findCityById (long cityId) {
        return repository.findById(cityId).orElse(null);
    }

    public City updateCity (City city) {
        City existingCity = repository.findById(city.getCityId()).orElse(null);
        assert existingCity != null;
        existingCity.setCityName(city.getCityName());
        return repository.save(existingCity);
    }

    public String deleteCity (long cityId) {
        repository.deleteById(cityId);
        return "City removed:"+cityId;
    }


}
