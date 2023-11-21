package com.api.restApi.controller;

import com.api.restApi.entity.request.CreateProductReq;
import com.api.restApi.entity.request.Product;
import com.api.restApi.entity.request.ReqModel;
import com.api.restApi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/load")
    public ResponseEntity<Map<String, Object>> createSampleProduct(@RequestBody CreateProductReq req) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Product> productList = service.createProducts(req);
        response.put("data", productList);
        response.put("status", "Success");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> addProduct(@RequestBody Product product) {
        Map<String, Object> response = new HashMap<String, Object>();
        if (!isProductExists(product.getId())) {
            Product savedProduct = service.saveProduct(product);
            response.put("request", savedProduct);
            response.put("status", "Success");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return errorResponse(product.getId(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> findAllProducts() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Product> productList = service.getProducts();
        response.put("data", productList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findProductById(@PathVariable int id) {
        Map<String, Object> response = new HashMap<String, Object>();
        Product existingProduct = service.getProductById(id);
        if (existingProduct != null) {
            response.put("data", existingProduct);
            response.put("status", "Success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return errorResponse(id, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProduct(@PathVariable int id,
                                                             @RequestBody Product product) {
        Map<String, Object> response = new HashMap<String, Object>();
        if (isProductExists(id)) {
            Product updateProduct = service.updateProduct(product);
            response.put("request", updateProduct);
            response.put("status", "Success");
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } else {
            return errorResponse(id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable int id) {
        Map<String, Object> response = new HashMap<String, Object>();
        if (isProductExists(id)) {
            String res = service.deleteProduct(id);
            response.put("res", res);
            response.put("status", "Success");
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } else {
            return errorResponse(id, HttpStatus.NOT_FOUND);
        }
    }

    private ResponseEntity<Map<String, Object>> errorResponse(int id, HttpStatus httpStatus) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("status", "Failed");
        if (httpStatus.equals(HttpStatus.NOT_FOUND)) {
            response.put("message", "Product id :" + id + " not found");
        } else if (httpStatus.equals(HttpStatus.CONFLICT)) {
            response.put("message", "Product id :" + id + " already exists");
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    private boolean isProductExists(int id) {
        return service.getProductById(id) == null ? false : true;
    }



    @PostMapping("/tcreate")
    private ResponseEntity<Map<String, Object>> getListData(@RequestBody ReqModel reqModel){
        Map<String, Object> response = new HashMap<String, Object>();

        ///“N10”, “L10”, “P10”, “G14”, “g10”, “T11”, “G16”, “h12”, “g1”
        //with item start with ‘g’ or ‘G’  Sort the list
        List<String> status= reqModel.getStatus();

        List<String> charList = new ArrayList<>();
        charList.add("g");
        charList.add("G");

        List<String> filterItems= status.stream().filter(x->charList.stream().anyMatch(x::startsWith)).toList();
        response.put("output",status);
        
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
