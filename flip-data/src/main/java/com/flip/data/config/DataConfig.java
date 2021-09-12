package com.flip.data.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages ={"com.flip.data"})
@EntityScan(basePackages = {"com.flip.data"})
@ComponentScan(basePackages = {"com.flip.data"})
public class DataConfig {
    
}
