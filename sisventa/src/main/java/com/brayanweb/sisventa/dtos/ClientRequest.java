package com.brayanweb.sisventa.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientRequest {
    
    @NotNull(message="El nombre es requerido")
    @Size(min = 2, max = 50)
    private String name;
    
    @NotNull(message="El apellido es requerido")
    @Size(min = 2, max = 50)
    private String lastName;
    
    @NotNull(message="El dni es requerido")
    @Pattern(regexp = "[0-9]{8}", message = "DNI debe tener 8 dígitos")
    private String dni;
    
    private String ruc;
    private String phone;
    private String address;
            
}
