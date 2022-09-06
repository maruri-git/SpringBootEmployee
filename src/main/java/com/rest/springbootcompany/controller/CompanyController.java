package com.rest.springbootcompany.controller;

import com.rest.springbootcompany.model.Company;
import com.rest.springbootcompany.repository.CompanyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class CompanyController {
    private CompanyRepository employeeRepository;

    public CompanyController(CompanyRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<Company> getAll() {
        return this.employeeRepository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public Company findById(@PathVariable Integer id) {
        return this.employeeRepository.findById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Company> findByGender(@RequestParam String gender) {
        return this.employeeRepository.findByGender(gender);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Company addEmployee(@RequestBody Company employee) {
        return this.employeeRepository.add(employee);
    }

    @PutMapping(path = {"/{id}"})
    @ResponseStatus(HttpStatus.CREATED)
    public Company updateEmployee(@PathVariable Integer id, @RequestBody Company employee) {
        employee.setId(id);
        return this.employeeRepository.updateEmployee(employee);
    }

    @DeleteMapping(path = {"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Integer id) {
        this.employeeRepository.delete(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getByPage(int page, int pageSize){
        return this.employeeRepository.findByPage(page, pageSize);
    }
}
