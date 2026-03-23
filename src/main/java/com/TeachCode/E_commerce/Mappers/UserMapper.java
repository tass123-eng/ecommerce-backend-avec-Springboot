/**
 * 
 */
package com.TeachCode.E_commerce.Mappers;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.TeachCode.E_commerce.DTO.response.DtoUser;
import com.TeachCode.E_commerce.DTO.response.UserDto;
import com.TeachCode.E_commerce.Models.User;

/**
 * 
 */
@Service
public class UserMapper implements Function<User, DtoUser> {

    @Override
    public DtoUser apply(User user) {
        
        return new DtoUser(
                user.getId(), 
                user.getFullName(), 
                user.getEmail(),user.getPassword(),
                user.getRole()
        );
    }

    public UserDto toResponse(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                user.isEnabled(),
                user.isVerified()
        );
    }
}
