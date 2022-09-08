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

}
