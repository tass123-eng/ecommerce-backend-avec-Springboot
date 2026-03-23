/**
 * 
 */
package com.TeachCode.E_commerce.Models;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 */
@Data
@Builder
@ToString // Remplace la méthode toString pour l'affichage
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    // precision : Nombre total de chiffres
    // scale : Nombre de chiffres après la virgule
    @Column(name = "unit_price", precision = 13, scale = 3, nullable = false)
    private BigDecimal unitPrice;

    // new field for storing image data as byte array
    // @Lob : indique que l'attribut doit être stocké sous forme de "Large OBject"
    // Elle est utilisée pour gérer des données volumineuses, comme des fichiers
    @Lob
    @Column(name = "image_data", columnDefinition = "LONGBLOB")
    private byte[] imageData;

    @Column(name = "image_content_type")
    private String imageContentType; // To store the mime type of the image

    @Column(name = "active")
    private Boolean active;

    @Column(name = "units_in_stock")
    private Integer unitsInStock;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "date_updated")
    private Instant lastUpdated;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_status_id", nullable = false)
    private ProductStatus productStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory category;
}
