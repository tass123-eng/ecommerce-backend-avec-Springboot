/**
 * 
 */
package com.TeachCode.E_commerce.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategoryRequestDto {

    @NotBlank(message = "Category name cannot be empty")
    @Size(max = 50, message = "Category name cannot exceed 50 characters")
    private String categoryName;
}
