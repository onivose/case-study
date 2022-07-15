package com.hexaware.ordermanagement.service;

import com.hexaware.ordermanagement.model.Customer;

public interface CustomerService {
    /**
     * Create a new user, checking if username and email are available, and encrypt the password before saving to the database
     *
     * @param customer to be created
     * @return created customer from Database
     */
    Customer createCustomer(Customer customer);
    /**
     * Returns the current user as a Customer object if the credentials are valid and null otherwise
     * @param username
     * @param password
     * @return customer from Database or null
     * */
    Customer validateCredentials (String username, String password);

    /**
     * Fetches the customer by id from the db
     * @param customerId
     * @return
     */
    Customer getCustomerById(Integer customerId);

    /**
     * Fetches the customer by username from the db
     * @param username
     * @return
     */
    Customer getCustomerByUsername(String username);

}
