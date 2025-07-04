package app.carrillo.franchises.config

import io.swagger.v3.oas.models.OpenAPI
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@TestPropertySource(properties = ["spring.mongodb.embedded.version=3.5.5"])
class SwaggerConfigTest {

    private val swaggerConfig = SwaggerConfig()

    @Test
    fun `should create OpenAPI configuration with correct info`() {
        // When
        val openAPI: OpenAPI = swaggerConfig.customOpenAPI()

        // Then
        assertNotNull(openAPI)
        assertNotNull(openAPI.info)
        
        val info = openAPI.info
        assertEquals("Franchises Management API", info.title)
        assertEquals("1.0.0", info.version)
        assertNotNull(info.description)
        assertEquals("RESTful API for managing franchises, branches and products. Built with Spring Boot and Kotlin, using MongoDB for data persistence.", info.description)
    }

    @Test
    fun `should configure contact information correctly`() {
        // When
        val openAPI: OpenAPI = swaggerConfig.customOpenAPI()

        // Then
        val contact = openAPI.info.contact
        assertNotNull(contact)
        assertEquals("José Carrillo", contact.name)
        assertEquals("https://carrillo.app", contact.url)
        assertEquals("m@carrillo.app", contact.email)
    }

    @Test
    fun `should configure license information correctly`() {
        // When
        val openAPI: OpenAPI = swaggerConfig.customOpenAPI()

        // Then
        val license = openAPI.info.license
        assertNotNull(license)
        assertEquals("MIT License", license.name)
        assertEquals("https://opensource.org/licenses/MIT", license.url)
    }

    @Test
    fun `should configure servers correctly`() {
        // When
        val openAPI: OpenAPI = swaggerConfig.customOpenAPI()

        // Then
        val servers = openAPI.servers
        assertNotNull(servers)
        assertEquals(2, servers.size)
        
        val prodServer = servers[0]
        assertEquals("https://franchises.isapp.dev", prodServer.url)
        assertEquals("Production Server", prodServer.description)
        
        val localServer = servers[1]
        assertEquals("http://localhost:3081", localServer.url)
        assertEquals("Local Development Server", localServer.description)
    }

    @Test
    fun `should configure tags correctly`() {
        // When
        val openAPI: OpenAPI = swaggerConfig.customOpenAPI()

        // Then
        val tags = openAPI.tags
        assertNotNull(tags)
        assertEquals(2, tags.size)
        
        val tagNames = tags.map { it.name }
        assertTrue(tagNames.contains("Franchise Management"))
        assertTrue(tagNames.contains("Home"))
    }


}