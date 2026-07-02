import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

class Customer {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Provide a valid email address")
    private String email;

    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Enter a valid 10 digit Indian mobile number")
    private String mobile;

    @Min(value = 18, message = "Customer must be at least 18 years old")
    private int age;

    public Customer() {
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMobile() { return mobile; }
    public int getAge() { return age; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public void setAge(int age) { this.age = age; }
}

@RestController
@RequestMapping("/api/customers")
class CustomerController {

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@Valid @RequestBody Customer customer) {
        String reply = "Customer " + customer.getName()
                + " registered successfully with email " + customer.getEmail();
        return ResponseEntity.status(HttpStatus.CREATED).body(reply);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}

@SpringBootApplication
public class CustomerControllerTest {
    public static void main(String[] args) {
        SpringApplication.run(CustomerControllerTest.class, args);
        System.out.println("Registration endpoint: POST http://localhost:8080/api/customers/register");
        System.out.println("Sample body:");
        System.out.println("{ \"name\": \"Jeeva Elango\", \"email\": \"jeeva@test.com\",");
        System.out.println("  \"mobile\": \"9876543210\", \"age\": 21 }");
    }
}
