public class FinancialForecast {


    public static double calculateFutureValue(double presentValue, double growthRate, int years) {
        if (years == 0) {
            return presentValue;
        }
        return calculateFutureValue(presentValue * (1 + growthRate), growthRate, years - 1);
    }

  
    public static double calculateFutureValueIterative(double presentValue, double growthRate, int years) {
        double futureValue = presentValue;
        for (int i = 0; i < years; i++) {
            futureValue *= (1 + growthRate);
        }
        return futureValue;
    }

    public static void main(String[] args) {
        double investmentAmount = 100000.00;
        double annualGrowthRate = 0.08;

        System.out.println("Financial Forecasting Tool");
        System.out.println("Initial Investment : Rs." + investmentAmount);
        System.out.println("Annual Growth Rate : " + (annualGrowthRate * 100) + "%");
        System.out.println();

        int[] forecastYears = {1, 3, 5, 10, 20};

        System.out.println("Projection using Recursive approach:");
        for (int years : forecastYears) {
            double futureValue = calculateFutureValue(investmentAmount, annualGrowthRate, years);
            System.out.printf("  After %2d year(s) : Rs.%.2f%n", years, futureValue);
        }

        System.out.println();

        System.out.println("Projection using Iterative approach (for verification):");
        for (int years : forecastYears) {
            double futureValue = calculateFutureValueIterative(investmentAmount, annualGrowthRate, years);
            System.out.printf("  After %2d year(s) : Rs.%.2f%n", years, futureValue);
        }

    }
}
