package com.rest.springbootemployee.services;

import com.rest.springbootemployee.model.Company;
import com.rest.springbootemployee.model.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ControllerServiceTest {

    @InjectMocks
    private CompanyService companyService;

    @Mock
    private CompanyRepository companyRepository;
    private final List<Employee> employeeList = Arrays.asList(
            new Employee(1, "Mark", 23, "Male", 99999),
            new Employee(2, "Luke", 23, "Male", 9999999),
            new Employee(3, "John", 24, "Male", 99999),
            new Employee(4, "Ruby", 24, "Female", 80000),
            new Employee(5, "Lily", 20, "Female", 900000)
    );

    @Test
    void should_return_all_companies_when_get_all_given_companies() {
        //given
        Company company1 = new Company(1, "spring", employeeList);
        Company company2 = new Company(2, "summer", employeeList);

        List<Company> companies = new ArrayList<>(Arrays.asList(company1, company2));
        when(companyRepository.findAll()).thenReturn(companies);
        //when
        List<Company> companiesFromRepository = companyService.findAll();
        //then
        verify(companyRepository).findAll();
        assertThat(companiesFromRepository, hasSize(2));
    }
    @Test
    void should_return_company_when_find_by_id_given_id() {
        //given
        Company company = new Company(1, "spring", employeeList);
        when(companyRepository.findCompanyById(1)).thenReturn(company);
        //when
        Company companyFromRepository = companyService.findCompanyById(1);
        //then
        verify(companyRepository).findCompanyById(1);
        assertThat(companyFromRepository.getId(), is(1));
        assertThat(companyFromRepository.getName(), is("spring"));
    }
    @Test
    void should_return_two_companies_when_find_by_page_given_page_page_size() {
        //given
        final int page = 1;
        final int pageSize = 2;
        Company company1 = new Company(1, "spring", employeeList);
        Company company2 = new Company(2, "summer", employeeList);
        List<Company> companies = new ArrayList<>(Arrays.asList(company1,company2));
        when(companyRepository.findCompanyByPage(page,pageSize)).thenReturn(companies);
        //when
        List<Company> companiesFromRepository = companyService.findCompanyByPage(page,pageSize);
        //then
        verify(companyRepository).findCompanyByPage(page,pageSize);
        assertThat(companiesFromRepository, hasSize(2));
    }
    @Test
    void should_return_added_company_when_add_given_new_company() {
        //given
        Company company = new Company(1, "spring", employeeList);
        when(companyRepository.addCompany(company)).thenReturn(company);
        //when
        Company newCompany = companyService.addCompany(company);
        //then
        verify(companyRepository).addCompany(company);
        assertThat(newCompany.getName(), is("spring"));
    }
    @Test
    void should_return_updated_company_when_update_given_id_and_new_company_details() {
        //given
        Company company = new Company(1, "spring", employeeList);

        when(companyRepository.updateCompany(1, company)).thenReturn(company);
        //when
        Company updatedCompany = companyService.updateCompany(1,company);
        //then
        verify(companyRepository).updateCompany(1,company);
        assertThat(updatedCompany.getId(), is(1));
        assertThat(updatedCompany.getName(), is("spring"));
    }
    @Test
    void should_delete_company_when_delete_given_id() {
        //given
        doNothing().when(companyRepository).delete(1);
        //when
        companyService.delete(1);
        //then
        verify(companyRepository).delete(1);
    }
}
