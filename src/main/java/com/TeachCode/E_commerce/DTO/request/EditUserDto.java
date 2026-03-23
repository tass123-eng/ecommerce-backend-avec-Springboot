/**
 * 
 */
package com.TeachCode.E_commerce.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditUserDto {
    private String fullName;
    private String email;
}
