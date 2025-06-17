package app.carrillo.franchises.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

/**
 * Unit tests for Franchise domain entities
 * Tests the data classes: Franchise, Branch, and Product
 */
@DisplayName("Franchise Domain Tests")
class FranchiseTest {

    @Nested
    @DisplayName("Franchise Entity Tests")
    inner class FranchiseEntityTests {

        /**
         * Test franchise creation with all parameters
         */
        @Test
        @DisplayName("Should create franchise with all parameters")
        fun `should create franchise with all parameters`() {
            // Given
            val name = "Test Franchise"
            val address = "123 Test Street"
            val description = "A test franchise"
            val branches = listOf(
                Branch("Branch 1", listOf(Product("Product 1", 10, 100.0))),
                Branch("Branch 2", listOf(Product("Product 2", 5, 50.0)))
            )

            // When
            val franchise = Franchise(
                name = name,
                address = address,
                description = description,
                branches = branches
            )

            // Then
            assertNotNull(franchise.id)
            assertEquals(name, franchise.name)
            assertEquals(address, franchise.address)
            assertEquals(description, franchise.description)
            assertEquals(2, franchise.branches.size)
            assertTrue(franchise.id.isNotEmpty())
        }

        /**
         * Test franchise creation with minimal parameters
         */
        @Test
        @DisplayName("Should create franchise with minimal parameters")
        fun `should create franchise with minimal parameters`() {
            // Given
            val name = "Minimal Franchise"

            // When
            val franchise = Franchise(name = name)

            // Then
            assertNotNull(franchise.id)
            assertEquals(name, franchise.name)
            assertNull(franchise.address)
            assertNull(franchise.description)
            assertTrue(franchise.branches.isEmpty())
        }

        /**
         * Test franchise ID generation
         */
        @Test
        @DisplayName("Should generate unique IDs for different franchises")
        fun `should generate unique IDs for different franchises`() {
            // Given & When
            val franchise1 = Franchise(name = "Franchise 1")
            val franchise2 = Franchise(name = "Franchise 2")

            // Then
            assertNotEquals(franchise1.id, franchise2.id)
            assertTrue(franchise1.id.isNotEmpty())
            assertTrue(franchise2.id.isNotEmpty())
        }

        /**
         * Test franchise equality
         */
        @Test
        @DisplayName("Should handle franchise equality correctly")
        fun `should handle franchise equality correctly`() {
            // Given
            val id = "test-id"
            val name = "Test Franchise"
            val address = "Test Address"
            
            val franchise1 = Franchise(id = id, name = name, address = address)
            val franchise2 = Franchise(id = id, name = name, address = address)
            val franchise3 = Franchise(id = "different-id", name = name, address = address)

            // Then
            assertEquals(franchise1, franchise2)
            assertNotEquals(franchise1, franchise3)
            assertEquals(franchise1.hashCode(), franchise2.hashCode())
        }
    }

    @Nested
    @DisplayName("Branch Entity Tests")
    inner class BranchEntityTests {

        /**
         * Test branch creation with products
         */
        @Test
        @DisplayName("Should create branch with products")
        fun `should create branch with products`() {
            // Given
            val branchName = "Test Branch"
            val products = listOf(
                Product("Product 1", 10, 100.0),
                Product("Product 2", 5, 50.0)
            )

            // When
            val branch = Branch(name = branchName, products = products)

            // Then
            assertEquals(branchName, branch.name)
            assertEquals(2, branch.products.size)
            assertEquals("Product 1", branch.products[0].name)
            assertEquals("Product 2", branch.products[1].name)
        }

        /**
         * Test branch creation without products
         */
        @Test
        @DisplayName("Should create branch without products")
        fun `should create branch without products`() {
            // Given
            val branchName = "Empty Branch"

            // When
            val branch = Branch(name = branchName)

            // Then
            assertEquals(branchName, branch.name)
            assertTrue(branch.products.isEmpty())
        }

        /**
         * Test branch equality
         */
        @Test
        @DisplayName("Should handle branch equality correctly")
        fun `should handle branch equality correctly`() {
            // Given
            val name = "Test Branch"
            val products = listOf(Product("Product 1", 10, 100.0))
            
            val branch1 = Branch(name = name, products = products)
            val branch2 = Branch(name = name, products = products)
            val branch3 = Branch(name = "Different Branch", products = products)

            // Then
            assertEquals(branch1, branch2)
            assertNotEquals(branch1, branch3)
            assertEquals(branch1.hashCode(), branch2.hashCode())
        }
    }

    @Nested
    @DisplayName("Product Entity Tests")
    inner class ProductEntityTests {

        /**
         * Test product creation with all parameters
         */
        @Test
        @DisplayName("Should create product with all parameters")
        fun `should create product with all parameters`() {
            // Given
            val name = "Test Product"
            val stock = 15
            val price = 99.99

            // When
            val product = Product(name = name, stock = stock, price = price)

            // Then
            assertEquals(name, product.name)
            assertEquals(stock, product.stock)
            assertEquals(price, product.price)
        }

        /**
         * Test product creation without price
         */
        @Test
        @DisplayName("Should create product without price")
        fun `should create product without price`() {
            // Given
            val name = "No Price Product"
            val stock = 10

            // When
            val product = Product(name = name, stock = stock)

            // Then
            assertEquals(name, product.name)
            assertEquals(stock, product.stock)
            assertNull(product.price)
        }

        /**
         * Test product with zero stock
         */
        @Test
        @DisplayName("Should handle product with zero stock")
        fun `should handle product with zero stock`() {
            // Given
            val name = "Out of Stock Product"
            val stock = 0
            val price = 50.0

            // When
            val product = Product(name = name, stock = stock, price = price)

            // Then
            assertEquals(name, product.name)
            assertEquals(0, product.stock)
            assertEquals(price, product.price)
        }

        /**
         * Test product equality
         */
        @Test
        @DisplayName("Should handle product equality correctly")
        fun `should handle product equality correctly`() {
            // Given
            val name = "Test Product"
            val stock = 10
            val price = 100.0
            
            val product1 = Product(name = name, stock = stock, price = price)
            val product2 = Product(name = name, stock = stock, price = price)
            val product3 = Product(name = "Different Product", stock = stock, price = price)

            // Then
            assertEquals(product1, product2)
            assertNotEquals(product1, product3)
            assertEquals(product1.hashCode(), product2.hashCode())
        }

        /**
         * Test product with negative stock
         */
        @Test
        @DisplayName("Should handle product with negative stock")
        fun `should handle product with negative stock`() {
            // Given
            val name = "Negative Stock Product"
            val stock = -5
            val price = 25.0

            // When
            val product = Product(name = name, stock = stock, price = price)

            // Then
            assertEquals(name, product.name)
            assertEquals(-5, product.stock)
            assertEquals(price, product.price)
        }
    }

    @Nested
    @DisplayName("Domain Integration Tests")
    inner class DomainIntegrationTests {

        /**
         * Test complete franchise structure
         */
        @Test
        @DisplayName("Should create complete franchise structure")
        fun `should create complete franchise structure`() {
            // Given
            val products1 = listOf(
                Product("Laptop", 5, 1000.0),
                Product("Mouse", 20, 25.0)
            )
            val products2 = listOf(
                Product("Keyboard", 15, 75.0),
                Product("Monitor", 8, 300.0)
            )
            val branches = listOf(
                Branch("Downtown Branch", products1),
                Branch("Mall Branch", products2)
            )

            // When
            val franchise = Franchise(
                name = "Tech Store Franchise",
                address = "123 Business Ave",
                description = "Electronics retail franchise",
                branches = branches
            )

            // Then
            assertEquals("Tech Store Franchise", franchise.name)
            assertEquals(2, franchise.branches.size)
            assertEquals(2, franchise.branches[0].products.size)
            assertEquals(2, franchise.branches[1].products.size)
            assertEquals("Laptop", franchise.branches[0].products[0].name)
            assertEquals(1000.0, franchise.branches[0].products[0].price)
        }

        /**
         * Test franchise with empty branches
         */
        @Test
        @DisplayName("Should handle franchise with empty branches")
        fun `should handle franchise with empty branches`() {
            // Given
            val emptyBranches = listOf(
                Branch("Empty Branch 1"),
                Branch("Empty Branch 2")
            )

            // When
            val franchise = Franchise(
                name = "Empty Franchise",
                branches = emptyBranches
            )

            // Then
            assertEquals(2, franchise.branches.size)
            assertTrue(franchise.branches[0].products.isEmpty())
            assertTrue(franchise.branches[1].products.isEmpty())
        }
    }
}