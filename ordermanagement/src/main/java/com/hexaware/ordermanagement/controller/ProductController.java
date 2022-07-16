package com.hexaware.ordermanagement.controller;
import com.hexaware.ordermanagement.model.Product;
import com.hexaware.ordermanagement.service.ProductService;
import com.hexaware.ordermanagement.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * <H1>FOR INTERNAL USE ONLY </H1>
     * used to pre-populate database with products
     * not required as part of case study
     * @return JsonResponse with 201 status code
     */
    @PostMapping
    public ResponseEntity<JsonResponse> populateProductsInDb(){
        productService.getInitialProducts();
        return new ResponseEntity<>(new JsonResponse(true, "All products are now in Database",null),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<JsonResponse> getAllAvailableProducts(){
        List<Product> availableProducts =  productService.getAllAvailableProducts();

        if (availableProducts.isEmpty()){
            return new ResponseEntity<>(new JsonResponse(false,
                    "There are no products currently available for purchase",
                    null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new JsonResponse(true, "All available products retrieved",availableProducts),
                HttpStatus.OK);
    }
}
