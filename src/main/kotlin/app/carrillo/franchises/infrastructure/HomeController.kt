package app.carrillo.franchises.infrastructure

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema

@RestController
@Tag(name = "Home", description = "API home endpoint providing welcome message and documentation links")
class HomeController {

    @GetMapping("/")
    @Operation(
        summary = "Get API welcome message",
        description = "Returns a welcome message and provides links to API documentation. This is the main entry point for the Franchises Management API."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Welcome message and documentation links retrieved successfully",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(
                    type = "object",
                    example = """{"message": "Welcome to the Franchises API!", "documentation": "Access our API documentation at /swagger-ui.html"}"""
                )
            )]
        ),
        ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = [Content(mediaType = "application/json")]
        )
    ])
    fun home(): Map<String, String> {
        val swaggerUrl = "/swagger-ui.html"
        return mapOf(
            "message" to "Welcome to the Franchises API!",
            "documentation" to "Access our API documentation at $swaggerUrl"
        )
    }
}