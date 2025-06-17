package app.carrillo.franchises.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(Info().title("Franchises API")
                .version("1.0")
                .description("API documentation for the Franchises application")
                .contact(io.swagger.v3.oas.models.info.Contact().name("José Carrillo").url("https://carrillo.app").email("m@carrillo.app"))
                .license(io.swagger.v3.oas.models.info.License().name("MIT License").url("https://opensource.org/licenses/MIT")))
    }
}