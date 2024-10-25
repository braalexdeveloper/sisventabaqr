package com.brayanweb.sisventa.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="clients")
public class Client {
   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   private Long id;
   
   @Column(nullable=false)
   private String name;
   
   @Column(nullable=false)
   private String lastName;
   
   @Column(nullable=false,unique=true)
   private String dni;
   
   @Column(nullable=false,unique=true)
   private String ruc;
   
   private String phone;
   private String address;
   
   @OneToMany(mappedBy="client",fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.MERGE})
   private Set<Sale> sales=new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
   
    public Set<Sale> getSales(){
        return sales;
    }
    
    public void setSales(Set<Sale> sales){
        this.sales=sales;
    }
   
   
}
