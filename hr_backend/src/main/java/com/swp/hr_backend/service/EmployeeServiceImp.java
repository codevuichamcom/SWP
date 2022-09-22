package com.swp.hr_backend.service;

import java.util.Optional;

import com.swp.hr_backend.entity.Employee;
import com.swp.hr_backend.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmployeeServiceImp implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    @Override
    public Integer findRoleIDByAccountID(String accountID) {
        Optional<Employee> employee = employeeRepository.findById(accountID);
        if(employee.isEmpty()){
            return null;
        }
        return employee.get().getRole().getRole_id();
    }
    
}
