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
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private double salary;

    @ManyToOne
    private Company company;

    @ManyToOne
    private City city;

    @OneToMany(mappedBy = "employee")
    private List<User> users;
}
