package com.example.CompanyEmployee.controller;

import com.example.CompanyEmployee.models.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.CompanyEmployee.services.CityService;

import java.util.List;

/**
 * CityController class
 * Den här klassen är en controller-klass som hanterar alla anrop för städer.
 * Den här klassen innehåller metoder för att hämta, lägga till, uppdatera och ta bort städer.
 * Den här klassen använder CityService för att utföra affärslogik.
 * @Author Clara Brorson
 */
@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired private CityService cityService;

    @GetMapping("/{cityId}")
    public City findCityById(@PathVariable Long cityId) {
        return cityService.findCityById(cityId);
    }
    @GetMapping
    public List<City> getCities() {
        return cityService.findAllCities();
    }

    @PostMapping
    public City addCity(@RequestBody City city) {
        return cityService.addCity(city);
    }

    @PutMapping("/{cityId}")
    public City updateCity(@PathVariable Long cityId, @RequestBody City city) {
        return cityService.updateCity(city);
    }

    @DeleteMapping("/{cityId}")
    public String deleteCity(@PathVariable Long cityId) {
        return cityService.deleteCity(cityId);
    }

}
