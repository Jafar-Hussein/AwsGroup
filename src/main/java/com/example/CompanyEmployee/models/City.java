package com.example.CompanyEmployee.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="cities")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long cityId;
    private String cityName;

    @JsonIgnore
    @OneToMany(mappedBy = "city")
    private List<Company> companies;

    @JsonIgnore
    @OneToMany(mappedBy = "city")
    private List<Employee> employees;

    public City(long cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }
}
