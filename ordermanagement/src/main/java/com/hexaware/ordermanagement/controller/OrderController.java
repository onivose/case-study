package com.hexaware.ordermanagement.controller;

import com.hexaware.ordermanagement.model.JsonResponse;
import com.hexaware.ordermanagement.model.Order;
import com.hexaware.ordermanagement.service.CartService;
import com.hexaware.ordermanagement.service.CustomerService;
import com.hexaware.ordermanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    private CustomerService customerService;
    private OrderService orderService;
    private CartService cartService;

    @Autowired
    public OrderController(CustomerService customerService, OrderService orderService, CartService cartService) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.cartService = cartService;
    }
    @PostMapping
    public ResponseEntity<JsonResponse> createOrder(@Validated @RequestBody Order order){
        JsonResponse jsonResponse = new JsonResponse(true, "Order successfully created", order);
        return ResponseEntity.ok(jsonResponse);
    }
}
