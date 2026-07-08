import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}

@FeignClient(name = "product-service")
interface ProductClient {

    @GetMapping("/products/{id}")
    String getProductName(@PathVariable("id") String id);
}

class OrderDetails {
    private String orderId;
    private String productId;
    private String productName;
    private int quantity;

    public OrderDetails(String orderId, String productId, String productName, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getOrderId() { return orderId; }
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }

    public String toString() {
        return "Order{orderId=" + orderId + ", product=" + productName
            + ", productId=" + productId + ", qty=" + quantity + "}";
    }
}

@RestController
class OrderController {

    private final ProductClient productClient;

    @Autowired
    public OrderController(ProductClient productClient) {
        this.productClient = productClient;
    }

    @GetMapping("/orders/{orderId}/product/{productId}")
    public OrderDetails placeOrder(@PathVariable String orderId, @PathVariable String productId) {
        String productName = productClient.getProductName(productId);
        return new OrderDetails(orderId, productId, productName, 1);
    }
}
