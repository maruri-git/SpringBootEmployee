package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.exception.CompanyNotFoundException;
import com.rest.springbootemployee.model.Company;
import com.rest.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private List<Company> companies;
    private int id;

    public CompanyRepository() {
        this.companies = new ArrayList<>();
        this.id = 0;
    }

    public List<Company> findAll() {
        return this.companies;
    }

    public Company findCompanyById(Integer id) {
        return findById(id);
    }

    public List<Employee> findEmployeesByCompanyId(Integer id) {
        Company ExistingCompany = findById(id);

        return ExistingCompany.getEmployees();
    }

    public List<Company> findCompanyByPage(Integer page, Integer pageSize) {
        return companies.stream()
                .skip((long) (page - 1) *pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company addCompany(Company company) {
        int nextId = generateNextId();
        company.setId(nextId);
        this.companies.add(company);

        return company;
    }

    public Company updateCompany(Integer id, Company company) {
        Company ExistingCompany = findById(id);

        if(company.getName() != null) {
            ExistingCompany.setName(company.getName());
        }
        if(company.getEmployees() != null) {
            ExistingCompany.setEmployees(company.getEmployees());
        }

        return ExistingCompany;
    }

    public void delete(Integer id) {
        Company existingCompany = findById(id);
        companies.remove(existingCompany);
    }

    private Company findById(Integer id) {
        return this.companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);
    }

    private int generateNextId() {
        return this.id += 1;
    }

    public void clear() {
        this.companies.clear();
    }
}
