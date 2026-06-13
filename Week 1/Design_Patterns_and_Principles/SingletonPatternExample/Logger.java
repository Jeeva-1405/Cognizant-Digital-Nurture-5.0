public class Logger {

    private static Logger instance;

    private Logger() {
        // private constructor prevents instantiation from outside
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String message) {
        System.out.println("[LOG] " + message);
    }

    public static void main(String[] args) {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        logger1.log("Application started");
        logger2.log("User logged in");

        if (logger1 == logger2) {
            System.out.println("Both variables point to the same Logger instance.");
        } else {
            System.out.println("Different instances - Singleton not working.");
        }
    }
}
