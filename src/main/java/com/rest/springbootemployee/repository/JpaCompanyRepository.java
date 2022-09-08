package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.model.Company;
import com.rest.springbootemployee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaCompanyRepository extends JpaRepository<Company, Integer> {

//    @Query(value = "SELECT c.employees FROM Company c WHERE cCompanyId=:companyId", nativeQuery = true)
//    public List<Employee> findEmployeesByCompanyId(Integer companyId);

}
