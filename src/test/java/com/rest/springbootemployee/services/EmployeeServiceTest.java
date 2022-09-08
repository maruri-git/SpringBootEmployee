package com.rest.springbootemployee.services;

import com.rest.springbootemployee.model.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @InjectMocks
    EmployeeService employeeService;
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    JpaEmployeeRepository jpaEmployeeRepository;

    @BeforeEach
    void cleanRepository() {
        employeeRepository.clear();
        jpaEmployeeRepository.deleteAll();
    }

    @Test
    void should_return_all_employees_when_find_all_given_employees() {
        //given
        Employee maleEmployee = new Employee(1, "TestMale", 1, "Male", 1);
        Employee femaleEmployee = new Employee(2, "TestFemale", 1, "Female", 1);
        List<Employee> employeeList = new ArrayList<>(Arrays.asList(maleEmployee, femaleEmployee));
        when(jpaEmployeeRepository.findAll()).thenReturn(employeeList);
        //when
        List<Employee> employeesFromRepository = employeeService.findAll();
        //then
        verify(jpaEmployeeRepository).findAll();
        assertThat(employeesFromRepository, hasSize(2));
        assertThat(employeesFromRepository.get(0), is(maleEmployee));
        assertThat(employeesFromRepository.get(1), is(femaleEmployee));
    }

    @Test
    void should_return_employee_when_find_by_id_given_employees_and_id() {
        //given
        final int id = 1;
        Employee maleEmployee = new Employee(id, "TestMale", 20, "Male", 2000);
        when(jpaEmployeeRepository.findById(id)).thenReturn(Optional.of(maleEmployee));
        //when
        Employee employee = employeeService.findById(id);
        //then
        verify(jpaEmployeeRepository).findById(id);
        assertThat(employee, is(maleEmployee));
        assertThat(employee.getId(), is(id));
    }

    @Test
    void should_return_male_employees_when_find_by_gender_given_employees() {
        //given
        Employee maleEmployeeOne = new Employee(1, "Male1", 20, "Male", 2000);
        Employee maleEmployeeTwo = new Employee(2, "Male2", 20, "Male", 2000);
        List<Employee> maleEmployeeList = new ArrayList<>(Arrays.asList(maleEmployeeOne, maleEmployeeTwo));
        given(jpaEmployeeRepository.findByGender(anyString())).willReturn(maleEmployeeList);
        //when
        List<Employee> employeesFromRepository = employeeService.findByGender("Male");
        //then
        verify(jpaEmployeeRepository).findByGender("Male");
        assertThat(employeesFromRepository, hasSize(2));
        assertThat(employeesFromRepository, everyItem(hasProperty("gender", is("Male"))));
    }

    @Test
    void should_return_two_employees_when_find_by_page_given_page_page_size() {
        //given
        int page = 0;
        int pageSize = 2;
        Employee employee1 = new Employee(1, "Male1", 20, "Male", 2000);
        Employee employee2 = new Employee(2, "Female1", 22, "Female", 2000);
        List<Employee> employeeList = new ArrayList<>(Arrays.asList(employee1, employee2));
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Employee> employeePage = new PageImpl<>(employeeList);

        given(jpaEmployeeRepository.findAll(pageable)).willReturn(employeePage);
        //when
        List<Employee> employeesFromRepository = employeeService.findByPage(page + 1, pageSize);
        //then
        verify(jpaEmployeeRepository).findAll(pageable);
        assertThat(employeesFromRepository, hasSize(2));
//        assertThat(employeesFromRepository, everyItem(hasProperty("id", contains(employee1.getId(), employee2.getId()))));
        assertThat(employeesFromRepository.get(0).getId(), is(employee1.getId()));
        assertThat(employeesFromRepository.get(1).getId(), is(employee2.getId()));
    }

    @Test
    void should_return_updated_employee_when_update_given_updated_employee_and_id() {
        //given
        Employee employee = new Employee(1, "Ruby", 20, "Female", 3000);

        Employee editedEmployee = new Employee(1, "Ruby", 21, "Female", 3001);
        
        given(jpaEmployeeRepository.findById(1)).willReturn(Optional.of(editedEmployee));
        //when
        Employee updatedEmployee = employeeService.updateEmployee(editedEmployee);
        //then
        verify(jpaEmployeeRepository).findById(employee.getId());
        assertThat(updatedEmployee.getName(), is(employee.getName()));
        assertThat(updatedEmployee.getAge(), is(21));
        assertThat(updatedEmployee.getGender(), is(employee.getGender()));
        assertThat(updatedEmployee.getSalary(), is(3001));
    }

    @Test
    void should_add_employee_when_add_given_new_employee() {
        //given
        Employee employee = new Employee(1, "TestMale", 20, "Male", 2000);
        when(jpaEmployeeRepository.save(employee)).thenReturn(employee);
        //when
        Employee newEmployee = employeeService.save(employee);
        //then
        verify(jpaEmployeeRepository).save(employee);
        assertThat(newEmployee.getId(), is(1));
        assertThat(newEmployee, is(employee));
    }

    @Test
    void should_delete_employee_when_delete_given_id() {
        //given
        doNothing().when(jpaEmployeeRepository).deleteById(1);
        //when
        employeeService.deleteById(1);
        //then
        verify(jpaEmployeeRepository).deleteById(1);
    }
}
