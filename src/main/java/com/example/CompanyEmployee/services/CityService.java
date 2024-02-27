package com.example.CompanyEmployee.services;

import com.example.CompanyEmployee.models.City;
import com.example.CompanyEmployee.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CityService {

    @Autowired
    CityRepository repository;

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
        existingCity.setCityName(city.getCityName());
        return repository.save(existingCity);
    }

    public String deleteCity (long cityId) {
        repository.deleteById(cityId);
        return "City removed:"+cityId;
    }


}
