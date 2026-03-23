/**
 * 
 */
package com.TeachCode.E_commerce.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.TeachCode.E_commerce.DTO.response.ProductStatusWithCount;
import com.TeachCode.E_commerce.Models.ProductStatus;

/**
 * 
 */
public interface ProductStatusRepository extends JpaRepository<ProductStatus, Long> {
    @Query("""
        SELECT new com.TeachCode.E_commerce.DTO.response.ProductStatusWithCount(
            c.id, c.statusName, COUNT(p)
        )
        FROM ProductStatus c
        LEFT JOIN c.products p
        GROUP BY c.id, c.statusName
    """)
    List<ProductStatusWithCount> findAllWithProductCount();
}
