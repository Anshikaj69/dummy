package com.banking.account_service.controller;


import com.banking.account_service.dto.AccountResponse;
import com.banking.account_service.dto.CreateAccountRequest;
import com.banking.account_service.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/accounts")
@Slf4j
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.createAccount(request));

    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccount(
            @PathVariable String accountNumber ){

        return ResponseEntity.ok(accountService.getAccount(accountNumber));
    }

    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<BigDecimal> getBalance(
            @PathVariable String accountNumber ){

        return ResponseEntity.ok(accountService.getBalance(accountNumber));
    }

    @PutMapping("{accountNumber}/block")
    public ResponseEntity<String> blockAccount(
            @PathVariable String accountNumber ){
        accountService.blockAccount(accountNumber);
        return ResponseEntity.ok("Account blocked");
    }

//    saga step 1: deduct balance, also called by transaction service when trasnfer is inititated

    @PutMapping("{accountNumber}/deduct")
    public ResponseEntity<String> deductBalance(
            @PathVariable String accountNumber,
            @RequestParam BigDecimal amount ){
        accountService.deductBalance(accountNumber, amount);
        return ResponseEntity.ok("balance dedducted");
    }

//    Saga step 4 : commpenssating  transaction endpoint ,
//    called by trasnction service in 2 scenarios :
//    1. fraud detetcted: undo step 1, refund
//    2. transaction completed : credit reciver

    @PutMapping("/{accountNumber}/credit")
    public ResponseEntity<String> creditBalance(@PathVariable String accountNumber, @RequestParam BigDecimal amount){

        accountService.creditBalance(accountNumber, amount);
        return ResponseEntity.ok("balance credited success");
    }



}


