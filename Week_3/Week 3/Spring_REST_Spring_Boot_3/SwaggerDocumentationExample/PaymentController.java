import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Schema(description = "Represents a payment transaction")
class Payment {

    @Schema(description = "Unique payment identifier", example = "PAY-4501")
    private String id;

    @Schema(description = "Payer name", example = "Jeeva Elango")
    private String payer;

    @Schema(description = "Payment amount in INR", example = "2500.00")
    private double amount;

    @Schema(description = "Payment status", example = "SUCCESS")
    private String status;

    public Payment() {
    }

    public Payment(String id, String payer, double amount, String status) {
        this.id = id;
        this.payer = payer;
        this.amount = amount;
        this.status = status;
    }

    public String getId() { return id; }
    public String getPayer() { return payer; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
}

@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payment API", description = "Endpoints to manage payment transactions")
class PaymentController {

    private final Map<String, Payment> ledger = new HashMap<>();

    public PaymentController() {
        ledger.put("PAY-4501", new Payment("PAY-4501", "Jeeva Elango", 2500, "SUCCESS"));
        ledger.put("PAY-4502", new Payment("PAY-4502", "Meera Iyer", 5670, "SUCCESS"));
    }

    @Operation(
            summary = "Fetch a payment by ID",
            description = "Returns the payment record for the given payment identifier"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment found",
                    content = @Content(schema = @Schema(implementation = Payment.class))),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPayment(
            @Parameter(description = "Payment identifier", example = "PAY-4501")
            @PathVariable String id) {
        Payment payment = ledger.get(id);
        if (payment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(payment);
    }

    @Operation(summary = "Record a new payment")
    @ApiResponse(responseCode = "201", description = "Payment recorded")
    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        ledger.put(payment.getId(), payment);
        return ResponseEntity.status(201).body(payment);
    }
}

@SpringBootApplication
public class PaymentControllerTest {
    public static void main(String[] args) {
        SpringApplication.run(PaymentControllerTest.class, args);
        System.out.println("Swagger UI available at http://localhost:8080/swagger-ui.html");
        System.out.println("OpenAPI spec available at http://localhost:8080/v3/api-docs");
        System.out.println("Requires springdoc-openapi-starter-webmvc-ui dependency");
    }
}
