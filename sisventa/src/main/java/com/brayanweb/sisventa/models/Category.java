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
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable=false)
    @NotBlank(message="Name is mandatory")
    private String name;
    
    private String description;
    
    @OneToMany(mappedBy="category",fetch=FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval=true)
    private Set<Product> products=new HashSet<>();
    
    public Long getId(){
        return id;
    }
    
    public void setId(Long id){
        this.id=id;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name=name;
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setDescription(String description){
        this.description=description;
    }
    
    public Set<Product> getProducts(){
        return products;
    }
    
    public void setProducts(Set<Product> products){
        this.products=products;
    }
}
