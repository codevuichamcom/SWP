package com.swp.hr_backend.service;

import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    public String findRolenameByRoleID(int role_id);
}
