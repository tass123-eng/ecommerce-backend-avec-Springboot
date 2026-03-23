/**
 * 
 */
package com.TeachCode.E_commerce.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * 
 */
//@Embeddable
@Entity
@Data
public class OrderItem {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private Long productId;
	private String productName;
	//Prix au moment de la commande
	private Double price;
	private int quantity;
	
	// Déplacement de la relation : C'est ici qu'on définit la clé étrangère
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore // Pour éviter les boucles infinies lors de la conversion JSON
    private Order order;
}
