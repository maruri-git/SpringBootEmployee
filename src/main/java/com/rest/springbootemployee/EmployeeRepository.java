package com.rest.springbootemployee;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private List<Employee> employees;

    public EmployeeRepository() {
        this.employees = new ArrayList<>();
        this.employees.add((new Employee(1,"Mark", 23, "Male", 99999)));
        this.employees.add(new Employee(2, "Luke", 23, "Male", 9999999));
        this.employees.add(new Employee(3, "John", 24, "Male", 99999));
    }

    public List<Employee> findAll() {
        return this.employees;
    }
}
