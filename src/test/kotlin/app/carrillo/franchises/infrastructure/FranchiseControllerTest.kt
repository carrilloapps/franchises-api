package app.carrillo.franchises.infrastructure

import app.carrillo.franchises.application.FranchiseService
import app.carrillo.franchises.domain.Branch
import app.carrillo.franchises.domain.Franchise
import app.carrillo.franchises.domain.Product
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(FranchiseController::class)
class FranchiseControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var franchiseService: FranchiseService

    @Test
    @DisplayName("Should add franchise successfully")
    fun `should add franchise successfully`() {
        // Given
        val franchise = Franchise(name = "Test Franchise", address = "Test Address")
        val savedFranchise = franchise.copy(id = "test-id")
        
        whenever(franchiseService.addFranchise(franchise)).thenReturn(Mono.just(savedFranchise))

        // When & Then
        webTestClient.post()
            .uri("/franchises")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(franchise)
            .exchange()
            .expectStatus().isCreated
            .expectBody(Franchise::class.java)
            .isEqualTo(savedFranchise)
    }

    @Test
    @DisplayName("Should get all franchises successfully")
    fun `should get all franchises successfully`() {
        // Given
        val franchise1 = Franchise(id = "1", name = "Franchise 1")
        val franchise2 = Franchise(id = "2", name = "Franchise 2")
        
        whenever(franchiseService.getAllFranchises()).thenReturn(Flux.just(franchise1, franchise2))

        // When & Then
        webTestClient.get()
            .uri("/franchises")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Franchise::class.java)
            .hasSize(2)
            .contains(franchise1, franchise2)
    }

    @Test
    @DisplayName("Should get franchise by id successfully")
    fun `should get franchise by id successfully`() {
        // Given
        val franchiseId = "test-id"
        val franchise = Franchise(id = franchiseId, name = "Test Franchise")
        
        whenever(franchiseService.getFranchiseById(franchiseId)).thenReturn(Mono.just(franchise))

        // When & Then
        webTestClient.get()
            .uri("/franchises/{id}", franchiseId)
            .exchange()
            .expectStatus().isOk
            .expectBody(Franchise::class.java)
            .isEqualTo(franchise)
    }

    @Test
    @DisplayName("Should return 404 when franchise not found")
    fun `should return 404 when franchise not found`() {
        // Given
        val franchiseId = "non-existent-id"
        
        whenever(franchiseService.getFranchiseById(franchiseId)).thenReturn(Mono.empty())

        // When & Then
        webTestClient.get()
            .uri("/franchises/{id}", franchiseId)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    @DisplayName("Should add branch to franchise successfully")
    fun `should add branch to franchise successfully`() {
        // Given
        val franchiseId = "test-id"
        val branch = Branch(name = "Test Branch")
        val updatedFranchise = Franchise(id = franchiseId, name = "Test Franchise", branches = mutableListOf(branch))
        
        whenever(franchiseService.addBranchToFranchise(franchiseId, branch)).thenReturn(Mono.just(updatedFranchise))

        // When & Then
        webTestClient.post()
            .uri("/franchises/{franchiseId}/branches", franchiseId)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(branch)
            .exchange()
            .expectStatus().isOk
            .expectBody(Franchise::class.java)
            .isEqualTo(updatedFranchise)
    }

    @Test
    @DisplayName("Should add product to branch successfully")
    fun `should add product to branch successfully`() {
        // Given
        val franchiseId = "test-id"
        val branchName = "Test Branch"
        val product = Product(name = "Test Product", stock = 10, price = 100.0)
        val updatedFranchise = Franchise(id = franchiseId, name = "Test Franchise")
        
        whenever(franchiseService.addProductToBranch(franchiseId, branchName, product)).thenReturn(Mono.just(updatedFranchise))

        // When & Then
        webTestClient.post()
            .uri("/franchises/{franchiseId}/branches/{branchName}/products", franchiseId, branchName)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(product)
            .exchange()
            .expectStatus().isOk
            .expectBody(Franchise::class.java)
            .isEqualTo(updatedFranchise)
    }

    @Test
    @DisplayName("Should delete product from branch successfully")
    fun `should delete product from branch successfully`() {
        // Given
        val franchiseId = "test-id"
        val branchName = "Test Branch"
        val productName = "Test Product"
        val updatedFranchise = Franchise(id = franchiseId, name = "Test Franchise")
        
        whenever(franchiseService.deleteProductFromBranch(franchiseId, branchName, productName)).thenReturn(Mono.just(updatedFranchise))

        // When & Then
        webTestClient.delete()
            .uri("/franchises/{franchiseId}/branches/{branchName}/products/{productName}", franchiseId, branchName, productName)
            .exchange()
            .expectStatus().isOk
            .expectBody(Franchise::class.java)
            .isEqualTo(updatedFranchise)
    }

    @Test
    @DisplayName("Should update franchise name successfully")
    fun `should update franchise name successfully`() {
        // Given
        val franchiseId = "test-id"
        val newName = "Updated Franchise Name"
        val updatedFranchise = Franchise(id = franchiseId, name = newName)
        
        whenever(franchiseService.updateFranchiseName(franchiseId, newName)).thenReturn(Mono.just(updatedFranchise))

        // When & Then
        webTestClient.put()
            .uri("/franchises/{franchiseId}/name?newName={newName}", franchiseId, newName)
            .exchange()
            .expectStatus().isOk
            .expectBody(Franchise::class.java)
            .isEqualTo(updatedFranchise)
    }
}