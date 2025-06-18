package app.carrillo.franchises.infrastructure

import app.carrillo.franchises.application.FranchiseService
import app.carrillo.franchises.domain.Branch
import app.carrillo.franchises.domain.Franchise
import app.carrillo.franchises.domain.Product
import app.carrillo.franchises.infrastructure.FranchiseController
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
        val franchises = listOf(
            Franchise(id = "1", name = "Franchise 1", address = "Address 1"),
            Franchise(id = "2", name = "Franchise 2", address = "Address 2")
        )
        
        whenever(franchiseService.getAllFranchises()).thenReturn(Flux.fromIterable(franchises))

        // When & Then
        webTestClient.get()
            .uri("/franchises")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Franchise::class.java)
            .hasSize(2)
    }

    @Test
    @DisplayName("Should get franchise by id successfully")
    fun `should get franchise by id successfully`() {
        // Given
        val franchiseId = "test-id"
        val franchise = Franchise(id = franchiseId, name = "Test Franchise", address = "Test Address")
        
        whenever(franchiseService.getFranchiseById(franchiseId)).thenReturn(Mono.just(franchise))

        // When & Then
        webTestClient.get()
            .uri("/franchises/{franchiseId}", franchiseId)
            .exchange()
            .expectStatus().isOk
            .expectBody(Franchise::class.java)
            .isEqualTo(franchise)
    }

    @Test
    @DisplayName("Should return not found when franchise does not exist")
    fun `should return not found when franchise does not exist`() {
        // Given
        val franchiseId = "non-existent-id"
        
        whenever(franchiseService.getFranchiseById(franchiseId)).thenReturn(Mono.empty())

        // When & Then
        webTestClient.get()
            .uri("/franchises/{franchiseId}", franchiseId)
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
    @DisplayName("Should modify product stock successfully")
    fun `should modify product stock successfully`() {
        // Given
        val franchiseId = "test-id"
        val branchName = "Test Branch"
        val productName = "Test Product"
        val newStock = 25
        val updatedFranchise = Franchise(id = franchiseId, name = "Test Franchise")
        
        whenever(franchiseService.modifyProductStock(franchiseId, branchName, productName, newStock)).thenReturn(Mono.just(updatedFranchise))

        // When & Then
        webTestClient.put()
            .uri("/franchises/{franchiseId}/branches/{branchName}/products/{productName}/stock?newStock={newStock}", franchiseId, branchName, productName, newStock)
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

    @Test
    @DisplayName("Should update franchise address successfully")
    fun `should update franchise address successfully`() {
        // Given
        val franchiseId = "test-id"
        val newAddress = "123 New Street, New City"
        val updatedFranchise = Franchise(id = franchiseId, name = "Test Franchise", address = newAddress)
        
        whenever(franchiseService.updateFranchiseAddress(franchiseId, newAddress)).thenReturn(Mono.just(updatedFranchise))

        // When & Then
        webTestClient.put()
            .uri("/franchises/{franchiseId}/address?newAddress={newAddress}", franchiseId, newAddress)
            .exchange()
            .expectStatus().isOk
            .expectBody(Franchise::class.java)
            .isEqualTo(updatedFranchise)
    }

    @Test
    @DisplayName("Should update franchise description successfully")
    fun `should update franchise description successfully`() {
        // Given
        val franchiseId = "test-id"
        val newDescription = "Updated franchise description"
        val updatedFranchise = Franchise(id = franchiseId, name = "Test Franchise", description = newDescription)
        
        whenever(franchiseService.updateFranchiseDescription(franchiseId, newDescription)).thenReturn(Mono.just(updatedFranchise))

        // When & Then
        webTestClient.put()
            .uri("/franchises/{franchiseId}/description?newDescription={newDescription}", franchiseId, newDescription)
            .exchange()
            .expectStatus().isOk
            .expectBody(Franchise::class.java)
            .isEqualTo(updatedFranchise)
    }

    @Test
    @DisplayName("Should update branch name successfully")
    fun `should update branch name successfully`() {
        // Given
        val franchiseId = "test-id"
        val oldBranchName = "Old Branch"
        val newBranchName = "New Branch Name"
        val updatedFranchise = Franchise(id = franchiseId, name = "Test Franchise")
        
        whenever(franchiseService.updateBranchName(franchiseId, oldBranchName, newBranchName)).thenReturn(Mono.just(updatedFranchise))

        // When & Then
        webTestClient.put()
            .uri("/franchises/{franchiseId}/branches/{oldBranchName}/name?newBranchName={newBranchName}", franchiseId, oldBranchName, newBranchName)
            .exchange()
            .expectStatus().isOk
            .expectBody(Franchise::class.java)
            .isEqualTo(updatedFranchise)
    }

    @Test
    @DisplayName("Should update product name successfully")
    fun `should update product name successfully`() {
        // Given
        val franchiseId = "test-id"
        val branchName = "Test Branch"
        val oldProductName = "Old Product"
        val newProductName = "New Product Name"
        val updatedFranchise = Franchise(id = franchiseId, name = "Test Franchise")
        
        whenever(franchiseService.updateProductName(franchiseId, branchName, oldProductName, newProductName)).thenReturn(Mono.just(updatedFranchise))

        // When & Then
        webTestClient.put()
            .uri("/franchises/{franchiseId}/branches/{branchName}/products/{oldProductName}/name?newProductName={newProductName}", franchiseId, branchName, oldProductName, newProductName)
            .exchange()
            .expectStatus().isOk
            .expectBody(Franchise::class.java)
            .isEqualTo(updatedFranchise)
    }

    @Test
    @DisplayName("Should update product price successfully")
    fun `should update product price successfully`() {
        // Given
        val franchiseId = "test-id"
        val branchName = "Test Branch"
        val productName = "Test Product"
        val newPrice = 150.0
        val updatedFranchise = Franchise(id = franchiseId, name = "Test Franchise")
        
        whenever(franchiseService.updateProductPrice(franchiseId, branchName, productName, newPrice)).thenReturn(Mono.just(updatedFranchise))

        // When & Then
        webTestClient.put()
            .uri("/franchises/{franchiseId}/branches/{branchName}/products/{productName}/price?newPrice={newPrice}", franchiseId, branchName, productName, newPrice)
            .exchange()
            .expectStatus().isOk
            .expectBody(Franchise::class.java)
            .isEqualTo(updatedFranchise)
    }

    @Test
    @DisplayName("Should get product with most stock per branch successfully")
    fun `should get product with most stock per branch successfully`() {
        // Given
        val franchiseId = "test-id"
        val productMap1 = mapOf("Branch 1" to Product(name = "Product 1", stock = 15, price = 100.0))
        val productMap2 = mapOf("Branch 2" to Product(name = "Product 2", stock = 20, price = 150.0))
        
        whenever(franchiseService.getProductWithMostStockPerBranch(franchiseId))
            .thenReturn(Flux.just(productMap1, productMap2))

        // When & Then
        webTestClient.get()
            .uri("/franchises/$franchiseId/products/most-stock-per-branch")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Map::class.java)
            .hasSize(2)
    }

    @Test
    @DisplayName("Should handle error when adding franchise fails")
    fun `should handle error when adding franchise fails`() {
        // Given
        val franchise = Franchise(name = "Test Franchise", address = "Test Address")
        val error = RuntimeException("Database error")
        
        whenever(franchiseService.addFranchise(franchise)).thenReturn(Mono.error(error))

        // When & Then
        webTestClient.post()
            .uri("/franchises")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(franchise)
            .exchange()
            .expectStatus().is5xxServerError
    }

    @Test
    @DisplayName("Should handle error when getting all franchises fails")
    fun `should handle error when getting all franchises fails`() {
        // Given
        val error = RuntimeException("Database connection error")
        whenever(franchiseService.getAllFranchises()).thenReturn(Flux.error(error))

        // When & Then
        webTestClient.get()
            .uri("/franchises")
            .exchange()
            .expectStatus().is5xxServerError
    }

    @Test
    @DisplayName("Should handle error when getting franchise by id fails")
    fun `should handle error when getting franchise by id fails`() {
        // Given
        val franchiseId = "test-id"
        val error = RuntimeException("Franchise not found")
        whenever(franchiseService.getFranchiseById(franchiseId)).thenReturn(Mono.error(error))

        // When & Then
        webTestClient.get()
            .uri("/franchises/$franchiseId")
            .exchange()
            .expectStatus().is5xxServerError
    }

    @Test
    @DisplayName("Should handle error when getting products with most stock fails")
    fun `should handle error when getting products with most stock fails`() {
        // Given
        val franchiseId = "test-id"
        val error = RuntimeException("Failed to get products")
        whenever(franchiseService.getProductWithMostStockPerBranch(franchiseId)).thenReturn(Flux.error(error))

        // When & Then
        webTestClient.get()
            .uri("/franchises/$franchiseId/products/most-stock-per-branch")
            .exchange()
            .expectStatus().is5xxServerError
    }

}