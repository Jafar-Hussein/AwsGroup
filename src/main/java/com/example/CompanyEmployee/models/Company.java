package com.example.CompanyEmployee.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * @Author Jafar Hussein
 * @Class Company
 * @Description: Denna klassen är en modell för företaget. Den innehåller attribut som id, företagsnamn, stad och en lista av anställda.
 * @Realtion: Klassen har en ManyToOne-relation med City-klassen och en OneToMany-relation med Employee-klassen.
 * */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long companyId;
    private String companyName;
    @ManyToOne()
    private City city;
    @JsonIgnore
   @OneToOne
    private User user;
    @JsonManagedReference
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees;

    public Company(Long companyId, String companyName, City city) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.city = city;
    }
    public Company( String companyName, City city) {
        this.companyName = companyName;
        this.city = city;
    }
}
