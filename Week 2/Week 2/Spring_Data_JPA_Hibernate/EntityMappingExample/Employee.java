import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

@Entity
@Table(name = "employees")
class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String name;

    @Column(name = "department")
    private String department;

    @Column(name = "salary")
    private double salary;

    public Employee() {
    }

    public Employee(String name, String department, double salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }

    public String toString() {
        return "Employee{id=" + id + ", name=" + name
                + ", department=" + department + ", salary=" + salary + "}";
    }
}

interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

@SpringBootApplication
public class EmployeeEntityTest {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeEntityTest.class, args);
    }

    @Bean
    public CommandLineRunner run(EmployeeRepository repository) {
        return args -> {
            repository.save(new Employee("Jeeva Elango", "Engineering", 75000));
            repository.save(new Employee("Ravi Kumar", "Design", 65000));
            repository.save(new Employee("Priya Sharma", "Operations", 90000));

            System.out.println("All employees saved:");
            repository.findAll().forEach(System.out::println);
        };
    }
}
