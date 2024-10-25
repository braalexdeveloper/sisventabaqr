
package com.brayanweb.sisventa.config;

import com.brayanweb.sisventa.jwt.JwtAuthenticationFilter;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;
    
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,AuthenticationProvider authProvider){
        this.jwtAuthenticationFilter=jwtAuthenticationFilter;
        this.authProvider=authProvider;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
          .csrf(csrf->csrf.disable())
          
          .authorizeHttpRequests(
          authRequest->authRequest
                  .requestMatchers("/api/auth/**").permitAll()
                  .anyRequest().authenticated()
          )
                .sessionManagement(
                sessionManager->sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
                        
    }
}
