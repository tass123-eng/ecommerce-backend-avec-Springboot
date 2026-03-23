/**
 * 
 */
package com.TeachCode.E_commerce.DTO.request;

import lombok.Data;

/**
 * 
 */
@Data
public class OtpVerificationRequest {
    private String email;
    private String otp;
}
