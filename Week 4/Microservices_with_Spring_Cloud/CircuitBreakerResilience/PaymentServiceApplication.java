import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

@Service
class PaymentGatewayService {

    private final RestTemplate restTemplate;

    @Autowired
    public PaymentGatewayService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "paymentGateway", fallbackMethod = "paymentFallback")
    public String processPayment(String orderId, double amount) {
        String url = "http://external-payment-gateway/charge?orderId=" + orderId + "&amount=" + amount;
        return restTemplate.getForObject(url, String.class);
    }

    public String paymentFallback(String orderId, double amount, Throwable throwable) {
        return "Payment queued for retry - order " + orderId
            + " amount " + amount + ". Gateway unavailable: " + throwable.getMessage();
    }
}

@RestController
class PaymentController {

    private final PaymentGatewayService paymentGatewayService;

    @Autowired
    public PaymentController(PaymentGatewayService paymentGatewayService) {
        this.paymentGatewayService = paymentGatewayService;
    }

    @GetMapping("/payments/{orderId}/charge/{amount}")
    public String charge(@PathVariable String orderId, @PathVariable double amount) {
        return paymentGatewayService.processPayment(orderId, amount);
    }
}
