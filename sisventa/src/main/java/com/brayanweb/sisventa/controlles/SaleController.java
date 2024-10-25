package com.brayanweb.sisventa.controlles;

import com.brayanweb.sisventa.dtos.SaleRequest;
import com.brayanweb.sisventa.dtos.SaleResponse;
import com.brayanweb.sisventa.services.SaleService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sales")
public class SaleController {
    
    private final SaleService saleService;
    
    public SaleController(SaleService saleService){
        this.saleService=saleService;
    }
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getSales() {
        List<SaleResponse> sales = saleService.getSales();
        return ResponseEntity.ok(createResponse("sales", sales));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getSale(@PathVariable("id") Long id) {
        SaleResponse sale = saleService.getSale(id);
        return ResponseEntity.ok(createResponse("sale",sale));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody SaleRequest sale, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMap(result));
        }

        SaleResponse createdSale = saleService.create(sale);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponse("sale", createdSale));
    }

    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Object>> delete(@PathVariable("id") Long id){
        String response=saleService.delete(id);
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
