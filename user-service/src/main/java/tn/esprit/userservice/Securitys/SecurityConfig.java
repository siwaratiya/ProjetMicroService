package tn.esprit.userservice.Securitys;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import javax.ws.rs.HttpMethod;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .anyRequest()
                //.authenticated();
                .permitAll(); // Allow all requests without authentication
//        http
//                .csrf()
//                .disable()
//                .authorizeRequests(authorizeRequests ->
//                        authorizeRequests
//                                .antMatchers(HttpMethod.POST, "/keycloak/login").permitAll() // Permit all for this specific path
//                                .antMatchers(HttpMethod.POST, "/keycloak/logout").permitAll()
//                                .antMatchers(HttpMethod.POST, "/account/register").permitAll()
//                                .antMatchers(HttpMethod.GET, "/attachment/**").permitAll()
//                                .antMatchers(HttpMethod.GET, "/account/confirm-email/**").permitAll()
//                                .anyRequest().authenticated());


        http
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthConverter);
        http
                .sessionManagement()
                .sessionCreationPolicy(STATELESS);

        return http.build();
    }
}