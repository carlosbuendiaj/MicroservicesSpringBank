package com.carlosb.accounts.service.implementation;

import com.carlosb.accounts.exception.CustomerAlreadyExistsException;
import com.carlosb.accounts.exception.ResourceNotFoundException;
import com.carlosb.accounts.mapper.AccountsMapper;
import com.carlosb.accounts.mapper.CustomerMapper;
import com.carlosb.accounts.repository.AccountsRepository;
import com.carlosb.accounts.repository.CustomerRepository;
import com.carlosb.accounts.service.IAccountsService;
import com.carlosb.accounts.constants.AccountConstants;
import com.carlosb.accounts.dto.AccountsDto;
import com.carlosb.accounts.dto.CustomerDto;
import com.carlosb.accounts.entity.Accounts;
import com.carlosb.accounts.entity.Customer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImplementation implements IAccountsService {


    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        customerRepository.findByMobileNumber(customer.getMobileNumber())
                .ifPresent(c -> {
                    throw new CustomerAlreadyExistsException("Customer already registered with mobile number " + customerDto.getMobileNumber());
                });
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));

    }


    private Accounts createNewAccount(Customer customer){
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);
        return newAccount;

    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {

        Customer customer =  customerRepository.findByMobileNumber(mobileNumber).orElseThrow( () ->
         {
             throw new ResourceNotFoundException("Customer", "mobile number", mobileNumber);
         });

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow( () ->
        {
            throw new ResourceNotFoundException("Account", "CustomerId", customer.getCustomerId().toString());
        });

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.maptoAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean UpdateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;

        AccountsDto accountsDto = customerDto.getAccountsDto();
        if ( accountsDto != null){

            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow( () ->
                    new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accounts, accountsDto);
            accounts = accountsRepository.save(accounts);
            var customerId =accounts.getCustomerId();

            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "customerId", customerId.toString())
            );

            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow( () ->
        {
                throw new ResourceNotFoundException("Customer", "mobile number", mobileNumber);
        });
        var customerId =customer.getCustomerId();
        accountsRepository.deleteByCustomerId(customerId);
        customerRepository.deleteById(customerId);

        return true;
    }


}
