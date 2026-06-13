class Task {
    int taskId;
    String taskName;
    String status;
    Task next;

    public Task(int taskId, String taskName, String status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.status = status;
        this.next = null;
    }

    public String toString() {
        return "Task{ID=" + taskId + ", Name=" + taskName + ", Status=" + status + "}";
    }
}

public class TaskManagement {
    private Task head;

    public void addTask(Task task) {
        if (head == null) {
            head = task;
        } else {
            Task current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = task;
        }
        System.out.println("Added: " + task);
    }

    public Task searchTask(int taskId) {
        Task current = head;
        while (current != null) {
            if (current.taskId == taskId) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public void traverseTasks() {
        System.out.println("All Tasks:");
        Task current = head;
        while (current != null) {
            System.out.println("  " + current);
            current = current.next;
        }
    }

    public void deleteTask(int taskId) {
        if (head == null) {
            System.out.println("No tasks found.");
            return;
        }

        if (head.taskId == taskId) {
            System.out.println("Deleted: " + head);
            head = head.next;
            return;
        }

        Task current = head;
        while (current.next != null) {
            if (current.next.taskId == taskId) {
                System.out.println("Deleted: " + current.next);
                current.next = current.next.next;
                return;
            }
            current = current.next;
        }
        System.out.println("Task ID " + taskId + " not found.");
    }

    public static void main(String[] args) {
        TaskManagement taskList = new TaskManagement();

        taskList.addTask(new Task(1, "Design Database Schema", "Pending"));
        taskList.addTask(new Task(2, "Develop REST APIs", "In Progress"));
        taskList.addTask(new Task(3, "Write Unit Tests", "Pending"));
        taskList.addTask(new Task(4, "Deploy to Staging", "Pending"));

        System.out.println();
        taskList.traverseTasks();

        System.out.println();
        System.out.println("Searching for Task ID 3:");
        Task found = taskList.searchTask(3);
        System.out.println(found != null ? "Found: " + found : "Not found");

        System.out.println();
        taskList.deleteTask(2);

        System.out.println();
        taskList.traverseTasks();

    }
}
