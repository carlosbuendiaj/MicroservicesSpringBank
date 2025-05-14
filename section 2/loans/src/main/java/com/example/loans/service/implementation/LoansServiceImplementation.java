package com.example.loans.service.implementation;

import com.example.loans.exception.ResourceNotFoundException;
import com.example.loans.constants.LoansConstants;
import com.example.loans.dto.LoansDto;
import com.example.loans.entity.Loans;
import com.example.loans.exception.LoanAlreadyExistsException;
import com.example.loans.mapper.LoansMapper;
import com.example.loans.repository.LoansRepository;
import com.example.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImplementation implements ILoansService {

    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> loans = loansRepository.findByMobileNumber(mobileNumber);
        if(loans.isPresent()){
            throw new LoanAlreadyExistsException("Loan already exists for mobile number " + mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans loans = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        loans.setLoanNumber(String.valueOf(randomLoanNumber));
        loans.setMobileNumber(mobileNumber);
        loans.setLoanType(LoansConstants.HOME_LOAN);
        loans.setTotalLoan(0);
        loans.setAmountPaid(1);
        loans.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return loans;
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow( () ->
        {
            throw new ResourceNotFoundException("Loan", "LoanNumber", mobileNumber);
        });
        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {

            var loansDtoLoanNumber = loansDto.getLoanNumber();

            Loans loans = loansRepository.findByLoanNumber(loansDtoLoanNumber).orElseThrow( () ->
            {
                throw new ResourceNotFoundException("Loan", "mobile number", loansDtoLoanNumber);
            });
            LoansMapper.mapToLoans(loansDto, loans);
            loansRepository.save(loans);
            return true;

    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobile number", mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }
}
