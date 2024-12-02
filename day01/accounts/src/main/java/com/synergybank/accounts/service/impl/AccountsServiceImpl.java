package com.synergybank.accounts.service.impl;

import com.synergybank.accounts.dto.AccountsDto;
import com.synergybank.accounts.dto.CustomerDto;
import com.synergybank.accounts.entity.Accounts;
import com.synergybank.accounts.entity.Customer;
import com.synergybank.accounts.exceptions.CustomerAlreadyExistsException;
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
}
