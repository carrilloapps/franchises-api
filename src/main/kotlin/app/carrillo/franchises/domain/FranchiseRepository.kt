package app.carrillo.franchises.domain

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Interface for the Franchise Repository.
 * Defines the contract for data access operations related to Franchise entities.
 * This interface is part of the domain layer, ensuring the core business logic
 * is independent of the persistence implementation details.
 */
interface FranchiseRepository {
    /**
     * Saves a given franchise.
     * @param franchise The franchise to save.
     * @return A Mono emitting the saved franchise.
     */
    fun save(franchise: Franchise): Mono<Franchise>

    /**
     * Finds a franchise by its ID.
     * @param id The ID of the franchise to find.
     * @return A Mono emitting the found franchise, or Mono.empty() if not found.
     */
    fun findById(id: String): Mono<Franchise>

    /**
     * Finds all franchises.
     * @return A Flux emitting all franchises.
     */
    fun findAll(): Flux<Franchise>

    /**
     * Deletes a given franchise.
     * @param franchise The franchise to delete.
     * @return A Mono<Void> indicating completion.
     */
    fun delete(franchise: Franchise): Mono<Void>

    /**
     * Deletes a franchise by its ID.
     * @param id The ID of the franchise to delete.
     * @return A Mono<Void> indicating completion.
     */
    fun deleteById(id: String): Mono<Void>
}