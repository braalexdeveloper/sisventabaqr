package com.brayanweb.sisventa.controlles;

import com.brayanweb.sisventa.dtos.RoleRequest;
import com.brayanweb.sisventa.dtos.RoleResponse;
import com.brayanweb.sisventa.services.RoleService;
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
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getRoles() {
        List<RoleResponse> roles = roleService.getRoles();
        return ResponseEntity.ok(createResponse("roles", roles));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getRole(@PathVariable("id") Long id) {
        RoleResponse role = roleService.getRole(id);
        return ResponseEntity.ok(createResponse("role", role));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody RoleRequest role, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMap(result));
        }

        RoleResponse createdRole = roleService.create(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponse("role", createdRole));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") Long id,@Valid @RequestBody RoleRequest 
        roleRequest,BindingResult result){
        if(result.hasErrors()){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMap(result));
        }
        RoleResponse updatedRole = roleService.update(id,roleRequest);
        return ResponseEntity.ok(createResponse("role",updatedRole));

    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Object>> delete(@PathVariable("id") Long id){
        String response=roleService.delete(id);
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
