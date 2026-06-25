import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class AddressService {
    public String getAddress(String city) {
        return "Registered office in " + city;
    }
}

class StudentService {
    private AddressService addressService;
    private String studentName;

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void showStudentInfo() {
        System.out.println("Student: " + studentName);
        System.out.println("Address: " + addressService.getAddress("Chennai"));
    }
}

@Configuration
class StudentConfig {
    @Bean
    public AddressService addressService() {
        return new AddressService();
    }

    @Bean
    public StudentService studentService() {
        StudentService service = new StudentService();
        service.setAddressService(addressService());
        service.setStudentName("Kavya Ramesh");
        return service;
    }
}

public class StudentServiceTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(StudentConfig.class);

        StudentService service = context.getBean(StudentService.class);
        service.showStudentInfo();

        context.close();
    }
}
