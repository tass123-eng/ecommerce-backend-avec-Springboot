/**
 * 
 */
package com.TeachCode.E_commerce.DTO.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * 
 */
@Data
@Builder
public class ProductDto {

    @NotBlank(message = "Le nom du produit est obligatoire")
    private String name;

    private String description;

    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.001", message = "Le prix doit être supérieur à 0")
    @Digits(integer = 10, fraction = 3, message = "Le prix ne peut pas dépasser 3 décimales")
    private BigDecimal unitPrice;

    @NotNull(message = "Le stock est obligatoire")
    @Min(value = 0, message = "Le stock ne peut pas être négatif")
    private Integer unitsInStock;

    private boolean active;

    private String imageBase64;

    private String imageContentType;

    @NotNull(message = "L'identifiant de la catégorie est obligatoire")
    private Long categoryId;
    
    @NotNull(message = "L'identifiant du statut est obligatoire")
    private Long productStatusId;
}
