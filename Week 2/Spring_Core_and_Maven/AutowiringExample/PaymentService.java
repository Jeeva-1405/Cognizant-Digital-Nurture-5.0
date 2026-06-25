import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
class TransactionLogger {
    public void log(String message) {
        System.out.println("[TXN] " + message);
    }
}

@Component
class PaymentService {
    private final TransactionLogger logger;

    @Autowired
    public PaymentService(TransactionLogger logger) {
        this.logger = logger;
    }

    public void processPayment(String customer, double amount) {
        logger.log("Processing payment for " + customer + " of Rs." + amount);
        System.out.println("Payment of Rs." + amount + " received from " + customer);
    }
}

@Configuration
@ComponentScan(basePackageClasses = PaymentService.class)
class PaymentConfig {
}

public class PaymentServiceTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(PaymentConfig.class);

        PaymentService service = context.getBean(PaymentService.class);

        service.processPayment("Arjun Nair", 4500.00);
        service.processPayment("Meera Iyer", 12750.50);
        service.processPayment("Vikram Singh", 899.00);

        context.close();
    }
}
