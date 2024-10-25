package com.brayanweb.sisventa.repositories;

import com.brayanweb.sisventa.models.SaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleProductRepository extends JpaRepository<SaleProduct,Long>{
    
}
