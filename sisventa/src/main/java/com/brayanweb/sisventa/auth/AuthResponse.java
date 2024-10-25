package com.brayanweb.sisventa.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

    private Long id;
    private String email;
    private String roleName;
    private String token;

//constructor privado para el builder
    private AuthResponse(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.roleName = builder.roleName;
        this.token = builder.token;
    }
    
    public static Builder builder(){
        return new Builder();
    }

//Clase builder estatica
    public static class Builder{

        private Long id;
        private String email;
        private String roleName;
        private String token;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setRoleName(String roleName) {
            this.roleName = roleName;
            return this;
        }

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        //Metodo para construir el objeto authresposne
        public AuthResponse build() {
            return new AuthResponse(this);
        }

    }
}
