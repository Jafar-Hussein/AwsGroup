package com.example.CompanyEmployee.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    @ManyToOne
    private City city;
    @OneToMany(mappedBy = "company")
    private List<Employee> employee;
}
