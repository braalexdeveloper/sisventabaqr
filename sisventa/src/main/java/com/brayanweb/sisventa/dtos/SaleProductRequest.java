package com.brayanweb.sisventa.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleProductRequest {
    private Integer quantity;
    private Double subtotal;
    private Long product_id;
}
