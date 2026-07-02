import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@JacksonXmlRootElement(localName = "product")
class Product {
    private int id;
    private String name;
    private String category;
    private double price;

    public Product() {
    }

    public Product(int id, String name, String category, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
}

@RestController
@RequestMapping("/api/products")
class ProductController {

    private final List<Product> catalog = List.of(
            new Product(1, "Wireless Mouse", "Electronics", 899),
            new Product(2, "Cotton T-Shirt", "Apparel", 549),
            new Product(3, "Steel Water Bottle", "Kitchen", 399),
            new Product(4, "Notebook Pack", "Stationery", 199)
    );

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Product> allProducts() {
        return catalog;
    }

    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Product productById(@PathVariable int id) {
        return catalog.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
}

@SpringBootApplication
public class ProductControllerTest {
    public static void main(String[] args) {
        SpringApplication.run(ProductControllerTest.class, args);
        System.out.println("Products available in both JSON and XML formats");
        System.out.println("Add header: Accept: application/json");
        System.out.println("Add header: Accept: application/xml");
        System.out.println("Endpoint: http://localhost:8080/api/products");
        System.out.println("Requires jackson-dataformat-xml on the classpath");
    }
}
