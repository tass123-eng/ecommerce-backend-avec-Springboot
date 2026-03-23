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
import org.springframework.stereotype.Repository;

import com.TeachCode.E_commerce.Models.User;

import jakarta.persistence.Tuple;

/**
 * 
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByVerificationToken(String verificationToken);

    Page<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String firstName,
            String lastName,
            String email,
            Pageable pageable
    );

	// Récupérer statistique des utilisateurs suivant la date
    @Query("""
        SELECT new map(
            FUNCTION('DATE', u.createdAt) as date,
            COUNT(u) as totalUsers
        )
        FROM User u
        WHERE u.createdAt BETWEEN :start AND :end
        GROUP BY DATE(u.createdAt)
        ORDER BY DATE(u.createdAt)
    """)
    List<Map<String, Object>> getUserStats(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    
	// Statistiques mensuelles
    @Query("""
        SELECT MONTH(u.createdAt) as month, COUNT(u) as count
        FROM User u
        WHERE YEAR(u.createdAt) = :year
        GROUP BY MONTH(u.createdAt)
        ORDER BY MONTH(u.createdAt)
    """)
    List<Tuple> getMonthlyNewUsers(@Param("year") int year);
}
