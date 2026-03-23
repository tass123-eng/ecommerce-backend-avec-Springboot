/**
 * 
 */
package com.TeachCode.E_commerce.Models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "products") // Évite les boucles infinies lors de l'affichage
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Se base uniquement sur l'ID
@Entity
@Table(name = "product_status")
public class ProductStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include // Utilisé pour hashCode et equals
    private Long id;

    @Column(name = "status_name")
    private String statusName;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productStatus")
    // Set<Product> pour stocker la liste (sans doublons) de tous les produits
    private Set<Product> products;
}
