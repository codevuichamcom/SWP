package com.swp.hr_backend.service;

import java.util.Optional;

import com.swp.hr_backend.entity.Role;
import com.swp.hr_backend.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public String findRolenameByRoleID(int role_id) {
         Optional<Role> role =  roleRepository.findById(role_id);
         if(role.isEmpty()){
            return null;
         }
         return role.get().getRole_name();  
    }
    

}
