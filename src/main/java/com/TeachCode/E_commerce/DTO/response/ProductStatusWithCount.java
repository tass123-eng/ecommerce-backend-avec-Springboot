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
public class ProductStatusWithCount {
    private Long id;
    private String statusName;
    private long productCount;
}
