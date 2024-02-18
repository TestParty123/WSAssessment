import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StoreInventoryTest {

    // addProduct method test cases
    @Nested
    class AddProductTests
    {
        // Successfully adds product to inventory
        @Test
        void addProductSuccess() {
        StoreInventory inventory = new StoreInventory();

        assertTrue(inventory.addProduct(1, "Milk", 4.60, 4), "Adding product should be added successfully.");
        }

        // Test for adding product with duplicate ID
        @Test
        void addProductWithDuplicateId() {
        StoreInventory inventory = new StoreInventory();
        inventory.addProduct(1, "Milk", 4.60, 4);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    inventory.addProduct(1, "Milk", 4.60, 4);
                }, "Expected an IllegalArgumentException to be thrown for duplicate ID.");

        String expectedMessage = "Error: A product with that ID already exists.";
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);

        assertTrue(actualMessage.contains(expectedMessage), "Should throw an IllegalArgumentException for duplicate ID.");
        }

        // Test for invalid price amount
        @Test
        void addProductWithNegativePrice() {
        StoreInventory inventory = new StoreInventory();

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    inventory.addProduct(1, "Milk", -10.60, 4);
                }, "Expected an IllegalArgumentException to be thrown for non-negative price or quantity");

        String expectedMessage = "Error: Price and quantity must be non-negative.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage), "Should throw an IllegalArgumentException for negative price");
        }

        // Test for invalid quantity amount
        @Test
        void addProductWithNegativeQuantity() {
        StoreInventory inventory = new StoreInventory();

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    inventory.addProduct(1, "Milk", 10.60, -4);
                }, "Expected an IllegalArgumentException to be thrown for non-negative price or quantity");

        String expectedMessage = "Error: Price and quantity must be non-negative.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage), "Should throw an IllegalArgumentException for negative quantity");
        }

        // Test for empty or null product name
        @Test
        void addProductEmptyName() {
        StoreInventory inventory = new StoreInventory();

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    inventory.addProduct(1, "        ", 10.60, -4);
                }, "Should throw an IllegalArgumentException for empty product name");

        String expectedMessage = "Error: Product name must not be empty.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage), "Should throw an IllegalArgumentException for empty product name");
        }
    }

    // removeProduct method test cases
    @Nested
    class RemoveProductTest {

        // Successfully remove product from inventory
        @Test
        void removeProductSuccess() {
            StoreInventory inventory = new StoreInventory();
            inventory.addProduct(1, "Cereal", 4.60, 4);
            assertTrue(inventory.removeProduct(1), "Removing product should be successful");
        }

        // Test for removal of non-existent product in inventory
        @Test
        void removeNonExistentProduct() {
            StoreInventory inventory = new StoreInventory();
            assertFalse(inventory.removeProduct(4567), "Removing non-existent product should return false");
        }
    }

    // viewInventory method test cases
    @Nested
    class ViewInventoryTests {

        // Successfully output inventory with correct formatting
        @Test
        void viewInventorySummary() {
            StoreInventory inventory = new StoreInventory();
            inventory.addProduct(1, "Milk", 2.5, 3);
            inventory.addProduct(2, "Cheese", 3.45, 10);

            String expectedOutput = "ID: 1, Name: Milk, Price: 2.5, Quantity: 3\n" +
                    "ID: 2, Name: Cheese, Price: 3.45, Quantity: 10\n";
            String actualOutput = inventory.viewInventory();

            assertEquals(expectedOutput, actualOutput, "Inventory output does not follow expected formatting.");
        }

        // Test blank output of empty inventory
        @Test
        void viewEmptyInventory() {
            StoreInventory inventory = new StoreInventory();
            String expectedOutput = "";
            String actualOutput = inventory.viewInventory();

            assertEquals(expectedOutput, actualOutput, "Inventory output should be empty");
        }
    }

    // updateProductQuantity method test cases
    @Nested
    class UpdateProductQuantityTest {

        // Successfully update products quantity
        @Test
        void updateProductQuantitySuccess() {
            StoreInventory inventory = new StoreInventory();
            inventory.addProduct(1, "Milk", 2.5, 3);

            assertTrue(inventory.updateProductQuantity(1, 20), "Product quantity should update successfully.");
        }

        // Test for attempting to update non-existent product ID
        @Test
        void updateProductQuantityNonExistentProduct() {
            StoreInventory inventory = new StoreInventory();
            assertFalse(inventory.updateProductQuantity(4, 99), "Attempting to update non-existent product should return false.");
        }

        // Test for attempting to update quantity to negative amount
        @Test
        void updateProductQuantityNegativeQuantity() {
            StoreInventory inventory = new StoreInventory();
            inventory.addProduct(1, "Milk", 2.5, 45);

            Exception exception = assertThrows(IllegalArgumentException.class,
                    () -> {
                        inventory.updateProductQuantity(1, -99);
                    }, "Negative quantity should throw an IllegalArgumentException");

            String expectedMessage = "Error: New quantity must be a non negative number";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage), "Exception message for negative quantity not allowed.");
        }
    }

    // totalInventoryValue method test cases
    @Nested
    class TotalInventoryValueTests {
        // Successfully returns inventory value for empty inventory
        @Test
        void totalInventoryValueEmpty() {
            StoreInventory inventory = new StoreInventory();

            assertEquals(0.0, inventory.totalInventoryValue(), "Total value of inventory should match expected value for empty inventory.");
        }

        // Successfully calculates and returns inventory value for single product
        @Test
        void totalInventoryValueSingleProducts() {
            StoreInventory inventory = new StoreInventory();
            inventory.addProduct(1, "Coffee", 4.97, 67);

            assertEquals(332.99, inventory.totalInventoryValue(), "Total value of inventory should match expected value for single product.");
        }

        // Successfully calculates and returns inventory value for multiple products
        @Test
        void totalInventoryValueMultipleProducts() {
            StoreInventory inventory = new StoreInventory();
            inventory.addProduct(1, "Coffee", 4.99, 10);
            inventory.addProduct(2, "Bread", 2.5, 45);

            assertEquals(162.4, inventory.totalInventoryValue(), "Total value of inventory should match expected value for multiple products.");
        }
    }
}