package com.synergybank.accounts.controller;

import com.synergybank.accounts.dto.CustomerDto;
import com.synergybank.accounts.dto.ResponseDto;
import com.synergybank.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AccountsController {

    private IAccountsService iAccountsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> create(@RequestBody CustomerDto customerDto){
        iAccountsService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto("Success", HttpStatus.CREATED));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetch(@RequestParam String mobileNumber){
        CustomerDto customerDto = iAccountsService.fetchCustomer(mobileNumber);
        return ResponseEntity.status(200).body(customerDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> delete(@RequestParam String mobileNumber){
        iAccountsService.deleteAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto());
    }

    @PatchMapping("/update")
    public ResponseEntity<ResponseDto> update(@RequestBody CustomerDto customerDto,
                                              @RequestParam String mobileNumber){
        iAccountsService.updateAccount(mobileNumber, customerDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ResponseDto("Updated", HttpStatus.ACCEPTED)
        );
    }
}
