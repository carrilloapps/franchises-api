package app.carrillo.franchises.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import io.swagger.v3.oas.models.tags.Tag
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.media.Schema
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
                    
                    ## API Standards
                    - **REST Compliance**: Follows RESTful design principles
                    - **HTTP Status Codes**: Proper use of standard HTTP status codes
                    - **Content Type**: JSON request/response format
                    - **Error Handling**: Consistent error response structure
                    
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
                    .url("https://opensource.org/licenses/MIT"))
                .termsOfService("https://carrillo.app/terms"))
            .servers(listOf(
                Server().url("http://localhost:3081").description("Local Development Server"),
                Server().url("https://franchises.isapp.dev").description("Production Server")
            ))
            .externalDocs(ExternalDocumentation()
                .description("Find more info about Franchises Management API")
                .url("https://github.com/carrilloapps/franchises-api/wiki"))
            .tags(listOf(
                Tag().name("Franchise Management")
                    .description("Operations related to franchise management")
                    .externalDocs(ExternalDocumentation()
                        .description("Franchise Management Guide")
                        .url("https://github.com/carrilloapps/franchises-api/wiki/franchise-management")),
                Tag().name("Branch Operations")
                    .description("Operations for managing branches within franchises")
                    .externalDocs(ExternalDocumentation()
                        .description("Branch Operations Guide")
                        .url("https://github.com/carrilloapps/franchises-api/wiki/branch-operations")),
                Tag().name("Product Management")
                    .description("Operations for managing products within branches")
                    .externalDocs(ExternalDocumentation()
                        .description("Product Management Guide")
                        .url("https://github.com/carrilloapps/franchises-api/wiki/product-management")),
                Tag().name("Analytics")
                    .description("Inventory and stock analytics endpoints")
                    .externalDocs(ExternalDocumentation()
                        .description("Analytics Guide")
                        .url("https://github.com/carrilloapps/franchises-api/wiki/analytics"))
            ))
            .components(Components()
                .responses(mapOf(
                    "BadRequest" to ApiResponse()
                        .description("Bad Request - Invalid input parameters")
                        .content(Content().addMediaType("application/json", 
                            MediaType().schema(Schema<Any>().type("object")
                                .addProperty("error", Schema<Any>().type("string").example("Invalid request parameters"))
                                .addProperty("message", Schema<Any>().type("string").example("The provided data is not valid"))
                                .addProperty("timestamp", Schema<Any>().type("string").format("date-time"))
                                .addProperty("path", Schema<Any>().type("string").example("/api/franchises"))))),
                    "NotFound" to ApiResponse()
                        .description("Not Found - Resource not found")
                        .content(Content().addMediaType("application/json", 
                            MediaType().schema(Schema<Any>().type("object")
                                .addProperty("error", Schema<Any>().type("string").example("Resource not found"))
                                .addProperty("message", Schema<Any>().type("string").example("The requested resource was not found"))
                                .addProperty("timestamp", Schema<Any>().type("string").format("date-time"))
                                .addProperty("path", Schema<Any>().type("string").example("/api/franchises/123"))))),
                    "InternalServerError" to ApiResponse()
                        .description("Internal Server Error - Unexpected server error")
                        .content(Content().addMediaType("application/json", 
                            MediaType().schema(Schema<Any>().type("object")
                                .addProperty("error", Schema<Any>().type("string").example("Internal server error"))
                                .addProperty("message", Schema<Any>().type("string").example("An unexpected error occurred"))
                                .addProperty("timestamp", Schema<Any>().type("string").format("date-time"))
                                .addProperty("path", Schema<Any>().type("string").example("/api/franchises"))))),
                    "Success" to ApiResponse()
                        .description("Operation completed successfully")
                        .content(Content().addMediaType("application/json", 
                            MediaType().schema(Schema<Any>().type("object")
                                .addProperty("message", Schema<Any>().type("string").example("Operation completed successfully"))
                                .addProperty("timestamp", Schema<Any>().type("string").format("date-time")))))
                )))
    }
}