package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.model.Employee;
import com.rest.springbootemployee.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {
    @Autowired
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAll() {
        return this.employeeService.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public Employee findById(@PathVariable Integer id) {
        return this.employeeService.findById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> findByGender(@RequestParam String gender) {
        return this.employeeService.findByGender(gender);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee) {
        return this.employeeService.add(employee);
    }

    @PutMapping(path = {"/{id}"})
    @ResponseStatus(HttpStatus.CREATED)
    public Employee updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        employee.setId(id);
        return this.employeeService.updateEmployee(employee);
        // TODO: should not edit name, gender
    }

    @DeleteMapping(path = {"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Integer id) {
        this.employeeService.delete(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getByPage(int page, int pageSize) {
        return this.employeeService.findByPage(page, pageSize);
    }
}
