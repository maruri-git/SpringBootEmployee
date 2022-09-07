package com.rest.springbootemployee.model;

import com.rest.springbootemployee.model.Employee;

import java.util.*;

public class Company {
    private Integer id;
    private String name;
    private List<Employee> employees;

    public Company(Integer id, String name, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String companyName) {
        this.name = companyName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", companyName='" + name + '\'' +
                ", employees=" + employees +
                '}';
    }
}
