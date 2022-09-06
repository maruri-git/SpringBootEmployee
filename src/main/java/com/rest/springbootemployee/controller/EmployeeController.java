package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {
    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<Employee> getAll() {
        return this.employeeRepository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public Employee findById(@PathVariable Integer id) {
        return this.employeeRepository.findById(id);
    }
}
