package com.swp.hr_backend.service;

import org.springframework.stereotype.Service;

import com.swp.hr_backend.entity.Account;

@Service
public interface AccountService {
    public Account findAccountByUsername(String username);
}
