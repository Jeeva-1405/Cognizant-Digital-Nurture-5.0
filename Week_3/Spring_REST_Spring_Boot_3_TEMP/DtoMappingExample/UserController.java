import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

class UserEntity {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private String phoneNumber;
    private double salary;

    public UserEntity(Long id, String firstName, String lastName, String email,
                      String passwordHash, String phoneNumber, double salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public double getSalary() { return salary; }
}

class UserDto {
    private Long id;
    private String fullName;
    private String email;

    public UserDto(Long id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
}

class UserDetailsDto {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;

    public UserDetailsDto(Long id, String fullName, String email, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
}

class UserMapper {
    public static UserDto toSummary(UserEntity entity) {
        String fullName = entity.getFirstName() + " " + entity.getLastName();
        return new UserDto(entity.getId(), fullName, entity.getEmail());
    }

    public static UserDetailsDto toDetails(UserEntity entity) {
        String fullName = entity.getFirstName() + " " + entity.getLastName();
        return new UserDetailsDto(entity.getId(), fullName, entity.getEmail(),
                entity.getPhoneNumber());
    }
}

@RestController
@RequestMapping("/api/users")
class UserController {

    private final List<UserEntity> users = new ArrayList<>();

    public UserController() {
        users.add(new UserEntity(1L, "Jeeva", "Elango", "jeeva@example.com",
                "hashed_pwd_1", "9876543210", 75000));
        users.add(new UserEntity(2L, "Kavya", "Ramesh", "kavya@example.com",
                "hashed_pwd_2", "9123456780", 68000));
        users.add(new UserEntity(3L, "Arjun", "Nair", "arjun@example.com",
                "hashed_pwd_3", "9988776655", 82000));
    }

    @GetMapping
    public List<UserDto> listUsers() {
        List<UserDto> result = new ArrayList<>();
        for (UserEntity user : users) {
            result.add(UserMapper.toSummary(user));
        }
        return result;
    }

    @GetMapping("/{id}")
    public UserDetailsDto getUserDetails(@PathVariable Long id) {
        for (UserEntity user : users) {
            if (user.getId().equals(id)) {
                return UserMapper.toDetails(user);
            }
        }
        return null;
    }
}

@SpringBootApplication
public class UserControllerTest {
    public static void main(String[] args) {
        SpringApplication.run(UserControllerTest.class, args);
        System.out.println("User API running on http://localhost:8080/api/users");
        System.out.println("Passwords and internal fields are never exposed to clients");
    }
}
