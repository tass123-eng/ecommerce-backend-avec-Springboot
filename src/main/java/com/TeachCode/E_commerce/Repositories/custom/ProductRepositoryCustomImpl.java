/**
 * 
 */
package com.TeachCode.E_commerce.Repositories.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.TeachCode.E_commerce.Models.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * 
 */
@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    /**
     * Méthode personnalisée pour récupérer des produits aléatoires d'une catégorie.
     * Spring Data JPA standard ne permet pas d'utiliser directement la clause 
     * avec des paramètres dynamiques dans une requête JPQL, et l'utilisation 
     * Pour contourner ces limitations, on utilise un repository custom avec un
     */
    @Override
    public List<Product> findRandomByCategory(Long categoryId, Long excludeId, int limit) {
        String sql = "SELECT * FROM products p WHERE p.category_id = :categoryId AND p.id <> :excludeId ORDER BY RAND()";
        return em.createNativeQuery(sql, Product.class)
                .setParameter("categoryId", categoryId)
                .setParameter("excludeId", excludeId)
                .setMaxResults(limit)
                .getResultList();
    }
}
