package com.flip.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Charles on 19/10/2022
 */

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Flipit REST API",
                version = "${api.version}",
                contact = @Contact(
                        name = "Flipit Tech",
                        url = "https://flipit-web.vercel.app/",
                        email = "flipialphatest@gmail.com"
                ),
                license = @License(
                        name = "License of API",
                        url = "API license URL"
                ),
                description = "API documentation for Flipit app."
        ),
        security = @SecurityRequirement(name = "JWT")
)
@SecurityScheme(
        name = "JWT",
        scheme = "bearer",
        bearerFormat = "JWT",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(@Value("${api.version}") String apiVersion) {
        return new OpenAPI()
                .components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Flipit REST API")
                        .version(apiVersion)
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Flipit Tech")
                                .url("https://flipit-web.vercel.app/")
                                .email("flipialphatest@gmail.com"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("License of API")
                                .url("API license URL"))
                        .description("API documentation for Flipit app."));
    }

}
