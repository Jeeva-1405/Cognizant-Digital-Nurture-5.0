import java.util.HashMap;
import java.util.Map;

class LoanEligibilityBefore {
    public String checkEligibility(int age, double income, int creditScore, boolean hasDefault) {
        if (age >= 21) {
            if (age <= 60) {
                if (income >= 25000) {
                    if (creditScore >= 700) {
                        if (!hasDefault) {
                            return "APPROVED";
                        } else {
                            return "REJECTED - Past default";
                        }
                    } else {
                        return "REJECTED - Low credit score";
                    }
                } else {
                    return "REJECTED - Insufficient income";
                }
            } else {
                return "REJECTED - Age above limit";
            }
        } else {
            return "REJECTED - Underage";
        }
    }
}

class LoanEligibilityAfter {

    private static final int MIN_AGE = 21;
    private static final int MAX_AGE = 60;
    private static final double MIN_INCOME = 25000;
    private static final int MIN_CREDIT_SCORE = 700;

    public String checkEligibility(int age, double income, int creditScore, boolean hasDefault) {
        if (age < MIN_AGE) return "REJECTED - Underage";
        if (age > MAX_AGE) return "REJECTED - Age above limit";
        if (income < MIN_INCOME) return "REJECTED - Insufficient income";
        if (creditScore < MIN_CREDIT_SCORE) return "REJECTED - Low credit score";
        if (hasDefault) return "REJECTED - Past default";
        return "APPROVED";
    }
}

public class CyclomaticComplexityFixDemo {
    public static void main(String[] args) {
        LoanEligibilityBefore before = new LoanEligibilityBefore();
        LoanEligibilityAfter after = new LoanEligibilityAfter();

        int[][] testAges = {{25, 30000, 720, 0}, {19, 30000, 720, 0}, {45, 15000, 750, 0}};

        System.out.println("Before refactor (cyclomatic complexity 6, deep nesting):");
        System.out.println("  " + before.checkEligibility(25, 30000, 720, false));
        System.out.println("  " + before.checkEligibility(19, 30000, 720, false));
        System.out.println("  " + before.checkEligibility(45, 15000, 750, false));

        System.out.println();

        System.out.println("After refactor (guard clauses, complexity reduced to independent checks):");
        System.out.println("  " + after.checkEligibility(25, 30000, 720, false));
        System.out.println("  " + after.checkEligibility(19, 30000, 720, false));
        System.out.println("  " + after.checkEligibility(45, 15000, 750, false));
    }
}
