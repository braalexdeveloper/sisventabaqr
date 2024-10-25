package com.brayanweb.sisventa.dtos;


import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleResponse {
    private Long id;
    private Date saleDate;
    private Double total;
    private String nameClient;
}
