package com.ClinicaOdontologicaCHUTT.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncoder {
    //metodo de encriptado
    @Bean // es un objeto que lo inyectará spring
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
