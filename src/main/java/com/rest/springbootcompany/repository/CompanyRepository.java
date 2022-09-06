package com.rest.springbootcompany.repository;

import com.rest.springbootcompany.exception.CompanyNotFoundException;
import com.rest.springbootcompany.model.Company;
import com.rest.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private List<Company> companies;

    public CompanyRepository() {
        List<Employee> employeeList = EmployeeDataLoader();
        List<Company> companyList = new ArrayList<>();
        companyList.add(new Company(1, "spring", employeeList));
        companyList.add(new Company(2, "fall", employeeList));
        this.companies = companyList;
    }

    private List<Employee> EmployeeDataLoader() {
        List<Employee> employees = new ArrayList<>();
        employees.add((new Employee(1,"Mark", 23, "Male", 99999)));
        employees.add(new Employee(2, "Luke", 23, "Male", 9999999));
        employees.add(new Employee(3, "John", 24, "Male", 99999));
        employees.add(new Employee(4, "Ruby", 24, "Female", 80000));
        employees.add(new Employee(5, "Lily", 20, "Female", 900000));
        return employees;
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

    public List<Company> findCompanyByPage(int page, int pageSize) {
        return companies.stream()
                .skip((page-1)*pageSize)
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

    private Integer generateNextId() {
        return this.companies.stream()
                .mapToInt(Company::getId)
                .max()
                .orElse(1) + 1;
    }
}
