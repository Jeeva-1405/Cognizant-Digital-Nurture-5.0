import java.util.ArrayList;
import java.util.List;

class OrderProcessorBefore {
    public double process(String type, double amount, int qty, boolean express) {
        double total = 0;
        if (type.equals("ELECTRONICS")) {
            total = amount * qty;
            if (total > 1000) {
                total = total - (total * 0.1);
            }
        } else if (type.equals("GROCERY")) {
            total = amount * qty;
            if (total > 500) {
                total = total - (total * 0.05);
            }
        } else if (type.equals("CLOTHING")) {
            total = amount * qty;
            if (total > 800) {
                total = total - (total * 0.08);
            }
        }
        if (express) {
            total = total + 50;
        }
        return total;
    }
}

interface DiscountPolicy {
    double threshold();
    double discountRate();
}

class ElectronicsDiscount implements DiscountPolicy {
    public double threshold() { return 1000; }
    public double discountRate() { return 0.10; }
}

class GroceryDiscount implements DiscountPolicy {
    public double threshold() { return 500; }
    public double discountRate() { return 0.05; }
}

class ClothingDiscount implements DiscountPolicy {
    public double threshold() { return 800; }
    public double discountRate() { return 0.08; }
}

class OrderProcessorAfter {

    private static final double EXPRESS_SURCHARGE = 50.0;

    public double process(DiscountPolicy policy, double unitPrice, int quantity, boolean express) {
        double subtotal = unitPrice * quantity;
        double discounted = applyDiscount(policy, subtotal);
        return express ? discounted + EXPRESS_SURCHARGE : discounted;
    }

    private double applyDiscount(DiscountPolicy policy, double subtotal) {
        if (subtotal > policy.threshold()) {
            return subtotal - (subtotal * policy.discountRate());
        }
        return subtotal;
    }
}

public class RefactorCodeSmellsDemo {
    public static void main(String[] args) {
        OrderProcessorBefore before = new OrderProcessorBefore();
        System.out.println("Before refactor (nested conditionals, duplication):");
        System.out.println("  Electronics total: " + before.process("ELECTRONICS", 150.0, 8, true));
        System.out.println("  Grocery total    : " + before.process("GROCERY", 60.0, 10, false));

        System.out.println();

        OrderProcessorAfter after = new OrderProcessorAfter();
        System.out.println("After refactor (strategy pattern, single responsibility):");
        System.out.println("  Electronics total: " + after.process(new ElectronicsDiscount(), 150.0, 8, true));
        System.out.println("  Grocery total    : " + after.process(new GroceryDiscount(), 60.0, 10, false));
        System.out.println("  Clothing total   : " + after.process(new ClothingDiscount(), 200.0, 5, true));
    }
}
