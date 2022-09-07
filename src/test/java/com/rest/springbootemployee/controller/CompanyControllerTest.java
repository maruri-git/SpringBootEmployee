package com.rest.springbootemployee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.springbootemployee.model.Company;
import com.rest.springbootemployee.model.Employee;
import com.rest.springbootemployee.services.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {

    @Autowired
    private MockMvc client;

    @Autowired
    private CompanyService companyService;

    @BeforeEach
    void beforeEach() {
        this.companyService.clear();
    }

    private final List<Employee> employeeList = Arrays.asList(
            new Employee(1, "Mark", 23, "Male", 99999),
            new Employee(2, "Luke", 23, "Male", 9999999),
            new Employee(3, "John", 24, "Male", 99999),
            new Employee(4, "Ruby", 24, "Female", 80000),
            new Employee(5, "Lily", 20, "Female", 900000)
    );


    @Test
    void should_return_companies_when_get_given_companies() throws Exception {
        //given
        companyService.addCompany(new Company(1, "spring", employeeList));
        companyService.addCompany(new Company(2, "summer", employeeList));
        //when then
        client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder("spring", "summer")));

    }

    @Test
    void should_return_company_by_id_when_get_given_companies_and_id() throws Exception {
        //given
        Company company = companyService.addCompany(new Company(1, "spring", employeeList));
        //when then
        client.perform(MockMvcRequestBuilders.get("/companies/{id}", company.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("spring")));
    }

    @Test
    void should_return_two_companies_when_get_given_page_and_page_size() throws Exception {
        //given
        final int page = 1;
        final int pageSize = 2;
        companyService.addCompany(new Company(1, "spring", employeeList));
        companyService.addCompany(new Company(2, "summer", employeeList));
        companyService.addCompany(new Company(3, "fall", employeeList));
        companyService.addCompany(new Company(4, "winter", employeeList));
        //when
        client.perform(MockMvcRequestBuilders.get("/companies?page={page}&pageSize={pageSize}", page, pageSize))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(pageSize)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", containsInAnyOrder(1, 2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder("spring", "summer")));

    }

    @Test
    void should_return_employees_when_get_given_company_id() throws Exception {
        //given
        companyService.addCompany(new Company(1, "spring", employeeList));
        //when then
        client.perform(MockMvcRequestBuilders.get("/companies/{id}/employees", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder("Mark", "Luke", "John", "Ruby", "Lily")));
    }

    @Test
    void should_create_company_when_post_given_new_company() throws Exception {
        //given

        String companyStr = new ObjectMapper().writeValueAsString(companyService.addCompany(new Company(1, "spring", employeeList)));
        //when then
        client.perform(MockMvcRequestBuilders.post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(companyStr))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("spring")));
    }

    @Test
    void should_update_company_when_put_given_updated_company_and_id() throws Exception {
        //given
        Company company = companyService.addCompany(new Company(1, "spring", employeeList));
        Company updatedCompany = companyService.updateCompany(company.getId(), company);
        String updatedCompanyJson = new ObjectMapper().writeValueAsString(updatedCompany);
        //when then
        client.perform(MockMvcRequestBuilders.put("/companies/{id}", company.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCompanyJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(company.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(updatedCompany.getName())));
    }

    @Test
    void should_delete_company_when_delete_given_id() throws Exception {
        //given
        Company company = companyService.addCompany(new Company(1, "spring", employeeList));
        //when then
        client.perform(MockMvcRequestBuilders.delete("/companies/{id}", company.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}