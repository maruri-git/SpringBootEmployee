package com.rest.springbootemployee.services;

import com.rest.springbootemployee.model.Company;
import com.rest.springbootemployee.model.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    public List<Company> findAll() {
        return this.companyRepository.findAll();
    }

    public Company findCompanyById(Integer id) {
        return this.companyRepository.findCompanyById(id);
    }

    public List<Employee> findEmployeesByCompanyId(Integer id) {
        return this.companyRepository.findEmployeesByCompanyId(id);
    }

    public List<Company> findCompanyByPage(Integer page, Integer pageSize) {
        return this.companyRepository.findCompanyByPage(page, pageSize);
    }

    public Company addCompany(Company company) {
        return this.companyRepository.addCompany(company);
    }

    public Company updateCompany(Integer id, Company company) {
        return this.companyRepository.updateCompany(id, company);
    }

    public void delete(Integer id) {
        this.companyRepository.delete(id);
    }
}
