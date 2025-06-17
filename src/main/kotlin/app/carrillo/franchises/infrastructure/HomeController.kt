package app.carrillo.franchises.infrastructure

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    @GetMapping("/")
    fun home(): Map<String, String> {
        return mapOf("message" to "Welcome to the Franchises API! Access /franchises for the main API endpoints.")
    }
}