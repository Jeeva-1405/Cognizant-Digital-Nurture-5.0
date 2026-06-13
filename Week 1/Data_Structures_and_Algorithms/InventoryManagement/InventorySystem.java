import java.util.HashMap;
import java.util.Map;

class Product {
    private int productId;
    private String productName;
    private int quantity;
    private double price;

    public Product(int productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(double price) { this.price = price; }

    public String toString() {
        return "Product{ID=" + productId + ", Name=" + productName
                + ", Qty=" + quantity + ", Price=$" + price + "}";
    }
}

class Inventory {
    private Map<Integer, Product> products = new HashMap<>();

    public void addProduct(Product product) {
        products.put(product.getProductId(), product);
        System.out.println("Added: " + product);
    }


    public void updateProduct(int productId, int newQuantity, double newPrice) {
        Product product = products.get(productId);
        if (product != null) {
            product.setQuantity(newQuantity);
            product.setPrice(newPrice);
            System.out.println("Updated: " + product);
        } else {
            System.out.println("Product ID " + productId + " not found.");
        }
    }


    public void deleteProduct(int productId) {
        Product removed = products.remove(productId);
        if (removed != null) {
            System.out.println("Deleted: " + removed);
        } else {
            System.out.println("Product ID " + productId + " not found.");
        }
    }

    public void displayAll() {
        System.out.println("Current Inventory:");
        for (Product p : products.values()) {
            System.out.println("  " + p);
        }
    }
}

public class InventorySystem {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        inventory.addProduct(new Product(101, "Laptop", 50, 75000.00));
        inventory.addProduct(new Product(102, "Mouse", 200, 450.00));
        inventory.addProduct(new Product(103, "Keyboard", 150, 850.00));

        System.out.println();
        inventory.displayAll();

        System.out.println();
        inventory.updateProduct(101, 45, 72000.00);

        System.out.println();
        inventory.deleteProduct(103);

        System.out.println();
        inventory.displayAll();


    }
}
