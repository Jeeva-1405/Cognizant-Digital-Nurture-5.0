import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GreetingController {

    @GetMapping("/greet")
    public String greet() {
        return "Welcome to Spring Boot 3 REST API";
    }

    @GetMapping("/greet/personal")
    public String personalGreeting(@RequestParam String name) {
        return "Hello " + name + ", nice to have you here";
    }

    @GetMapping("/status")
    public String status() {
        return "Service is up and running";
    }
}

@SpringBootApplication
public class HelloRestTest {
    public static void main(String[] args) {
        SpringApplication.run(HelloRestTest.class, args);
        System.out.println("Server started on http://localhost:8080");
        System.out.println("Try http://localhost:8080/greet");
        System.out.println("Try http://localhost:8080/greet/personal?name=Jeeva");
        System.out.println("Try http://localhost:8080/status");
    }
}
