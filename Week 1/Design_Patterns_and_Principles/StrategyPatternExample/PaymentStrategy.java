interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cardHolderName;

    public CreditCardPayment(String cardHolderName, String cardNumber) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
    }

    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using Credit Card.");
        System.out.println("Card Holder: " + cardHolderName);
        System.out.println("Card Number: **** **** **** " + cardNumber.substring(cardNumber.length() - 4));
    }
}

class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using PayPal.");
        System.out.println("PayPal Account: " + email);
    }
}

class PaymentContext {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }

    public void executePayment(double amount) {
        if (paymentStrategy == null) {
            System.out.println("No payment strategy selected.");
            return;
        }
        paymentStrategy.pay(amount);
    }
}

public class PaymentStrategyTest {
    public static void main(String[] args) {
        PaymentContext context = new PaymentContext();

        System.out.println("Customer chooses Credit Card:");
        context.setPaymentStrategy(new CreditCardPayment("Jeeva Elango", "1234567890123456"));
        context.executePayment(1500.00);

        System.out.println();

        System.out.println("Customer switches to PayPal:");
        context.setPaymentStrategy(new PayPalPayment("jeeva@example.com"));
        context.executePayment(750.50);
    }
}
