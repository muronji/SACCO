package com.example.loanandrepayment.loans;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class LoansConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Demo API")
                        .version("1.0")
                        .description("Demo API for Spring Boot"));
    }

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("loanandrepayment")
                .pathsToMatch("/**")
                .packagesToScan("com.example.loanandrepayment")
                .build();
    }
}












