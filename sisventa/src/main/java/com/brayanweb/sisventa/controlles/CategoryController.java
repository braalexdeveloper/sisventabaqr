package com.brayanweb.sisventa.controlles;

import com.brayanweb.sisventa.dtos.CategoryRequest;
import com.brayanweb.sisventa.dtos.CategoryResponse;
import com.brayanweb.sisventa.services.CategoryService;
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
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getCategories() {
        List<CategoryResponse> categories = categoryService.getCategories();
        return ResponseEntity.ok(createResponse("success", "categories", categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getCategory(@PathVariable("id") Long id) {
        CategoryResponse category = categoryService.getCategory(id);
        return ResponseEntity.ok(createResponse("success", "category", category));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CategoryRequest category, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reportError(result));
        }
        CategoryResponse createdCategory = categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponse("success", "category", createdCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") Long id, @Valid @RequestBody CategoryRequest category, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reportError(result));
        }
        CategoryResponse updatedCategory = categoryService.update(id, category);
        return ResponseEntity.ok(createResponse("success", "category", updatedCategory));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {

        String messageDelete = categoryService.delete(id);
        return ResponseEntity.ok(createResponse("success", "msg", messageDelete));
    }

    private Map<String, Object> createResponse(String status, String key, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put(key, data);
        return response;
    }

    private Map<String, Object> reportError(BindingResult result) {
        Map<String, Object> errors = new HashMap<>();

        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return errors;
    }
}
