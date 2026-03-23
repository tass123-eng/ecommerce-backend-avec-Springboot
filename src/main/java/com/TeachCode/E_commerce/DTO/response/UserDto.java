/**
 * 
 */
package com.TeachCode.E_commerce.DTO.response;

import com.TeachCode.E_commerce.Models.Role;

/**
 * 
 */
public record UserDto(
	    Long id,
	    String firstName,
	    String lastName,
	    String email,
	    Role role,
	    boolean isEnabled,
	    boolean isVerified
) {}
