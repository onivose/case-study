package com.hexaware.ordermanagement.service;

import com.hexaware.ordermanagement.repository.CartRepo;
import com.hexaware.ordermanagement.repository.CustomerRepo;
import com.hexaware.ordermanagement.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl {

    private OrderRepo orderRepo;
    private CartRepo cartRepo;
    private CustomerRepo customerRepo;

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo, CartRepo cartRepo, CustomerRepo customerRepo) {
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.customerRepo = customerRepo;
    }
}
