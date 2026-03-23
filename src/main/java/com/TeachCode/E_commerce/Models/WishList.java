/**
 * 
 */
package com.TeachCode.E_commerce.Models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wishlists")
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    // Une wishlist peut contenir plusieurs produits
    // Relation unidirectionnelle : Wishlist -> Product
    // Avec une relation Bidirectionnelle, Hibernate modifie une collection
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "wishlist_products",
        joinColumns = @JoinColumn(name = "wishlist_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonIgnoreProperties({"category", "productStatus"}) // Éviter la boucle
    private Set<Product> products = new HashSet<>();

    // Méthode utilitaire pour ajouter un produit sans doublon
    public void addProduct(Product product) {
        if (products == null) {
            products = new HashSet<>();
        }
        this.products.add(product);
    }
}
