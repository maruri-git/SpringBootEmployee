package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(params = {"gender"})
    public List<Employee> findByGender(@RequestParam String gender) {
        return this.employeeRepository.findByGender(gender);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee) {
        return this.employeeRepository.add(employee);
    }

    @PutMapping(path = {"/{id}"})
    @ResponseStatus(HttpStatus.CREATED)
    public Employee updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        employee.setId(id);
        return this.employeeRepository.updateEmployee(employee);
    }

    @DeleteMapping(path = {"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Integer id) {
        this.employeeRepository.delete(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getByPage(int page, int pageSize){
        return this.employeeRepository.findByPage(page, pageSize);
    }
}
