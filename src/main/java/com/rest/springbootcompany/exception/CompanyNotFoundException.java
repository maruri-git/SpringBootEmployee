package com.rest.springbootcompany.exception;

public class CompanyNotFoundException extends RuntimeException{
    public CompanyNotFoundException() {
        super("Company not found");
    }
}
