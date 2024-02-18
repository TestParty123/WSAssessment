import java.util.ArrayList;
import java.util.List;

/*
    The StoreInventory class manages a collection of products in a store's inventory.
    It provides methods to add, remove, and update products,
    view the entire inventory, and calculate the total value of all the products in the inventory.
 */

public class StoreInventory {
    private final List<Product> inventory;

    public StoreInventory() {
        this.inventory = new ArrayList<>();
    }

    public boolean addProduct(int id, String name, double price, int quantity){
        // Check if name is empty or null
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: Product name must not be empty.");
        }

        String productName = name.trim();
        // Check to see if provided ID already exists
        for (Product product : inventory) {
            if(product.getId() == id) {
                throw new IllegalArgumentException("Error: A product with that ID already exists.");
            }
        }

        // Check if price and quantity are non-negative
        if (price < 0 || quantity < 0) {
            throw new IllegalArgumentException("Error: Price and quantity must be non-negative.");
        }

        // Adds new product to inventory if it passes checks
        Product newProduct = new Product(id, productName, price, quantity);
        inventory.add(newProduct);
        System.out.println(productName + " successfully added to inventory");
        return true;
    }

    public boolean removeProduct(int id) {
        return inventory.removeIf(product -> product.getId() == id);
    }

    public boolean updateProductQuantity(int id, int newQuantity) {
        if(newQuantity < 0) {
            throw new IllegalArgumentException("Error: New quantity must be a non negative number");
        }

        for (Product product : inventory) {
            if (product.getId() == id) {
                product.setQuantity(newQuantity);
                System.out.println("Quantity for " + product.getName() + " successfully updated to " + newQuantity);
                return true;
            }
        }

        return false;
    }

    public String viewInventory() {
        StringBuilder inventoryDetails = new StringBuilder();

        for (Product product : inventory) {
            inventoryDetails.append("ID: ").append(product.getId())
                    .append(", Name: ").append(product.getName())
                    .append(", Price: ").append(product.getPrice())
                    .append(", Quantity: ").append(product.getQuantity())
                    .append("\n");
        }

        return inventoryDetails.toString();
    }

    public double totalInventoryValue() {
        double totalValue = 0.0;

        for (Product product : inventory) {
            totalValue += product.getPrice() * product.getQuantity();
        }

        return totalValue;
    }
}
