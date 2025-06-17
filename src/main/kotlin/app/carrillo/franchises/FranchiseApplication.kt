package app.carrillo.franchises

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer

@SpringBootApplication
@ComponentScan(basePackages = [
    "app.carrillo.franchises.infrastructure",
    "app.carrillo.franchises.application",
    "app.carrillo.franchises.domain"
])
class FranchiseApplication {

    @Bean
    fun corsConfigurer(): WebFluxConfigurer {
        return object : WebFluxConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/franchises/**")
                    .allowedOrigins("*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .maxAge(3600)
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<FranchiseApplication>(*args)
}