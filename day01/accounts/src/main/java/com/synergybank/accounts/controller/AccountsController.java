package com.synergybank.accounts.controller;

import com.synergybank.accounts.dto.CustomerDto;
import com.synergybank.accounts.dto.ResponseDto;
import com.synergybank.accounts.service.IAccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Validated
public class AccountsController {

    private IAccountsService iAccountsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> create(@RequestBody @Valid CustomerDto customerDto){
        iAccountsService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto("Success", HttpStatus.CREATED));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetch(@RequestParam
                                                 @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number should have 10 digits")
                                                 String mobileNumber){
        CustomerDto customerDto = iAccountsService.fetchCustomer(mobileNumber);
        return ResponseEntity.status(200).body(customerDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> delete(@RequestParam
                                                  @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number should have 10 digits")
                                                  String mobileNumber){
        iAccountsService.deleteAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto("Deleted", HttpStatus.OK));
    }

    @PatchMapping("/update")
    public ResponseEntity<ResponseDto> update(@RequestBody @Valid CustomerDto customerDto,
                                              @RequestParam
                                              @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number should have 10 digits")
                                              String mobileNumber){
        iAccountsService.updateAccount(mobileNumber, customerDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ResponseDto("Updated", HttpStatus.ACCEPTED)
        );
    }
}
