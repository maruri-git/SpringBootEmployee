package com.rest.springbootemployee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.springbootemployee.model.Employee;
import com.rest.springbootemployee.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc client;

    @Autowired
    private EmployeeService employeeService;

    @BeforeEach
    void beforeEach() {
        employeeService.clear();
    }

    @Test
    void should_return_employees_when_get_given_employees() throws Exception {
        //given
        employeeService.add(new Employee(1, "Marcelo", 20, "Male", 20000));
        employeeService.add(new Employee(2, "Maria", 30, "Female", 999999));
        //when then
        client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder("Marcelo", "Maria")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].age", containsInAnyOrder(20, 30)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].gender", containsInAnyOrder("Male", "Female")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].salary", containsInAnyOrder(20000, 999999)));
    }

    @Test
    void should_return_employee_by_id_when_get_given_employees_and_id() throws Exception {
        //given
//        employeeRepository.add(new Employee("Marcelo", 20, "Male", 20000));
        Employee employee = employeeService.add(new Employee(2, "Maria", 30, "Female", 999999));
        //when then
        client.perform(MockMvcRequestBuilders.get("/employees/{id}", employee.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Maria"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(999999));
    }

    @Test
    void should_return_by_gender_when_get_given_employees() throws Exception {
        //given
        employeeService.add(new Employee(1, "Marcelo", 20, "Male", 20000));
        employeeService.add(new Employee(2, "Maria", 30, "Female", 999999));
        employeeService.add(new Employee(3, "Elijah", 25, "Male", 99999));
        employeeService.add(new Employee(4, "Jepster", 22, "Male", 20000));
        //when
        client.perform(MockMvcRequestBuilders.get("/employees?gender={gender}", "Male"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].gender", everyItem(is("Male"))));

    }

    @Test
    void should_return_by_page_when_get_given_employees() throws Exception {
        //given
        employeeService.add(new Employee(1, "Marcelo", 20, "Male", 20000));
        employeeService.add(new Employee(2, "Maria", 30, "Female", 999999));
        employeeService.add(new Employee(3, "Elijah", 25, "Male", 99999));
        employeeService.add(new Employee(4, "Jepster", 22, "Male", 20000));
        //when
        client.perform(MockMvcRequestBuilders.get("/employees?page={page}&pageSize={pageSize}", 1, 2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", containsInAnyOrder("Marcelo", "Maria")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].age", containsInAnyOrder(20, 30)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].gender", containsInAnyOrder("Male", "Female")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].salary", containsInAnyOrder(20000, 999999)));
    }

    @Test
    void should_create_employee_when_post_given_new_employee() throws Exception {
        //given
        Employee employee = new Employee(1, "abcdf", 1, "Male", 1);
        String json = new ObjectMapper().writeValueAsString(employee);
        //when
        client.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("abcdf"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(1));
        // then
        List<Employee> employeeList = employeeService.findAll();
        Employee employeeFromRepository = employeeList.get(0);
        assertThat(employeeList, hasSize(1));
        assertThat(employeeFromRepository.getName(), is("abcdf"));
        assertThat(employeeFromRepository.getAge(), is(1));
        assertThat(employeeFromRepository.getGender(), is("Male"));
        assertThat(employeeFromRepository.getSalary(), is(1));
    }

    @Test
    void should_update_when_put_given_employee_and_updated_employee() throws Exception {
        //given
        Employee employee = employeeService.add(new Employee(1, "Marcelo", 20, "Male", 1));
        Employee updatedEmployee = new Employee(2, "Marcelo", 21, "Male", 2);
        String updatedEmployeeJson = new ObjectMapper().writeValueAsString(updatedEmployee);
        //when
        client.perform(MockMvcRequestBuilders.put("/employees/{id}", employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedEmployeeJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Marcelo")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", is(updatedEmployee.getAge())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary", is(updatedEmployee.getSalary())));
        //then
        Employee employeeFromRepository = employeeService.findAll().get(0);
        assertThat(employeeFromRepository.getName(), is("Marcelo"));
        assertThat(employeeFromRepository.getAge(), is(21));
        assertThat(employeeFromRepository.getSalary(), is(2));
    }

    @Test
    void should_delete_when_delete_given_id() throws Exception {
        //given
        Employee createdEmployee = employeeService.add(new Employee(1, "Marcelo", 20, "Male", 1));
        //when
        client.perform(MockMvcRequestBuilders.delete("/employees/{id}", createdEmployee.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        //then
        List<Employee> employeeList = employeeService.findAll();
        assertThat(employeeList, hasSize(0));
    }
}