/**
 * 
 */
package com.TeachCode.E_commerce.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.TeachCode.E_commerce.Models.Product;
import com.TeachCode.E_commerce.Models.WishList;

/**
 * 
 */
public interface WishListRepository extends JpaRepository<WishList, Long> {

    @Query(value = "SELECT w FROM WishList w WHERE w.userId = :userId")
    List<WishList> findAllWishListById(Long userId);

    Optional<WishList> findByUserId(Long userId);

    @Query("SELECT p FROM WishList w JOIN w.products p WHERE w.userId = :userId")
    Page<Product> findProductsByUserId(@Param("userId") Long userId, Pageable pageable);
}
