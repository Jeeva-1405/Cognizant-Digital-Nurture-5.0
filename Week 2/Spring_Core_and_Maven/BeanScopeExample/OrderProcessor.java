import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

class OrderProcessor {
    private int orderCount;

    public OrderProcessor() {
        this.orderCount = 0;
    }

    public void placeOrder(String customer) {
        orderCount++;
        System.out.println("Order " + orderCount + " placed by " + customer
                + " (instance=" + System.identityHashCode(this) + ")");
    }
}

class OrderTracker {
    public void track(String orderId) {
        System.out.println("Tracking order " + orderId
                + " (instance=" + System.identityHashCode(this) + ")");
    }
}

@Configuration
class OrderConfig {
    @Bean
    @Scope("singleton")
    public OrderProcessor orderProcessor() {
        return new OrderProcessor();
    }

    @Bean
    @Scope("prototype")
    public OrderTracker orderTracker() {
        return new OrderTracker();
    }
}

public class OrderProcessorTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(OrderConfig.class);

        System.out.println("Singleton scope test:");
        OrderProcessor processor1 = context.getBean(OrderProcessor.class);
        OrderProcessor processor2 = context.getBean(OrderProcessor.class);

        processor1.placeOrder("Karthik");
        processor2.placeOrder("Divya");

        if (processor1 == processor2) {
            System.out.println("Same singleton bean returned both times");
        }

        System.out.println();
        System.out.println("Prototype scope test:");
        OrderTracker tracker1 = context.getBean(OrderTracker.class);
        OrderTracker tracker2 = context.getBean(OrderTracker.class);

        tracker1.track("ORD-501");
        tracker2.track("ORD-502");

        if (tracker1 != tracker2) {
            System.out.println("Different prototype instances created");
        }

        context.close();
    }
}
