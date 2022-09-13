package com.flip.api.config;

import com.flip.service.config.ServiceConfig;
import com.flip.data.config.DataConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@Configuration
@Import(value = {DataConfig.class, ServiceConfig.class})
@ComponentScan(basePackages = {"com.flip.api"})
public class ApiConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); //BCryptPasswordEncoder();
    }
}