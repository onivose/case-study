package com.hexaware.ordermanagement.service;

import com.hexaware.ordermanagement.model.Product;

import java.util.List;

public interface ProductService {

    void getInitialProducts();

    Product createProduct(Product product); // not required by case study (can manually add in front end)

    /**
     * gets a specific product by productId
     * @param productId
     * @return order
     */
    Product getProductById(Integer productId);

    void updateProduct(Product product);

    List<Product> getAllAvailableProducts();

    //----------------------------------------------------------------------------------------------------------
    // don't need this if adding products manually to order object in front end
    // void addProductToOrder(Integer productId, Integer orderId); // not required by case study

}
