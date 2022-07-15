package com.hexaware.ordermanagement.repository;

import com.hexaware.ordermanagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    @Modifying
    @Query("update Product set purchased = true where productId = ?1")
    Integer setPurchasedFor (Integer productId);

    List<Product> findByPurchased(Boolean purchased);

    @Query(value = "select * from products where name iLIKE ?1%", nativeQuery = true)
    List<Product> findByName(String name);

    @Query(value = "select * from products where manufacturer iLIKE ?1%", nativeQuery = true)
    List<Product> findByManufacturer(String manufacturer);

    @Query(value = "select * from products where type iLIKE ?1%", nativeQuery = true)
    List<Product> findByType(String type);
}
