package com.hexaware.ordermanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @CreationTimestamp
    private Timestamp timeSubmitted;

    @Column (columnDefinition = "float default 0")
    private Double total;

    @Column (columnDefinition = "boolean default false")
    private Boolean submitted;

    @ManyToOne
    @JoinColumn(
            name = "customerIdFk",
            referencedColumnName = "customerId",
            nullable = false
    )
    private Customer customer;

    // @OnDelete to make sure that if all the products of an order have been deleted, the order itself will also be deleted
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "orderFk")
    private List<Product> products = new ArrayList<>();


}
