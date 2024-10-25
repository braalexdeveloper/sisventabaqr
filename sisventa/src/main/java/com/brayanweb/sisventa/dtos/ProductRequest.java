package com.brayanweb.sisventa.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductRequest {
    
    @NotNull(message = "El nombre es requerido")
    @Size(min = 2, max = 50)
    private String name;

    private String description;

    @NotNull(message = "El precio es requerido")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private Double price;

    @NotNull(message = "El stock es requerido")
    @Min(value = 0, message = "El stock debe ser un número mayor o igual a 0")
    private Integer stock;
    
    private MultipartFile imageFile;

    @NotNull(message = "La categoría es requerida")
    private Long category_id;
}
