package com.hexaware.ordermanagement.controller;

import com.hexaware.ordermanagement.util.JsonResponse;
import com.hexaware.ordermanagement.model.Order;
import com.hexaware.ordermanagement.service.CartService;
import com.hexaware.ordermanagement.service.CustomerService;
import com.hexaware.ordermanagement.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(CustomerController.class);

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

        logger.info("REQUEST: " + "--POST-- api/v1/order @ " + LocalDateTime.now());

        JsonResponse jsonResponse = new JsonResponse(true, "Order successfully created", order);
        return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<JsonResponse> getAllOrders(){

        logger.info("REQUEST: " + "--GET-- api/v1/order @ " + LocalDateTime.now());

        JsonResponse jsonResponse;
        List<Order> orderListFromDb = orderService.getAllOrders();

        if(orderListFromDb.isEmpty()) {
            jsonResponse = new JsonResponse(false, "No orders available to display", null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }
         jsonResponse = new JsonResponse(true, "All orders successfully retrieved", orderListFromDb);
        return ResponseEntity.ok(jsonResponse);
    }

    @GetMapping("{orderId}")
    public ResponseEntity<JsonResponse> getOrderById(@PathVariable Integer orderId){

        logger.info("REQUEST: " + "--GET-- api/v1/order/" + orderId + " @ " + LocalDateTime.now());

        JsonResponse jsonResponse;
        Optional<Order> orderFromDb = orderService.getOrderById(orderId);

        if(orderFromDb.isEmpty()) {
             jsonResponse = new JsonResponse(false, "No order exist with order Id: " + orderId, null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }

        jsonResponse = new JsonResponse(true, "Order with order Id: " + orderId + " found", orderFromDb);
        return ResponseEntity.ok(jsonResponse);
    }
}
