/**
 * 
 */
package com.TeachCode.E_commerce.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.TeachCode.E_commerce.DTO.response.ProductCategoryWithCount;
import com.TeachCode.E_commerce.Models.ProductCategory;

/**
 * 
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
	
	@Query("""
			    SELECT new com.TeachCode.E_commerce.DTO.response.ProductCategoryWithCount(
			        c.id, c.categoryName, COUNT(p)
			    )
			    FROM ProductCategory c
			    LEFT JOIN c.products p
			    GROUP BY c.id, c.categoryName
			""")
	List<ProductCategoryWithCount> findAllWithProductCount();

	Optional<ProductCategory> findByCategoryName(String categoryName);
}
