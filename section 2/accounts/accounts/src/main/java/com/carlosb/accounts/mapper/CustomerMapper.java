package com.carlosb.accounts.mapper;

import com.carlosb.accounts.dto.CustomerDto;
import com.carlosb.accounts.entity.Customer;

public class CustomerMapper {

    public static CustomerDto mapToCustomerDto (Customer customer, CustomerDto customerDto){
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        customerDto.setName(customer.getName());
        return customerDto;
    }

    public static Customer mapToCustomer (CustomerDto customerDto, Customer customer){
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        customer.setName(customerDto.getMobileNumber());
        return customer;
    }

}
