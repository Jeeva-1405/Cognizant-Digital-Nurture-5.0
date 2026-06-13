interface PaymentProcessor {
    void processPayment(double amount);
}

class PayPalGateway {
    public void makePayment(double amount) {
        System.out.println("Processing payment of $" + amount + " via PayPal.");
    }
}

class StripeGateway {
    public void charge(double amount) {
        System.out.println("Charging $" + amount + " through Stripe.");
    }
}

class RazorpayGateway {
    public void initiatePayment(double amount) {
        System.out.println("Initiating payment of Rs." + amount + " via Razorpay.");
    }
}

class PayPalAdapter implements PaymentProcessor {
    private PayPalGateway payPal;

    public PayPalAdapter(PayPalGateway payPal) {
        this.payPal = payPal;
    }

    public void processPayment(double amount) {
        payPal.makePayment(amount);
    }
}

class StripeAdapter implements PaymentProcessor {
    private StripeGateway stripe;

    public StripeAdapter(StripeGateway stripe) {
        this.stripe = stripe;
    }

    public void processPayment(double amount) {
        stripe.charge(amount);
    }
}

class RazorpayAdapter implements PaymentProcessor {
    private RazorpayGateway razorpay;

    public RazorpayAdapter(RazorpayGateway razorpay) {
        this.razorpay = razorpay;
    }

    public void processPayment(double amount) {
        razorpay.initiatePayment(amount);
    }
}

public class PaymentProcessorTest {
    public static void main(String[] args) {
        PaymentProcessor paypal = new PayPalAdapter(new PayPalGateway());
        PaymentProcessor stripe = new StripeAdapter(new StripeGateway());
        PaymentProcessor razorpay = new RazorpayAdapter(new RazorpayGateway());

        paypal.processPayment(150.00);
        stripe.processPayment(200.50);
        razorpay.processPayment(5000.00);
    }
}
