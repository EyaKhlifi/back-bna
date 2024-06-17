package com.example.ebank.service;

import com.example.ebank.entity.Account;
import com.example.ebank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAccountsByClientId(Long clientId) {
        return accountRepository.findByClientId(clientId);
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    // other methods
}
