# 🚀 Franquicias API

<p>
  <a href="https://github.com/carrilloapps/franchises-api/releases/latest">
    <img src="https://img.shields.io/github/v/release/carrilloapps/franchises-api?style=flat-square" alt="Latest Release">
  </a>
  <a href="https://github.com/carrilloapps/franchises-api/pkgs/container/franchises-api">
    <img src="https://img.shields.io/badge/Docker%20Image-ghcr.io%2Fcarrilloapps%2Ffranchises--api-blue?style=flat-square&logo=docker" alt="Docker Image">
  </a>
  <a href="https://github.com/carrilloapps/franchises-api/actions/workflows/docker-image.yml">
    <img src="https://github.com/carrilloapps/franchises-api/actions/workflows/docker-image.yml/badge.svg" alt="Build and Push Docker Image">
  </a>
  <a href="https://opensource.org/licenses/MIT">
    <img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="License: MIT">
  </a>
  <a href="https://kotlinlang.org/">
    <img src="https://img.shields.io/badge/Kotlin-1.9.21-blue.svg?logo=kotlin" alt="Kotlin">
  </a>
  <a href="https://spring.io/projects/spring-boot/">
    <img src="https://img.shields.io/badge/Spring_Boot-3.2.0-green.svg?logo=spring" alt="Spring Boot">
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
</p>

This project delivers a robust and scalable RESTful API designed for comprehensive management of franchises, branches, and products. Developed with **Spring Boot** and **Kotlin**, it utilizes **MongoDB** for efficient and flexible data persistence. This API is ideal for businesses looking to streamline their franchise operations, offering functionalities for adding new franchises, managing branches, handling product inventory, and providing insightful data on stock levels. 🌿

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
    git clone https://github.com/carrilloapps/franchises.git # Replace with actual repository URL
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
    The application will be accessible at `http://localhost:8080`. 🌐

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
    This command will launch both the MongoDB container and the Spring Boot application, making the application available at `http://localhost:8080`. 🐳

## 💾 Database Configuration

The application is configured to connect to a MongoDB instance. By default, it attempts to connect to `mongodb://localhost:27017/franchisesdb`. You can modify this configuration in `src/main/resources/application.properties`.

To quickly spin up a MongoDB instance using Docker:
```bash
docker run --name mongo-franchises -p 27017:27017 -d mongo
```

## 🧪 Running Tests

To run all unit and integration tests, use the following Gradle command:

```bash
./gradlew test
```

This will execute all tests located in `src/test/kotlin` and provide a detailed report of the test results. ✅

## 🌐 API Endpoints

All API endpoints are conveniently prefixed with `/franchises`. 🔗

## 📖 Swagger UI

Once the application is up and running, you can explore and interact with the API via Swagger UI:

`http://localhost:8080/swagger-ui.html`

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

## 🚀 GitHub Actions

This project leverages GitHub Actions to automate the Docker image build and publish process. This ensures that every significant change is automatically built and made ready for deployment. 🚀

### Workflow Details

*   **Trigger**: Automatically runs on new tags matching `v*.*.*`. ➡️
*   **Build Tool**: Uses Gradle to build the Docker image via `./gradlew bootBuildImage`. 🏗️
*   **Registry**: Pushes the built image to `ghcr.io/${{ github.repository }}`. Tags include the Git tag (e.g., `v1.0.0` becomes `1.0.0`). 🏷️
*   **Authentication**: Authenticates securely using `GITHUB_TOKEN` for access to the GitHub Container Registry. 🔑
