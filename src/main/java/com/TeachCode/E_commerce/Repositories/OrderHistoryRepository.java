/**
 * 
 */
package com.TeachCode.E_commerce.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TeachCode.E_commerce.Models.OrderHistory;

/**
 * 
 */
@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

}
