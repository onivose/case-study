package com.hexaware.ordermanagement.service;

import com.hexaware.ordermanagement.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    /**
     * finds all orders in the database
     * @return List of orders or null if no orders are present
     */
    List<Order> getAllOrders();

    /**
     * finds all orders for the customer wth the given id
     * @param customerId
     * @return List of orders or null if no orders are present for that customer
     */
    List<Order> getAllOrdersByCustomerId(Integer customerId);

    /**
     * gets a specific order by orderId
     * @param orderId
     * @return order
     */
    Optional<Order> getOrderById(Integer orderId);

    /**
     * creates an order and persists it into the database
     * @param order
     * @return order
     */
    Order createOrder(Order order);

}
