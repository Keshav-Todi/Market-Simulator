import java.util.*;

public class MarketSimulator {
    //double[] x = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // Time intervals
    double[] y = new double[10];
    double[] y1 = new double[10];
    double[] xValues, y0Values = {1, 2}, y1Values = {2, 3}, y2Values = {4, 5}, y3Values = {-1, -2};
    double[][] ys;
    String[] ns;
    double[][] marketSimulations;
    String[] marketConditionNames;
    array_length_short ob30 = new array_length_short();
    MarketCondition[] conditions1 = MarketCondition.values();
    int n_l = conditions1.length;
    ScatterPlotExample example = new ScatterPlotExample();

    // New array to store daily market conditions
    MarketCondition[] dailyMarketConditions;
    //int numberOfDays = 7; // Total number of days for the simulation

    double[] x; // Time intervals for all days
    double[] cumulativeY; // Cumulative market values for all days
    int numberOfDays = 0; // Total number of days for the simulation
    int intervalsPerDay = 10; // Number of intervals per day    

    double level_y[] = new double[50];double level_amt[] = new double[50];
    int level_y_l = 0;

    public MarketSimulator() {
        x = new double[numberOfDays * intervalsPerDay];
        cumulativeY = new double[numberOfDays * intervalsPerDay];
        dailyMarketConditions = new MarketCondition[numberOfDays];

        for (int i = 0; i < x.length; i++) {
            x[i] = i + 1;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        MarketSimulator simulator = new MarketSimulator();
        System.out.println("Enter done to stop");
        String nn = "";
        while (true) {
            simulator.simulateMarketForAllDays(36000.0, 14,1);
            nn = sc.nextLine();
            if (nn.equals("done"))
                break;
        }
    }

    private void generateDailyMarketConditions(int numDays) {
        Random rand = new Random();
        MarketCondition previousCondition = null;
        MarketCondition beforePreviousCondition = null;

        // Create a list with an even higher frequency of SIDEWAYS and VOLATILE_SWING
        List<MarketCondition> weightedConditions = new ArrayList<>();
        for (MarketCondition condition : MarketCondition.values()) {
            weightedConditions.add(condition); // Add each condition once

            // Increase the frequency of SIDEWAYS and VOLATILE_SWING
            if (condition == MarketCondition.SIDEWAYS || condition == MarketCondition.VOLATILE_SWING) {
                // Add these conditions multiple times to significantly increase their probability
                for (int i = 0; i < 30; i++) { // Increase this number for higher probability
                    weightedConditions.add(condition);
                }
            }
            if (condition == MarketCondition.SHARP_REVERSAL || condition == MarketCondition.PEAK_UP_THEN_REVERSAL) {
                // Add these conditions multiple times to significantly increase their probability
                for (int i = 0; i < 5; i++) { // Increase this number for higher probability
                    weightedConditions.add(condition);
                }
            }
            if (condition == MarketCondition.LARGE_BULLISH || condition == MarketCondition.LARGE_BEARISH) {
                // Add these conditions multiple times to significantly increase their probability
                for (int i = 0; i < 1; i++) { // Increase this number for higher probability
                    weightedConditions.add(condition);
                }
            }
            //LARGE_BULLISH,
            //LARGE_BEARISH,
            //SIDEWAYS,
            //SHARP_REVERSAL,
            //VOLATILE_SWING,
            //PEAK_UP_THEN_REVERSAL,
        }

        for (int i = numDays; i < numberOfDays; i++) {
            MarketCondition newCondition;
            do {
                int randomIndex = rand.nextInt(weightedConditions.size());
                newCondition = weightedConditions.get(randomIndex);
            } while ((newCondition == MarketCondition.LARGE_BULLISH || newCondition == MarketCondition.LARGE_BEARISH) &&
            newCondition == previousCondition && newCondition == beforePreviousCondition);
            beforePreviousCondition = previousCondition;
            previousCondition = newCondition;

            dailyMarketConditions[i] = newCondition;
        }
    }

    public void simulateMarketForAllDays(double amt, int plt, int numberdays) {
        numberOfDays = numberdays;
        x = new double[numberOfDays * intervalsPerDay];
        cumulativeY = new double[numberOfDays * intervalsPerDay];
        dailyMarketConditions = new MarketCondition[numberOfDays];

        for (int i = 0; i < x.length; i++) {
            x[i] = i + 1;
        }
        
        System.out.println("Number of days "+numberOfDays);
        generateDailyMarketConditions(0); // Regenerate market conditions for each simulation
        double currentAmt = amt;
        int currentDayStartIndex = 0;
        level_y = new double[50];level_amt = new double[50];
        level_y_l = 0;
        double per_chgg=0; int midDay=numberOfDays/2-1, lastDay=numberOfDays-1; 

        MarketSimulator_rough obb=new MarketSimulator_rough();
        double day_chg_arr[][]=obb.generateMarketBounds(numberOfDays);
        //ob30.pnt(day_chg_arr);

        int i1=0;
        

        for (int day = 0; day < numberOfDays; day++) {
            MarketCondition condition = dailyMarketConditions[day];
            double[] priceChanges = generatePriceChanges(intervalsPerDay, condition);
            double[] yValues = price_chg(currentAmt, priceChanges);

            currentAmt = yValues[yValues.length - 1]; 

            per_chgg=(currentAmt-amt)/amt*100.0;

            System.out.println(condition);
            //ob30.pnt(priceChanges);
            //ob30.pnt(yValues);
            System.out.println("day "+day+" curr_amt "+currentAmt+" per_chgg "+per_chgg);
            //ob30.pnt(day_chg_arr);

            while(per_chgg>day_chg_arr[day][0] || per_chgg<day_chg_arr[day][1])
            {
                generateDailyMarketConditions(day);
                condition = dailyMarketConditions[day];

                priceChanges = generatePriceChanges(intervalsPerDay, condition);
                yValues = price_chg(currentAmt, priceChanges);

                currentAmt = yValues[yValues.length - 1]; 

                per_chgg=(currentAmt-amt)/amt*100.0;

                i1++;
                if(i1>10000)
                {day=0;break;}
                //System.out.println("here i1 "+i1);
            }
            if(i1>10000)
            
            {
                i1=0;
                //System.out.println("there i1 "+i1+" day "+day);
                dailyMarketConditions = new MarketCondition[numberOfDays];
                generateDailyMarketConditions(0);
                currentAmt = amt;
                currentDayStartIndex = 0;
                level_y = new double[50];level_amt = new double[50];
                level_y_l = 0;

                day=-1;
                continue;
            }
            else
            { 
                i1=0;
                //System.out.println("fhere i1 "+i1);
            }
            /*while(day<=(midDay)&&(per_chgg>7.0||per_chgg<-7.0))
            {
            generateDailyMarketConditions(day);
            condition = dailyMarketConditions[day];

            priceChanges = generatePriceChanges(intervalsPerDay, condition);
            yValues = price_chg(currentAmt, priceChanges);

            currentAmt = yValues[yValues.length - 1]; 

            per_chgg=(currentAmt-amt)/amt*100.0;
            }
            while((day>midDay&&day<=(lastDay))&&(per_chgg>15.0||per_chgg<-15.0))
            {
            generateDailyMarketConditions(day);
            condition = dailyMarketConditions[day];

            priceChanges = generatePriceChanges(intervalsPerDay, condition);
            yValues = price_chg(currentAmt, priceChanges);

            currentAmt = yValues[yValues.length - 1]; 

            per_chgg=(currentAmt-amt)/amt*100.0;
            }*/

            // Append yValues of the day to cumulativeY
            System.arraycopy(yValues, 0, cumulativeY, currentDayStartIndex, yValues.length);

            // Update currentAmt for the next day
            currentDayStartIndex += intervalsPerDay;
            level_y[level_y_l] = currentDayStartIndex;
            level_amt[level_y_l]=currentAmt;
            // Print the market condition for the day
            //System.out.println("Day " + day + ": " + condition);
            //System.out.println("check stuck 1");
            //System.out.printf(" : %.2f %.2f ",level_amt[level_y_l], per_chgg);
            //System.out.println("check stuck 2");
            System.out.println();
            level_y_l++;

        }
        //System.out.println("check stuck 3");
        //ob30.pnt(x);
        //ob30.pnt(cumulativeY);
        // Plot the combined data for all days
        if (plt == 1) {
            //System.out.println("check stuck 4");
            example.updateChartData(x, new double[][]{cumulativeY}, amt, level_y, level_y_l, new String[]{"Combined Trend"});
        }
        //System.out.println("check stuck 5");
    }

    enum MarketCondition {
        LARGE_BULLISH,
        LARGE_BEARISH,
        SIDEWAYS,
        SHARP_REVERSAL,
        VOLATILE_SWING,
        //UP_WAVE,
        //DOWN_WAVE,
        PEAK_UP_THEN_REVERSAL,
        //SLOW_UPTREND_IN_WAVE,
        //SLOW_DOWNTREND_IN_WAVE
    }

    static double[] price_chg(double ini_amt, double[] y) {
        double[] y1 = new double[y.length];
        y1[0] = ini_amt;
        for (int i = 1; i < y.length; i++)
            y1[i] = y1[i - 1] * (100 + y[i]) / 100;
        return y1;
    }

    public static double[] generatePriceChanges(int size, MarketCondition condition) {
        double[] priceChanges = new double[size];
        Random rand = new Random();

        switch (condition) {
            case LARGE_BULLISH:
                for (int i = 0; i < size; i++) {
                    priceChanges[i] = rand.nextDouble() * 1.15; // Large increase
                }
                break;
            case LARGE_BEARISH:
                for (int i = 0; i < size; i++) {
                    priceChanges[i] = -rand.nextDouble() * 1.15; // Large decrease
                }
                break;
            case SIDEWAYS:
                for (int i = 0; i < size; i++) {
                    priceChanges[i] = (rand.nextDouble() - 0.5) * 0.1; // Oscillating around zero
                }
                break;
            case SHARP_REVERSAL:
                for (int i = 0; i < size; i++) {
                    if (i <= size / 2) {
                        priceChanges[i] = -2.0 + rand.nextDouble(); // Sharp drop
                    } else {
                        priceChanges[i] = 1.25 + rand.nextDouble(); // Sharp rise
                    }
                }
                break;
            case VOLATILE_SWING:
                double direction = 1.0;
                for (int i = 0; i < size; i++) {
                    priceChanges[i] = direction * (0.5 + rand.nextDouble() * 1); // Random swing
                    direction *= -1; // Change direction
                }
                break;
                /*case UP_WAVE:
                for (int i = 0; i < size; i++) {
                priceChanges[i] = 2.0 + Math.sin(i * 2 * Math.PI / size) * 2.0; // Upward wave
                }
                break;
            case DOWN_WAVE:
                for (int i = 0; i < size; i++) {
                priceChanges[i] = -2.0 - Math.sin(i * 2 * Math.PI / size) * 2.0; // Downward wave
                }
                break;*/
            case PEAK_UP_THEN_REVERSAL:
                for (int i = 0; i < size; i++) {
                    if (i <= size / 2) {
                        priceChanges[i] = 2.0; // Initial peak up
                    } else {
                        priceChanges[i] = -2.0; // Then reversal
                    }
                }
                break;
                /*case SLOW_UPTREND_IN_WAVE:
                for (int i = 0; i < size; i++) {
                priceChanges[i] = 1.0 + Math.sin(i * Math.PI / size) * 0.5; // Slower upward trend
                }
                break;
            case SLOW_DOWNTREND_IN_WAVE:
                for (int i = 0; i < size; i++) {
                priceChanges[i] = -1.0 - Math.sin(i * Math.PI / size) * 0.5; // Slower downward trend
                }
                break;*/
        }

        return priceChanges;
    }
}