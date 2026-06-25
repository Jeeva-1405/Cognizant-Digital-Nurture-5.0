import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

@Entity
class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Worker> workers = new ArrayList<>();

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public List<Worker> getWorkers() { return workers; }

    public void addWorker(Worker worker) {
        workers.add(worker);
        worker.setDepartment(this);
    }

    public String toString() {
        return "Department{id=" + id + ", name=" + name
                + ", workerCount=" + workers.size() + "}";
    }
}

@Entity
class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String role;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Worker() {
    }

    public Worker(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String toString() {
        return "Worker{name=" + name + ", role=" + role + "}";
    }
}

interface DepartmentRepository extends JpaRepository<Department, Long> {
}

@SpringBootApplication
public class DepartmentMappingTest {

    public static void main(String[] args) {
        SpringApplication.run(DepartmentMappingTest.class, args);
    }

    @Bean
    public CommandLineRunner run(DepartmentRepository repository) {
        return args -> {
            Department engineering = new Department("Engineering");
            engineering.addWorker(new Worker("Arjun Nair", "Backend Developer"));
            engineering.addWorker(new Worker("Meera Iyer", "Frontend Developer"));
            engineering.addWorker(new Worker("Vikram Singh", "DevOps Engineer"));

            Department finance = new Department("Finance");
            finance.addWorker(new Worker("Anita Krishnan", "Accountant"));
            finance.addWorker(new Worker("Suresh Babu", "Auditor"));

            repository.save(engineering);
            repository.save(finance);

            System.out.println("All departments with their workers:");
            repository.findAll().forEach(dept -> {
                System.out.println(dept);
                dept.getWorkers().forEach(w -> System.out.println("   " + w));
            });
        };
    }
}
