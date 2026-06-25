import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class GreetingService {
    private String message;

    public GreetingService() {
        this.message = "Hello from Spring IoC container";
    }

    public void greet(String name) {
        System.out.println(message + ", " + name);
    }
}

@Configuration
class AppConfig {
    @Bean
    public GreetingService greetingService() {
        return new GreetingService();
    }
}

public class HelloSpringTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        GreetingService service = context.getBean(GreetingService.class);

        service.greet("Jeeva");
        service.greet("Ravi");
        service.greet("Priya");

        context.close();
    }
}
