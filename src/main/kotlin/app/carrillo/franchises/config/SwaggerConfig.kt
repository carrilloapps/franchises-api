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
                .description("RESTful API for managing franchises, branches and products. Built with Spring Boot and Kotlin, using MongoDB for data persistence.")
                .contact(io.swagger.v3.oas.models.info.Contact()
                    .name("José Carrillo")
                    .url("https://carrillo.app")
                    .email("m@carrillo.app"))
                .license(io.swagger.v3.oas.models.info.License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT"))
                .termsOfService("https://carrillo.app/terms"))
            .servers(listOf(
                Server().url("https://franchises.isapp.dev").description("Production Server"),
                Server().url("http://localhost:3081").description("Local Development Server")
            ))
            .externalDocs(ExternalDocumentation()
                .description("Find more info about Franchises Management API")
                .url("https://github.com/carrilloapps/franchises-api"))
            .tags(listOf(
                Tag().name("Franchise Management")
                    .description("Operations related to franchise management, including branches and products")
                    .externalDocs(ExternalDocumentation()
                        .description("Franchise Management Guide")
                        .url("https://github.com/carrilloapps/franchises-api/blob/master/src/main/kotlin/app/carrillo/franchises/infrastructure/FranchiseController.kt")),
                Tag().name("Home")
                    .description("API home endpoint and general information")
                    .externalDocs(ExternalDocumentation()
                        .description("Home Endpoint Documentation")
                        .url("https://github.com/carrilloapps/franchises-api/blob/master/src/main/kotlin/app/carrillo/franchises/infrastructure/HomeController.kt"))
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