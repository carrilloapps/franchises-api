package app.carrillo.franchises.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.BeforeEach
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.Duration

/**
 * Unit tests for FranchiseRepository interface
 * Tests the contract and behavior expectations of the repository
 */
@DisplayName("FranchiseRepository Tests")
class FranchiseRepositoryTest {

    private lateinit var testRepository: TestFranchiseRepository
    private lateinit var sampleFranchise: Franchise
    private lateinit var sampleBranch: Branch
    private lateinit var sampleProduct: Product

    /**
     * Set up test data before each test
     */
    @BeforeEach
    fun setUp() {
        testRepository = TestFranchiseRepository()
        sampleProduct = Product("Test Product", 10, 100.0)
        sampleBranch = Branch("Test Branch", listOf(sampleProduct))
        sampleFranchise = Franchise(
            id = "test-id",
            name = "Test Franchise",
            address = "Test Address",
            description = "Test Description",
            branches = listOf(sampleBranch)
        )
    }

    @Nested
    @DisplayName("Save Operations")
    inner class SaveOperations {

        /**
         * Test saving a franchise successfully
         */
        @Test
        @DisplayName("Should save franchise successfully")
        fun `should save franchise successfully`() {
            // When
            val result = testRepository.save(sampleFranchise)

            // Then
            StepVerifier.create(result)
                .expectNext(sampleFranchise)
                .verifyComplete()

            assertTrue(testRepository.franchises.containsKey(sampleFranchise.id))
            assertEquals(sampleFranchise, testRepository.franchises[sampleFranchise.id])
        }

        /**
         * Test saving multiple franchises
         */
        @Test
        @DisplayName("Should save multiple franchises")
        fun `should save multiple franchises`() {
            // Given
            val franchise2 = Franchise(
                id = "test-id-2",
                name = "Second Franchise",
                address = "Second Address"
            )

            // When
            val result1 = testRepository.save(sampleFranchise)
            val result2 = testRepository.save(franchise2)

            // Then
            StepVerifier.create(result1)
                .expectNext(sampleFranchise)
                .verifyComplete()

            StepVerifier.create(result2)
                .expectNext(franchise2)
                .verifyComplete()

            assertEquals(2, testRepository.franchises.size)
        }

        /**
         * Test updating existing franchise
         */
        @Test
        @DisplayName("Should update existing franchise")
        fun `should update existing franchise`() {
            // Given
            testRepository.franchises[sampleFranchise.id] = sampleFranchise
            val updatedFranchise = sampleFranchise.copy(name = "Updated Name")

            // When
            val result = testRepository.save(updatedFranchise)

            // Then
            StepVerifier.create(result)
                .expectNext(updatedFranchise)
                .verifyComplete()

            assertEquals("Updated Name", testRepository.franchises[sampleFranchise.id]?.name)
        }
    }

    @Nested
    @DisplayName("Find Operations")
    inner class FindOperations {

        /**
         * Test finding franchise by existing ID
         */
        @Test
        @DisplayName("Should find franchise by existing ID")
        fun `should find franchise by existing ID`() {
            // Given
            testRepository.franchises[sampleFranchise.id] = sampleFranchise

            // When
            val result = testRepository.findById(sampleFranchise.id)

            // Then
            StepVerifier.create(result)
                .expectNext(sampleFranchise)
                .verifyComplete()
        }

        /**
         * Test finding franchise by non-existing ID
         */
        @Test
        @DisplayName("Should return empty for non-existing ID")
        fun `should return empty for non-existing ID`() {
            // When
            val result = testRepository.findById("non-existing-id")

            // Then
            StepVerifier.create(result)
                .verifyComplete()
        }

        /**
         * Test finding all franchises when repository is empty
         */
        @Test
        @DisplayName("Should return empty flux when no franchises exist")
        fun `should return empty flux when no franchises exist`() {
            // When
            val result = testRepository.findAll()

            // Then
            StepVerifier.create(result)
                .verifyComplete()
        }

        /**
         * Test finding all franchises with data
         */
        @Test
        @DisplayName("Should return all franchises")
        fun `should return all franchises`() {
            // Given
            val franchise2 = Franchise(
                id = "test-id-2",
                name = "Second Franchise"
            )
            testRepository.franchises[sampleFranchise.id] = sampleFranchise
            testRepository.franchises[franchise2.id] = franchise2

            // When
            val result = testRepository.findAll()

            // Then
            StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete()
        }

        /**
         * Test finding all franchises returns correct data
         */
        @Test
        @DisplayName("Should return correct franchise data")
        fun `should return correct franchise data`() {
            // Given
            testRepository.franchises[sampleFranchise.id] = sampleFranchise

            // When
            val result = testRepository.findAll()

            // Then
            StepVerifier.create(result)
                .assertNext { franchise ->
                    assertEquals(sampleFranchise.id, franchise.id)
                    assertEquals(sampleFranchise.name, franchise.name)
                    assertEquals(sampleFranchise.address, franchise.address)
                    assertEquals(sampleFranchise.description, franchise.description)
                    assertEquals(sampleFranchise.branches.size, franchise.branches.size)
                }
                .verifyComplete()
        }
    }

    @Nested
    @DisplayName("Delete Operations")
    inner class DeleteOperations {

        /**
         * Test deleting franchise by entity
         */
        @Test
        @DisplayName("Should delete franchise by entity")
        fun `should delete franchise by entity`() {
            // Given
            testRepository.franchises[sampleFranchise.id] = sampleFranchise
            assertTrue(testRepository.franchises.containsKey(sampleFranchise.id))

            // When
            val result = testRepository.delete(sampleFranchise)

            // Then
            StepVerifier.create(result)
                .verifyComplete()

            assertFalse(testRepository.franchises.containsKey(sampleFranchise.id))
        }

        /**
         * Test deleting franchise by ID
         */
        @Test
        @DisplayName("Should delete franchise by ID")
        fun `should delete franchise by ID`() {
            // Given
            testRepository.franchises[sampleFranchise.id] = sampleFranchise
            assertTrue(testRepository.franchises.containsKey(sampleFranchise.id))

            // When
            val result = testRepository.deleteById(sampleFranchise.id)

            // Then
            StepVerifier.create(result)
                .verifyComplete()

            assertFalse(testRepository.franchises.containsKey(sampleFranchise.id))
        }

        /**
         * Test deleting non-existing franchise by entity
         */
        @Test
        @DisplayName("Should handle deleting non-existing franchise by entity")
        fun `should handle deleting non-existing franchise by entity`() {
            // When
            val result = testRepository.delete(sampleFranchise)

            // Then
            StepVerifier.create(result)
                .verifyComplete()
        }

        /**
         * Test deleting non-existing franchise by ID
         */
        @Test
        @DisplayName("Should handle deleting non-existing franchise by ID")
        fun `should handle deleting non-existing franchise by ID`() {
            // When
            val result = testRepository.deleteById("non-existing-id")

            // Then
            StepVerifier.create(result)
                .verifyComplete()
        }
    }

    @Nested
    @DisplayName("Repository Contract Tests")
    inner class RepositoryContractTests {

        /**
         * Test that save operation is reactive
         */
        @Test
        @DisplayName("Should handle reactive save operations")
        fun `should handle reactive save operations`() {
            // When
            val result = testRepository.save(sampleFranchise)

            // Then
            assertNotNull(result)
            assertTrue(result is Mono<Franchise>)
        }

        /**
         * Test that findById operation is reactive
         */
        @Test
        @DisplayName("Should handle reactive findById operations")
        fun `should handle reactive findById operations`() {
            // When
            val result = testRepository.findById("any-id")

            // Then
            assertNotNull(result)
            assertTrue(result is Mono<Franchise>)
        }

        /**
         * Test that findAll operation is reactive
         */
        @Test
        @DisplayName("Should handle reactive findAll operations")
        fun `should handle reactive findAll operations`() {
            // When
            val result = testRepository.findAll()

            // Then
            assertNotNull(result)
            assertTrue(result is Flux<Franchise>)
        }

        /**
         * Test that delete operations are reactive
         */
        @Test
        @DisplayName("Should handle reactive delete operations")
        fun `should handle reactive delete operations`() {
            // When
            val deleteResult = testRepository.delete(sampleFranchise)
            val deleteByIdResult = testRepository.deleteById("any-id")

            // Then
            assertNotNull(deleteResult)
            assertNotNull(deleteByIdResult)
            assertTrue(deleteResult is Mono<Void>)
            assertTrue(deleteByIdResult is Mono<Void>)
        }

        /**
         * Test repository operations with timeout
         */
        @Test
        @DisplayName("Should complete operations within reasonable time")
        fun `should complete operations within reasonable time`() {
            // Given
            testRepository.franchises[sampleFranchise.id] = sampleFranchise

            // When & Then
            StepVerifier.create(testRepository.save(sampleFranchise))
                .expectNext(sampleFranchise)
                .expectComplete()
                .verify(Duration.ofSeconds(1))

            StepVerifier.create(testRepository.findById(sampleFranchise.id))
                .expectNext(sampleFranchise)
                .expectComplete()
                .verify(Duration.ofSeconds(1))

            StepVerifier.create(testRepository.findAll())
                .expectNextCount(1)
                .expectComplete()
                .verify(Duration.ofSeconds(1))
        }
    }

    /**
     * Test implementation of FranchiseRepository for testing purposes
     */
    private class TestFranchiseRepository : FranchiseRepository {
        val franchises = mutableMapOf<String, Franchise>()

        override fun save(franchise: Franchise): Mono<Franchise> {
            franchises[franchise.id] = franchise
            return Mono.just(franchise)
        }

        override fun findById(id: String): Mono<Franchise> {
            return franchises[id]?.let { Mono.just(it) } ?: Mono.empty()
        }

        override fun findAll(): Flux<Franchise> {
            return Flux.fromIterable(franchises.values)
        }

        override fun delete(franchise: Franchise): Mono<Void> {
            franchises.remove(franchise.id)
            return Mono.empty()
        }

        override fun deleteById(id: String): Mono<Void> {
            franchises.remove(id)
            return Mono.empty()
        }
    }
}