package app.carrillo.franchises.application

import app.carrillo.franchises.application.FranchiseService
import app.carrillo.franchises.domain.Franchise
import app.carrillo.franchises.domain.Branch
import app.carrillo.franchises.domain.Product
import app.carrillo.franchises.infrastructure.FranchiseRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

/**
 * Unit tests for FranchiseService
 */
@DisplayName("FranchiseService Tests")
class FranchiseServiceTest {

    private val franchiseRepository: FranchiseRepository = mock()
    private val franchiseService = FranchiseService(franchiseRepository)

    @Test
    @DisplayName("Should add franchise successfully")
    fun `should add franchise successfully`() {
        // Given
        val franchise = Franchise(name = "Test Franchise")
        val savedFranchise = franchise.copy(id = "generated-id")
        
        whenever(franchiseRepository.save(franchise)).thenReturn(Mono.just(savedFranchise))

        // When & Then
        StepVerifier.create(franchiseService.addFranchise(franchise))
            .expectNext(savedFranchise)
            .verifyComplete()
    }

    @Test
    @DisplayName("Should get all franchises successfully")
    fun `should get all franchises successfully`() {
        // Given
        val franchise1 = Franchise(id = "1", name = "Franchise 1")
        val franchise2 = Franchise(id = "2", name = "Franchise 2")
        
        whenever(franchiseRepository.findAll()).thenReturn(Flux.just(franchise1, franchise2))

        // When & Then
        StepVerifier.create(franchiseService.getAllFranchises())
            .expectNext(franchise1)
            .expectNext(franchise2)
            .verifyComplete()
    }

    @Test
    @DisplayName("Should get franchise by id successfully")
    fun `should get franchise by id successfully`() {
        // Given
        val franchiseId = "test-id"
        val franchise = Franchise(id = franchiseId, name = "Test Franchise")
        
        whenever(franchiseRepository.findById(franchiseId)).thenReturn(Mono.just(franchise))

        // When & Then
        StepVerifier.create(franchiseService.getFranchiseById(franchiseId))
            .expectNext(franchise)
            .verifyComplete()
    }

    @Test
    @DisplayName("Should add branch to franchise successfully")
    fun `should add branch to franchise successfully`() {
        // Given
        val franchiseId = "test-id"
        val branchName = "New Branch"
        val existingFranchise = Franchise(id = franchiseId, name = "Test Franchise", branches = emptyList())
        val newBranch = Branch(name = branchName, products = emptyList())
        val updatedFranchise = existingFranchise.copy(branches = listOf(newBranch))
        
        whenever(franchiseRepository.findById(franchiseId)).thenReturn(Mono.just(existingFranchise))
        whenever(franchiseRepository.save(updatedFranchise)).thenReturn(Mono.just(updatedFranchise))

        // When & Then
        val newBranchToAdd = Branch(name = branchName, products = emptyList())
        StepVerifier.create(franchiseService.addBranchToFranchise(franchiseId, newBranchToAdd))
            .expectNext(updatedFranchise)
            .verifyComplete()
    }

    @Test
    @DisplayName("Should add product to branch successfully")
    fun `should add product to branch successfully`() {
        // Given
        val franchiseId = "test-id"
        val branchName = "Test Branch"
        val existingBranch = Branch(name = branchName, products = emptyList())
        val existingFranchise = Franchise(id = franchiseId, name = "Test Franchise", branches = listOf(existingBranch))
        val product = Product(name = "New Product", stock = 10)
        val updatedBranch = existingBranch.copy(products = listOf(product))
        val updatedFranchise = existingFranchise.copy(branches = listOf(updatedBranch))
        
        whenever(franchiseRepository.findById(franchiseId)).thenReturn(Mono.just(existingFranchise))
        whenever(franchiseRepository.save(updatedFranchise)).thenReturn(Mono.just(updatedFranchise))

        // When & Then
        StepVerifier.create(franchiseService.addProductToBranch(franchiseId, branchName, product))
            .expectNext(updatedFranchise)
            .verifyComplete()
    }

    @Test
    @DisplayName("Should return empty when franchise not found")
    fun `should return empty when franchise not found`() {
        // Given
        val franchiseId = "non-existent-id"
        
        whenever(franchiseRepository.findById(franchiseId)).thenReturn(Mono.empty())

        // When & Then
        StepVerifier.create(franchiseService.getFranchiseById(franchiseId))
            .verifyComplete()
    }

    @Test
    @DisplayName("Should handle repository error when adding franchise")
    fun `should handle repository error when adding franchise`() {
        // Given
        val franchise = Franchise(name = "Test Franchise")
        val error = RuntimeException("Database error")
        
        whenever(franchiseRepository.save(franchise)).thenReturn(Mono.error(error))

        // When & Then
        StepVerifier.create(franchiseService.addFranchise(franchise))
            .expectError(RuntimeException::class.java)
            .verify()
    }

    @Test
    @DisplayName("Should return empty flux when no franchises exist")
    fun `should return empty flux when no franchises exist`() {
        // Given
        whenever(franchiseRepository.findAll()).thenReturn(Flux.empty())

        // When & Then
        StepVerifier.create(franchiseService.getAllFranchises())
            .verifyComplete()
    }

    @Test
    @DisplayName("Should delete product from branch successfully")
    fun `should delete product from branch successfully`() {
        // Given
        val franchiseId = "test-id"
        val branchName = "Test Branch"
        val productName = "Product to Delete"
        val productToKeep = Product(name = "Product to Keep", stock = 5)
        val productToDelete = Product(name = productName, stock = 10)
        val existingBranch = Branch(name = branchName, products = listOf(productToKeep, productToDelete))
        val existingFranchise = Franchise(id = franchiseId, name = "Test Franchise", branches = listOf(existingBranch))
        val updatedBranch = existingBranch.copy(products = listOf(productToKeep))
        val updatedFranchise = existingFranchise.copy(branches = listOf(updatedBranch))
        
        whenever(franchiseRepository.findById(franchiseId)).thenReturn(Mono.just(existingFranchise))
        whenever(franchiseRepository.save(updatedFranchise)).thenReturn(Mono.just(updatedFranchise))

        // When & Then
        StepVerifier.create(franchiseService.deleteProductFromBranch(franchiseId, branchName, productName))
            .expectNext(updatedFranchise)
            .verifyComplete()
    }

    @Test
    @DisplayName("Should modify product stock successfully")
    fun `should modify product stock successfully`() {
        // Given
        val franchiseId = "test-id"
        val branchName = "Test Branch"
        val productName = "Test Product"
        val newStock = 25
        val originalProduct = Product(name = productName, stock = 10)
        val existingBranch = Branch(name = branchName, products = listOf(originalProduct))
        val existingFranchise = Franchise(id = franchiseId, name = "Test Franchise", branches = listOf(existingBranch))
        val updatedProduct = originalProduct.copy(stock = newStock)
        val updatedBranch = existingBranch.copy(products = listOf(updatedProduct))
        val updatedFranchise = existingFranchise.copy(branches = listOf(updatedBranch))
        
        whenever(franchiseRepository.findById(franchiseId)).thenReturn(Mono.just(existingFranchise))
        whenever(franchiseRepository.save(updatedFranchise)).thenReturn(Mono.just(updatedFranchise))

        // When & Then
        StepVerifier.create(franchiseService.modifyProductStock(franchiseId, branchName, productName, newStock))
            .expectNext(updatedFranchise)
            .verifyComplete()
    }

    @Test
    @DisplayName("Should update franchise name successfully")
    fun `should update franchise name successfully`() {
        // Given
        val franchiseId = "test-id"
        val newName = "Updated Franchise Name"
        val existingFranchise = Franchise(id = franchiseId, name = "Old Name")
        val updatedFranchise = existingFranchise.copy(name = newName)
        
        whenever(franchiseRepository.findById(franchiseId)).thenReturn(Mono.just(existingFranchise))
        whenever(franchiseRepository.save(updatedFranchise)).thenReturn(Mono.just(updatedFranchise))

        // When & Then
        StepVerifier.create(franchiseService.updateFranchiseName(franchiseId, newName))
            .expectNext(updatedFranchise)
            .verifyComplete()
    }

    @Test
    @DisplayName("Should update franchise address successfully")
    fun `should update franchise address successfully`() {
        // Given
        val franchiseId = "test-id"
        val newAddress = "123 New Street, New City"
        val existingFranchise = Franchise(id = franchiseId, name = "Test Franchise", address = "Old Address")
        val updatedFranchise = existingFranchise.copy(address = newAddress)
        
        whenever(franchiseRepository.findById(franchiseId)).thenReturn(Mono.just(existingFranchise))
        whenever(franchiseRepository.save(updatedFranchise)).thenReturn(Mono.just(updatedFranchise))

        // When & Then
        StepVerifier.create(franchiseService.updateFranchiseAddress(franchiseId, newAddress))
            .expectNext(updatedFranchise)
            .verifyComplete()
    }

    @Test
    @DisplayName("Should update franchise description successfully")
    fun `should update franchise description successfully`() {
        // Given
        val franchiseId = "test-id"
        val newDescription = "Updated franchise description with new details"
        val existingFranchise = Franchise(id = franchiseId, name = "Test Franchise", description = "Old description")
        val updatedFranchise = existingFranchise.copy(description = newDescription)
        
        whenever(franchiseRepository.findById(franchiseId)).thenReturn(Mono.just(existingFranchise))
        whenever(franchiseRepository.save(updatedFranchise)).thenReturn(Mono.just(updatedFranchise))

        // When & Then
        StepVerifier.create(franchiseService.updateFranchiseDescription(franchiseId, newDescription))
            .expectNext(updatedFranchise)
            .verifyComplete()
    }

    @Test
    @DisplayName("Should update branch name successfully")
    fun `should update branch name successfully`() {
        // Given
        val franchiseId = "test-id"
        val oldBranchName = "Old Branch"
        val newBranchName = "New Branch Name"
        val branch = Branch(name = oldBranchName)
        val existingFranchise = Franchise(id = franchiseId, name = "Test Franchise", branches = mutableListOf(branch))
        val updatedBranch = branch.copy(name = newBranchName)
        val updatedFranchise = existingFranchise.copy(branches = mutableListOf(updatedBranch))
        
        whenever(franchiseRepository.findById(franchiseId)).thenReturn(Mono.just(existingFranchise))
        whenever(franchiseRepository.save(updatedFranchise)).thenReturn(Mono.just(updatedFranchise))

        // When & Then
        StepVerifier.create(franchiseService.updateBranchName(franchiseId, oldBranchName, newBranchName))
            .expectNext(updatedFranchise)
            .verifyComplete()
    }

    @Test
    @DisplayName("Should update product name successfully")
    fun `should update product name successfully`() {
        // Given
        val franchiseId = "test-id"
        val branchName = "Test Branch"
        val oldProductName = "Old Product"
        val newProductName = "New Product Name"
        val product = Product(name = oldProductName, stock = 10, price = 100.0)
        val branch = Branch(name = branchName, products = mutableListOf(product))
        val existingFranchise = Franchise(id = franchiseId, name = "Test Franchise", branches = mutableListOf(branch))
        val updatedProduct = product.copy(name = newProductName)
        val updatedBranch = branch.copy(products = mutableListOf(updatedProduct))
        val updatedFranchise = existingFranchise.copy(branches = mutableListOf(updatedBranch))
        
        whenever(franchiseRepository.findById(franchiseId)).thenReturn(Mono.just(existingFranchise))
        whenever(franchiseRepository.save(updatedFranchise)).thenReturn(Mono.just(updatedFranchise))

        // When & Then
        StepVerifier.create(franchiseService.updateProductName(franchiseId, branchName, oldProductName, newProductName))
            .expectNext(updatedFranchise)
            .verifyComplete()
    }

    @Test
    @DisplayName("Should update product price successfully")
    fun `should update product price successfully`() {
        // Given
        val franchiseId = "test-id"
        val branchName = "Test Branch"
        val productName = "Test Product"
        val newPrice = 150.0
        val product = Product(name = productName, stock = 10, price = 100.0)
        val branch = Branch(name = branchName, products = mutableListOf(product))
        val existingFranchise = Franchise(id = franchiseId, name = "Test Franchise", branches = mutableListOf(branch))
        val updatedProduct = product.copy(price = newPrice)
        val updatedBranch = branch.copy(products = mutableListOf(updatedProduct))
        val updatedFranchise = existingFranchise.copy(branches = mutableListOf(updatedBranch))
        
        whenever(franchiseRepository.findById(franchiseId)).thenReturn(Mono.just(existingFranchise))
        whenever(franchiseRepository.save(updatedFranchise)).thenReturn(Mono.just(updatedFranchise))

        // When & Then
        StepVerifier.create(franchiseService.updateProductPrice(franchiseId, branchName, productName, newPrice))
            .expectNext(updatedFranchise)
            .verifyComplete()
    }

    @Test
    @DisplayName("Should get product with most stock per branch successfully")
    fun `should get product with most stock per branch successfully`() {
        // Given
        val franchiseId = "test-id"
        val franchise = Franchise(
            id = franchiseId,
            name = "Test Franchise",
            address = "Test Address",
            branches = listOf(
                Branch(
                    name = "Branch 1",
                    products = listOf(
                        Product(name = "Product 1", stock = 15, price = 100.0),
                        Product(name = "Product 2", stock = 8, price = 150.0)
                    )
                ),
                Branch(
                    name = "Branch 2",
                    products = listOf(
                        Product(name = "Product 3", stock = 20, price = 200.0),
                        Product(name = "Product 4", stock = 5, price = 250.0)
                    )
                )
            )
        )
        
        whenever(franchiseRepository.findById(franchiseId)).thenReturn(Mono.just(franchise))

        // When & Then
        StepVerifier.create(franchiseService.getProductWithMostStockPerBranch(franchiseId))
            .expectNextMatches { result ->
                result.containsKey("Branch 1") && result["Branch 1"]?.name == "Product 1" && result["Branch 1"]?.stock == 15
            }
            .expectNextMatches { result ->
                result.containsKey("Branch 2") && result["Branch 2"]?.name == "Product 3" && result["Branch 2"]?.stock == 20
            }
            .verifyComplete()
    }

    @Test
    @DisplayName("Should handle error when getting product with most stock per branch fails")
    fun `should handle error when getting product with most stock per branch fails`() {
        // Given
        val franchiseId = "test-id"
        val error = RuntimeException("Database error")
        whenever(franchiseRepository.findById(franchiseId)).thenReturn(Mono.error(error))

        // When & Then
        StepVerifier.create(franchiseService.getProductWithMostStockPerBranch(franchiseId))
            .expectError(RuntimeException::class.java)
            .verify()
    }

    @Test
    @DisplayName("Should return empty flux when franchise has no branches")
    fun `should return empty flux when franchise has no branches`() {
        // Given
        val franchiseId = "test-id"
        val franchise = Franchise(
            id = franchiseId,
            name = "Test Franchise",
            address = "Test Address",
            branches = emptyList()
        )
        whenever(franchiseRepository.findById(franchiseId)).thenReturn(Mono.just(franchise))

        // When & Then
        StepVerifier.create(franchiseService.getProductWithMostStockPerBranch(franchiseId))
            .verifyComplete()
    }
}