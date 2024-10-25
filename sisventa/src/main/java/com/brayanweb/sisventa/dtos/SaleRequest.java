package com.brayanweb.sisventa.dtos;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleRequest {
    @NotNull(message="La fecha es requerida")
    private Date saleDate;
    
    @NotNull(message="El total es requerido")
    @DecimalMin(value="0.0",inclusive=false,message="El total debe ser mayor a 0")
    private Double total;
    
    @NotNull(message="El id del cliente es requerido")
    private Long client_id;
    
    @NotNull(message="Los productos de la venta son requerido")
    private List<SaleProductRequest> sale_products;
}
