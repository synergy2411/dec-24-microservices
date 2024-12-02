package com.synergybank.accounts.service.impl;

import com.synergybank.accounts.dto.CustomerDto;
import com.synergybank.accounts.entity.Customer;
import com.synergybank.accounts.exceptions.CustomerAlreadyExistsException;
import com.synergybank.accounts.mapper.CustomerMapper;
import com.synergybank.accounts.repository.CustomerRepository;
import com.synergybank.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already exists in the Database - " + customerDto.getName());
        }
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        customerRepository.save(customer);
    }
}
