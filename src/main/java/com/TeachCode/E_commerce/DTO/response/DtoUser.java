/**
 * 
 */
package com.TeachCode.E_commerce.DTO.response;

import com.TeachCode.E_commerce.Models.Role;

/**
 * 
 */
public record DtoUser(
	    Long id,
	    String fullName,
	    String email,
	    String password,
	    Role role
) {}
