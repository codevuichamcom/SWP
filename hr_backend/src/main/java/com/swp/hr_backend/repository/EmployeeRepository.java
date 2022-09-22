package com.swp.hr_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swp.hr_backend.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,String> {
    public Employee findByAccount_id(String account_id);
}
