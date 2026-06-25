import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Entity
@EntityListeners(AuditingEntityListener.class)
class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String holderName;
    private double balance;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Account() {
    }

    public Account(String holderName, double balance) {
        this.holderName = holderName;
        this.balance = balance;
    }

    public Long getId() { return id; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setBalance(double balance) { this.balance = balance; }

    public String toString() {
        return "Account{id=" + id + ", holder=" + holderName
                + ", balance=" + balance
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + "}";
    }
}

interface AccountRepository extends JpaRepository<Account, Long> {
}

@SpringBootApplication
@EnableJpaAuditing
public class AccountAuditingTest {

    public static void main(String[] args) {
        SpringApplication.run(AccountAuditingTest.class, args);
    }

    @Bean
    public CommandLineRunner run(AccountRepository repository) {
        return args -> {
            Account account = repository.save(new Account("Mohan Raj", 15000));
            System.out.println("After create:");
            System.out.println(account);

            Thread.sleep(1500);

            account.setBalance(20000);
            Account updated = repository.save(account);

            System.out.println();
            System.out.println("After update:");
            System.out.println(updated);

            System.out.println();
            Optional<Account> fetched = repository.findById(updated.getId());
            fetched.ifPresent(a -> {
                System.out.println("Fetched record:");
                System.out.println(a);
            });
        };
    }
}
