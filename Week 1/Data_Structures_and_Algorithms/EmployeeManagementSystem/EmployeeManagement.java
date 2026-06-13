class Employee {
    int employeeId;
    String name;
    String position;
    double salary;

    public Employee(int employeeId, String name, String position, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public String toString() {
        return "Employee{ID=" + employeeId + ", Name=" + name
                + ", Position=" + position + ", Salary=" + salary + "}";
    }
}

public class EmployeeManagement {
    private Employee[] employees;
    private int count;

    public EmployeeManagement(int capacity) {
        employees = new Employee[capacity];
        count = 0;
    }

    public void addEmployee(Employee employee) {
        if (count < employees.length) {
            employees[count] = employee;
            count++;
            System.out.println("Added: " + employee);
        } else {
            System.out.println("Array is full. Cannot add more employees.");
        }
    }

    public Employee searchEmployee(int employeeId) {
        for (int i = 0; i < count; i++) {
            if (employees[i].employeeId == employeeId) {
                return employees[i];
            }
        }
        return null;
    }

    public void traverseEmployees() {
        System.out.println("All Employees:");
        for (int i = 0; i < count; i++) {
            System.out.println("  " + employees[i]);
        }
    }

    public void deleteEmployee(int employeeId) {
        for (int i = 0; i < count; i++) {
            if (employees[i].employeeId == employeeId) {
                Employee removed = employees[i];
                for (int j = i; j < count - 1; j++) {
                    employees[j] = employees[j + 1];
                }
                employees[count - 1] = null;
                count--;
                System.out.println("Deleted: " + removed);
                return;
            }
        }
        System.out.println("Employee ID " + employeeId + " not found.");
    }

    public static void main(String[] args) {
        EmployeeManagement system = new EmployeeManagement(10);

        system.addEmployee(new Employee(1, "Jeeva", "Developer", 75000));
        system.addEmployee(new Employee(2, "Ravi", "Designer", 65000));
        system.addEmployee(new Employee(3, "Priya", "Manager", 90000));

        System.out.println();
        system.traverseEmployees();

        System.out.println();
        System.out.println("Searching for Employee ID 2:");
        Employee emp = system.searchEmployee(2);
        System.out.println(emp != null ? "Found: " + emp : "Not found");

        System.out.println();
        system.deleteEmployee(2);

        System.out.println();
        system.traverseEmployees();


    }
}
