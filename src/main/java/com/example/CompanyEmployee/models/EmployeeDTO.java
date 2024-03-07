package com.example.CompanyEmployee.models;

import lombok.Data;

@Data
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private double salary;
    private City city;
    private Company company;
}
