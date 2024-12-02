package com.synergybank.accounts.service;

import com.synergybank.accounts.dto.CustomerDto;
import org.springframework.stereotype.Service;

@Service
public interface IAccountsService {
    void createAccount(CustomerDto customerDto);

    CustomerDto fetchCustomer(String mobileNumber);

    void deleteAccount(String mobileNumber);

    void updateAccount(String mobileNumber, CustomerDto customerDto);
}
