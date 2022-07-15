package com.hexaware.ordermanagement.controller;
import com.hexaware.ordermanagement.model.Customer;
import com.hexaware.ordermanagement.model.JsonResponse;
import com.hexaware.ordermanagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {
    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * <H1>For Internal Use Only</H1>
     * used to pre-populate database with users
     * @param customer
     * @return
     */
    @PostMapping("create")
    public ResponseEntity<JsonResponse> createCustomer (@Validated @RequestBody Customer customer){
        try {
            Customer created = customerService.createCustomer(customer);
            JsonResponse jsonResponse = new JsonResponse(true, "Successfully created", created);
            return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
        } catch (Exception e){
            JsonResponse jsonResponse = new JsonResponse(false, "Username or Email Taken", null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("login")
    public ResponseEntity<JsonResponse> login ( @RequestBody Customer customer){
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
