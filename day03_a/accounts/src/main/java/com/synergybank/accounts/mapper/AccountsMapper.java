package com.synergybank.accounts.mapper;

import com.synergybank.accounts.dto.AccountsDto;
import com.synergybank.accounts.entity.Accounts;

public class AccountsMapper {

    public static AccountsDto mapToAccountsDto(Accounts accounts, AccountsDto accountsDto) {
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setCustomerId(accounts.getCustomerId());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }

    public static Accounts mapToAccounts(AccountsDto accountsDto, Accounts accounts) {
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setCustomerId(accountsDto.getCustomerId());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }

}
