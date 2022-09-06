package com.rest.springbootcompany.controller;

import com.rest.springbootcompany.model.Company;
import com.rest.springbootcompany.repository.CompanyRepository;
import com.rest.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/companies")
public class CompanyController {
    private CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public List<Company> getAllCompany() {
        return this.companyRepository.findAll();
    }

    @GetMapping({"/{id}"})
    public Company getCompanyById(@PathVariable Integer id) {
        return this.companyRepository.findCompanyById(id);
    }

    @GetMapping("{id}/employees")
    public List<Employee> getEmployeesByCompanyId(@PathVariable Integer id) {
        return this.companyRepository.findEmployeesByCompanyId(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getCompanyByPage(int page, int pageSize){
        return this.companyRepository.findCompanyByPage(page, pageSize);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody Company company){
        return this.companyRepository.addCompany(company);
    }
}
