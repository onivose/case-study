package com.hexaware.ordermanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String manufacturer;

    @Column(nullable = false)
    private String type;

    @Column
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(columnDefinition = "boolean default false")
    private Boolean purchased;

    // CascadeType.All means that if the order is deleted, the corresponding product will also be deleted
    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL) //todo look into Lazy fetch meaning
    @JoinColumn(name = "order_id_fk")
    private Order orderFk;
}
