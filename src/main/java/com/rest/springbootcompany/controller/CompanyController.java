package com.rest.springbootcompany.controller;

import com.rest.springbootcompany.model.Company;
import com.rest.springbootcompany.repository.CompanyRepository;
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
}
