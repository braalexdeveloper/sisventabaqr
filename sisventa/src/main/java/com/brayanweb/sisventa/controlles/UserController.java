package com.brayanweb.sisventa.controlles;

import com.brayanweb.sisventa.dtos.UserRequest;
import com.brayanweb.sisventa.dtos.UserResponse;
import com.brayanweb.sisventa.services.UserService;
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
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService){
        this.userService=userService;
    }
    
    @GetMapping
    public ResponseEntity<Map<String,Object>> getUsers(){
        List<UserResponse> users=userService.getUsers();
        return ResponseEntity.ok(createResponse("success","users",users));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getUser(@PathVariable("id") Long id){
        UserResponse user=userService.getUser(id);
        return ResponseEntity.ok(createResponse("succes","user",user));
    }
    
    @PostMapping
    public ResponseEntity<Map<String,Object>> create(@Valid @RequestBody UserRequest userRequest,BindingResult result){
        
        if(result.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMap(result));
        }
        
        UserResponse createdUser=userService.create(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponse("success","user",createdUser));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable("id") Long id,@Valid @RequestBody UserRequest userRequest,BindingResult result){
        
        if(result.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMap(result));
        }
        
        UserResponse updatedUser=userService.update(id, userRequest);
        return ResponseEntity.ok(createResponse("success","user",updatedUser));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Object>> delete(@PathVariable("id") Long id){
        String messageDelete=userService.delete(id);
        return ResponseEntity.ok(createResponse("success","msg",messageDelete));
    }
    
    private Map<String,Object> createResponse(String status,String key,Object data){
     Map<String,Object> response=new HashMap<>();
        response.put("status",status);
        response.put(key,data);
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
