/**
 * 
 */
package com.TeachCode.E_commerce.DTO.request;

import lombok.Data;

/**
 * 
 */
@Data
public class ResetPasswordRequest {
    private String email;
    private String newPassword;
}
