package com.rest.springbootemployee.services;

import com.rest.springbootemployee.exception.EmployeeNotFoundException;
import com.rest.springbootemployee.model.Employee;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired

    private JpaEmployeeRepository jpaEmployeeRepository;

    public EmployeeService(JpaEmployeeRepository jpaEmployeeRepository) {
        this.jpaEmployeeRepository = jpaEmployeeRepository;
    }

    public void clear() {
        this.jpaEmployeeRepository.deleteAll();
    }

    public Employee save(Employee employee) {
        return this.jpaEmployeeRepository.save(employee);
    }

    public List<Employee> findAll() {
        return this.jpaEmployeeRepository.findAll();
    }

    public Employee findById(Integer id) {
        return this.jpaEmployeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return this.jpaEmployeeRepository.findByGender(gender);
    }

    public Employee updateEmployee(Employee employee) {
        Employee existingEmployee = findById(employee.getId());

        if (employee.getAge() != null) {
            existingEmployee.setAge(employee.getAge());
        }
        if (employee.getSalary() != null) {
            existingEmployee.setSalary(employee.getSalary());
        }
        jpaEmployeeRepository.save(existingEmployee);
        return existingEmployee;
    }

    public void deleteById(Integer id) {
        this.jpaEmployeeRepository.deleteById(id);
    }

    public List<Employee> findByPage(int page, int pageSize) {
        PageRequest employeePage = PageRequest.of(page - 1, pageSize);
        return this.jpaEmployeeRepository.findAll(employeePage).toList();
    }
}
