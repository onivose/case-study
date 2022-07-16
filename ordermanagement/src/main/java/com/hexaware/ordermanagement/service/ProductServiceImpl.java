package com.hexaware.ordermanagement.service;

import com.hexaware.ordermanagement.model.Product;
import com.hexaware.ordermanagement.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    private ProductRepo productRepo;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public void getInitialProducts() {

        Product product1 = Product.builder()
                .name("Galaxy S21")
                .manufacturer("Samsung")
                .type("Smartphone")
                .price(Double.parseDouble("900.50"))
                .purchased(false)
                .build();

        Product product2 = Product.builder()
                .name("Galaxy S21 Ultra")
                .manufacturer("Samsung")
                .type("Smartphone")
                .price(Double.parseDouble("1100.50"))
                .purchased(false)
                .build();

        Product product3 = Product.builder()
                .name("Galaxy S22")
                .manufacturer("Samsung")
                .type("Smartphone")
                .price(Double.parseDouble("950.50"))
                .purchased(false)
                .build();

        List<Product> initialProducts = new ArrayList<>();
        initialProducts.add(product1);
        initialProducts.add(product2);

        initialProducts.forEach(this::createProduct);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepo.save(product);
    }

    /**
     * gets a specific product by productId
     *
     * @param productId
     * @return order
     */
    @Override
    public Product getProductById(Integer productId) {
        return this.productRepo.findById(productId).orElse(null);
    }
}
