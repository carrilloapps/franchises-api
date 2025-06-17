package app.carrillo.franchises.infrastructure

import app.carrillo.franchises.application.FranchiseService
import app.carrillo.franchises.domain.Branch
import app.carrillo.franchises.domain.Franchise
import app.carrillo.franchises.domain.Product

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import org.slf4j.LoggerFactory

/**
 * REST Controller for managing [Franchise] entities.
 * Exposes endpoints for adding, updating, and retrieving franchise data.
 *
 * @param franchiseService The service for handling franchise business logic.
 */
@RestController
@RequestMapping("/franchises")
class FranchiseController(private val franchiseService: FranchiseService) {

    private val logger = LoggerFactory.getLogger(FranchiseController::class.java)

    /**
     * Adds a new franchise.
     *
     * @param franchise The [Franchise] object to be added.
     * @return A [Mono] emitting the saved [Franchise] with HTTP status 201 (Created).
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addFranchise(@RequestBody franchise: Franchise): Mono<Franchise> {
        logger.info("Received request to add new franchise: {}", franchise.name)
        return franchiseService.addFranchise(franchise)
            .doOnSuccess { savedFranchise ->
                logger.info("Successfully added franchise with ID: {}", savedFranchise.id)
            }
            .doOnError { error ->
                logger.error("Failed to add franchise {}: {}", franchise.name, error.message)
            }
    }

    /**
     * Retrieves all franchises.
     *
     * @return A [Flux] emitting all [Franchise] objects.
     */
    @GetMapping
    fun getAllFranchises(): Flux<Franchise> {
        logger.info("Received request to get all franchises.")
        return franchiseService.getAllFranchises()
            .doOnComplete { logger.info("Successfully retrieved all franchises.") }
            .doOnError { error ->
                logger.error("Failed to retrieve all franchises: {}", error.message)
            }
    }

    /**
     * Retrieves a franchise by its ID.
     *
     * @param id The ID of the franchise to retrieve.
     * @return A [Mono] emitting the [Franchise] object if found, or [Mono.empty] if not found.
     */
    @GetMapping("/{id}")
    fun getFranchiseById(@PathVariable id: String): Mono<ResponseEntity<Franchise>> {
        logger.info("Received request to get franchise by ID: {}", id)
        return franchiseService.getFranchiseById(id)
            .map { franchise ->
                logger.info("Successfully retrieved franchise with ID: {}", id)
                ResponseEntity.ok(franchise)
            }
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .doOnError { error ->
                logger.error("Failed to retrieve franchise with ID {}: {}", id, error.message)
            }
    }

    /**
     * Adds a new branch to an existing franchise.
     *
     * @param franchiseId The ID of the franchise to add the branch to.
     * @param branch The [Branch] object to be added.
     * @return A [Mono] emitting the updated [Franchise] with HTTP status 200 (OK) if found, or 404 (Not Found) if the franchise is not found.
     */
    @PostMapping("/{franchiseId}/branches")
    fun addBranchToFranchise(@PathVariable franchiseId: String, @RequestBody branch: Branch): Mono<ResponseEntity<Franchise>> {
        logger.info("Received request to add branch {} to franchise with ID: {}", branch.name, franchiseId)
        return franchiseService.addBranchToFranchise(franchiseId, branch)
            .map { franchise ->
                logger.info("Successfully added branch to franchise with ID: {}", franchiseId)
                ResponseEntity.ok(franchise)
            }
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .doOnError { error ->
                logger.error("Failed to add branch to franchise {}: {}", franchiseId, error.message)
            }
    }

    /**
     * Adds a new product to a branch within a franchise.
     *
     * @param franchiseId The ID of the franchise.
     * @param branchName The name of the branch.
     * @param product The [Product] object to be added.
     * @return A [Mono] emitting the updated [Franchise] with HTTP status 200 (OK) if found, or 404 (Not Found) if the franchise or branch is not found.
     */
    @PostMapping("/{franchiseId}/branches/{branchName}/products")
    fun addProductToBranch(@PathVariable franchiseId: String, @PathVariable branchName: String, @RequestBody product: Product): Mono<ResponseEntity<Franchise>> {
        logger.info("Received request to add product {} to branch {} in franchise with ID: {}", product.name, branchName, franchiseId)
        return franchiseService.addProductToBranch(franchiseId, branchName, product)
            .map { franchise ->
                logger.info("Successfully added product to branch {} in franchise with ID: {}", branchName, franchiseId)
                ResponseEntity.ok(franchise)
            }
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .doOnError { error ->
                logger.error("Failed to add product to branch {} in franchise {}: {}", branchName, franchiseId, error.message)
            }
    }

    /**
     * Deletes a product from a branch within a franchise.
     *
     * @param franchiseId The ID of the franchise.
     * @param branchName The name of the branch.
     * @param productName The name of the product to be deleted.
     * @return A [Mono] emitting the updated [Franchise] with HTTP status 200 (OK) if found, or 404 (Not Found) if the franchise, branch, or product is not found.
     */
    @DeleteMapping("/{franchiseId}/branches/{branchName}/products/{productName}")
    fun deleteProductFromBranch(@PathVariable franchiseId: String, @PathVariable branchName: String, @PathVariable productName: String): Mono<ResponseEntity<Franchise>> {
        logger.info("Received request to delete product {} from branch {} in franchise with ID: {}", productName, branchName, franchiseId)
        return franchiseService.deleteProductFromBranch(franchiseId, branchName, productName)
            .map { franchise ->
                logger.info("Successfully deleted product {} from branch {} in franchise with ID: {}", productName, branchName, franchiseId)
                ResponseEntity.ok(franchise)
            }
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .doOnError { error ->
                logger.error("Failed to delete product {} from branch {} in franchise {}: {}", productName, branchName, franchiseId, error.message)
            }
    }

    /**
     * Modifies the stock of a product within a branch of a franchise.
     *
     * @param franchiseId The ID of the franchise.
     * @param branchName The name of the branch.
     * @param productName The name of the product to modify.
     * @param newStock The new stock quantity for the product.
     * @return A [Mono] emitting the updated [Franchise] with HTTP status 200 (OK) if found, or 404 (Not Found) if the franchise, branch, or product is not found.
     */
    @PutMapping("/{franchiseId}/branches/{branchName}/products/{productName}/stock")
    fun modifyProductStock(@PathVariable franchiseId: String, @PathVariable branchName: String, @PathVariable productName: String, @RequestParam newStock: Int): Mono<ResponseEntity<Franchise>> {
        logger.info("Received request to modify stock of product {} in branch {} in franchise with ID: {} to new stock: {}", productName, branchName, franchiseId, newStock)
        return franchiseService.modifyProductStock(franchiseId, branchName, productName, newStock)
            .map { franchise ->
                logger.info("Successfully modified stock of product {} in branch {} in franchise with ID: {}", productName, branchName, franchiseId)
                ResponseEntity.ok(franchise)
            }
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .doOnError { error ->
                logger.error("Failed to modify stock of product {} in branch {} in franchise {}: {}", productName, branchName, franchiseId, error.message)
            }
    }

    /**
     * Finds the product with the most stock for each branch within a specific franchise.
     *
     * @param franchiseId The ID of the franchise.
     * @return A [Flux] emitting a map where the key is the branch name and the value is the product with the most stock.
     */
    @GetMapping("/{franchiseId}/products/most-stock-per-branch")
    fun getProductWithMostStockPerBranch(@PathVariable franchiseId: String): Flux<Map<String, Product>> {
        logger.info("Received request to get product with most stock per branch for franchise with ID: {}", franchiseId)
        return franchiseService.getProductWithMostStockPerBranch(franchiseId)
            .doOnComplete { logger.info("Successfully retrieved product with most stock per branch for franchise with ID: {}", franchiseId) }
            .doOnError { error ->
                logger.error("Failed to get product with most stock per branch for franchise {}: {}", franchiseId, error.message)
            }
    }

    /**
     * Updates the name of a franchise.
     *
     * @param franchiseId The ID of the franchise to update.
     * @param newName The new name for the franchise.
     * @return A [Mono] emitting the updated [Franchise] with HTTP status 200 (OK) if found, or 404 (Not Found) if the franchise is not found.
     */
    @PutMapping("/{franchiseId}/name")
    fun updateFranchiseName(@PathVariable franchiseId: String, @RequestParam newName: String): Mono<ResponseEntity<Franchise>> {
        logger.info("Received request to update name of franchise with ID: {} to {}", franchiseId, newName)
        return franchiseService.updateFranchiseName(franchiseId, newName)
            .map { franchise ->
                logger.info("Successfully updated name of franchise with ID: {}", franchiseId)
                ResponseEntity.ok(franchise)
            }
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .doOnError { error ->
                logger.error("Failed to update name of franchise with ID {}: {}", franchiseId, error.message)
            }
    }

    /**
     * Updates the address of a franchise.
     * @param franchiseId The ID of the franchise to update.
     * @param newAddress The new address for the franchise.
     * @return A [Mono] emitting the updated [Franchise] with HTTP status 200 (OK) if found, or 404 (Not Found) if the franchise is not found.
     */
    @PutMapping("/{franchiseId}/address")
    fun updateFranchiseAddress(@PathVariable franchiseId: String, @RequestParam newAddress: String): Mono<ResponseEntity<Franchise>> {
        logger.info("Received request to update address of franchise with ID: {} to {}", franchiseId, newAddress)
        return franchiseService.updateFranchiseAddress(franchiseId, newAddress)
            .map { franchise ->
                logger.info("Successfully updated address of franchise with ID: {}", franchiseId)
                ResponseEntity.ok(franchise)
            }
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .doOnError { error ->
                logger.error("Failed to update address of franchise with ID {}: {}", franchiseId, error.message)
            }
    }

    /**
     * Updates the description of a franchise.
     * @param franchiseId The ID of the franchise to update.
     * @param newDescription The new description for the franchise.
     * @return A [Mono] emitting the updated [Franchise] with HTTP status 200 (OK) if found, or 404 (Not Found) if the franchise is not found.
     */
    @PutMapping("/{franchiseId}/description")
    fun updateFranchiseDescription(@PathVariable franchiseId: String, @RequestParam newDescription: String): Mono<ResponseEntity<Franchise>> {
        logger.info("Received request to update description of franchise with ID: {} to {}", franchiseId, newDescription)
        return franchiseService.updateFranchiseDescription(franchiseId, newDescription)
            .map { franchise ->
                logger.info("Successfully updated description of franchise with ID: {}", franchiseId)
                ResponseEntity.ok(franchise)
            }
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .doOnError { error ->
                logger.error("Failed to update description of franchise with ID {}: {}", franchiseId, error.message)
            }
    }

    /**
     * Updates the name of a branch within a franchise.
     *
     * @param franchiseId The ID of the franchise.
     * @param oldBranchName The current name of the branch.
     * @param newBranchName The new name for the branch.
     * @return A [Mono] emitting the updated [Franchise] with HTTP status 200 (OK) if found, or 404 (Not Found) if the franchise or branch is not found.
     */
    @PutMapping("/{franchiseId}/branches/{oldBranchName}/name")
    fun updateBranchName(@PathVariable franchiseId: String, @PathVariable oldBranchName: String, @RequestParam newBranchName: String): Mono<ResponseEntity<Franchise>> {
        logger.info("Received request to update name of branch {} in franchise with ID: {} to {}", oldBranchName, franchiseId, newBranchName)
        return franchiseService.updateBranchName(franchiseId, oldBranchName, newBranchName)
            .map { franchise ->
                logger.info("Successfully updated name of branch {} in franchise with ID: {}", oldBranchName, franchiseId)
                ResponseEntity.ok(franchise)
            }
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .doOnError { error ->
                logger.error("Failed to update name of branch {} in franchise {}: {}", oldBranchName, franchiseId, error.message)
            }
    }

    /**
     * Updates the name of a product within a branch of a franchise.
     *
     * @param franchiseId The ID of the franchise.
     * @param branchName The name of the branch where the product is located.
     * @param oldProductName The current name of the product.
     * @param newProductName The new name for the product.
     * @return A [Mono] emitting the updated [Franchise] with HTTP status 200 (OK) if found, or 404 (Not Found) if the franchise, branch, or product is not found.
     */
    @PutMapping("/{franchiseId}/branches/{branchName}/products/{oldProductName}/name")
    fun updateProductName(@PathVariable franchiseId: String, @PathVariable branchName: String, @PathVariable oldProductName: String, @RequestParam newProductName: String): Mono<ResponseEntity<Franchise>> {
        logger.info("Received request to update name of product {} in branch {} in franchise with ID: {} to {}", oldProductName, branchName, franchiseId, newProductName)
        return franchiseService.updateProductName(franchiseId, branchName, oldProductName, newProductName)
            .map { franchise ->
                logger.info("Successfully updated name of product {} in branch {} in franchise with ID: {}", oldProductName, branchName, franchiseId)
                ResponseEntity.ok(franchise)
            }
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .doOnError { error ->
                logger.error("Failed to update name of product {} in branch {} in franchise {}: {}", oldProductName, branchName, franchiseId, error.message)
            }
    }

    /**
     * Updates the price of a product within a branch of a franchise.
     *
     * @param franchiseId The ID of the franchise.
     * @param branchName The name of the branch where the product is located.
     * @param productName The name of the product to update.
     * @param newPrice The new price for the product.
     * @return A [Mono] emitting the updated [Franchise] with HTTP status 200 (OK) if found, or 404 (Not Found) if the franchise, branch, or product is not found.
     */
    @PutMapping("/{franchiseId}/branches/{branchName}/products/{productName}/price")
    fun updateProductPrice(@PathVariable franchiseId: String, @PathVariable branchName: String, @PathVariable productName: String, @RequestParam newPrice: Double): Mono<ResponseEntity<Franchise>> {
        logger.info("Received request to update price of product {} in branch {} in franchise with ID: {} to {}", productName, branchName, franchiseId, newPrice)
        return franchiseService.updateProductPrice(franchiseId, branchName, productName, newPrice)
            .map { franchise ->
                logger.info("Successfully updated price of product {} in branch {} in franchise with ID: {}", productName, branchName, franchiseId)
                ResponseEntity.ok(franchise)
            }
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .doOnError { error ->
                logger.error("Failed to update price of product {} in branch {} in franchise {}: {}", productName, branchName, franchiseId, error.message)
            }
    }
}