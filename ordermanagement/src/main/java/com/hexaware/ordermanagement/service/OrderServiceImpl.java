package com.hexaware.ordermanagement.service;

import com.hexaware.ordermanagement.model.Customer;
import com.hexaware.ordermanagement.model.Order;
import com.hexaware.ordermanagement.model.Product;
import com.hexaware.ordermanagement.repository.CustomerRepo;
import com.hexaware.ordermanagement.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{

    private OrderRepo orderRepo;
    private CustomerRepo customerRepo;

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo, CustomerRepo customerRepo) {
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
    }

    /**
     * finds all orders in the database
     *
     * @return List of orders or null if no orders are present
     */
    @Override
    public List<Order> getAllOrders() {
        return this.orderRepo.findAll();
    }

    /**
     * finds all orders in the database with total greater than the given value
     *
     * @param total
     * @return List of orders or null if no orders are present
     */
    @Override
    public List<Order> findAllWithTotalGreaterThan(Double total) {
        return this.orderRepo.findByTotalGreaterThan(total);
    }

    /**
     * finds all orders in the database with total less than the given value
     *
     * @param total
     * @return List of orders or null if no orders are present
     */
    @Override
    public List<Order> findAllWithTotalLessThan(Double total) {
        return orderRepo.findByTotalLessThan(total);
    }

    /**
     * finds all orders for the customer wth the given id
     *
     * @param customerId
     * @return List of orders or null if no orders are present for that customer
     */
    @Override
    public List<Order> getAllOrdersByCustomerId(Integer customerId) {
        Customer customer = customerRepo.findById(customerId).orElse(null);

        return customer !=null ? this.orderRepo.getByCustomer(customer) : null;

        /*List<Order> orderListFromDb = this.orderRepo.getByCustomer(customer);
        return orderListFromDb;*/
    }

    /**
     * gets a specific order by orderId
     *
     * @param orderId
     * @return optional<order> -> need to use .isPresent() and .get() to retrieve order
     */
    @Override
    public Optional<Order> getOrderById(Integer orderId) {
        return this.orderRepo.findById(orderId);
    }

    /**
     * creates an order and persists it into the database
     *
     * @param order
     * @return order
     */
    @Override
    public Order createOrder(Order order) {
        return this.orderRepo.save(order);
    }

    /**
     * Calculates the total sum of an order
     *
     * @param order
     * @return total sum of products in order
     */
    @Override
    public Double calculateOrderTotal(Order order) {
        List<Product> products = order.getProducts();
        List<Double> productPrices = new ArrayList<>();
        for (Product product : products){
            productPrices.add(product.getPrice());
        }
        return productPrices.stream().mapToDouble(Double::doubleValue).sum(); //todo look into this line
    }
}
