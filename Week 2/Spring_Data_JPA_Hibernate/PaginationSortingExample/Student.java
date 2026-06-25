import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

@Entity
class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String course;
    private double cgpa;

    public Student() {
    }

    public Student(String name, String course, double cgpa) {
        this.name = name;
        this.course = course;
        this.cgpa = cgpa;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public double getCgpa() { return cgpa; }

    public String toString() {
        return "Student{name=" + name + ", course=" + course + ", cgpa=" + cgpa + "}";
    }
}

interface StudentRepository extends JpaRepository<Student, Long> {
}

@SpringBootApplication
public class StudentPaginationTest {

    public static void main(String[] args) {
        SpringApplication.run(StudentPaginationTest.class, args);
    }

    @Bean
    public CommandLineRunner run(StudentRepository repository) {
        return args -> {
            repository.save(new Student("Jeeva Elango", "ECE", 7.36));
            repository.save(new Student("Mohan Raj", "CSE", 8.42));
            repository.save(new Student("Anita Krishnan", "ECE", 9.10));
            repository.save(new Student("Suresh Babu", "MECH", 6.85));
            repository.save(new Student("Lakshmi N", "CSE", 9.45));
            repository.save(new Student("Karthik R", "CSE", 7.92));
            repository.save(new Student("Divya P", "ECE", 8.60));
            repository.save(new Student("Vikram S", "MECH", 7.10));

            System.out.println("All students sorted by CGPA descending:");
            repository.findAll(Sort.by(Sort.Direction.DESC, "cgpa"))
                    .forEach(System.out::println);

            System.out.println();
            System.out.println("Page 1 (3 students per page), sorted by name:");
            Pageable firstPage = PageRequest.of(0, 3, Sort.by("name"));
            Page<Student> page1 = repository.findAll(firstPage);
            page1.getContent().forEach(System.out::println);

            System.out.println();
            System.out.println("Page 2 (3 students per page), sorted by name:");
            Pageable secondPage = PageRequest.of(1, 3, Sort.by("name"));
            Page<Student> page2 = repository.findAll(secondPage);
            page2.getContent().forEach(System.out::println);

            System.out.println();
            System.out.println("Total pages: " + page1.getTotalPages());
            System.out.println("Total elements: " + page1.getTotalElements());
        };
    }
}
