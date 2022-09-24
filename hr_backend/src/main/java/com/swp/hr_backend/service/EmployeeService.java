package com.swp.hr_backend.service;

import org.springframework.stereotype.Service;
public interface EmployeeService {
    public Integer findRoleIDByAccountID(String accountID);
}
