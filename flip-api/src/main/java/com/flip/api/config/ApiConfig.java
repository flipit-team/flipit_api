package com.flip.api.config;

import com.flip.data.config.DataConfig;
import com.flip.service.config.ServiceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Import(value = {DataConfig.class, ServiceConfig.class})
@ComponentScan(basePackages = {"com.flip.api"})
public class ApiConfig /*extends WebMvcConfigurerAdapter*/ {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); //BCryptPasswordEncoder();
    }

}