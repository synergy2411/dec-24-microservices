package com.synergybank.accounts.service.impl;

import com.synergybank.accounts.dto.AccountsDto;
import com.synergybank.accounts.dto.CardsDto;
import com.synergybank.accounts.dto.CustomerDetailsDto;
import com.synergybank.accounts.dto.LoansDto;
import com.synergybank.accounts.entity.Accounts;
import com.synergybank.accounts.entity.Customer;
import com.synergybank.accounts.exceptions.ResourseNotFoundException;
import com.synergybank.accounts.mapper.AccountsMapper;
import com.synergybank.accounts.mapper.CustomerMapper;
import com.synergybank.accounts.repository.AccountsRepository;
import com.synergybank.accounts.repository.CustomerRepository;
import com.synergybank.accounts.service.ICustomerDetailsService;
import com.synergybank.accounts.service.client.CardsFeignClient;
import com.synergybank.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerDetailsServiceImpl implements ICustomerDetailsService {

    private CustomerRepository customerRepository;
    private AccountsRepository accountsRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourseNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourseNotFoundException("Accounts", "customer Id", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());

        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);

        CardsDto cardsDto = cardsDtoResponseEntity.getBody();
        customerDetailsDto.setCardsDto(cardsDto);

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);

        LoansDto loansDto = loansDtoResponseEntity.getBody();

        customerDetailsDto.setLoansDto(loansDto);

        return customerDetailsDto;
    }
}
