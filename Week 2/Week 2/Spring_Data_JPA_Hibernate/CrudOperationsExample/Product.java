import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Optional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

@Entity
class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private double price;

    public Product() {
    }

    public Product(String name, String category, double price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }
    public void setName(String name) { this.name = name; }

    public String toString() {
        return "Product{id=" + id + ", name=" + name
                + ", category=" + category + ", price=" + price + "}";
    }
}

interface ProductRepository extends JpaRepository<Product, Long> {
}

@SpringBootApplication
public class ProductCrudTest {

    public static void main(String[] args) {
        SpringApplication.run(ProductCrudTest.class, args);
    }

    @Bean
    public CommandLineRunner run(ProductRepository repository) {
        return args -> {
            Product p1 = repository.save(new Product("Laptop", "Electronics", 55000));
            Product p2 = repository.save(new Product("Headphones", "Electronics", 2500));
            Product p3 = repository.save(new Product("Notebook", "Stationery", 150));

            System.out.println("After save:");
            repository.findAll().forEach(System.out::println);

            System.out.println();
            System.out.println("Find by ID " + p1.getId() + ":");
            Optional<Product> found = repository.findById(p1.getId());
            found.ifPresent(System.out::println);

            System.out.println();
            System.out.println("Updating headphones price to 1999...");
            p2.setPrice(1999);
            repository.save(p2);
            repository.findById(p2.getId()).ifPresent(System.out::println);

            System.out.println();
            System.out.println("Deleting notebook with ID " + p3.getId());
            repository.deleteById(p3.getId());

            System.out.println();
            System.out.println("Final list of products:");
            repository.findAll().forEach(System.out::println);

            System.out.println();
            System.out.println("Total products remaining: " + repository.count());
        };
    }
}
