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

### API Documentation & Swagger Enhancements
- **Enhanced Swagger Configuration**: Comprehensive OpenAPI 3.0 documentation setup
  - Custom API information with title, description, version, and contact details
  - External documentation links and license information
  - Reusable components for error responses and pagination
  - Organized API tags for better endpoint categorization
  - Server configuration for different environments
- **Controller Documentation**: Complete Swagger annotations for all endpoints
  - `FranchiseController.kt`: Detailed operation descriptions, parameter documentation, and response schemas
  - `HomeController.kt`: Welcome endpoint documentation with proper response examples
  - Comprehensive error response documentation (400, 404, 500 status codes)
  - Request/response body examples and schema definitions
- **Domain Model Documentation**: Swagger schema annotations for all data models
  - `Franchise`, `Branch`, and `Product` entities with field descriptions
  - Property examples and validation constraints documentation
  - Clear data type definitions and required field specifications

### Testing Infrastructure
- **Comprehensive Unit Testing Suite**: Implemented complete test coverage for all layers
  - Domain layer tests (`FranchiseTest.kt`) for `Franchise`, `Branch`, and `Product` entities
  - Repository contract tests (`FranchiseRepositoryTest.kt`) with reactive testing using StepVerifier
  - Application layer tests (`FranchiseServiceTest.kt`) for business logic validation
  - Application configuration tests (`FranchiseApplicationTest.kt`) for Spring Boot context and CORS setup
  - Infrastructure layer tests (`HomeControllerTest.kt`) for endpoint validation
- **Testing Dependencies**: Added Mockito support (`mockito-core`, `mockito-kotlin`, `mockito-inline`) for comprehensive mocking
- **Reactive Testing**: Integrated `reactor-test` for testing reactive streams and WebFlux components
- **JUnit 5 Integration**: Full JUnit 5 support with `kotlin-test-junit5` for modern testing practices

### CI/CD Pipeline Optimization
- **Separated Docker Workflows**: Optimized CI/CD pipeline structure
  - `ci.yml`: Focused on testing, building, and security scanning
  - `docker-image.yml`: Dedicated Docker image building and publishing
  - Eliminated duplicate Docker build processes for improved performance
  - Centralized Docker operations for better maintainability
- **GitHub Actions Workflow**: Automated CI/CD pipeline with:
  - Automated testing on pushes to `master` and `develop` branches
  - Pull request validation for `master` and `develop` branches
  - Multi-job pipeline with test, build, and security scan stages
  - Java 17 and Gradle setup with dependency caching
  - Security vulnerability scanning with GitHub Security

### Code Coverage & Quality
- **JaCoCo Integration**: Configured JaCoCo plugin for comprehensive code coverage analysis
  - Automatic test report generation in XML and HTML formats
  - Coverage verification with 80% minimum threshold (increased from 70%)
  - Integration with CI/CD pipeline for coverage reporting
  - Reports available in `build/reports/jacoco/test/` directory
  - Exclusion of configuration classes (Swagger, Spring Boot) from coverage reports
  - **Current Coverage**: 82% instruction coverage, exceeding the 80% requirement
- **Code Quality Improvements**: Enhanced maintainability and documentation standards
  - Comprehensive inline documentation for all public APIs
  - Consistent code formatting and style guidelines
  - Improved error handling and validation patterns

### Test Results
- **71 Total Tests**: All tests passing successfully (increased from 39)
  - Entity functionality and validation tests
  - Controller endpoint tests with comprehensive scenarios
  - Service layer business logic validation
  - Repository integration tests with reactive streams
  - Configuration and application context tests
  - Repository behavior and reactive operations tests
  - Application configuration and context tests
  - Edge cases and error handling tests
- **Build Success**: Zero compilation errors with comprehensive test coverage