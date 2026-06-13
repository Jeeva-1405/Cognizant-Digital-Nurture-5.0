public class FinancialForecast {

    /*
     * Recursive method to calculate future value.
     * Formula: FV = PV * (1 + growthRate)^years
     *
     * Base case: when years == 0, return the present value.
     * Recursive case: multiply current value by (1 + growthRate) and recurse with years - 1.
     */
    public static double calculateFutureValue(double presentValue, double growthRate, int years) {
        if (years == 0) {
            return presentValue;
        }
        return calculateFutureValue(presentValue * (1 + growthRate), growthRate, years - 1);
    }

    // Iterative version for comparison and optimization
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

        /*
         * Time Complexity:
         * Recursive - O(n) where n is the number of years
         * Each recursive call reduces n by 1 until it reaches the base case.
         *
         * Optimization Note:
         * For very large values of n, the recursive approach may cause a stack overflow.
         * The iterative approach is preferred in production to avoid excessive stack usage.
         * Alternatively, memoization can be applied if sub-results are reused.
         */
    }
}
