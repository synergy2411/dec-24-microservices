package com.synergybank.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountsDto {
    private Long accountNumber;

    private Long customerId;

    private String accountType;

    private String branchAddress;
}
