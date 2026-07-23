import java.util.ArrayList;
import java.util.List;

class InterestCalculatorBeforeCopilot {
    public double calculate(String accountType, double balance, int years) {
        double result = 0;
        if (accountType.equals("SAVINGS")) {
            if (balance > 100000) {
                result = balance * 0.04 * years;
            } else {
                result = balance * 0.035 * years;
            }
        } else if (accountType.equals("FIXED_DEPOSIT")) {
            if (years >= 5) {
                result = balance * 0.07 * years;
            } else if (years >= 3) {
                result = balance * 0.065 * years;
            } else {
                result = balance * 0.06 * years;
            }
        } else if (accountType.equals("RECURRING_DEPOSIT")) {
            result = balance * 0.055 * years;
        }
        return result;
    }
}

interface InterestRateStrategy {
    double calculateInterest(double balance, int years);
}

class SavingsInterestStrategy implements InterestRateStrategy {
    private static final double HIGH_BALANCE_THRESHOLD = 100000;
    private static final double HIGH_BALANCE_RATE = 0.04;
    private static final double STANDARD_RATE = 0.035;

    public double calculateInterest(double balance, int years) {
        double rate = balance > HIGH_BALANCE_THRESHOLD ? HIGH_BALANCE_RATE : STANDARD_RATE;
        return balance * rate * years;
    }
}

class FixedDepositInterestStrategy implements InterestRateStrategy {
    public double calculateInterest(double balance, int years) {
        double rate;
        if (years >= 5) {
            rate = 0.07;
        } else if (years >= 3) {
            rate = 0.065;
        } else {
            rate = 0.06;
        }
        return balance * rate * years;
    }
}

class RecurringDepositInterestStrategy implements InterestRateStrategy {
    private static final double FIXED_RATE = 0.055;

    public double calculateInterest(double balance, int years) {
        return balance * FIXED_RATE * years;
    }
}

class InterestCalculatorAfterCopilot {

    public double calculate(InterestRateStrategy strategy, double balance, int years) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        if (years < 0) {
            throw new IllegalArgumentException("Years cannot be negative");
        }
        return strategy.calculateInterest(balance, years);
    }
}

public class CopilotAssistedRefactoringDemo {
    public static void main(String[] args) {
        InterestCalculatorBeforeCopilot before = new InterestCalculatorBeforeCopilot();
        System.out.println("Before Copilot-assisted refactor:");
        System.out.println("  Savings interest: " + before.calculate("SAVINGS", 150000, 3));
        System.out.println("  Fixed deposit interest: " + before.calculate("FIXED_DEPOSIT", 200000, 5));
        System.out.println("  Recurring deposit interest: " + before.calculate("RECURRING_DEPOSIT", 50000, 2));

        System.out.println();

        InterestCalculatorAfterCopilot after = new InterestCalculatorAfterCopilot();
        System.out.println("After Copilot-assisted refactor (strategy pattern, validation added):");
        System.out.println("  Savings interest: " + after.calculate(new SavingsInterestStrategy(), 150000, 3));
        System.out.println("  Fixed deposit interest: " + after.calculate(new FixedDepositInterestStrategy(), 200000, 5));
        System.out.println("  Recurring deposit interest: " + after.calculate(new RecurringDepositInterestStrategy(), 50000, 2));
    }
}
