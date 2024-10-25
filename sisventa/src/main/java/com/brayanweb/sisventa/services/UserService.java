package com.brayanweb.sisventa.services;

import com.brayanweb.sisventa.dtos.UserRequest;
import com.brayanweb.sisventa.dtos.UserResponse;
import com.brayanweb.sisventa.exceptions.ResourceNotFoundException;
import com.brayanweb.sisventa.models.Role;
import com.brayanweb.sisventa.models.User;
import com.brayanweb.sisventa.repositories.RoleRepository;
import com.brayanweb.sisventa.repositories.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository,RoleRepository roleRepository,PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
        this.passwordEncoder=passwordEncoder;
    }
    
    public List<UserResponse> getUsers(){
        List<UserResponse> users=userRepository.findAll().stream().map(this::convertToUserResponse)
                .collect(Collectors.toList());
        return users;
    }
    
    public UserResponse getUser(Long id){
        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Usuario no encontrado"));
        return convertToUserResponse(user);
    }
    
    @Transactional
    public UserResponse create(UserRequest userRequest){
        Role role=roleRepository.findById(userRequest.getRole_id()).orElseThrow(()->new ResourceNotFoundException("Rol no encontrado"));
        User createdUser=userRepository.save(convertToUser(new User(),userRequest,role));
        return convertToUserResponse(createdUser);
    }
    
    @Transactional
    public UserResponse update(Long id,UserRequest userRequest){
        User updatedUser=userRepository.findById(id).map(userFound->{
            Role role=roleRepository.findById(userRequest.getRole_id()).orElseThrow(()->new ResourceNotFoundException("Rol no encontrado"));;
            return userRepository.save(convertToUser(userFound,userRequest,role));
        }).orElseThrow(()->new ResourceNotFoundException("Usuario no encontrado"));
        
        return convertToUserResponse(updatedUser);
    }
    
    @Transactional
    public String delete(Long id){
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
        return "Usuario elimindo con éxito";
    }
    
    private UserResponse convertToUserResponse(User user){
        UserResponse userResponse=new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setRoleName(user.getRole().getName());
        return userResponse;
    }
    
    private User convertToUser(User user,UserRequest userRequest,Role role){
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(role);
        return user;
    }
}
