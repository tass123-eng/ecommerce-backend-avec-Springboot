/**
 * 
 */
package com.TeachCode.E_commerce.Services.impl;

import org.springframework.stereotype.Service;

import com.TeachCode.E_commerce.Models.Role;
import com.TeachCode.E_commerce.Models.User;
import com.TeachCode.E_commerce.Services.AuthenticationService;

/**
 * 
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Override
    public User getCurrentUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("admin@test.com");
        user.setRole(Role.ADMIN);
        
        return user;
    }

}
