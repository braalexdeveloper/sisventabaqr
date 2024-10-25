package com.brayanweb.sisventa.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest {
    
    @NotNull(message="El nombre es requerido")
    private String name;
    
    private String description;
}
