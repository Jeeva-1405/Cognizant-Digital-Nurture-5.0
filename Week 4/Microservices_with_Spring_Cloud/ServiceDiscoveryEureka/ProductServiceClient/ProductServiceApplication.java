import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}

@RestController
class ProductController {

    private final Map<String, String> catalog = new HashMap<>();

    public ProductController() {
        catalog.put("P101", "Wireless Mouse");
        catalog.put("P102", "Mechanical Keyboard");
        catalog.put("P103", "27-inch Monitor");
    }

    @GetMapping("/products/{id}")
    public String getProduct(@PathVariable String id) {
        return catalog.getOrDefault(id, "Product not found");
    }

    @GetMapping("/products/health")
    public String health() {
        return "Product Service is UP and registered with Eureka";
    }
}
