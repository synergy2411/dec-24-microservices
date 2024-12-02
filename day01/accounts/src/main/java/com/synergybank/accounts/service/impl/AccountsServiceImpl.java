package com.synergybank.accounts.service.impl;

import com.synergybank.accounts.dto.AccountsDto;
import com.synergybank.accounts.dto.CustomerDto;
import com.synergybank.accounts.entity.Accounts;
import com.synergybank.accounts.entity.Customer;
import com.synergybank.accounts.exceptions.CustomerAlreadyExistsException;
import com.synergybank.accounts.exceptions.ResourseNotFoundException;
import com.synergybank.accounts.mapper.AccountsMapper;
import com.synergybank.accounts.mapper.CustomerMapper;
import com.synergybank.accounts.repository.AccountsRepository;
import com.synergybank.accounts.repository.CustomerRepository;
import com.synergybank.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private CustomerRepository customerRepository;
    private AccountsRepository accountsRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already exists in the Database - " + customerDto.getName());
        }
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        customerRepository.save(customer);

        Accounts accounts = createNewAccount(customer.getCustomerId());
        accountsRepository.save(accounts);
    }


    private Accounts createNewAccount(Long customerId) {
        Accounts accounts = new Accounts();
        Long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        accounts.setAccountNumber(randomAccNumber);
        accounts.setAccountType("SAVINGS");
        accounts.setCustomerId(customerId);
        accounts.setBranchAddress("201 Main Road, Wakad, Pune");
        return accounts;
    }


    @Override
    public CustomerDto fetchCustomer(String mobileNumber) {
        Customer foundCustomer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourseNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        CustomerDto customerDto = CustomerMapper
                .mapToCustomerDto(foundCustomer, new CustomerDto());

        Accounts accounts = accountsRepository.findByCustomerId(foundCustomer.getCustomerId()).orElseThrow(
                () -> new ResourseNotFoundException("Accounts", "customer id", foundCustomer.getCustomerId().toString())
        );
        AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(accounts, new AccountsDto());
        customerDto.setAccountsDto(accountsDto);
        return customerDto;
    }

    @Override
    public void deleteAccount(String mobileNumber) {
        Customer foundCustomer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourseNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(foundCustomer.getCustomerId()).orElseThrow(
                () -> new ResourseNotFoundException("Accounts", "customer id", foundCustomer.getCustomerId().toString())
        );
        accountsRepository.delete(accounts);
        customerRepository.delete(foundCustomer);
    }

    @Override
    public void updateAccount(String mobileNumber, CustomerDto customerDto) {
        Customer foundCustomer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourseNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Customer updatedCustomer = CustomerMapper.mapToCustomer(customerDto, foundCustomer);

        Accounts accounts = accountsRepository.findByCustomerId(foundCustomer.getCustomerId()).orElseThrow(
                () -> new ResourseNotFoundException("Accounts", "customer id", foundCustomer.getCustomerId().toString())
        );

        Accounts updatedAccount = AccountsMapper.mapToAccounts(customerDto.getAccountsDto(), accounts);

        customerRepository.save(updatedCustomer);
        accountsRepository.save(updatedAccount);
    }

}
