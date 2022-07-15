package com.hexaware.ordermanagement.repository;

import com.hexaware.ordermanagement.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    Customer findByUsername(String username);
    Customer findByEmail(String email);
}
