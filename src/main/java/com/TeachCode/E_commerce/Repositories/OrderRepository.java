/**
 * 
 */
package com.TeachCode.E_commerce.Repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.TeachCode.E_commerce.DTO.response.TopSellingProductDTO;
import com.TeachCode.E_commerce.Models.Order;
import com.TeachCode.E_commerce.Models.OrderStatus;

import jakarta.persistence.Tuple;

/**
 * 
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
	Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    @Query("SELECT SUM(o.totalPrice + o.shippingCost) FROM Order o WHERE o.status = com.TeachCode.E_commerce.Models.OrderStatus.DELIVERED")
    Double getTotalRevenue();

    Page<Order> findOrdersByUserId(Long userId, Pageable pageable);

    @Query("""
        SELECT new com.TeachCode.E_commerce.DTO.response.TopSellingProductDTO(
            p,
            SUM(oi.quantity),
            SUM(oi.quantity * oi.price)
        )
        FROM Order o
        JOIN o.items oi
        JOIN Product p ON p.id = oi.productId
        WHERE o.status = com.TeachCode.E_commerce.Models.OrderStatus.DELIVERED
        GROUP BY p
    """)
    List<TopSellingProductDTO> findTopSellingProducts(Pageable pageable);
    
 // Statistique (Nombre de commandes, nombre de produits, nombre de articles) par date
    @Query("""
        SELECT new map(
            DATE(o.createdAt) as date,
            COUNT(DISTINCT o) as totalOrders,
            COUNT(oi) as totalItems,
            COUNT(DISTINCT oi.productId) as totalProducts
        )
        FROM Order o
        JOIN o.items oi
        WHERE o.createdAt BETWEEN :start AND :end
        GROUP BY DATE(o.createdAt)
        ORDER BY DATE(o.createdAt)
    """)
    List<Map<String, Object>> getOrdersStatsPerDate(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    // Chiffre d'affaires mensuel (commandes livrées uniquement)
    @Query("""
        SELECT MONTH(o.createdAt) as month,
        SUM(o.totalPrice + o.shippingCost) as revenue
        FROM Order o
        WHERE YEAR(o.createdAt) = :year
        AND o.status = com.TeachCode.E_commerce.Models.OrderStatus.DELIVERED
        GROUP BY MONTH(o.createdAt)
        ORDER BY MONTH(o.createdAt)
    """)
    List<Tuple> getMonthlyRevenue(@Param("year") int year);
    
 // Répartition des commandes par statut
    @Query("""
        SELECT o.status as status, COUNT(o) as count
        FROM Order o
        WHERE YEAR(o.createdAt) = :year
        GROUP BY o.status
    """)
    List<Tuple> getOrderStatusCounts(@Param("year") int year);

    // Récupérer les commandes par produit
    @Query("SELECT o FROM Order o JOIN o.items i WHERE i.productId = :productId")
    Page<Order> findOrdersByProductId(@Param("productId") Long productId, Pageable pageable);

    // Récupérer les ventes pour un produit sur une année (commandes livrées uniquement)
    @Query("""
        SELECT MONTH(o.createdAt) as month,
        SUM(i.price * i.quantity) as totalSales
        FROM Order o
        JOIN o.items i
        WHERE YEAR(o.createdAt) = :year
        AND i.productId = :productId
        AND o.status = com.TeachCode.E_commerce.Models.OrderStatus.DELIVERED
        GROUP BY MONTH(o.createdAt)
    """)
    List<Tuple> getMonthlySalesByProduct(@Param("year") int year, @Param("productId") Long productId);
    
 // Récupérer le nombre total d'articles vendus et la somme des ventes (commandes livrées uniquement)
    @Query("""
        SELECT
            SUM(oi.quantity) AS totalQuantity,
            SUM(oi.quantity * oi.price) AS totalSales
        FROM Order o
        JOIN o.items oi
        WHERE oi.productId = :productId
        AND o.status = com.TeachCode.E_commerce.Models.OrderStatus.DELIVERED
    """)
    Tuple getProductSalesSummary(@Param("productId") Long productId);

    // Récupération complète d'une commande avec ses items et son historique (optimisé FETCH)
    @Query("""
        SELECT o FROM Order o
        LEFT JOIN FETCH o.items
        LEFT JOIN FETCH o.histories h
        LEFT JOIN FETCH h.changedBy
        WHERE o.id = :id
    """)
    Optional<Order> findByIdWithItemsAndHistories(@Param("id") Long id);
    
}
