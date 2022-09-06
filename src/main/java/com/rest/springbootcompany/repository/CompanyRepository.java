package com.rest.springbootcompany.repository;

import com.rest.springbootcompany.exception.CompanyNotFoundException;
import com.rest.springbootcompany.model.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private List<Company> employees;
    private Integer employeeSize;
    public CompanyRepository() {
        this.employees = new ArrayList<>();
        this.employees.add((new Company(1,"Mark", 23, "Male", 99999)));
        this.employees.add(new Company(2, "Luke", 23, "Male", 9999999));
        this.employees.add(new Company(3, "John", 24, "Male", 99999));
        this.employeeSize = this.employees.size();
    }

    public List<Company> findAll() {
        return this.employees;
    }

    public Company findById(Integer id) {
        return this.employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);
    }

    public List<Company> findByGender(String gender) {
        return this.employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }


    public Company add(Company employee) {
        int nextId = generateNextId();
        employee.setId(nextId);
        this.employees.add(employee);
        return employee;
    }

    public Company updateEmployee(Company employee) {
        Company updatedEmployee = this.employees.stream()
                .filter(employee1 -> employee1.getId().equals((employee.getId())))
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);

        updatedEmployee.setName(employee.getName());
        updatedEmployee.setAge(employee.getAge());
        updatedEmployee.setGender(employee.getGender());
        updatedEmployee.setSalary(employee.getSalary());
        return updatedEmployee;
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
