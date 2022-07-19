package com.hexaware.ordermanagement.controller;
import com.hexaware.ordermanagement.model.Product;
import com.hexaware.ordermanagement.service.ProductService;
import com.hexaware.ordermanagement.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
@CrossOrigin(origins = { "http://localhost:8080", "http://127.0.0.1:5502" })
public class ProductController {

    private ProductService productService;

    //todo add logging

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
                    "There Are No Available Products To Purchase At This Time",
                    null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new JsonResponse(true, "All available products retrieved",availableProducts),
                HttpStatus.OK);
    }
}
