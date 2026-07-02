import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

class Book {
    private Long id;
    private String title;
    private String author;
    private double price;

    public Book() {
    }

    public Book(Long id, String title, String author, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setPrice(double price) { this.price = price; }
}

@RestController
@RequestMapping("/api/books")
class BookController {

    private final Map<Long, Book> library = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public BookController() {
        addSample("Effective Java", "Joshua Bloch", 1200);
        addSample("Spring in Action", "Craig Walls", 950);
        addSample("Clean Code", "Robert Martin", 780);
    }

    private void addSample(String title, String author, double price) {
        long id = counter.incrementAndGet();
        library.put(id, new Book(id, title, author, price));
    }

    @GetMapping
    public Collection<Book> getAllBooks() {
        return library.values();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book book = library.get(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        long id = counter.incrementAndGet();
        book.setId(id);
        library.put(id, book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updated) {
        Book existing = library.get(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        existing.setTitle(updated.getTitle());
        existing.setAuthor(updated.getAuthor());
        existing.setPrice(updated.getPrice());
        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (library.remove(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

@SpringBootApplication
public class BookControllerTest {
    public static void main(String[] args) {
        SpringApplication.run(BookControllerTest.class, args);
        System.out.println("Book API available at http://localhost:8080/api/books");
    }
}
