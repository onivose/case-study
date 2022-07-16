package com.hexaware.ordermanagement.service;

import com.hexaware.ordermanagement.model.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    /**
     * finds all orders in the database
     * @return List of orders or null if no orders are present
     */
    List<Order> getAllOrders();

    /**
     * finds all orders in the database with total greater than the given value
     * @return List of orders or null if no orders are present
     */
    List<Order> findAllWithTotalGreaterThan(Double total);

    /**
     * finds all orders in the database with total less than the given value
     * @return List of orders or null if no orders are present
     */
    List<Order> findAllWithTotalLessThan(Double total);

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
    Order getOrderById(Integer orderId);

    /**
     * creates an order with no products and persists it into the database
     * @param order
     * @return order
     */
    Order beginNewOrder(Integer customerId);

    /**
     * Calculates the total sum of an order
     *
     * @param order
     * @return total sum of products in order
     */
    Double calculateOrderTotal (Order order);

    Order submitOrder(List<Integer> productIds, Integer orderId);
}
