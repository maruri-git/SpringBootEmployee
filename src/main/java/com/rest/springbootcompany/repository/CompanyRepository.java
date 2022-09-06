package com.rest.springbootcompany.repository;

import com.rest.springbootcompany.exception.CompanyNotFoundException;
import com.rest.springbootcompany.model.Company;
import com.rest.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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

    public void delete(Integer id) {
        Company existingEmployee = findById(id);
        employees.remove(existingEmployee);
    }

    public List<Company> findByPage(int page, int pageSize) {
        return employees.stream()
                .skip((page-1)*pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    private Integer generateNextId() {
        return this.employees.stream()
                .mapToInt(employee -> employee.getId())
                .max()
                .orElse(1) + 1;
    }
}
