import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
class StudentController {

    private final List<String> students = List.of(
            "Jeeva Elango", "Kavya Ramesh", "Arjun Nair",
            "Meera Iyer", "Vikram Singh", "Anita Krishnan"
    );

    @GetMapping("/{index}")
    public String getStudentByIndex(@PathVariable int index) {
        if (index < 0 || index >= students.size()) {
            return "Student index out of range";
        }
        return "Student at position " + index + " : " + students.get(index);
    }

    @GetMapping("/name/{name}")
    public String greetStudent(@PathVariable String name) {
        return "Hello " + name + ", your record is being processed";
    }

    @GetMapping("/search")
    public String searchStudent(@RequestParam String name) {
        for (String student : students) {
            if (student.toLowerCase().contains(name.toLowerCase())) {
                return "Match found: " + student;
            }
        }
        return "No student matching '" + name + "'";
    }

    @GetMapping("/filter")
    public String filterByCourseAndYear(
            @RequestParam String course,
            @RequestParam(defaultValue = "2027") int year) {
        return "Filtering students from " + course + " batch of " + year;
    }

    @GetMapping("/city/{city}/dept/{dept}")
    public String getByCityAndDept(@PathVariable String city, @PathVariable String dept) {
        return "Listing students from " + city + " in the " + dept + " department";
    }
}

@SpringBootApplication
public class StudentControllerTest {
    public static void main(String[] args) {
        SpringApplication.run(StudentControllerTest.class, args);
        System.out.println("Server running on http://localhost:8080");
        System.out.println("Try /students/2");
        System.out.println("Try /students/name/Jeeva");
        System.out.println("Try /students/search?name=Kavya");
        System.out.println("Try /students/filter?course=ECE&year=2027");
        System.out.println("Try /students/city/Chennai/dept/ECE");
    }
}
