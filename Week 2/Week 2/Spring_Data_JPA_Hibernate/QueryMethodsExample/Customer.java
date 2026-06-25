import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

@Entity
class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;
    private int age;

    public Customer() {
    }

    public Customer(String name, String city, int age) {
        this.name = name;
        this.city = city;
        this.age = age;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public int getAge() { return age; }

    public String toString() {
        return "Customer{id=" + id + ", name=" + name
                + ", city=" + city + ", age=" + age + "}";
    }
}

interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByCity(String city);
    List<Customer> findByAgeGreaterThan(int age);
    List<Customer> findByNameStartingWith(String prefix);
    List<Customer> findByCityAndAgeLessThan(String city, int age);
}

@SpringBootApplication
public class CustomerQueryTest {

    public static void main(String[] args) {
        SpringApplication.run(CustomerQueryTest.class, args);
    }

    @Bean
    public CommandLineRunner run(CustomerRepository repository) {
        return args -> {
            repository.save(new Customer("Karthik Raj", "Chennai", 28));
            repository.save(new Customer("Divya Pillai", "Bangalore", 32));
            repository.save(new Customer("Rahul Mehta", "Chennai", 24));
            repository.save(new Customer("Sneha Reddy", "Hyderabad", 35));
            repository.save(new Customer("Karuna Singh", "Chennai", 41));

            System.out.println("Customers from Chennai:");
            repository.findByCity("Chennai").forEach(System.out::println);

            System.out.println();
            System.out.println("Customers older than 30:");
            repository.findByAgeGreaterThan(30).forEach(System.out::println);

            System.out.println();
            System.out.println("Customers whose name starts with K:");
            repository.findByNameStartingWith("K").forEach(System.out::println);

            System.out.println();
            System.out.println("Chennai customers under 30:");
            repository.findByCityAndAgeLessThan("Chennai", 30).forEach(System.out::println);
        };
    }
}
