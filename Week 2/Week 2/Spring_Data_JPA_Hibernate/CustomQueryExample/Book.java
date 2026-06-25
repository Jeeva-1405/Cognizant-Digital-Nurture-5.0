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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Entity
class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private double price;
    private int yearPublished;

    public Book() {
    }

    public Book(String title, String author, double price, int yearPublished) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.yearPublished = yearPublished;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }

    public String toString() {
        return "Book{title=" + title + ", author=" + author
                + ", price=" + price + ", year=" + yearPublished + "}";
    }
}

interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.price BETWEEN :low AND :high")
    List<Book> findBooksInPriceRange(@Param("low") double low, @Param("high") double high);

    @Query("SELECT b FROM Book b WHERE b.yearPublished >= :year ORDER BY b.yearPublished DESC")
    List<Book> findBooksPublishedAfter(@Param("year") int year);

    @Query("SELECT b FROM Book b WHERE LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> searchByAuthor(@Param("keyword") String keyword);

    @Query("SELECT COUNT(b) FROM Book b WHERE b.author = :author")
    long countBooksByAuthor(@Param("author") String author);
}

@SpringBootApplication
public class BookCustomQueryTest {

    public static void main(String[] args) {
        SpringApplication.run(BookCustomQueryTest.class, args);
    }

    @Bean
    public CommandLineRunner run(BookRepository repository) {
        return args -> {
            repository.save(new Book("Spring in Action", "Craig Walls", 850, 2022));
            repository.save(new Book("Effective Java", "Joshua Bloch", 1200, 2018));
            repository.save(new Book("Java Concurrency", "Brian Goetz", 950, 2006));
            repository.save(new Book("Clean Code", "Robert Martin", 700, 2008));
            repository.save(new Book("Hibernate in Practice", "Madhusudhan Konda", 1100, 2023));

            System.out.println("Books priced between 800 and 1100:");
            repository.findBooksInPriceRange(800, 1100).forEach(System.out::println);

            System.out.println();
            System.out.println("Books published in 2018 or later:");
            repository.findBooksPublishedAfter(2018).forEach(System.out::println);

            System.out.println();
            System.out.println("Books by authors containing 'Bloch':");
            repository.searchByAuthor("Bloch").forEach(System.out::println);

            System.out.println();
            long count = repository.countBooksByAuthor("Joshua Bloch");
            System.out.println("Total books by Joshua Bloch: " + count);
        };
    }
}
