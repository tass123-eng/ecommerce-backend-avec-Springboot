/**
 * 
 */
package com.TeachCode.E_commerce.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryWithCount {
    private Long id;
    private String categoryName;
    private long productCount;
}
