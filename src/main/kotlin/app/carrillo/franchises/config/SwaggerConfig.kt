package app.carrillo.franchises.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import io.swagger.v3.oas.models.tags.Tag
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(Info()
                .title("Franchises Management API")
                .version("1.0.0")
                .description("""
                    # Franchises Management API
                    
                    A comprehensive RESTful API for managing franchises, branches, and products. 
                    Built with Spring Boot and Kotlin, utilizing MongoDB for data persistence.
                    
                    ## Features
                    - **Franchise Management**: Create and manage franchise entities
                    - **Branch Operations**: Add and manage branches within franchises
                    - **Product Handling**: Complete CRUD operations for products
                    - **Inventory Analytics**: Get insights on stock levels per branch
                    - **Dynamic Updates**: Update names, addresses, descriptions, and prices
                    
                    ## Test Coverage
                    - **Current Coverage**: 80% instruction coverage
                    - **Minimum Threshold**: 30% (automatically verified)
                    - **Test Count**: 60+ comprehensive tests
                    - **Testing Layers**: Domain, Application, and Infrastructure
                    
                    ## Quality Assurance
                    - Automated CI/CD pipeline with GitHub Actions
                    - JaCoCo code coverage reporting
                    - Comprehensive unit and integration tests
                    - Security scanning and vulnerability detection
                    
                    For more information, visit the [GitHub Repository](https://github.com/carrilloapps/franchises-api)
                """)
                .contact(io.swagger.v3.oas.models.info.Contact()
                    .name("José Carrillo")
                    .url("https://carrillo.app")
                    .email("m@carrillo.app"))
                .license(io.swagger.v3.oas.models.info.License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")))
            .servers(listOf(
                Server().url("http://localhost:3081").description("Local Development Server"),
                Server().url("https://franchises.isapp.dev").description("Production Server")
            ))
            .tags(listOf(
                Tag().name("Franchise Management").description("Operations related to franchise management"),
                Tag().name("Branch Operations").description("Operations for managing branches within franchises"),
                Tag().name("Product Management").description("Operations for managing products within branches"),
                Tag().name("Analytics").description("Inventory and stock analytics endpoints")
            ))
    }
}