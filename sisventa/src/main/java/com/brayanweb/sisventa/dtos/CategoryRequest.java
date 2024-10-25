package com.brayanweb.sisventa.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryRequest {
    
    @NotNull(message="El nombre es requerido")
    private String name;
    
    private String description;
}
