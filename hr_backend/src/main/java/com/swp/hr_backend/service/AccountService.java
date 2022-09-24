package com.swp.hr_backend.service;
import com.swp.hr_backend.entity.Account;
public interface AccountService {
    public Account findAccountByUsername(String username);
}
