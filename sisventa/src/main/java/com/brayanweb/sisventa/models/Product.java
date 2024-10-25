package com.brayanweb.sisventa.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="products")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable=false)
    @Size(max=100,message="El nombre debe tener maximo 100 caracteres")
    private String name;
    
    @Lob
    private String description;
    
    @Column(nullable=false)
    private Double price;
    
    @Column(nullable=false)
    private Integer stock;
    
    private String image;
    
    @ManyToOne
    @JoinColumn(name="category_id",nullable=false)
    private Category category;
    
    
    // Relación con la entidad intermedia
    @OneToMany(mappedBy = "product")
    private Set<SaleProduct> saleProducts = new HashSet<>(); // Mantén esta relación
    
}
