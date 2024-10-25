package com.brayanweb.sisventa.services;

import com.brayanweb.sisventa.dtos.RoleRequest;
import com.brayanweb.sisventa.dtos.RoleResponse;
import com.brayanweb.sisventa.exceptions.ResourceNotFoundException;
import com.brayanweb.sisventa.models.Role;
import com.brayanweb.sisventa.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    
    public RoleService(RoleRepository roleRepository){
        this.roleRepository=roleRepository;
    }
    
    public List<RoleResponse> getRoles(){
        List<RoleResponse> roles=roleRepository.findAll().stream()
                .map(this::convertToRoleResponse).collect(Collectors.toList());
        return roles;
    }
    
    public RoleResponse getRole(Long id){
        Role role=roleRepository.findById(id).orElseThrow(
        ()->new ResourceNotFoundException("Rol no encontrado"));
        
        return convertToRoleResponse(role);
    }
    
    public RoleResponse create(RoleRequest roleRequest){
        Role createdRole=roleRepository.save(convertToRole(new Role(),roleRequest));
        return convertToRoleResponse(createdRole);
    }
    
    @Transactional
    public RoleResponse update(Long id,RoleRequest roleRequest){
        Role role=roleRepository.findById(id).map(roleFound->{
            return roleRepository.save(convertToRole(roleFound,roleRequest));
        }).orElseThrow(()->new ResourceNotFoundException("Rol no encontrado"));
        
        return convertToRoleResponse(role);
    }
    
    @Transactional
    public String delete(Long id){
        if(!roleRepository.existsById(id)){
           throw new ResourceNotFoundException("Rol no encontrado"); 
        }
        roleRepository.deleteById(id);
        return "Rol Eliminado Correctamente";
    }
    
    private Role convertToRole(Role role,RoleRequest roleRequest){
        role.setName(roleRequest.getName());
        role.setDescription(roleRequest.getDescription());
        return role;
    }
    
    private RoleResponse convertToRoleResponse(Role role){
        RoleResponse roleResponse=new RoleResponse();
        roleResponse.setId(role.getId());
        roleResponse.setName(role.getName());
        roleResponse.setDescription(role.getDescription());
        return roleResponse;
    }
    
    public Role createRoleIfNotFound(String name){
        if(!roleRepository.existsByName(name)){
            Role role=new Role();
            role.setName(name);
            return roleRepository.save(role);
        }
        return null;
    }
}
