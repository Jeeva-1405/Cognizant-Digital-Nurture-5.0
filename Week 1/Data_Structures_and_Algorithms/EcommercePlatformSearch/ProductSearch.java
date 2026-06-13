class Product {
    int productId;
    String productName;
    String category;

    public Product(int productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }

    public String toString() {
        return "Product{ID=" + productId + ", Name=" + productName + ", Category=" + category + "}";
    }
}

public class ProductSearch {

    public static Product linearSearch(Product[] products, int targetId) {
        for (Product product : products) {
            if (product.productId == targetId) {
                return product;
            }
        }
        return null;
    }


    public static Product binarySearch(Product[] products, int targetId) {
        int left = 0;
        int right = products.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (products[mid].productId == targetId) {
                return products[mid];
            } else if (products[mid].productId < targetId) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Product[] products = {
            new Product(1, "Laptop", "Electronics"),
            new Product(2, "T-Shirt", "Clothing"),
            new Product(3, "Novel", "Books"),
            new Product(4, "Headphones", "Electronics"),
            new Product(5, "Running Shoes", "Footwear")
        };

        System.out.println("Linear Search for Product ID 3:");
        Product result = linearSearch(products, 3);
        System.out.println(result != null ? "Found: " + result : "Not found");

        System.out.println();

        System.out.println("Binary Search for Product ID 4 (sorted array):");
        result = binarySearch(products, 4);
        System.out.println(result != null ? "Found: " + result : "Not found");

        System.out.println();

        System.out.println("Binary Search for Product ID 99:");
        result = binarySearch(products, 99);
        System.out.println(result != null ? "Found: " + result : "Not found");

    }
}
