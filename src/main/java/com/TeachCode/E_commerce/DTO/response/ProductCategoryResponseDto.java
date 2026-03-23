/**
 * 
 */
package com.TeachCode.E_commerce.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategoryResponseDto {
    private Long id;
    private String categoryName;
}
