package com.brayanweb.sisventa.controlles;

import com.brayanweb.sisventa.dtos.ProductRequest;
import com.brayanweb.sisventa.dtos.ProductResponse;
import com.brayanweb.sisventa.services.ProductService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    
    public ProductController(ProductService productService){
        this.productService=productService;
    }
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getProducts() {
        List<ProductResponse> products = productService.getProducts();
        return ResponseEntity.ok(createResponse("products", products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProduc(@PathVariable("id") Long id) {
        ProductResponse product = productService.getProduct(id);
        return ResponseEntity.ok(createResponse("product", product));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody ProductRequest product, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMap(result));
        }

        ProductResponse createdProduct = productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponse("poduct", createdProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") Long id,@Valid @RequestBody ProductRequest 
        productRequest,BindingResult result){
        if(result.hasErrors()){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMap(result));
        }
        ProductResponse updatedProduct = productService.update(id,productRequest);
        return ResponseEntity.ok(createResponse("product",updatedProduct));

    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Object>> delete(@PathVariable("id") Long id){
        String response=productService.delete(id);
        return ResponseEntity.ok(createResponse("msg",response));
    }

    private Map<String, Object> createResponse(String key, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put(key, data);
        return response;
    }

    private Map<String, Object> createErrorMap(BindingResult result) {
        Map<String, Object> errors = new HashMap<>();

        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return errors;
    }
}
