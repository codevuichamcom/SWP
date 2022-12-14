package com.swp.hr_backend.service;

import org.springframework.stereotype.Service;

import com.swp.hr_backend.entity.Account;
import com.swp.hr_backend.repository.AccountRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
    @Override
    public Account findAccountByUsername(String username) {
        return  accountRepository.findByUsername(username).get();
    }
     
}
