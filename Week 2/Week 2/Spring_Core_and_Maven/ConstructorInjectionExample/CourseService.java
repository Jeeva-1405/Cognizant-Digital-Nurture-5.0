import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

interface CourseRepository {
    String findCourseById(int id);
}

class CourseRepositoryImpl implements CourseRepository {
    public String findCourseById(int id) {
        if (id == 101) return "Spring Core Fundamentals";
        if (id == 102) return "Spring Data JPA";
        if (id == 103) return "Spring Boot REST";
        return "Course not found";
    }
}

class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void displayCourse(int id) {
        String course = courseRepository.findCourseById(id);
        System.out.println("Course ID " + id + " : " + course);
    }
}

@Configuration
class CourseConfig {
    @Bean
    public CourseRepository courseRepository() {
        return new CourseRepositoryImpl();
    }

    @Bean
    public CourseService courseService() {
        return new CourseService(courseRepository());
    }
}

public class CourseServiceTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(CourseConfig.class);

        CourseService service = context.getBean(CourseService.class);

        service.displayCourse(101);
        service.displayCourse(102);
        service.displayCourse(103);
        service.displayCourse(999);

        context.close();
    }
}
