package com.brayanweb.sisventa.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientResponse {
private Long id;
private String name;
private String lastName;
private String dni;
private String ruc;
private String phone;
private String address;
}
