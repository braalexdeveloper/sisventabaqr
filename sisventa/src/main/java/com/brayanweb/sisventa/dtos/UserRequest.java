package com.brayanweb.sisventa.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequest {
    @NotNull(message="Email requerido")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotNull(message="Password requerido")
    private String password;
    
    @NotNull(message="Role id requerido")
    private Long role_id;
}
