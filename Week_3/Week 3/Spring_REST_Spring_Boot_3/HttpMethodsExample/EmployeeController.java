import java.util.ArrayList;
import java.util.List;
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

class Employee {
    private int id;
    private String name;
    private String designation;

    public Employee() {
    }

    public Employee(int id, String name, String designation) {
        this.id = id;
        this.name = name;
        this.designation = designation;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDesignation() { return designation; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDesignation(String designation) { this.designation = designation; }
}

@RestController
@RequestMapping("/api/employees")
class EmployeeController {

    private final List<Employee> employees = new ArrayList<>();

    public EmployeeController() {
        employees.add(new Employee(1, "Jeeva Elango", "Software Engineer"));
        employees.add(new Employee(2, "Ravi Kumar", "Team Lead"));
        employees.add(new Employee(3, "Priya Sharma", "Product Manager"));
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employees;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        return employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        employees.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee updated) {
        for (Employee emp : employees) {
            if (emp.getId() == id) {
                emp.setName(updated.getName());
                emp.setDesignation(updated.getDesignation());
                return ResponseEntity.ok(emp);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        boolean removed = employees.removeIf(e -> e.getId() == id);
        if (removed) {
            return ResponseEntity.ok("Employee " + id + " removed");
        }
        return ResponseEntity.notFound().build();
    }
}

@SpringBootApplication
public class EmployeeControllerTest {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeControllerTest.class, args);
        System.out.println("Server running on http://localhost:8080/api/employees");
    }
}
