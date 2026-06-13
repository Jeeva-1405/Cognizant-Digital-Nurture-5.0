class Student {
    private String id;
    private String name;
    private String grade;

    public Student(String id, String name, String grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getGrade() { return grade; }

    public void setName(String name) { this.name = name; }
    public void setGrade(String grade) { this.grade = grade; }
}

class StudentView {
    public void displayStudentDetails(String id, String name, String grade) {
        System.out.println("Student Details:");
        System.out.println("  ID    : " + id);
        System.out.println("  Name  : " + name);
        System.out.println("  Grade : " + grade);
    }
}

class StudentController {
    private Student model;
    private StudentView view;

    public StudentController(Student model, StudentView view) {
        this.model = model;
        this.view = view;
    }

    public void setStudentName(String name) {
        model.setName(name);
    }

    public void setStudentGrade(String grade) {
        model.setGrade(grade);
    }

    public String getStudentName() {
        return model.getName();
    }

    public void updateView() {
        view.displayStudentDetails(model.getId(), model.getName(), model.getGrade());
    }
}

public class StudentMVCTest {
    public static void main(String[] args) {
        Student student = new Student("S101", "Jeeva Elango", "A");
        StudentView view = new StudentView();
        StudentController controller = new StudentController(student, view);

        System.out.println("Initial student record:");
        controller.updateView();

        System.out.println();
        controller.setStudentName("Jeeva E.");
        controller.setStudentGrade("A+");

        System.out.println("After update:");
        controller.updateView();
    }
}
