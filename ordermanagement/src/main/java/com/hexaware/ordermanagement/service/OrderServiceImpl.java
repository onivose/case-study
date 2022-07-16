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
    private ProductService productService;

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo, CustomerRepo customerRepo, ProductService productService) {
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
        this.productService = productService;
    }

    /**
     * finds all orders in the database
     *
     * @return List of orders or null if no orders are present
     */
    @Override //works
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
    @Override // works
    public List<Order> getAllOrdersByCustomerId(Integer customerId) {
        Customer customer = customerRepo.findById(customerId).orElse(null);

        return customer != null ? this.orderRepo.getByCustomer(customer) : null;

    }

    /**
     * gets a specific order by orderId
     *
     * @param orderId
     * @return optional<order> -> need to use .isPresent() and .get() to retrieve order
     */
    @Override // works
    public Order getOrderById(Integer orderId) {
        return this.orderRepo.findById(orderId).orElse(null);
    }

    /**
     * creates an order with no products and persists it into the database
     *
     * @param customerId
     * @return order
     */
    @Override // works
    public Order beginNewOrder(Integer customerId) {

        Optional<Customer> author = customerRepo.findById(customerId);
        if (author.isPresent()){
            Order newOrder = Order.builder()
                    .customer(author.get())
                    .submitted(false)
                    .total((double) 0)
                    .build();

            return this.orderRepo.save(newOrder);
        }
        return null;
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

    @Override
    public Order submitOrder(List<Integer> productIds, Integer orderId) {

        Order orderToSubmit = this.getOrderById(orderId);

        //---------------------------FOR TESTING ONLY, REMOVE AFTER TESTING--------------------------------------------
        // productService.getInitialProducts(); //persists some products in the db to check submit order functionality
        //-------------------------------------------------------------------------------------------------------------

        List<Product> orderProducts = new ArrayList<>();

        for (Integer id : productIds){
            // get the product by id
            Product orderProduct = productService.getProductById(id);

            // null check to make sure each product in the order exists
            if (orderProduct == null){
                return null;
            }

            // set purchased true so two customers can't buy the same product
            orderProduct.setPurchased(true);

            // set the order fk
            orderProduct.setOrderFk(orderToSubmit);

            //persist the product to the database
            productService.updateProduct(orderProduct);

            // add product to orderProduct list
            orderProducts.add(orderProduct);
        }

        orderToSubmit.setProducts(orderProducts);

        // calculate and set order total before saving to db
        orderToSubmit.setTotal(this.calculateOrderTotal(orderToSubmit));

        // change order status to "submitted"
        orderToSubmit.setSubmitted(true);

        // save and persist order in database
        // have to use save and flush here because we need to retrieve the persisted entity later in this transaction
        orderRepo.saveAndFlush(orderToSubmit);

        // return the persisted order to client via controller
        return this.getOrderById(orderToSubmit.getOrderId());
    }
}
