package com.rest.springbootemployee.services;

import com.rest.springbootemployee.model.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
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
public class EmployeeServiceTest {
    @InjectMocks
    EmployeeService employeeService;
    @Mock
    EmployeeRepository employeeRepository;

    @Test
    void should_return_all_employees_when_find_all_given_employees() {
        //given
        Employee maleEmployee = new Employee(1, "TestMale", 1, "Male", 1);
        Employee femaleEmployee = new Employee(2, "TestFemale", 1, "Female", 1);
        List<Employee> employeeList = new ArrayList<>(Arrays.asList(maleEmployee, femaleEmployee));
        when(employeeRepository.findAll()).thenReturn(employeeList);
        //when
        List<Employee> employeesFromRepository = employeeService.findAll();
        //then
        verify(employeeRepository).findAll();
        assertThat(employeesFromRepository, hasSize(2));
        assertThat(employeesFromRepository.get(0), is(maleEmployee));
        assertThat(employeesFromRepository.get(1), is(femaleEmployee));
    }

    @Test
    void should_return_employee_when_find_by_id_given_employees_and_id() {
        //given
        final int id = 1;
        Employee maleEmployee = new Employee(id, "TestMale", 20, "Male", 2000);
        maleEmployee.setId(id);
        when(employeeRepository.findById(id)).thenReturn(maleEmployee);
        //when
        Employee employee = employeeService.findById(id);
        //then
        verify(employeeRepository).findById(id);
        assertThat(employee, is(maleEmployee));
        assertThat(employee.getId(), is(id));
    }

    @Test
    void should_return_male_employees_when_find_by_gender_given_employees() {
        //given
        Employee maleEmployeeOne = new Employee(1, "Male1", 20, "Male", 2000);
        Employee maleEmployeeTwo = new Employee(2, "Male2", 20, "Male", 2000);
        List<Employee> maleEmployeeList = new ArrayList<>(Arrays.asList(maleEmployeeOne, maleEmployeeTwo));
        when(employeeRepository.findByGender(anyString())).thenReturn(maleEmployeeList);
        //when
        List<Employee> employeesFromRepository = employeeService.findByGender("Male");
        //then
        verify(employeeRepository).findByGender("Male");
        assertThat(employeesFromRepository, hasSize(2));
        assertThat(employeesFromRepository, everyItem(hasProperty("gender", is("Male"))));
    }

    @Test
    void should_return_two_employees_when_find_by_page_given_page_page_size() {
        //given
        int page = 1;
        int pageSize = 2;
        Employee employee1 = new Employee(1, "Male1", 20, "Male", 2000);
        Employee employee2 = new Employee(2, "Female1", 22, "Female", 2000);

        List<Employee> employeeList = new ArrayList<>(Arrays.asList(employee1, employee2));
        when(employeeRepository.findByPage(anyInt(), anyInt())).thenReturn(employeeList);
        //when
        List<Employee> employeesFromRepository = employeeService.findByPage(page, pageSize);
        //then
        verify(employeeRepository).findByPage(page, pageSize);
        assertThat(employeesFromRepository, hasSize(2));
        System.out.println(employeesFromRepository);
//        assertThat(employeesFromRepository, everyItem(hasProperty("id", contains(employee1.getId(), employee2.getId()))));
        assertThat(employeesFromRepository.get(0).getId(), is(employee1.getId()));
        assertThat(employeesFromRepository.get(1).getId(), is(employee2.getId()));
    }

    @Test
    void should_return_updated_employee_when_update_given_updated_employee_and_id() {
        //given
        Employee employee = new Employee(1, "Ruby", 20, "Female", 3000);
        Employee editedEmployee = new Employee(1, "Ruby", 21, "Female", 3000);

        when(employeeRepository.updateEmployee(employee)).thenReturn(employee);
        //when
        Employee updatedEmployee = employeeService.updateEmployee(editedEmployee);
        //then
        verify(employeeRepository).updateEmployee(employee);
        assertThat(updatedEmployee.getAge(), is(21));
        assertThat(updatedEmployee.getSalary(), is(3000));
    }

    @Test
    void should_add_employee_when_add_given_new_employee() {
        //given
        Employee employee = new Employee(1, "TestMale", 20, "Male", 2000);
        when(employeeRepository.add(employee)).thenReturn(employee);
        //when
        Employee newEmployee = employeeService.add(employee);
        //then
        verify(employeeRepository).add(employee);
        assertThat(newEmployee.getId(), is(1));
        assertThat(newEmployee, is(employee));
    }

    @Test
    void should_delete_employee_when_delete_given_id() {
        //given
        doNothing().when(employeeRepository).delete(1);
        //when
        employeeService.delete(1);
        //then
        verify(employeeRepository).delete(1);
    }
}
