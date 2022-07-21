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
                .name("Pixel 6")
                .manufacturer("Google")
                .type("Smartphone")
                .price(Double.parseDouble("599.99"))
                .purchased(false)
                .build();

        Product p11 = Product.builder()
                .name("Galaxy Tab S8")
                .manufacturer("Samsung")
                .type("Tablet")
                .price(Double.parseDouble("399.99"))
                .purchased(false)
                .build();

        Product p12 = Product.builder()
                .name("A50 Wireless Headset")
                .manufacturer("Astro Gaming")
                .type("Headphones")
                .price(Double.parseDouble("280.99"))
                .purchased(false)
                .build();

        Product p13 = Product.builder()
                .name("PlayStation 5")
                .manufacturer("Sony")
                .type("Gaming Console")
                .price(Double.parseDouble("599.99"))
                .purchased(false)
                .build();

        Product p14 = Product.builder()
                .name("Xbox Series X")
                .manufacturer("Microsoft")
                .type("Gaming Console")
                .price(Double.parseDouble("499.99"))
                .purchased(false)
                .build();

        Product p15 = Product.builder()
                .name("Nintendo Switch")
                .manufacturer("Nintendo")
                .type("Gaming Console")
                .price(Double.parseDouble("299.99"))
                .purchased(false)
                .build();

        Product p16 = Product.builder()
                .name("Amazon Fire TV 55\" 4 Series" )
                .manufacturer("Amazon")
                .type("Smart TV")
                .price(Double.parseDouble("499.99"))
                .purchased(false)
                .build();

        Product p17 = Product.builder()
                .name("Mobius EX251")
                .manufacturer("BenQ")
                .type("Gaming Monitor")
                .price(Double.parseDouble("229.99"))
                .purchased(false)
                .build();

        Product p18 = Product.builder()
                .name("G703 LightSpeed")
                .manufacturer("Logitech")
                .type("Gaming Mouse")
                .price(Double.parseDouble("78.99"))
                .purchased(false)
                .build();

        Product p19 = Product.builder()
                .name("Basilik X Hyperspeed")
                .manufacturer("Razer")
                .type("Gaming Mouse")
                .price(Double.parseDouble("59.99"))
                .purchased(false)
                .build();

        Product p20 = Product.builder()
                .name("G213 Prodigy")
                .manufacturer("Logitech")
                .type("Gaming Keyboard")
                .price(Double.parseDouble("69.99"))
                .purchased(false)
                .build();

        List<Product> initialProducts = new ArrayList<>(Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16,p17,p18,p19,p20));

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
