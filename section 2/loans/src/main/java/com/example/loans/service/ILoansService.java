package com.example.loans.service;


import com.example.loans.dto.LoansDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

public interface ILoansService {

    void createLoan(String mobileNumber);

    LoansDto fetchLoan(String mobileNumber);

    boolean updateLoan(LoansDto loansDto);

    boolean deleteLoan(String mobileNumber);
}
