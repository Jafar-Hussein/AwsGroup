package com.example.CompanyEmployee.repository;

import com.example.CompanyEmployee.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CityRepository interface extends JpaRepository
 * JPARepository innehåller metoder för att spara, uppdatera, radera och hämta data från databasen.
 * @Author Clara Brorson
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {


    City findByCityName(String cityName);
}
