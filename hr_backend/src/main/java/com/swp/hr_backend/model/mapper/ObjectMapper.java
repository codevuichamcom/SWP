package com.swp.hr_backend.model.mapper;

import com.swp.hr_backend.entity.Account;
import com.swp.hr_backend.model.response.LoginResponse;

public class ObjectMapper {
    public static LoginResponse accountToLoginResponse(Account account) {
        LoginResponse loginResponse = LoginResponse.builder().id(account.getAccount_id())
                .username(account.getUsername())
                .firstName(account.getFirstname())
                .lastName(account.getLastname())
                .email(account.getEmail())
                .gender(account.isGender())
                .status(account.isStatus())
                .phone(account.getPhone())
                .urlImg(account.getUrl_img())
                .build();
        return loginResponse;

    }
}
