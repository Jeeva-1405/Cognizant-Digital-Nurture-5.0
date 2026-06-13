interface CustomerRepository {
    String findCustomerById(int id);
}

class CustomerRepositoryImpl implements CustomerRepository {
    public String findCustomerById(int id) {
        if (id == 1) return "Jeeva Elango";
        if (id == 2) return "Ravi Kumar";
        if (id == 3) return "Priya Sharma";
        return "Customer not found";
    }
}

class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void displayCustomer(int id) {
        String customerName = customerRepository.findCustomerById(id);
        System.out.println("Customer ID " + id + " : " + customerName);
    }
}

public class CustomerServiceTest {
    public static void main(String[] args) {
        CustomerRepository repository = new CustomerRepositoryImpl();
        CustomerService service = new CustomerService(repository);

        service.displayCustomer(1);
        service.displayCustomer(2);
        service.displayCustomer(3);
        service.displayCustomer(99);
    }
}
