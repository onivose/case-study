package com.hexaware.ordermanagement.service;

import com.hexaware.ordermanagement.model.Product;
import com.hexaware.ordermanagement.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
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

        Product p1 = Product.builder()
                .name("Galaxy Buds")
                .manufacturer("Samsung")
                .type("Ear Buds")
                .price(Double.parseDouble("270.00"))
                .purchased(false)
                .build();

        Product p2 = Product.builder()
                .name("Air Pods Pro")
                .manufacturer("Samsung")
                .type("Ear Buds")
                .price(Double.parseDouble("280.99"))
                .purchased(false)
                .build();

        Product p3 = Product.builder()
                .name("Galaxy S22")
                .manufacturer("Samsung")
                .type("Smartphone")
                .price(Double.parseDouble("950.50"))
                .purchased(false)
                .build();

        Product p4 = Product.builder()
                .name("Galaxy S22 Ultra")
                .manufacturer("Samsung")
                .type("Smartphone")
                .price(Double.parseDouble("950.50"))
                .purchased(false)
                .build();

        Product p5 = Product.builder()
                .name("Apple iPhone 13")
                .manufacturer("Apple")
                .type("Smartphone")
                .price(Double.parseDouble("999.99"))
                .purchased(false)
                .build();

        Product p6 = Product.builder()
                .name("iPhone 13 Pro")
                .manufacturer("Apple")
                .type("Smartphone")
                .price(Double.parseDouble("1099.99"))
                .purchased(false)
                .build();

        Product p7 = Product.builder()
                .name("MacBook Air")
                .manufacturer("Apple")
                .type("Laptop")
                .price(Double.parseDouble("1199.99"))
                .purchased(false)
                .description("Apple's best designed and most performant laptop")
                .build();

        Product p8 = Product.builder()
                .name("HP Envy x360")
                .manufacturer("Hewlett-Packard")
                .type("Laptop")
                .price(Double.parseDouble("799.99"))
                .purchased(false)
                .description("a 2-in-1 convertible laptop that functions as both a computer and a tablet")
                .build();

        Product p9 = Product.builder()
                .name("iPad Pro")
                .manufacturer("Apple")
                .type("Tablet")
                .price(Double.parseDouble("799.99"))
                .purchased(false)
                .build();

        Product p10 = Product.builder()
                .name("Apple Watch")
                .manufacturer("Apple")
                .type("Smart Watch")
                .price(Double.parseDouble("399.99"))
                .purchased(false)
                .build();

        List<Product> initialProducts = new ArrayList<>(Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10));

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

    @Override
    public void updateProduct(Product product) {
        productRepo.saveAndFlush(product);
    }

    @Override
    public List<Product> getAllAvailableProducts() {
        return productRepo.findByPurchased(false);
    }
}
