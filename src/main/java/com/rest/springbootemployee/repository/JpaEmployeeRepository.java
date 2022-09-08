package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.model.Company;
import com.rest.springbootemployee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaEmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByGender(String gender);

    List<Employee> findByCompany(Company company);

}
