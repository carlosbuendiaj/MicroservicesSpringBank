package com.carlosb.accounts.service;

import com.carlosb.accounts.dto.CustomerDto;

public interface IAccountsService {

    /*
    *   creates an account
    *  @param customerDTO -CustomerDtoObject
    */

    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccount(String mobileNumber);

    boolean UpdateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);
}
