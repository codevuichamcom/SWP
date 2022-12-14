package com.swp.hr_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swp.hr_backend.entity.Account;
import com.swp.hr_backend.exception.custom.CustomUnauthorizedException;
import com.swp.hr_backend.model.CustomError;
import com.swp.hr_backend.model.mapper.ObjectMapper;
import com.swp.hr_backend.model.request.LoginRequest;
import com.swp.hr_backend.model.response.LoginResponse;
import com.swp.hr_backend.service.AccountService;
import com.swp.hr_backend.service.EmployeeService;
import com.swp.hr_backend.service.RoleService;
import com.swp.hr_backend.utils.JwtTokenUtil;

import lombok.RequiredArgsConstructor;
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class AuthenController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final AccountService accountService;
    private final EmployeeService employeeService;
    private final RoleService roleService;
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new CustomUnauthorizedException(CustomError.builder().code("unauthorized").message("Unauthorized").build());
        }
    }
    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest) throws Exception {

        authenticate(loginRequest.getUsername(), loginRequest.getPassword());

         final LoginResponse loginResponse = ObjectMapper.accountToLoginResponse(accountService.findAccountByUsername(loginRequest.getUsername())); 
          Account account = accountService.findAccountByUsername(loginRequest.getUsername());
          String roleName = null;
          Integer roleID = employeeService.findRoleIDByAccountID(account.getAccountID());
          if( roleID != null){
             roleName = "Candidate";
          }
          roleName = roleService.findRolenameByRoleID(roleID);
          loginResponse.setRoleName(roleName);

        if (!loginResponse.isStatus()) {
            throw new CustomUnauthorizedException(CustomError.builder().code("unauthorized")
                    .message("Access denied, you are deactivate").build());
        }

        final String accessToken = jwtTokenUtil.generateToken(loginResponse.getUsername(), JwtTokenUtil.ACCESS_TOKEN_EXPIRED,roleName);
        final String refreshToken = jwtTokenUtil.generateToken(loginResponse.getUsername(), JwtTokenUtil.REFRESH_TOKEN_EXPIRED,roleName);
        loginResponse.setToken(accessToken);
        loginResponse.setRefreshToken(refreshToken);
        return ResponseEntity.ok(loginResponse);
    }

     
}
