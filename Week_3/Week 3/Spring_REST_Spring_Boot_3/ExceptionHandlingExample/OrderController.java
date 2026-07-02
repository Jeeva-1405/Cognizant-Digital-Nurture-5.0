import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}

class InvalidOrderException extends RuntimeException {
    public InvalidOrderException(String message) {
        super(message);
    }
}

class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;

    public ErrorResponse(int status, String error, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getError() { return error; }
    public String getMessage() { return message; }
}

class Order {
    private Long id;
    private String customer;
    private double amount;

    public Order() {
    }

    public Order(Long id, String customer, double amount) {
        this.id = id;
        this.customer = customer;
        this.amount = amount;
    }

    public Long getId() { return id; }
    public String getCustomer() { return customer; }
    public double getAmount() { return amount; }
}

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(OrderNotFoundException ex) {
        ErrorResponse body = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(), "Not Found", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<ErrorResponse> handleInvalid(InvalidOrderException ex) {
        ErrorResponse body = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), "Bad Request", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        ErrorResponse body = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@RestController
@RequestMapping("/api/orders")
class OrderController {

    private final Map<Long, Order> orders = new HashMap<>();

    public OrderController() {
        orders.put(101L, new Order(101L, "Meera Iyer", 4500));
        orders.put(102L, new Order(102L, "Vikram Singh", 12750));
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        Order order = orders.get(id);
        if (order == null) {
            throw new OrderNotFoundException("Order with ID " + id + " does not exist");
        }
        return order;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        if (order.getAmount() <= 0) {
            throw new InvalidOrderException("Order amount must be greater than zero");
        }
        if (order.getCustomer() == null || order.getCustomer().isBlank()) {
            throw new InvalidOrderException("Customer name is required");
        }
        orders.put(order.getId(), order);
        return order;
    }
}

@SpringBootApplication
public class OrderControllerTest {
    public static void main(String[] args) {
        SpringApplication.run(OrderControllerTest.class, args);
        System.out.println("Order service on http://localhost:8080/api/orders");
        System.out.println("Try GET /api/orders/999 for a Not Found response");
        System.out.println("Try POST with amount 0 for a Bad Request response");
    }
}
