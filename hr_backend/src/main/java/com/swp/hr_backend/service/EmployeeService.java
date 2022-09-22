package com.swp.hr_backend.service;

import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    public Integer findRoleIDByAccountID(String accountID);
}
