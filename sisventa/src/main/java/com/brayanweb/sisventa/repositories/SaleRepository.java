package com.brayanweb.sisventa.repositories;

import com.brayanweb.sisventa.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Long>{
    
}
