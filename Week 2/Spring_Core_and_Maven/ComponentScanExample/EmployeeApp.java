import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
class EmployeeRepository {
    public String fetchEmployee(int id) {
        if (id == 1) return "Anita Krishnan - HR Lead";
        if (id == 2) return "Suresh Babu - Software Engineer";
        if (id == 3) return "Lakshmi Narayanan - Project Manager";
        return "Employee record missing";
    }
}

@Service
class EmployeeService {
    private final EmployeeRepository repository;

    @Autowired
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public void showEmployee(int id) {
        System.out.println("Employee " + id + " : " + repository.fetchEmployee(id));
    }
}

@Configuration
@ComponentScan(basePackageClasses = EmployeeService.class)
class EmployeeAppConfig {
}

public class EmployeeAppTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(EmployeeAppConfig.class);

        EmployeeService service = context.getBean(EmployeeService.class);

        service.showEmployee(1);
        service.showEmployee(2);
        service.showEmployee(3);
        service.showEmployee(10);

        context.close();
    }
}
