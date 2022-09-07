package com.rest.springbootemployee.services;

import com.rest.springbootemployee.model.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public void clear() {
        this.employeeRepository.clear();
    }

    public Employee add(Employee employee) {
        return this.employeeRepository.add(employee);
    }

    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

    public Employee findById(Integer id) {
        return this.employeeRepository.findById(id);
    }

    public List<Employee> findByGender(String gender) {
        return this.employeeRepository.findByGender(gender);
    }

    public Employee updateEmployee(Employee employee) {
        Employee updatedEmployee = findById(employee.getId());

        if(employee.getAge() != null) {
            updatedEmployee.setAge(employee.getAge());
        }
        if(employee.getSalary() != null){
            updatedEmployee.setSalary(employee.getSalary());
        }

        return this.employeeRepository.updateEmployee(updatedEmployee);
    }

    public void delete(Integer id) {
        this.employeeRepository.delete(id);
    }

    public List<Employee> findByPage(int page, int pageSize) {
        return this.employeeRepository.findByPage(page, pageSize);
    }
}
