package com.rest.springbootemployee.services;

import com.rest.springbootemployee.model.Company;
import com.rest.springbootemployee.model.Employee;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    JpaCompanyRepository jpaCompanyRepository;

    @Autowired
    JpaEmployeeRepository jpaEmployeeRepository;

    public List<Company> findAll() {
        return this.jpaCompanyRepository.findAll();
    }

    public Company findCompanyById(Integer id) {
        return this.jpaCompanyRepository.findById(id).get();
    }

    public List<Employee> findEmployeesByCompanyId(Integer id) {
        Company company = findCompanyById(id);
        return this.jpaEmployeeRepository.findByCompany(company); // Method queries
    }

    public List<Company> findCompanyByPage(Integer page, Integer pageSize) {
        return this.jpaCompanyRepository.findAll(PageRequest.of(page, pageSize)).toList();
    }

    public Company addCompany(Company company) {
        return this.jpaCompanyRepository.save(company);
    }

    public Company updateCompany(Integer id, Company updatedCompany) {
        Company existingCompany = findCompanyById(id);

        if (updatedCompany.getName() != null) {
            existingCompany.setName(updatedCompany.getName());
        }
        if (updatedCompany.getEmployees() != null) {
            existingCompany.setEmployees(updatedCompany.getEmployees());
        }

        return this.jpaCompanyRepository.save(existingCompany);
    }

    public void delete(Integer id) {
        this.jpaCompanyRepository.deleteById(id);
    }

    public void clear() {
        this.jpaCompanyRepository.deleteAll();
    }
}
