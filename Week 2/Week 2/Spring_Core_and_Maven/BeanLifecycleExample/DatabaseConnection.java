import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class DatabaseConnection {
    private String url;
    private boolean connected;

    public DatabaseConnection() {
        System.out.println("Constructor: DatabaseConnection object created");
        this.url = "jdbc:mysql://localhost:3306/dn5db";
        this.connected = false;
    }

    @PostConstruct
    public void openConnection() {
        connected = true;
        System.out.println("PostConstruct: Connection opened to " + url);
    }

    public void executeQuery(String sql) {
        if (connected) {
            System.out.println("Executing: " + sql);
        } else {
            System.out.println("Connection not available");
        }
    }

    @PreDestroy
    public void closeConnection() {
        connected = false;
        System.out.println("PreDestroy: Connection to " + url + " closed");
    }
}

@Configuration
class ConnectionConfig {
    @Bean
    public DatabaseConnection databaseConnection() {
        return new DatabaseConnection();
    }
}

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ConnectionConfig.class);

        DatabaseConnection connection = context.getBean(DatabaseConnection.class);

        connection.executeQuery("SELECT * FROM employees");
        connection.executeQuery("SELECT * FROM departments");

        context.close();
    }
}
