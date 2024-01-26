import java.util.*;
public class MarketSimulator_rough {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of days: ");
        int numberOfDays = scanner.nextInt();

        double[][] marketBounds = generateMarketBounds(numberOfDays);
        for (int i = 0; i < marketBounds.length; i++) {
            System.out.println("Day " + (i + 1) + ": Upper Bound = " + marketBounds[i][0] + "%, Lower Bound = " + marketBounds[i][1] + "%");
        }

        scanner.close();
    }

    static double[][] generateMarketBounds(int numberOfDays) {
        double[][] bounds = new double[numberOfDays][2];

        for (int i = 0; i < numberOfDays; i++) {
            double progress = (double) i / (numberOfDays - 1);

            // Upper Bound: Gradual increase, more pronounced towards the end, 
            // but controlled to prevent unrealistic jumps in short periods
            bounds[i][0] = getUpperBound(progress, numberOfDays);

            // Lower Bound: Linear decrease, capped at -100%
            bounds[i][1] = getLowerBound(progress, numberOfDays);//-100 * progress; 
        }

        return bounds;
    }

    static double getUpperBound(double progress, int totalDays) {
        // Base growth rate for upper bound
        double baseGrowthRate = 5.0;

        // Adjust the growth rate based on the total number of days.
        // The growth rate increases more significantly for more extended periods.
        double growthRate = baseGrowthRate * Math.log(1 + totalDays) / Math.log(2);

        // Calculate the cap using the adjusted growth rate
        // The cap increases with the total number of days but at a controlled rate
        double cap = 10.0 + growthRate * Math.pow(progress, 2);

        // Calculate the upper bound, allowing for more significant increases as progress increases
        // The growth is more controlled for early progress, especially in shorter timeframes.
        double upperBound = 5.0 + (cap - 5.0) * Math.pow(progress, 2);

        return Math.min(upperBound, cap);
    }

    private static double getLowerBound(double progress, int totalDays) {
        // Base lower bound starts at a small negative value
        double startLowerBound = -2.0; // Example: Starting at -2%

        // Maximum lower bound is more negative, but within a realistic limit
        // It becomes more negative as the number of days increases
        double maxLowerBound = -2.0 - 30.0 * Math.pow((double)totalDays / 30, 0.5); // Adjust the exponent for rate of change

        // Calculate the lower bound, which becomes more negative over time
        double lowerBound = startLowerBound - (startLowerBound - maxLowerBound) * Math.pow(progress, 2);

        // Ensure the lower bound does not become unrealistically low
        return Math.max(lowerBound, maxLowerBound);
    }


}
