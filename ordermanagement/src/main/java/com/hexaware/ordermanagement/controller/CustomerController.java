package com.hexaware.ordermanagement.controller;
import com.hexaware.ordermanagement.model.Customer;
import com.hexaware.ordermanagement.util.JsonResponse;
import com.hexaware.ordermanagement.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * <H1>FOR INTERNAL USE ONLY</H1>
     * used to pre-populate database with users
     * not required as part of case study
     * @param customer
     * @return
     */
    @PostMapping("create")
    public ResponseEntity<JsonResponse> createCustomer (@Validated @RequestBody Customer customer){
        logger.info("REQUEST: " + "--POST-- api/v1/customer/create @ " + LocalDateTime.now());
        try {

            Customer created = customerService.createCustomer(customer);
            if (created != null){
                JsonResponse jsonResponse = new JsonResponse(true, "Successfully created", created);
                return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
            } else {
                JsonResponse jsonResponse = new JsonResponse(false, "Username or Email Taken", null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.CONFLICT);
            }
        } catch (DataIntegrityViolationException e){
            e.printStackTrace();
            JsonResponse jsonResponse = new JsonResponse(false, "An Error occurred: Missing a required field", null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("login")
    public ResponseEntity<JsonResponse> login ( @RequestBody Customer customer){
        logger.info("REQUEST: " + "--POST-- api/v1/customer/login @ " + LocalDateTime.now());

        Customer customerFromDb = customerService.getCustomerByUsername(customer.getUsername());
        if (customerFromDb == null){
            JsonResponse jsonResponse = new JsonResponse(false, "Username Not Found", null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        } else {
            Customer loginAttempt = customerService.validateCredentials(customer.getUsername(), customer.getPassword());
            if (loginAttempt == null){
                JsonResponse jsonResponse = new JsonResponse(false, "Incorrect Password", null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.UNAUTHORIZED);
            } else {
                JsonResponse jsonResponse = new JsonResponse(true, "Successfully Logged In", loginAttempt);
                return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
            }
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<JsonResponse> getCustomerById (@PathVariable Integer customerId){

        logger.info("REQUEST: " + "--GET-- api/v1/customer/" + customerId + " @ " + LocalDateTime.now());

        Customer customerFromDb = customerService.getCustomerById(customerId);

        if (customerFromDb == null){
            JsonResponse jsonResponse = new JsonResponse(false, "Customer does not exist", null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        } else{
            JsonResponse jsonResponse = new JsonResponse(true, "Customer successfully found", customerFromDb);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
    }
}
