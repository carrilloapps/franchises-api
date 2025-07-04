package app.carrillo.franchises.infrastructure

import app.carrillo.franchises.application.FranchiseService
import app.carrillo.franchises.domain.Branch
import app.carrillo.franchises.domain.Franchise
import app.carrillo.franchises.domain.Product

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
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
@Tag(name = "Franchise Management", description = "API for managing franchises, branches, and products")
class FranchiseController(private val franchiseService: FranchiseService) {

    private val logger = LoggerFactory.getLogger(FranchiseController::class.java)

    /**
     * Adds a new franchise.
     *
     * @param franchise The [Franchise] object to be added.
     * @return A [Mono] emitting the saved [Franchise] with HTTP status 201 (Created).
     */
    @Operation(
        summary = "Create a new franchise",
        description = "Creates a new franchise with the provided information. The franchise can include initial branches and products."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Franchise created successfully",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Franchise::class))]),
        ApiResponse(responseCode = "400", description = "Invalid franchise data provided"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addFranchise(
        @Parameter(description = "Franchise object to be created", required = true)
        @RequestBody franchise: Franchise
    ): Mono<Franchise> {
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
    @Operation(
        summary = "Get all franchises",
        description = "Retrieves a list of all franchises in the system with their branches and products."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "List of franchises retrieved successfully",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Franchise::class))]),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
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
    @Operation(
        summary = "Get franchise by ID",
        description = "Retrieves a specific franchise by its unique identifier, including all its branches and products."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Franchise found and retrieved successfully",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Franchise::class))]),
        ApiResponse(responseCode = "404", description = "Franchise not found"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    @GetMapping("/{id}")
    fun getFranchiseById(
        @Parameter(description = "Unique identifier of the franchise", required = true, example = "507f1f77bcf86cd799439011")
        @PathVariable id: String
    ): Mono<ResponseEntity<Franchise>> {
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
    @Operation(
        summary = "Add branch to franchise",
        description = "Adds a new branch to an existing franchise. The branch can include initial products."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Branch added successfully to franchise",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Franchise::class))]),
        ApiResponse(responseCode = "404", description = "Franchise not found"),
        ApiResponse(responseCode = "400", description = "Invalid branch data provided"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    @PostMapping("/{franchiseId}/branches")
    fun addBranchToFranchise(
        @Parameter(description = "Unique identifier of the franchise", required = true, example = "507f1f77bcf86cd799439011")
        @PathVariable franchiseId: String,
        @Parameter(description = "Branch object to be added to the franchise", required = true)
        @RequestBody branch: Branch
    ): Mono<ResponseEntity<Franchise>> {
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
    @Operation(
        summary = "Add product to branch",
        description = "Adds a new product to a specific branch within a franchise. The product will be added with the specified name, price, and stock."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Product added successfully to branch",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Franchise::class))]),
        ApiResponse(responseCode = "404", description = "Franchise or branch not found"),
        ApiResponse(responseCode = "400", description = "Invalid product data provided"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    @PostMapping("/{franchiseId}/branches/{branchName}/products")
    fun addProductToBranch(
        @Parameter(description = "Unique identifier of the franchise", required = true, example = "507f1f77bcf86cd799439011")
        @PathVariable franchiseId: String,
        @Parameter(description = "Name of the branch where the product will be added", required = true, example = "Downtown Branch")
        @PathVariable branchName: String,
        @Parameter(description = "Product object to be added to the branch", required = true)
        @RequestBody product: Product
    ): Mono<ResponseEntity<Franchise>> {
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
    @Operation(
        summary = "Delete product from branch",
        description = "Removes a specific product from a branch within a franchise. This action is irreversible."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Product deleted successfully from branch",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Franchise::class))]),
        ApiResponse(responseCode = "404", description = "Franchise, branch, or product not found"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    @DeleteMapping("/{franchiseId}/branches/{branchName}/products/{productName}")
    fun deleteProductFromBranch(
        @Parameter(description = "Unique identifier of the franchise", required = true, example = "507f1f77bcf86cd799439011")
        @PathVariable franchiseId: String,
        @Parameter(description = "Name of the branch containing the product", required = true, example = "Downtown Branch")
        @PathVariable branchName: String,
        @Parameter(description = "Name of the product to be deleted", required = true, example = "Laptop")
        @PathVariable productName: String
    ): Mono<ResponseEntity<Franchise>> {
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
    @Operation(
        summary = "Modify product stock",
        description = "Updates the stock quantity of a specific product within a branch. This is useful for inventory management and stock adjustments."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Product stock updated successfully",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Franchise::class))]),
        ApiResponse(responseCode = "404", description = "Franchise, branch, or product not found"),
        ApiResponse(responseCode = "400", description = "Invalid stock value provided"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    @PutMapping("/{franchiseId}/branches/{branchName}/products/{productName}/stock")
    fun modifyProductStock(
        @Parameter(description = "Unique identifier of the franchise", required = true, example = "507f1f77bcf86cd799439011")
        @PathVariable franchiseId: String,
        @Parameter(description = "Name of the branch containing the product", required = true, example = "Downtown Branch")
        @PathVariable branchName: String,
        @Parameter(description = "Name of the product to modify stock", required = true, example = "Laptop")
        @PathVariable productName: String,
        @Parameter(description = "New stock quantity for the product", required = true, example = "50")
        @RequestParam newStock: Int
    ): Mono<ResponseEntity<Franchise>> {
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
    @Operation(
        summary = "Get product with most stock per branch",
        description = "Retrieves the product with the highest stock quantity for each branch within a specific franchise. This is useful for inventory analysis and stock management."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Products with most stock per branch retrieved successfully"),
        ApiResponse(responseCode = "404", description = "Franchise not found or has no branches"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    @GetMapping("/{franchiseId}/products/most-stock-per-branch")
    fun getProductWithMostStockPerBranch(
        @Parameter(description = "Unique identifier of the franchise", required = true, example = "507f1f77bcf86cd799439011")
        @PathVariable franchiseId: String
    ): Flux<Map<String, Product>> {
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
    @Operation(
        summary = "Update franchise name",
        description = "Updates the name of an existing franchise. This change will be reflected across all related operations."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Franchise name updated successfully",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Franchise::class))]),
        ApiResponse(responseCode = "404", description = "Franchise not found"),
        ApiResponse(responseCode = "400", description = "Invalid name provided"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    @PutMapping("/{franchiseId}/name")
    fun updateFranchiseName(
        @Parameter(description = "Unique identifier of the franchise", required = true, example = "507f1f77bcf86cd799439011")
        @PathVariable franchiseId: String,
        @Parameter(description = "New name for the franchise", required = true, example = "Tech Solutions Franchise")
        @RequestParam newName: String
    ): Mono<ResponseEntity<Franchise>> {
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
    @Operation(
        summary = "Update franchise address",
        description = "Updates the physical address of an existing franchise. This is useful for location management and contact information."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Franchise address updated successfully",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Franchise::class))]),
        ApiResponse(responseCode = "404", description = "Franchise not found"),
        ApiResponse(responseCode = "400", description = "Invalid address provided"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    @PutMapping("/{franchiseId}/address")
    fun updateFranchiseAddress(
        @Parameter(description = "Unique identifier of the franchise", required = true, example = "507f1f77bcf86cd799439011")
        @PathVariable franchiseId: String,
        @Parameter(description = "New address for the franchise", required = true, example = "123 Main Street, Downtown")
        @RequestParam newAddress: String
    ): Mono<ResponseEntity<Franchise>> {
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
    @Operation(
        summary = "Update franchise description",
        description = "Updates the description of an existing franchise. This provides detailed information about the franchise's business model and services."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Franchise description updated successfully",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Franchise::class))]),
        ApiResponse(responseCode = "404", description = "Franchise not found"),
        ApiResponse(responseCode = "400", description = "Invalid description provided"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    @PutMapping("/{franchiseId}/description")
    fun updateFranchiseDescription(
        @Parameter(description = "Unique identifier of the franchise", required = true, example = "507f1f77bcf86cd799439011")
        @PathVariable franchiseId: String,
        @Parameter(description = "New description for the franchise", required = true, example = "A technology-focused franchise specializing in electronic devices and accessories")
        @RequestParam newDescription: String
    ): Mono<ResponseEntity<Franchise>> {
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
    @Operation(
        summary = "Update branch name",
        description = "Updates the name of a specific branch within a franchise. This change will be reflected in all branch-related operations."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Branch name updated successfully",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Franchise::class))]),
        ApiResponse(responseCode = "404", description = "Franchise or branch not found"),
        ApiResponse(responseCode = "400", description = "Invalid branch name provided"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    @PutMapping("/{franchiseId}/branches/{oldBranchName}/name")
    fun updateBranchName(
        @Parameter(description = "Unique identifier of the franchise", required = true, example = "507f1f77bcf86cd799439011")
        @PathVariable franchiseId: String,
        @Parameter(description = "Current name of the branch to be updated", required = true, example = "Downtown Branch")
        @PathVariable oldBranchName: String,
        @Parameter(description = "New name for the branch", required = true, example = "Central Downtown Branch")
        @RequestParam newBranchName: String
    ): Mono<ResponseEntity<Franchise>> {
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
    @Operation(
        summary = "Update product name",
        description = "Updates the name of a specific product within a branch. This is useful for product rebranding or name corrections."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Product name updated successfully",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Franchise::class))]),
        ApiResponse(responseCode = "404", description = "Franchise, branch, or product not found"),
        ApiResponse(responseCode = "400", description = "Invalid product name provided"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    @PutMapping("/{franchiseId}/branches/{branchName}/products/{oldProductName}/name")
    fun updateProductName(
        @Parameter(description = "Unique identifier of the franchise", required = true, example = "507f1f77bcf86cd799439011")
        @PathVariable franchiseId: String,
        @Parameter(description = "Name of the branch containing the product", required = true, example = "Downtown Branch")
        @PathVariable branchName: String,
        @Parameter(description = "Current name of the product to be updated", required = true, example = "Laptop")
        @PathVariable oldProductName: String,
        @Parameter(description = "New name for the product", required = true, example = "Gaming Laptop")
        @RequestParam newProductName: String
    ): Mono<ResponseEntity<Franchise>> {
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
    @Operation(
        summary = "Update product price",
        description = "Updates the price of a specific product within a branch. This is essential for pricing management and promotional activities."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Product price updated successfully",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Franchise::class))]),
        ApiResponse(responseCode = "404", description = "Franchise, branch, or product not found"),
        ApiResponse(responseCode = "400", description = "Invalid price value provided"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    @PutMapping("/{franchiseId}/branches/{branchName}/products/{productName}/price")
    fun updateProductPrice(
        @Parameter(description = "Unique identifier of the franchise", required = true, example = "507f1f77bcf86cd799439011")
        @PathVariable franchiseId: String,
        @Parameter(description = "Name of the branch containing the product", required = true, example = "Downtown Branch")
        @PathVariable branchName: String,
        @Parameter(description = "Name of the product to update price", required = true, example = "Laptop")
        @PathVariable productName: String,
        @Parameter(description = "New price for the product", required = true, example = "999.99")
        @RequestParam newPrice: Double
    ): Mono<ResponseEntity<Franchise>> {
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