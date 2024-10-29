
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
           .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://example.com")); // Lista explícita de orígenes permitidos
                config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
                config.setAllowCredentials(true); // Permitir credenciales
                return config;
            }))
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
