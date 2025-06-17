package app.carrillo.franchises.application

import app.carrillo.franchises.domain.Branch
import app.carrillo.franchises.domain.Franchise
import app.carrillo.franchises.domain.Product
import app.carrillo.franchises.infrastructure.FranchiseRepository

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import org.slf4j.LoggerFactory

/**
 * Service class for managing [Franchise] entities.
 * Provides methods for adding, updating, and retrieving franchise data.
 *
 * @param franchiseRepository The repository for accessing franchise data.
 */
@Service
class FranchiseService(private val franchiseRepository: FranchiseRepository) {

    private val logger = LoggerFactory.getLogger(FranchiseService::class.java)

    /**
     * Adds a new franchise.
     *
     * @param franchise The [Franchise] object to be added.
     * @return A [Mono] emitting the saved [Franchise].
     */
    fun addFranchise(franchise: Franchise): Mono<Franchise> {
        logger.info("Attempting to add new franchise: {}", franchise.name)
        return franchiseRepository.save(franchise)
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
    fun getAllFranchises(): Flux<Franchise> {
        logger.info("Attempting to retrieve all franchises.")
        return franchiseRepository.findAll()
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
    fun getFranchiseById(id: String): Mono<Franchise> {
        logger.info("Attempting to retrieve franchise by ID: {}", id)
        return franchiseRepository.findById(id)
            .doOnSuccess { foundFranchise ->
                if (foundFranchise != null) {
                    logger.info("Successfully retrieved franchise with ID: {}", id)
                } else {
                    logger.warn("Franchise with ID {} not found.", id)
                }
            }
            .doOnError { error ->
                logger.error("Failed to retrieve franchise with ID {}: {}", id, error.message)
            }
    }

    /**
     * Adds a new branch to an existing franchise.
     *
     * @param franchiseId The ID of the franchise to add the branch to.
     * @param branch The [Branch] object to be added.
     * @return A [Mono] emitting the updated [Franchise], or [Mono.empty] if the franchise is not found.
     */
    fun addBranchToFranchise(franchiseId: String, branch: Branch): Mono<Franchise> {
        logger.info("Attempting to add branch {} to franchise with ID: {}", branch.name, franchiseId)
        return franchiseRepository.findById(franchiseId)
            .flatMap { franchise ->
                val updatedBranches = franchise.branches.toMutableList()
                updatedBranches.add(branch)
                logger.debug("Updating franchise {} with new branch {}", franchise.name, branch.name)
                franchiseRepository.save(franchise.copy(branches = updatedBranches))
            }
            .doOnSuccess { updatedFranchise ->
                logger.info("Successfully added branch to franchise with ID: {}", updatedFranchise.id)
            }
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
     * @return A [Mono] emitting the updated [Franchise], or [Mono.empty] if the franchise or branch is not found.
     */
    fun addProductToBranch(franchiseId: String, branchName: String, product: Product): Mono<Franchise> {
        logger.info("Attempting to add product {} to branch {} in franchise with ID: {}", product.name, branchName, franchiseId)
        return franchiseRepository.findById(franchiseId)
            .flatMap { franchise ->
                val updatedBranches = franchise.branches.map { branch ->
                    if (branch.name == branchName) {
                        val updatedProducts = branch.products.toMutableList()
                        updatedProducts.add(product)
                        logger.debug("Updating branch {} with new product {}", branchName, product.name)
                        branch.copy(products = updatedProducts)
                    } else {
                        branch
                    }
                }
                franchiseRepository.save(franchise.copy(branches = updatedBranches))
            }
            .doOnSuccess { updatedFranchise ->
                logger.info("Successfully added product to branch {} in franchise with ID: {}", branchName, updatedFranchise.id)
            }
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
     * @return A [Mono] emitting the updated [Franchise], or [Mono.empty] if the franchise, branch, or product is not found.
     */
    fun deleteProductFromBranch(franchiseId: String, branchName: String, productName: String): Mono<Franchise> {
        logger.info("Attempting to delete product {} from branch {} in franchise with ID: {}", productName, branchName, franchiseId)
        return franchiseRepository.findById(franchiseId)
            .flatMap { franchise ->
                val updatedBranches = franchise.branches.map { branch ->
                    if (branch.name == branchName) {
                        val updatedProducts = branch.products.filter { it.name != productName }
                        logger.debug("Deleting product {} from branch {}", productName, branchName)
                        branch.copy(products = updatedProducts)
                    } else {
                        branch
                    }
                }
                franchiseRepository.save(franchise.copy(branches = updatedBranches))
            }
            .doOnSuccess { updatedFranchise ->
                logger.info("Successfully deleted product {} from branch {} in franchise with ID: {}", productName, branchName, updatedFranchise.id)
            }
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
     * @return A [Mono] emitting the updated [Franchise], or [Mono.empty] if the franchise, branch, or product is not found.
     */
    fun modifyProductStock(franchiseId: String, branchName: String, productName: String, newStock: Int): Mono<Franchise> {
        logger.info("Attempting to modify stock of product {} in branch {} in franchise with ID: {} to new stock: {}", productName, branchName, franchiseId, newStock)
        return franchiseRepository.findById(franchiseId)
            .flatMap { franchise ->
                val updatedBranches = franchise.branches.map { branch ->
                    if (branch.name == branchName) {
                        val updatedProducts = branch.products.map { product ->
                            if (product.name == productName) {
                                logger.debug("Modifying stock of product {} from {} to {}", productName, product.stock, newStock)
                                product.copy(stock = newStock)
                            } else {
                                product
                            }
                        }
                        branch.copy(products = updatedProducts)
                    } else {
                        branch
                    }
                }
                franchiseRepository.save(franchise.copy(branches = updatedBranches))
            }
            .doOnSuccess { updatedFranchise ->
                logger.info("Successfully modified stock of product {} in branch {} in franchise with ID: {}", productName, branchName, updatedFranchise.id)
            }
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
    fun getProductWithMostStockPerBranch(franchiseId: String): Flux<Map<String, Product>> {
        logger.info("Attempting to get product with most stock per branch for franchise with ID: {}", franchiseId)
        return franchiseRepository.findById(franchiseId)
            .flatMapMany { franchise ->
                Flux.fromIterable(franchise.branches)
                    .map { branch ->
                        val maxStockProduct = branch.products.maxByOrNull { it.stock }
                        if (maxStockProduct != null) {
                            logger.debug("Found product with most stock for branch {}: {}", branch.name, maxStockProduct.name)
                            mapOf(branch.name to maxStockProduct)
                        } else {
                            logger.debug("No products found for branch {}", branch.name)
                            emptyMap()
                        }
                    }
                    .filter { it.isNotEmpty() }
            }
            .doOnComplete { logger.info("Completed getting product with most stock per branch for franchise with ID: {}", franchiseId) }
            .doOnError { error ->
                logger.error("Failed to get product with most stock per branch for franchise {}: {}", franchiseId, error.message)
            }
    }

    /**
     * Updates the name of a franchise.
     *
     * @param franchiseId The ID of the franchise to update.
     * @param newName The new name for the franchise.
     * @return A [Mono] emitting the updated [Franchise], or [Mono.empty] if the franchise is not found.
     */
    fun updateFranchiseName(franchiseId: String, newName: String): Mono<Franchise> {
        logger.info("Attempting to update name of franchise with ID: {} to {}", franchiseId, newName)
        return franchiseRepository.findById(franchiseId)
            .flatMap { franchise ->
                logger.debug("Updating franchise name from {} to {}", franchise.name, newName)
                franchiseRepository.save(franchise.copy(name = newName))
            }
            .doOnSuccess { updatedFranchise ->
                logger.info("Successfully updated name of franchise with ID: {} to {}", updatedFranchise.id, updatedFranchise.name)
            }
            .doOnError { error ->
                logger.error("Failed to update name of franchise {}: {}", franchiseId, error.message)
            }
    }

    /**
     * Updates the address of a franchise.
     * @param franchiseId The ID of the franchise to update.
     * @param newAddress The new address for the franchise.
     * @return A [Mono] emitting the updated [Franchise], or [Mono.empty] if the franchise is not found.
     */
    fun updateFranchiseAddress(franchiseId: String, newAddress: String): Mono<Franchise> {
        logger.info("Attempting to update address of franchise with ID: {} to {}", franchiseId, newAddress)
        return franchiseRepository.findById(franchiseId)
            .flatMap { franchise ->
                logger.debug("Updating franchise address from {} to {}", franchise.address, newAddress)
                franchiseRepository.save(franchise.copy(address = newAddress))
            }
            .doOnSuccess { updatedFranchise ->
                logger.info("Successfully updated address of franchise with ID: {} to {}", updatedFranchise.id, updatedFranchise.address)
            }
            .doOnError { error ->
                logger.error("Failed to update address of franchise {}: {}", franchiseId, error.message)
            }
    }

    /**
     * Updates the description of a franchise.
     * @param franchiseId The ID of the franchise to update.
     * @param newDescription The new description for the franchise.
     * @return A [Mono] emitting the updated [Franchise], or [Mono.empty] if the franchise is not found.
     */
    fun updateFranchiseDescription(franchiseId: String, newDescription: String): Mono<Franchise> {
        logger.info("Attempting to update description of franchise with ID: {} to {}", franchiseId, newDescription)
        return franchiseRepository.findById(franchiseId)
            .flatMap { franchise ->
                logger.debug("Updating franchise description from {} to {}", franchise.description, newDescription)
                franchiseRepository.save(franchise.copy(description = newDescription))
            }
            .doOnSuccess { updatedFranchise ->
                logger.info("Successfully updated description of franchise with ID: {} to {}", updatedFranchise.id, updatedFranchise.description)
            }
            .doOnError { error ->
                logger.error("Failed to update description of franchise {}: {}", franchiseId, error.message)
            }
    }

    /**
     * Updates the name of a branch within a franchise.
     *
     * @param franchiseId The ID of the franchise.
     * @param oldBranchName The current name of the branch.
     * @param newBranchName The new name for the branch.
     * @return A [Mono] emitting the updated [Franchise], or [Mono.empty] if the franchise or branch is not found.
     */
    fun updateBranchName(franchiseId: String, oldBranchName: String, newBranchName: String): Mono<Franchise> {
        logger.info("Attempting to update name of branch {} in franchise with ID: {} to {}", oldBranchName, franchiseId, newBranchName)
        return franchiseRepository.findById(franchiseId)
            .flatMap { franchise ->
                val updatedBranches = franchise.branches.map { branch ->
                    if (branch.name == oldBranchName) {
                        logger.debug("Updating branch name from {} to {}", oldBranchName, newBranchName)
                        branch.copy(name = newBranchName)
                    } else {
                        branch
                    }
                }
                franchiseRepository.save(franchise.copy(branches = updatedBranches))
            }
            .doOnSuccess { updatedFranchise ->
                logger.info("Successfully updated name of branch {} in franchise with ID: {}", newBranchName, updatedFranchise.id)
            }
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
     * @return A [Mono] emitting the updated [Franchise], or [Mono.empty] if the franchise, branch, or product is not found.
     */
    fun updateProductName(franchiseId: String, branchName: String, oldProductName: String, newProductName: String): Mono<Franchise> {
        logger.info("Attempting to update name of product {} in branch {} in franchise with ID: {} to {}", oldProductName, branchName, franchiseId, newProductName)
        return franchiseRepository.findById(franchiseId)
            .flatMap { franchise ->
                val updatedBranches = franchise.branches.map { branch ->
                    if (branch.name == branchName) {
                        val updatedProducts = branch.products.map { product ->
                            if (product.name == oldProductName) {
                                logger.debug("Updating product name from {} to {}", oldProductName, newProductName)
                                product.copy(name = newProductName)
                            } else {
                                product
                            }
                        }
                        branch.copy(products = updatedProducts)
                    } else {
                        branch
                    }
                }
                franchiseRepository.save(franchise.copy(branches = updatedBranches))
            }
            .doOnSuccess { updatedFranchise ->
                logger.info("Successfully updated name of product {} in branch {} in franchise with ID: {}", newProductName, branchName, updatedFranchise.id)
            }
            .doOnError { error ->
                logger.error("Failed to update name of product {} in branch {} in franchise {}: {}", oldProductName, branchName, franchiseId, error.message)
            }
    }

    /**
     * Updates the price of a product within a branch of a franchise.
     * @param franchiseId The ID of the franchise.
     * @param branchName The name of the branch where the product is located.
     * @param productName The name of the product to update.
     * @param newPrice The new price for the product.
     * @return A [Mono] emitting the updated [Franchise], or [Mono.empty] if the franchise, branch, or product is not found.
     */
    fun updateProductPrice(franchiseId: String, branchName: String, productName: String, newPrice: Double): Mono<Franchise> {
        logger.info("Attempting to update price of product {} in branch {} in franchise with ID: {} to {}", productName, branchName, franchiseId, newPrice)
        return franchiseRepository.findById(franchiseId)
            .flatMap { franchise ->
                val updatedBranches = franchise.branches.map { branch ->
                    if (branch.name == branchName) {
                        val updatedProducts = branch.products.map { product ->
                            if (product.name == productName) {
                                logger.debug("Updating product price from {} to {}", product.price, newPrice)
                                product.copy(price = newPrice)
                            } else {
                                product
                            }
                        }
                        branch.copy(products = updatedProducts)
                    } else {
                        branch
                    }
                }
                franchiseRepository.save(franchise.copy(branches = updatedBranches))
            }
            .doOnSuccess { updatedFranchise ->
                logger.info("Successfully updated price of product {} in branch {} in franchise with ID: {}", productName, branchName, updatedFranchise.id)
            }
            .doOnError { error ->
                logger.error("Failed to update price of product {} in branch {} in franchise {}: {}", productName, branchName, franchiseId, error.message)
            }
    }
}