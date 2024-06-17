package com.example.ebank.controller;

import com.example.ebank.entity.Account;
import com.example.ebank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/client/{clientId}")
    public List<Account> getAccountsByClientId(@PathVariable Long clientId) {
        return accountService.getAccountsByClientId(clientId);
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.saveAccount(account);
    }

    // other endpoints
}
