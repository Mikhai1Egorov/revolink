package com.revolink.transaction.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI walletOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Transaction API")
                        .description("Revolink Mini API for managing wallets")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Mikhail Egorov")
                                .email("https://www.linkedin.com/in/maegorov/")
                        )
                );
    }
}