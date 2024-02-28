package com.example.CompanyEmployee.services;

import com.example.CompanyEmployee.models.City;
import com.example.CompanyEmployee.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class CityServiceTests {

    @Mock
    private CityRepository cityRepository;
    private CityService cityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        cityService = new CityService(cityRepository);
    }

    @Test
    void addCity() {

        City city = new City();
        when(cityRepository.save(any())).thenReturn(city);

        City result = cityService.addCity(city);

        assertEquals(city, result);
    }

    @Test
    void addCities() {

        List<City> cities = Collections.singletonList(new City());
        when(cityRepository.saveAll(any())).thenReturn(cities);

        List<City> result = cityService.addCities(cities);

        assertEquals(cities, result);
    }

    @Test
    void findAllCities() {

        List<City> cities = Collections.singletonList(new City());
        when(cityRepository.findAll()).thenReturn(cities);

        List<City> result = cityService.findAllCities();

        assertEquals(cities, result);
    }

    @Test
    void findCityById() {

        City city = new City();
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(city));

        City result = cityService.findCityById(1L);

        assertEquals(city, result);
    }

    @Test
    void updateCity() {

        City city = new City();
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(city));
        when(cityRepository.save(any())).thenReturn(city);

        City result = cityService.updateCity(city);

        assertEquals(city, result);
    }

    @Test
    void deleteCity() {

        doNothing().when(cityRepository).deleteById(anyLong());

        String result = cityService.deleteCity(1L);

        assertEquals("City removed:1", result);
    }
}
