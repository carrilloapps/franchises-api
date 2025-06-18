# 🚀 Franquicias API

<p>
  <a href="https://github.com/carrilloapps/franchises-api/releases/latest">
    <img src="https://img.shields.io/github/v/release/carrilloapps/franchises-api?style=flat-square" alt="Latest Release">
  </a>
  <a href="https://github.com/carrilloapps/franchises-api/actions/workflows/ci.yml">
    <img src="https://github.com/carrilloapps/franchises-api/actions/workflows/ci.yml/badge.svg" alt="🧪 Tests">
  </a>
  <a href="https://github.com/carrilloapps/franchises-api/pkgs/container/franchises-api">
    <img src="https://img.shields.io/badge/Docker%20Image-ghcr.io%2Fcarrilloapps%2Ffranchises--api-blue?style=flat-square&logo=docker" alt="Docker Image">
  </a>
  <a href="https://github.com/carrilloapps/franchises-api/actions/workflows/docker-image.yml">
    <img src="https://github.com/carrilloapps/franchises-api/actions/workflows/docker-image.yml/badge.svg" alt="🐳 Docker Build">
  </a>
  <a href="https://codecov.io/gh/carrilloapps/franchises-api">
    <img src="https://codecov.io/gh/carrilloapps/franchises-api/branch/master/graph/badge.svg" alt="📊 Code Coverage">
  </a>
  <a href="https://opensource.org/licenses/MIT">
    <img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="License: MIT">
  </a>
  <a href="https://kotlinlang.org/">
    <img src="https://img.shields.io/badge/Kotlin-2.2.0--RC3-blue.svg?logo=kotlin" alt="Kotlin">
  </a>
  <a href="https://spring.io/projects/spring-boot/">
    <img src="https://img.shields.io/badge/Spring_Boot-3.5.0-green.svg?logo=spring" alt="Spring Boot">
  </a>
  <a href="https://www.mongodb.com/">
    <img src="https://img.shields.io/badge/MongoDB-4.4-green.svg?logo=mongodb" alt="MongoDB">
  </a>
  <a href="https://gradle.org/">
    <img src="https://img.shields.io/badge/Gradle-8.5-blue.svg?logo=gradle" alt="Gradle">
  </a>
  <a href="https://www.docker.com/">
    <img src="https://img.shields.io/badge/Docker-24.0.5-blue.svg?logo=docker" alt="Docker">
  </a>
  <a href="https://x.com/carrilloapps">
    <img src="https://img.shields.io/twitter/follow/nestframework.svg?style=social&label=Follow" alt="Follow me">
  </a>
</p>

This project delivers a robust and scalable RESTful API designed for comprehensive management of franchises, branches, and products. Developed with **Spring Boot** and **Kotlin**, it utilizes **MongoDB** for efficient and flexible data persistence. This API is ideal for businesses looking to streamline their franchise operations, offering functionalities for adding new franchises, managing branches, handling product inventory, and providing insightful data on stock levels.

## 🛠️ Technologies Used

*   **Kotlin**: A modern, concise, and safe programming language that runs on the JVM.
*   **Spring Boot**: A framework that simplifies the development of production-ready Spring applications.
*   **MongoDB**: A NoSQL document database designed for scalability and flexibility.
*   **Gradle**: A powerful build automation tool.
*   **Docker**: A platform for developing, shipping, and running applications in containers.
*   **GitHub Actions**: For continuous integration and continuous delivery (CI/CD).

## 📂 Project Structure

```
franchises/
├── .github/workflows/
│   └── docker-image.yml # GitHub Actions workflow for Docker image build and push
├── src/
│   ├── main/
│   │   ├── kotlin/app/carrillo/franchises/
│   │   │   ├── FranchisesApplication.kt # Main application entry point
│   │   │   ├── config/ # Configuration classes
│   │   │   ├── controller/ # REST API controllers
│   │   │   ├── domain/ # Domain models (Franchise, Branch, Product)
│   │   │   ├── infrastructure/ # MongoDB repositories
│   │   │   └── application/ # Business logic services
│   │   └── resources/
│   │       └── application.properties # Application configuration
│   └── test/
│       └── kotlin/com/franchises/backend/ # Unit and integration tests
├── Dockerfile # Dockerfile for building the application image
├── compose.yaml # Docker Compose file for local development setup
├── build.gradle # Gradle build file
├── gradlew # Gradle wrapper script (Linux/macOS)
├── gradlew.bat # Gradle wrapper script (Windows)
├── README.md # Project README file
├── LICENSE # Project license file
├── CONTRIBUTING.md # Guidelines for contributing to the project
└── CODE_OF_CONDUCT.md # Code of Conduct for contributors
```

## 📚 Table of Contents

- [🌟 Project Overview](#-franquicias-api)
- [🛠️ Technologies Used](#️-technologies-used)
- [📂 Project Structure](#-project-structure)
- [✨ Features](#features)
- [⚙️ Prerequisites](#️-prerequisites)
- [🚀 Getting Started](#-getting-started)
  - [Local Setup](#local-setup)
  - [Running with Docker](#running-with-docker)
- [💾 Database Configuration](#-database-configuration)
- [🧪 Running Tests](#-running-tests)
- [🌐 API Endpoints](#-api-endpoints)
- [📖 Swagger UI](#-swagger-ui)
- [📊 Data Model](#-data-model)
- [🚀 GitHub Actions](#-github-actions)
- [🚀 Deployment](#-deployment)
- [🤝 Contribution Guidelines](#-contribution-guidelines)
- [📜 Code of Conduct](#-code-of-conduct)
- [⚖️ License](#️-license)

## ✨ Features

- **Franchise Management**: Add new franchises. 🏢
- **Branch Operations**: Add new branches to existing franchises. 🌳
- **Product Handling**: Add, delete, and modify product stock within branches. 📦
- **Inventory Insights**: Retrieve the product with the most stock per branch for any given franchise. 📈
- **Dynamic Updates (Plus)**:
  - Update franchise names. ✍️
  - Update branch names. 🏷️
  - Update product names. 📝
  - Update franchise address. 📍
  - Update franchise description. 📜
  - Update product price. 💰

## ⚙️ Prerequisites

Before you get started, ensure you have the following installed on your system:

*   **Java Development Kit (JDK) 17 or higher**: [Download Link](https://www.oracle.com/java/technologies/downloads/) ☕
*   **Gradle**: Typically bundled with Spring Boot projects, ensure it's accessible via your PATH or use `./gradlew`. ⚙️
*   **MongoDB**: Follow the [Installation Guide](https://docs.mongodb.com/manual/installation/) to set up your database. 🍃
*   **Docker and Docker Compose** (Optional, for containerized deployment): [Download Link](https://www.docker.com/products/docker-desktop) 🐳

## 🚀 Getting Started

### Local Setup

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/carrilloapps/franchises-api.git # Replace with actual repository URL
    cd franchises
    ```

2.  **Build the project:**
    ```bash
    ./gradlew build
    ```

3.  **Run the application:**
    ```bash
    ./gradlew bootRun
    ```
    The application will be accessible at `http://localhost:3081`. 🌐

### Running with Docker

1.  **Build the Docker image:**
    ```bash
    ./gradlew bootBuildImage
    ```

2.  **Run with Docker Compose:**
    Utilize the provided `compose.yaml` for a seamless setup with MongoDB:
    ```bash
    docker compose up
    ```
    This command will launch both the MongoDB container and the Spring Boot application, making the application available at `http://localhost:3081`. 🐳

## 💾 Database Configuration

The application is configured to connect to a MongoDB instance. By default, it attempts to connect to `mongodb://localhost:27017/franchisesdb`. You can modify this configuration in `src/main/resources/application.properties`.

To quickly spin up a MongoDB instance using Docker:
```bash
docker run --name mongo-franchises -p 27017:27017 -d mongo
```

## 🧪 Testing

### Running Tests

To run all unit and integration tests, use the following Gradle command:

```bash
./gradlew test
```

This will execute all tests located in `src/test/kotlin` and provide a detailed report of the test results. ✅

### Test Coverage

Generate code coverage reports using JaCoCo:

```bash
./gradlew jacocoTestReport
```

Coverage reports are generated in:
- **HTML Report**: `build/reports/jacoco/test/html/index.html`
- **XML Report**: `build/reports/jacoco/test/jacocoTestReport.xml`

The project maintains a minimum coverage threshold of **70%** with automatic verification:

```bash
./gradlew jacocoTestCoverageVerification
```

### Test Structure

Our comprehensive testing suite includes **39 tests** across multiple layers:

#### 🏗️ Domain Layer Tests (`FranchiseTest.kt`)
- **Entity Creation**: Validates proper instantiation of `Franchise`, `Branch`, and `Product` entities
- **Data Integrity**: Tests equality, hash codes, and data class functionality
- **Business Rules**: Ensures domain constraints and validation rules
- **Edge Cases**: Handles empty collections, null values, and boundary conditions

#### 📊 Repository Layer Tests (`FranchiseRepositoryTest.kt`)
- **Reactive Operations**: Tests using `StepVerifier` for reactive streams
- **CRUD Operations**: Validates create, read, update, and delete operations
- **Query Methods**: Tests custom repository methods and queries
- **Error Handling**: Ensures proper exception handling and error propagation
- **Mock Integration**: Uses Mockito for isolated unit testing

#### 🔧 Application Layer Tests (`FranchiseServiceTest.kt`)
- **Business Logic**: Validates service layer operations and workflows
- **Transaction Management**: Tests reactive transaction handling
- **Integration Points**: Ensures proper interaction between layers
- **Exception Scenarios**: Tests error handling and recovery mechanisms

#### ⚙️ Configuration Tests (`FranchiseApplicationTest.kt`)
- **Spring Context**: Validates application context loading
- **CORS Configuration**: Tests cross-origin resource sharing setup
- **Bean Validation**: Ensures proper dependency injection
- **Application Startup**: Validates successful application initialization

### Testing Technologies

- **JUnit 5**: Modern testing framework with advanced features
- **Kotlin Test**: Kotlin-specific testing utilities and assertions
- **Mockito**: Comprehensive mocking framework for unit tests
- **Reactor Test**: Specialized testing for reactive streams
- **StepVerifier**: Reactive stream testing and verification
- **Spring Boot Test**: Integration testing support for Spring applications
- **JaCoCo**: Code coverage analysis and reporting

### Test Execution Results

✅ **All 39 tests pass successfully**  
✅ **Zero compilation errors**  
✅ **Comprehensive coverage across all layers**  
✅ **Reactive programming patterns validated**  
✅ **Domain-driven design principles tested**

## 🌐 API Endpoints

All API endpoints are conveniently prefixed with `/franchises`. 🔗

## 📖 Swagger UI

Once the application is up and running, you can explore and interact with the API via Swagger UI:

`http://localhost:3081/swagger-ui.html`

This interactive documentation allows you to test all API endpoints directly from your browser. 🧪

## 🚀 Deployment

This application can be easily deployed as a Docker container. The `Dockerfile` and GitHub Actions workflow (`.github/workflows/docker-image.yml`) are set up to automate the build and push of Docker images to the GitHub Container Registry. You can adapt this workflow for other container registries or deployment platforms.

For production deployments, consider using container orchestration tools like Kubernetes or Docker Swarm. 🚢

-   **`POST /`**
    -   **Description**: Add a new franchise. ➕
    -   **Request Body**: `Franchise` object (e.g., `{"name": "Franchise A", "branches": []}`)
    -   **Response**: Created `Franchise` object. ✅

-   **`POST /{franchiseId}/branches`**
    -   **Description**: Add a new branch to a franchise. 🌿
    -   **Request Body**: `Branch` object (e.g., `{"name": "Branch 1", "products": []}`)
    -   **Response**: Updated `Franchise` object. ✅

-   **`POST /{franchiseId}/branches/{branchName}/products`**
    -   **Description**: Add a new product to a branch. 📦
    -   **Request Body**: `Product` object (e.g., `{"name": "Product X", "stock": 100}`)
    -   **Response**: Updated `Franchise` object. ✅

-   **`DELETE /{franchiseId}/branches/{branchName}/products/{productName}`**
    -   **Description**: Delete a product from a branch. 🗑️
    -   **Response**: Updated `Franchise` object. ✅

-   **`PUT /{franchiseId}/branches/{branchName}/products/{productName}/stock?newStock={newStock}`**
    -   **Description**: Modify the stock of a product. 🔄
    -   **Response**: Updated `Franchise` object. ✅

-   **`GET /{franchiseId}/products/most-stock`**
    -   **Description**: Get the product with the most stock per branch for a specific franchise. 📊
    -   **Response**: A list of maps, where each map contains a branch name and its highest stock product. ✅

-   **`PUT /{franchiseId}/name?newName={newName}`** (Plus)
    -   **Description**: Update the name of a franchise. ✍️
    -   **Response**: Updated `Franchise` object. ✅

-   **`PUT /{franchiseId}/address?newAddress={newAddress}`** (Plus)
    -   **Description**: Update the address of a franchise. 📍
    -   **Response**: Updated `Franchise` object. ✅

-   **`PUT /{franchiseId}/description?newDescription={newDescription}`** (Plus)
    -   **Description**: Update the description of a franchise. 📜
    -   **Response**: Updated `Franchise` object. ✅

-   **`GET /`**
    -   **Description**: Get all franchises. 📋
    -   **Response**: A list of `Franchise` objects. ✅

-   **`GET /{id}`**
    -   **Description**: Get a franchise by its ID. 🔍
    -   **Response**: A `Franchise` object. ✅

-   **`PUT /{franchiseId}/branches/{oldBranchName}/name?newBranchName={newBranchName}`** (Plus)
    -   **Description**: Update the name of a branch. 🏷️
    -   **Response**: Updated `Franchise` object. ✅

-   **`PUT /{franchiseId}/branches/{branchName}/products/{oldProductName}/name?newProductName={newProductName}`** (Plus)
    -   **Description**: Update the name of a product. 📝
    -   **Response**: Updated `Franchise` object. ✅

-   **`PUT /{franchiseId}/branches/{branchName}/products/{productName}/price?newPrice={newPrice}`** (Plus)
    -   **Description**: Update the price of a product. 💰
    -   **Response**: Updated `Franchise` object. ✅

## 📊 Data Model

### Franchise

```kotlin
data class Franchise(
    val id: String? = null,
    val name: String,
    val address: String? = null,
    val description: String? = null,
    val branches: List<Branch> = emptyList()
)
```

### Branch

```kotlin
data class Branch(
    val name: String,
    val products: List<Product> = emptyList()
)
```

### Product

```kotlin
data class Product(
    val name: String,
    var stock: Int,
    var price: Double? = null
)
```

## 🤝 Contribution Guidelines

We welcome contributions! Please refer to our [Contribution Guidelines](CONTRIBUTING.md) for detailed information on how to get involved, report bugs, suggest features, and submit code. Your contributions help make this project better! ✨

## 📜 Code of Conduct

To ensure a positive and inclusive environment, we adhere to a [Code of Conduct](CODE_OF_CONDUCT.md). We expect all contributors and participants to follow these guidelines to foster a respectful and welcoming community. Please read it to understand expected behavior. 💖

## ⚖️ License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details. 📄

## 🚀 CI/CD Pipeline

This project features a comprehensive CI/CD pipeline using GitHub Actions to ensure code quality, security, and automated deployment. 🚀

### Continuous Integration Workflow (`.github/workflows/ci.yml`)

Our CI pipeline automatically runs on:
- **Push events** to `master` and `develop` branches
- **Pull requests** targeting `master` and `develop` branches

#### Pipeline Stages

##### 🧪 Test Job
- **Environment**: Ubuntu Latest with Java 17
- **Caching**: Gradle dependencies cached for faster builds
- **Execution**: Runs complete test suite with `./gradlew test`
- **Coverage**: Generates JaCoCo coverage reports
- **Validation**: Ensures all 39 tests pass before proceeding

##### 🏗️ Build Job
- **Dependency**: Runs only after successful test completion
- **Process**: Compiles application using `./gradlew build`
- **Artifacts**: Validates build artifacts and dependencies
- **Quality Gate**: Ensures zero compilation errors

##### 🔒 Security Scan Job
- **Dependency**: Runs only after successful build completion
- **Tool**: GitHub Security scanning for vulnerability detection
- **Scope**: Analyzes dependencies and code for security issues
- **Reporting**: Automatic security alerts and recommendations

### Docker Image Automation

Additional GitHub Actions workflow for Docker image management:

*   **Trigger**: Automatically runs on new releases (`published` event). ➡️
*   **Build Tool**: Uses `docker/build-push-action` with Buildx for robust image building, including SBOM and provenance attestations. 🏗️
*   **Registry**: Pushes the built image to `ghcr.io/${{ github.repository }}`. Tags include the release version (e.g., `v1.0.0` becomes `1.0.0`). 🏷️
*   **Authentication**: Authenticates securely using `GITHUB_TOKEN` for access to the GitHub Container Registry. 🔑

### Quality Assurance

- **Automated Testing**: Every code change triggers comprehensive test execution
- **Code Coverage**: JaCoCo integration with 70% minimum coverage threshold
- **Security Scanning**: Automated vulnerability detection and reporting
- **Build Validation**: Ensures deployable artifacts before merge
- **Branch Protection**: Quality gates prevent broken code from reaching main branches

### Running from GitHub Container Registry Image

To run the application directly from the Docker image published to GitHub Container Registry, follow these steps:

1.  **Ensure Docker is installed**: Make sure you have Docker installed on your system. If not, you can download it from the [official Docker website](https://www.docker.com/products/docker-desktop).

2.  **Pull the Docker image**: Replace `YOUR_REPOSITORY` with your GitHub username or organization and `YOUR_IMAGE_NAME` with the repository name (e.g., `franchises-api`). Replace `TAG` with the desired release version (e.g., `1.0.0`).
    ```bash
    docker pull ghcr.io/YOUR_REPOSITORY/YOUR_IMAGE_NAME:TAG
    ```
    For example, to pull the `v1.0.0` image of this project:
    ```bash
    docker pull ghcr.io/carrilloapps/franchises-api:1.0.0
    ```

3.  **Run the Docker container**: You can run the image, mapping the application's port (3081) to a port on your host machine. Also, ensure your MongoDB instance is accessible from the container.
    ```bash
    docker run -p 3081:3081 ghcr.io/YOUR_REPOSITORY/YOUR_IMAGE_NAME:TAG
    ```
    If your MongoDB is running on `localhost` and you are running Docker on Linux, you might need to use `host.docker.internal` or the host's IP address for the MongoDB connection string within the container, or link a MongoDB container.

    Example with MongoDB running in a separate container (using `compose.yaml` is recommended for this):
    ```bash
    docker run -p 3081:3081 --network host ghcr.io/carrilloapps/franchises-api:1.0.0
    ```
    (Note: `--network host` might not be available on all Docker environments, especially Docker Desktop on macOS/Windows, where `host.docker.internal` is preferred for host access.)

4.  **Access the application**: Once the container is running, the application will be accessible at `http://localhost:3081`.
