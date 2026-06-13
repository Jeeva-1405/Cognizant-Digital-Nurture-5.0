class Order {
    int orderId;
    String customerName;
    double totalPrice;

    public Order(int orderId, String customerName, double totalPrice) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }

    public String toString() {
        return "Order{ID=" + orderId + ", Customer=" + customerName + ", Total=$" + totalPrice + "}";
    }
}

public class OrderSorting {

    // Bubble Sort - O(n^2)
    public static void bubbleSort(Order[] orders) {
        int n = orders.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (orders[j].totalPrice > orders[j + 1].totalPrice) {
                    Order temp = orders[j];
                    orders[j] = orders[j + 1];
                    orders[j + 1] = temp;
                }
            }
        }
    }

    // Quick Sort - O(n log n) average
    public static void quickSort(Order[] orders, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(orders, low, high);
            quickSort(orders, low, pivotIndex - 1);
            quickSort(orders, pivotIndex + 1, high);
        }
    }

    private static int partition(Order[] orders, int low, int high) {
        double pivot = orders[high].totalPrice;
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (orders[j].totalPrice <= pivot) {
                i++;
                Order temp = orders[i];
                orders[i] = orders[j];
                orders[j] = temp;
            }
        }

        Order temp = orders[i + 1];
        orders[i + 1] = orders[high];
        orders[high] = temp;

        return i + 1;
    }

    public static void printOrders(Order[] orders) {
        for (Order order : orders) {
            System.out.println("  " + order);
        }
    }

    public static void main(String[] args) {
        Order[] ordersForBubble = {
            new Order(1001, "Alice", 5200.00),
            new Order(1002, "Bob", 1800.00),
            new Order(1003, "Charlie", 3500.00),
            new Order(1004, "Diana", 900.00),
            new Order(1005, "Eve", 7200.00)
        };

        Order[] ordersForQuick = {
            new Order(1001, "Alice", 5200.00),
            new Order(1002, "Bob", 1800.00),
            new Order(1003, "Charlie", 3500.00),
            new Order(1004, "Diana", 900.00),
            new Order(1005, "Eve", 7200.00)
        };

        System.out.println("Orders sorted by Bubble Sort (ascending total price):");
        bubbleSort(ordersForBubble);
        printOrders(ordersForBubble);

        System.out.println();

        System.out.println("Orders sorted by Quick Sort (ascending total price):");
        quickSort(ordersForQuick, 0, ordersForQuick.length - 1);
        printOrders(ordersForQuick);

        /*
         * Performance Comparison:
         * Bubble Sort - O(n^2) time, O(1) space - simple but inefficient for large datasets
         * Quick Sort  - O(n log n) average time, O(log n) space - preferred for large datasets
         *
         * Quick Sort is generally preferred because it performs significantly better
         * on large order lists typical in e-commerce platforms.
         */
    }
}
