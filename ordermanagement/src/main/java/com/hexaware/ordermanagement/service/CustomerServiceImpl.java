package com.hexaware.ordermanagement.service;

import com.hexaware.ordermanagement.model.Customer;
import com.hexaware.ordermanagement.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepo customerRepo;

    @Autowired
    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    /**
     * Create a new user, checking if username and email are available, and encrypt the password before saving to the database
     *
     * @param customer to be created
     * @return created customer from Database
     */
    @Override
    public Customer createCustomer(Customer customer) {
        try{
            Customer nullEmail = customerRepo.findByEmail(customer.getEmail());
            Customer nullUsername = customerRepo.findByUsername(customer.getUsername());

            if (nullEmail == null && nullUsername == null){
                return customerRepo.save(customer);
            }
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return null;
    }

    /**
     * Returns the current user as a Customer object if the credentials are valid and null otherwise
     * Used for logging in
     * @param username
     * @param password
     * @return customer from Database or null
     */
    @Override
    public Customer validateCredentials(String username, String password) {
        Customer userLoggingIn = this.customerRepo.findByUsername(username);

        //User wasn't found so return null
        if(userLoggingIn == null)
            return null;

        // Return null if passwords dont match
        if(!password.equals(userLoggingIn.getPassword()))
            return null;

        return userLoggingIn;
    }

    /**
     * Fetches the customer by id from the db
     *
     * @param customerId
     * @return
     */
    @Override
    public Customer getCustomerById(Integer customerId) {
        return customerRepo.findById(customerId).orElse(null);
    }

    /**
     * Fetches the customer by username from the db
     * Used to log in and checking for valid username
     *
     * @param username
     * @return
     */
    @Override
    public Customer getCustomerByUsername(String username) {
        return this.customerRepo.findByUsername(username);
    }
}
