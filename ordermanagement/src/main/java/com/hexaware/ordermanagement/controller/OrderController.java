package com.hexaware.ordermanagement.controller;

import com.hexaware.ordermanagement.model.Customer;
import com.hexaware.ordermanagement.model.Product;
import com.hexaware.ordermanagement.service.ProductService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/order")
@CrossOrigin(origins = { "http://localhost:8080", "http://127.0.0.1:5502" })
public class OrderController {

    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private CustomerService customerService;
    private OrderService orderService;
    private ProductService productService;

    @Autowired
    public OrderController(CustomerService customerService, OrderService orderService, ProductService productService) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.productService = productService;
    }

    //WORKS
    @PostMapping("{customerId}")
    public ResponseEntity<JsonResponse> beginNewOrder(@PathVariable Integer customerId){

        Customer author = customerService.getCustomerById(customerId);

        // Null check to validate that customer exists
        if (author == null){
            JsonResponse jsonResponse = new JsonResponse(false, "Customer does not exist with id " + customerId, null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.CONFLICT);
        }

        Order newOrder = orderService.beginNewOrder(customerId);

        //Null check to validate that new order was successfully created
        if (newOrder == null){
            JsonResponse jsonResponse = new JsonResponse(false, "Order not created: Customer does not exist with id " + customerId, null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.CONFLICT);
        }

        JsonResponse jsonResponse = new JsonResponse(true, "Order successfully created", newOrder);
        return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);

    }

    //WORKS
    @PostMapping("submit/{orderId}")
    public ResponseEntity<JsonResponse> submitOrder(@RequestBody List<Integer> productIds, @PathVariable Integer orderId){

        logger.info("REQUEST: " + "--POST-- api/v1/order @ " + LocalDateTime.now());

        Order orderToSubmit = orderService.getOrderById(orderId);

        //Null check to validate that there is an order started with that id
        if(orderToSubmit == null){
            JsonResponse jsonResponse = new JsonResponse(false, "No order exist with order Id: " + orderId, null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }

        //Check to validate that the order with that id has not already been submitted
        else if(orderToSubmit.getSubmitted()){
            JsonResponse jsonResponse = new JsonResponse(false,
                    "Order with id " + orderId + " has already been submitted",
                    null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.CONFLICT);
        }

        // null check to make sure each product in the order exists
        for (Integer id : productIds) {
            // get the product by id
            Product orderProduct = productService.getProductById(id);

            if (orderProduct == null) {
                JsonResponse jsonResponse = new JsonResponse(false, "No product exist with Id: " + id, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
            }
        }

        // If all the validation checks are passed -> submit the order
        Order submittedOrder = orderService.submitOrder(productIds, orderId);

        JsonResponse jsonResponse = new JsonResponse(true, "Order successfully submitted", submittedOrder);
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    //WORKS
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

    //WORKS
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
            jsonResponse = new JsonResponse(false,
                    "No order(s) with total greater than $"+ total + " available to display",
                    null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }

        Integer numOfOrders = orderListFromDb.size();
        jsonResponse = new JsonResponse(true,
                numOfOrders + " order(s) with total greater than $"+ total + " successfully retrieved",
                orderListFromDb);
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
            jsonResponse = new JsonResponse(false,
                    "No order(s) with total less than $"+ total + " available to display",
                    null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }

        Integer numOfOrders = orderListFromDb.size();
        jsonResponse = new JsonResponse(true,
                numOfOrders + " order(s) with total less than $"+ total + " successfully retrieved",
                orderListFromDb);
        return ResponseEntity.ok(jsonResponse);

    }

    //WORKS
    /**
     * @param orderId
     * @return
     */
    @GetMapping("{orderId}")
    public ResponseEntity<JsonResponse> getOrderById(@PathVariable Integer orderId){

        logger.info("REQUEST: " + "--GET-- api/v1/order/" + orderId + " @ " + LocalDateTime.now());

        JsonResponse jsonResponse;
        Optional<Order> orderFromDb = Optional.ofNullable(orderService.getOrderById(orderId));

        if(!orderFromDb.isPresent()) {
            jsonResponse = new JsonResponse(false, "No order exist with order Id: " + orderId, null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }

        jsonResponse = new JsonResponse(true, "Order with order Id: " + orderId + " found", orderFromDb);
        return ResponseEntity.ok(jsonResponse);
    }

    //WORKS
    @GetMapping("customer/{customerId}")
    public ResponseEntity<JsonResponse> getAllOrdersForCustomerWithId(@PathVariable Integer customerId){
        logger.info("REQUEST: " + "--GET-- api/v1/order/customer/" + customerId + " @ " + LocalDateTime.now());

        try {
            JsonResponse jsonResponse;
            List<Order> orderListFromDb = orderService.getAllOrdersByCustomerId(customerId);

            if (orderListFromDb.isEmpty()) {
                jsonResponse = new JsonResponse(false, "No orders available to display for customer with id " + customerId, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
            }

            jsonResponse = new JsonResponse(true, "All orders successfully retrieved", orderListFromDb);
            return ResponseEntity.ok(jsonResponse);

        } catch (NullPointerException e) {
            //todo look into this line
            logger.warn("Null pointer Exception (Cause: customer with id: "+ customerId
                    + " does not exist.) Stack trace: " + Arrays.toString(e.getStackTrace()));

            JsonResponse jsonResponse = new JsonResponse(false, "Customer does not exist with id " + customerId, null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.CONFLICT);
        }
    }
}
