package com.brayanweb.sisventa.repositories;

import com.brayanweb.sisventa.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{

    public boolean existsByName(String name);
    
}
