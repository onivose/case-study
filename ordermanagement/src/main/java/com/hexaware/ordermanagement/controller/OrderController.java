package com.hexaware.ordermanagement.controller;

import com.hexaware.ordermanagement.util.JsonResponse;
import com.hexaware.ordermanagement.model.Order;
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

    @Autowired
    public OrderController(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    //todo finish this
    @PostMapping
    public ResponseEntity<JsonResponse> createOrder(@Validated @RequestBody Order orderToCreate){

        logger.info("REQUEST: " + "--POST-- api/v1/order @ " + LocalDateTime.now());

        //calculate and set order total before saving to db
        orderToCreate.setTotal(orderService.calculateOrderTotal(orderToCreate));

        orderService.createOrder(orderToCreate);

        JsonResponse jsonResponse = new JsonResponse(true, "Order successfully created", orderToCreate);
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

    /**
     * Endpoint to find all orders in the database with total greater than the given value in path parameter
     *
     * Method: GET
     * Url: api/v1/order/greaterThan/{total}
     * @param total -> total price to filter orders
     * @return JsonResponse with a list of orders as the "data" parameter or null if no orders are present that meet the criteria
     */
    @GetMapping("/greaterThan/{total}")
    ResponseEntity<JsonResponse> findAllWithTotalGreaterThan(@PathVariable Double total){
        logger.info("REQUEST: " + "--GET-- api/v1/order/greaterThan/" + total + " @ " + LocalDateTime.now());

        JsonResponse jsonResponse;
        List<Order> orderListFromDb = orderService.findAllWithTotalGreaterThan(total);

        if(orderListFromDb.isEmpty()) {
            jsonResponse = new JsonResponse(false, "No orders available to display", null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }
        jsonResponse = new JsonResponse(true, "All orders successfully retrieved", orderListFromDb);
        return ResponseEntity.ok(jsonResponse);
    }

    /**
     * Endpoint to find all orders in the database with total less than the given value in path parameter
     *
     * Method: GET
     * Url: api/v1/order/lessThan/{total}
     * @param total -> total price to filter orders
     * @return JsonResponse with a list of orders as the "data" parameter or null if no orders are present that meet the criteria
     */
    @GetMapping("/lessThan/{total}")
    ResponseEntity<JsonResponse> findAllWithTotalLessThan(@PathVariable Double total){
        logger.info("REQUEST: " + "--GET-- api/v1/order/lessThan/" + total + " @ " + LocalDateTime.now());

        JsonResponse jsonResponse;
        List<Order> orderListFromDb = orderService.findAllWithTotalLessThan(total);

        if(orderListFromDb.isEmpty()) {
            jsonResponse = new JsonResponse(false, "No orders available to display", null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }
        jsonResponse = new JsonResponse(true, "All orders successfully retrieved", orderListFromDb);
        return ResponseEntity.ok(jsonResponse);

    }

    /**
     * @param orderId
     * @return
     */
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

    @GetMapping("customer/{customerId}")
    public ResponseEntity<JsonResponse> getAllOrdersForCustomerWithId(@PathVariable Integer customerId){
        logger.info("REQUEST: " + "--GET-- api/v1/order/customer/" + customerId + " @ " + LocalDateTime.now());

        JsonResponse jsonResponse;
        List<Order> orderListFromDb = orderService.getAllOrdersByCustomerId(customerId);

        if(orderListFromDb.isEmpty()) {
            jsonResponse = new JsonResponse(false, "No orders available to display", null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }
        jsonResponse = new JsonResponse(true, "All orders successfully retrieved", orderListFromDb);
        return ResponseEntity.ok(jsonResponse);
    }


}
