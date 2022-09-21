package com.swp.hr_backend.service;

import com.swp.hr_backend.entity.Account;
import com.swp.hr_backend.repository.AccountRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private AccountRepository accountRepository;
    @Override
    public Account findAccountByUsername(String username) {
        return  accountRepository.findByUsername(username).get();
    }
     
}
