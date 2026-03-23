/**
 * 
 */
package com.TeachCode.E_commerce.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double totalPrice;

    private Double shippingCost;

    private LocalDateTime createdAt = LocalDateTime.now();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) 
    private Set<OrderItem> items = new HashSet<>();

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status = OrderStatus.PENDING;

    private String fullName;
    private String phoneNumber;
    private String address;
    private String city;
    private String postalCode;
    private String country;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) 
    private List<OrderHistory> histories = new ArrayList<>();
    
}
