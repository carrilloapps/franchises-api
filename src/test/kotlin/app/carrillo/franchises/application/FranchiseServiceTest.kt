package app.carrillo.franchises.application

import app.carrillo.franchises.domain.Franchise
import app.carrillo.franchises.domain.Branch
import app.carrillo.franchises.domain.Product
import app.carrillo.franchises.infrastructure.FranchiseRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

/**
 * Unit tests for FranchiseService
 * Tests all business logic methods with proper mocking and reactive testing
 */
@ExtendWith(MockitoExtension::class)
@DisplayName("FranchiseService Tests")
class FranchiseServiceTest {

    @Mock
    private lateinit var franchiseRepository: FranchiseRepository

    private lateinit var franchiseService: FranchiseService

    @BeforeEach
    fun setUp() {
        franchiseService = FranchiseService(franchiseRepository)
    }

    @Nested
    @DisplayName("Add Franchise Tests")
    inner class AddFranchiseTests {

        @Test
        @DisplayName("Should add franchise successfully")
        fun `should add franchise successfully`() {
            // Given
            val franchise = Franchise(name = "Test Franchise")
            val savedFranchise = franchise.copy(id = "generated-id")
            
            `when`(franchiseRepository.save(franchise)).thenReturn(Mono.just(savedFranchise))

            // When & Then
            StepVerifier.create(franchiseService.addFranchise(franchise))
                .expectNext(savedFranchise)
                .verifyComplete()
        }

        @Test
        @DisplayName("Should handle repository error when adding franchise")
        fun `should handle repository error when adding franchise`() {
            // Given
            val franchise = Franchise(name = "Test Franchise")
            val error = RuntimeException("Database error")
            
            `when`(franchiseRepository.save(franchise)).thenReturn(Mono.error(error))

            // When & Then
            StepVerifier.create(franchiseService.addFranchise(franchise))
                .expectError(RuntimeException::class.java)
                .verify()
        }
    }

    @Nested
    @DisplayName("Get All Franchises Tests")
    inner class GetAllFranchisesTests {

        @Test
        @DisplayName("Should return all franchises")
        fun `should return all franchises`() {
            // Given
            val franchise1 = Franchise(id = "1", name = "Franchise 1")
            val franchise2 = Franchise(id = "2", name = "Franchise 2")
            
            `when`(franchiseRepository.findAll()).thenReturn(Flux.just(franchise1, franchise2))

            // When & Then
            StepVerifier.create(franchiseService.getAllFranchises())
                .expectNext(franchise1)
                .expectNext(franchise2)
                .verifyComplete()
        }

        @Test
        @DisplayName("Should return empty flux when no franchises exist")
        fun `should return empty flux when no franchises exist`() {
            // Given
            `when`(franchiseRepository.findAll()).thenReturn(Flux.empty())

            // When & Then
            StepVerifier.create(franchiseService.getAllFranchises())
                .verifyComplete()
        }
    }

    @Nested
    @DisplayName("Get Franchise By ID Tests")
    inner class GetFranchiseByIdTests {

        @Test
        @DisplayName("Should return franchise when found")
        fun `should return franchise when found`() {
            // Given
            val franchiseId = "test-id"
            val franchise = Franchise(id = franchiseId, name = "Test Franchise")
            
            `when`(franchiseRepository.findById(franchiseId)).thenReturn(Mono.just(franchise))

            // When & Then
            StepVerifier.create(franchiseService.getFranchiseById(franchiseId))
                .expectNext(franchise)
                .verifyComplete()
        }

        @Test
        @DisplayName("Should return empty when franchise not found")
        fun `should return empty when franchise not found`() {
            // Given
            val franchiseId = "non-existent-id"
            
            `when`(franchiseRepository.findById(franchiseId)).thenReturn(Mono.empty())

            // When & Then
            StepVerifier.create(franchiseService.getFranchiseById(franchiseId))
                .verifyComplete()
        }
    }
}