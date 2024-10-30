package com.brayanweb.sisventa.repositories;

import com.brayanweb.sisventa.models.Client;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long>{
    Optional<Client> findByDniOrRuc(String dni,String ruc);
}
