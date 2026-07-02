import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

class Course {
    private int id;
    private String title;
    private String instructor;

    public Course(int id, String title, String instructor) {
        this.id = id;
        this.title = title;
        this.instructor = instructor;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getInstructor() { return instructor; }
}

@RestController
@RequestMapping("/api/courses")
class CourseController {

    private final List<Course> courses = List.of(
            new Course(1, "Spring Boot Essentials", "Ravi Kumar"),
            new Course(2, "React Fundamentals", "Priya Sharma"),
            new Course(3, "Java Concurrency", "Karthik Raj")
    );

    @GetMapping("/{id}")
    public EntityModel<Course> getCourse(@PathVariable int id) {
        Course course = courses.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Course not found"));

        EntityModel<Course> model = EntityModel.of(course);
        model.add(linkTo(methodOn(CourseController.class).getCourse(id)).withSelfRel());
        model.add(linkTo(methodOn(CourseController.class).allCourses()).withRel("all-courses"));
        return model;
    }

    @GetMapping
    public CollectionModel<EntityModel<Course>> allCourses() {
        List<EntityModel<Course>> models = new ArrayList<>();
        for (Course course : courses) {
            EntityModel<Course> model = EntityModel.of(course);
            model.add(linkTo(methodOn(CourseController.class).getCourse(course.getId()))
                    .withSelfRel());
            models.add(model);
        }
        CollectionModel<EntityModel<Course>> collection = CollectionModel.of(models);
        collection.add(linkTo(methodOn(CourseController.class).allCourses()).withSelfRel());
        return collection;
    }
}

@SpringBootApplication
public class CourseControllerTest {
    public static void main(String[] args) {
        SpringApplication.run(CourseControllerTest.class, args);
        System.out.println("HATEOAS enabled endpoints:");
        System.out.println("http://localhost:8080/api/courses");
        System.out.println("http://localhost:8080/api/courses/1");
        System.out.println("Requires spring-boot-starter-hateoas dependency");
    }
}
