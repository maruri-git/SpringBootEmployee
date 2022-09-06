package com.rest.springbootemployee.exception;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException() {
        super("No employee found with given id");
    }
}
