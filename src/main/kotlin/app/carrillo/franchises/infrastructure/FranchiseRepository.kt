package app.carrillo.franchises.infrastructure

import app.carrillo.franchises.domain.Franchise
import reactor.core.publisher.Flux

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import org.slf4j.LoggerFactory

/**
 * Repository interface for managing [Franchise] entities.
 * Extends [ReactiveMongoRepository] to provide reactive data access operations for MongoDB.
 */
@Repository
interface FranchiseRepository : ReactiveMongoRepository<Franchise, String> {


}