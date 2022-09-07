package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.model.Company;
import com.rest.springbootemployee.repository.CompanyRepository;
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

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Company updateCompany(@PathVariable Integer id, @RequestBody Company company) {
        company.setId(id);
        return this.companyRepository.updateCompany(id, company);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable Integer id) {this.companyRepository.delete(id);}
}