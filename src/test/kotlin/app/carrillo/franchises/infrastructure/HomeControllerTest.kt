package app.carrillo.franchises.infrastructure

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = ["spring.mongodb.embedded.version=3.5.5"])
class HomeControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    private val homeController = HomeController()

    @Test
    fun `should return welcome message and documentation link`() {
        // When
        val result = homeController.home()

        // Then
        assertNotNull(result)
        assertEquals(2, result.size)
        assertEquals("Welcome to the Franchises API!", result["message"])
        assertEquals("Access our API documentation at /swagger-ui.html", result["documentation"])
    }

    @Test
    fun `should return map with correct keys`() {
        // When
        val result = homeController.home()

        // Then
        assertTrue(result.containsKey("message"))
        assertTrue(result.containsKey("documentation"))
    }

    @Test
    fun `GET root endpoint should return 200 and correct JSON`() {
        // When & Then
        webTestClient.get()
            .uri("/")
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType("application/json")
            .expectBody()
            .jsonPath("$.message").isEqualTo("Welcome to the Franchises API!")
            .jsonPath("$.documentation").isEqualTo("Access our API documentation at /swagger-ui.html")
    }

    @Test
    fun `GET root endpoint should return valid JSON structure`() {
        // When & Then
        webTestClient.get()
            .uri("/")
            .exchange()
            .expectStatus().isOk
            .expectBody(Map::class.java)
            .consumeWith { result ->
                val responseMap = result.responseBody!!
                assertEquals(2, responseMap.size)
                assertTrue(responseMap.containsKey("message"))
                assertTrue(responseMap.containsKey("documentation"))
            }
    }

    @Test
    fun `should include swagger URL in documentation field`() {
        // When
        val result = homeController.home()

        // Then
        val documentation = result["documentation"] as String
        assertTrue(documentation.contains("/swagger-ui.html"))
        assertTrue(documentation.contains("API documentation"))
    }
}