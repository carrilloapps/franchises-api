# Changelog

## [v1.0.0] - 2024-12-19

The `v1.0.0` version of the "Franchises API" project includes the following features and improvements:

### Added
- Initial release of the Franchises API
- Complete CRUD operations for franchises, branches, and products
- Reactive programming with Spring WebFlux and MongoDB
- Comprehensive API documentation with Swagger/OpenAPI
- Docker support with multi-stage builds
- GitHub Actions workflow for automated Docker image publishing
- Plus features for dynamic updates (names, addresses, descriptions, prices)
- Inventory insights with most-stock product queries
- MongoDB reactive integration
- CORS configuration for cross-origin requests
- Comprehensive error handling and validation
- Clean Architecture implementation with domain-driven design
- Reactive repository pattern with MongoDB
- RESTful API design following best practices

### Testing Infrastructure
- **Comprehensive Unit Testing Suite**: Implemented complete test coverage for all layers
  - Domain layer tests (`FranchiseTest.kt`) for `Franchise`, `Branch`, and `Product` entities
  - Repository contract tests (`FranchiseRepositoryTest.kt`) with reactive testing using StepVerifier
  - Application layer tests (`FranchiseServiceTest.kt`) for business logic validation
  - Application configuration tests (`FranchiseApplicationTest.kt`) for Spring Boot context and CORS setup
- **Testing Dependencies**: Added Mockito support (`mockito-core`, `mockito-kotlin`, `mockito-inline`) for comprehensive mocking
- **Reactive Testing**: Integrated `reactor-test` for testing reactive streams and WebFlux components
- **JUnit 5 Integration**: Full JUnit 5 support with `kotlin-test-junit5` for modern testing practices

### CI/CD Pipeline
- **GitHub Actions Workflow**: Automated CI/CD pipeline (`.github/workflows/ci.yml`) with:
  - Automated testing on pushes to `master` and `develop` branches
  - Pull request validation for `master` and `develop` branches
  - Multi-job pipeline with test, build, and security scan stages
  - Java 17 and Gradle setup with dependency caching
  - Security vulnerability scanning with GitHub Security

### Code Coverage
- **JaCoCo Integration**: Configured JaCoCo plugin for code coverage analysis
  - Automatic test report generation in XML and HTML formats
  - Coverage verification with 70% minimum threshold
  - Integration with CI/CD pipeline for coverage reporting
  - Reports available in `build/reports/jacoco/test/` directory

### Test Results
- **39 Total Tests**: All tests passing successfully
  - Entity functionality and validation tests
  - Repository behavior and reactive operations tests
  - Application configuration and context tests
  - Edge cases and error handling tests
- **Build Success**: Zero compilation errors with comprehensive test coverage