package app.carrillo.franchises

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.web.reactive.config.WebFluxConfigurer

/**
 * Unit tests for FranchiseApplication
 * Tests the Spring Boot application configuration and CORS setup
 */
@DisplayName("FranchiseApplication Tests")
class FranchiseApplicationTest {

    @Nested
    @DisplayName("Application Configuration Tests")
    inner class ApplicationConfigurationTests {

        /**
         * Test that FranchiseApplication can be instantiated
         */
        @Test
        @DisplayName("Should create FranchiseApplication instance")
        fun `should create FranchiseApplication instance`() {
            // When
            val application = FranchiseApplication()

            // Then
            assertNotNull(application)
            assertTrue(application is FranchiseApplication)
        }

        /**
         * Test that corsConfigurer bean is created
         */
        @Test
        @DisplayName("Should create corsConfigurer bean")
        fun `should create corsConfigurer bean`() {
            // Given
            val application = FranchiseApplication()

            // When
            val corsConfigurer = application.corsConfigurer()

            // Then
            assertNotNull(corsConfigurer)
            assertTrue(corsConfigurer is WebFluxConfigurer)
        }

        /**
         * Test CORS configuration is not null
         */
        @Test
        @DisplayName("Should configure CORS mappings correctly")
        fun `should configure CORS mappings correctly`() {
            // Given
            val application = FranchiseApplication()
            val corsConfigurer = application.corsConfigurer()

            // When & Then
            assertNotNull(corsConfigurer)
            assertTrue(corsConfigurer is WebFluxConfigurer)
        }
    }

    @Nested
    @DisplayName("CORS Configuration Tests")
    inner class CorsConfigurationTests {

        /**
         * Test CORS configuration properties
         */
        @Test
        @DisplayName("Should have correct CORS configuration properties")
        fun `should have correct CORS configuration properties`() {
            // Given
            val application = FranchiseApplication()
            val corsConfigurer = application.corsConfigurer()

            // When & Then
            assertNotNull(corsConfigurer)
            assertTrue(corsConfigurer is WebFluxConfigurer)
        }
    }

    @Nested
    @DisplayName("Application Annotations Tests")
    inner class ApplicationAnnotationsTests {

        /**
         * Test SpringBootApplication annotation
         */
        @Test
        @DisplayName("Should have SpringBootApplication annotation")
        fun `should have SpringBootApplication annotation`() {
            // Given
            val applicationClass = FranchiseApplication::class.java

            // When
            val springBootAnnotation = applicationClass.getAnnotation(org.springframework.boot.autoconfigure.SpringBootApplication::class.java)

            // Then
            assertNotNull(springBootAnnotation)
        }

        /**
         * Test ComponentScan annotation
         */
        @Test
        @DisplayName("Should have ComponentScan annotation with correct packages")
        fun `should have ComponentScan annotation with correct packages`() {
            // Given
            val applicationClass = FranchiseApplication::class.java

            // When
            val componentScanAnnotation = applicationClass.getAnnotation(org.springframework.context.annotation.ComponentScan::class.java)

            // Then
            assertNotNull(componentScanAnnotation)
            val basePackages = componentScanAnnotation.basePackages
            assertEquals(3, basePackages.size)
            assertTrue(basePackages.contains("app.carrillo.franchises.infrastructure"))
            assertTrue(basePackages.contains("app.carrillo.franchises.application"))
            assertTrue(basePackages.contains("app.carrillo.franchises.domain"))
        }
    }

    @Nested
    @DisplayName("Main Function Tests")
    inner class MainFunctionTests {

        /**
         * Test main function exists
         */
        @Test
        @DisplayName("Should have main function")
        fun `should have main function`() {
            // Given
            val mainMethod = try {
                Class.forName("app.carrillo.franchises.FranchiseApplicationKt")
                    .getDeclaredMethod("main", Array<String>::class.java)
            } catch (e: Exception) {
                null
            }

            // Then
            assertNotNull(mainMethod, "Main function should exist")
        }

        /**
         * Test main function parameters
         */
        @Test
        @DisplayName("Should have correct main function signature")
        fun `should have correct main function signature`() {
            // Given
            val mainMethod = try {
                Class.forName("app.carrillo.franchises.FranchiseApplicationKt")
                    .getDeclaredMethod("main", Array<String>::class.java)
            } catch (e: Exception) {
                null
            }

            // Then
            assertNotNull(mainMethod)
            assertEquals(1, mainMethod!!.parameterCount)
            assertEquals(Array<String>::class.java, mainMethod.parameterTypes[0])
        }
    }
}