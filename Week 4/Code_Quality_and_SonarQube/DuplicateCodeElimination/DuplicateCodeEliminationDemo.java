import java.util.ArrayList;
import java.util.List;

class ReportGeneratorBefore {

    public String generateSalesReport(List<Double> values) {
        double sum = 0;
        for (double v : values) {
            sum += v;
        }
        double avg = sum / values.size();
        double max = values.get(0);
        for (double v : values) {
            if (v > max) max = v;
        }
        double min = values.get(0);
        for (double v : values) {
            if (v < min) min = v;
        }
        return "Sales Report -> Sum: " + sum + ", Avg: " + avg + ", Max: " + max + ", Min: " + min;
    }

    public String generateExpenseReport(List<Double> values) {
        double sum = 0;
        for (double v : values) {
            sum += v;
        }
        double avg = sum / values.size();
        double max = values.get(0);
        for (double v : values) {
            if (v > max) max = v;
        }
        double min = values.get(0);
        for (double v : values) {
            if (v < min) min = v;
        }
        return "Expense Report -> Sum: " + sum + ", Avg: " + avg + ", Max: " + max + ", Min: " + min;
    }
}

class StatSummary {
    private final double sum;
    private final double average;
    private final double max;
    private final double min;

    public StatSummary(double sum, double average, double max, double min) {
        this.sum = sum;
        this.average = average;
        this.max = max;
        this.min = min;
    }

    public double getSum() { return sum; }
    public double getAverage() { return average; }
    public double getMax() { return max; }
    public double getMin() { return min; }

    public String toString() {
        return "Sum: " + sum + ", Avg: " + average + ", Max: " + max + ", Min: " + min;
    }
}

class ReportGeneratorAfter {

    private StatSummary computeStats(List<Double> values) {
        double sum = 0;
        double max = values.get(0);
        double min = values.get(0);

        for (double v : values) {
            sum += v;
            if (v > max) max = v;
            if (v < min) min = v;
        }

        double avg = sum / values.size();
        return new StatSummary(sum, avg, max, min);
    }

    public String generateSalesReport(List<Double> values) {
        return "Sales Report -> " + computeStats(values);
    }

    public String generateExpenseReport(List<Double> values) {
        return "Expense Report -> " + computeStats(values);
    }
}

public class DuplicateCodeEliminationDemo {
    public static void main(String[] args) {
        List<Double> sales = List.of(1200.0, 3400.0, 950.0, 4200.0, 2750.0);
        List<Double> expenses = List.of(800.0, 1500.0, 600.0, 2100.0, 1750.0);

        ReportGeneratorBefore before = new ReportGeneratorBefore();
        System.out.println("Before refactor (duplicated statistics logic):");
        System.out.println("  " + before.generateSalesReport(sales));
        System.out.println("  " + before.generateExpenseReport(expenses));

        System.out.println();

        ReportGeneratorAfter after = new ReportGeneratorAfter();
        System.out.println("After refactor (shared computeStats method, no duplication):");
        System.out.println("  " + after.generateSalesReport(sales));
        System.out.println("  " + after.generateExpenseReport(expenses));
    }
}
