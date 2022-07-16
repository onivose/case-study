package com.hexaware.ordermanagement.repository;

import com.hexaware.ordermanagement.model.Customer;
import com.hexaware.ordermanagement.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {

    List<Order> getByCustomer(Customer customer);

    List<Order> findByTotalGreaterThan(Double total);

    List<Order> findByTotalLessThan(Double total);
}
