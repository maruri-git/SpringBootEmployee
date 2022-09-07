package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.model.Company;
import com.rest.springbootemployee.model.Employee;
import com.rest.springbootemployee.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<Company> getAllCompany() {
    return this.companyService.findAll();
    }

    @GetMapping({"/{id}"})
    public Company getCompanyById(@PathVariable Integer id) {
        return this.companyService.findCompanyById(id);
    }

    @GetMapping("{id}/employees")
    public List<Employee> getEmployeesByCompanyId(@PathVariable Integer id) {
        return this.companyService.findEmployeesByCompanyId(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getCompanyByPage(Integer page, Integer pageSize){
        return this.companyService.findCompanyByPage(page, pageSize);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody Company company){
        return this.companyService.addCompany(company);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Company updateCompany(@PathVariable Integer id, @RequestBody Company company) {
        company.setId(id);
        return this.companyService.updateCompany(id, company);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable Integer id) {this.companyService.delete(id);}
}
