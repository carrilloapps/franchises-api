package app.carrillo.franchises.infrastructure

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    @GetMapping("/")
    fun home(): Map<String, String> {
        val swaggerUrl = "/swagger-ui/index.html"
        return mapOf(
            "message" to "Welcome to the Franchises API!",
            "documentation" to "Access our API documentation at $swaggerUrl"
        )
    }
}