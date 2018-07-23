package by.epam.service;

import java.util.ArrayList;

public class TrustRateCalculator {

    private static final double MAX_STANDART_DEVIATION = 4.936612;

    /*
    Trust Rate is calculated based on:
    1) Calculated standard deviation of all reviews by target HR
    2) Calculated standard deviation of AVGs reviews by target HR
    3) Average of both deviations divided by MAX deviation is taken as a pre-penalty result.
    4) If reviewer has less than  5 reviews, he gets 30% rate penalty
    5) If reviewer has less than 10 reviews, he gets 20% rate penalty
    6) If reviewer has less than 20 reviews, he gets 10% rate penalty
    7) If reviewer has less than 30 reviews, he gets 5% rate penalty
    
    Note: HR has to have at least 2 written reviews to get rating.
     */


    public TrustRateCalculator() {
    }

    public static int generateTrustRate(ArrayList<Integer> allReviews, ArrayList<Double> avgReviews) {
        double allStaDiv = getStandardDeviation(allReviews);
        double avgStaDiv = getStandardDeviationD(avgReviews);
        int size = avgReviews.size();
        double result = (allStaDiv + avgStaDiv) / MAX_STANDART_DEVIATION * 100;
        if (size <= 5) {
            result *= 0.7;
        } else if (size <= 10) {
            result *= 0.8;
        } else if (size <= 20) {
            result *= 0.9;
        } else if (size <= 30) {
            result *= 0.95;
        }
        return (int) result;
    }

    public static strictfp double mean(ArrayList<Integer> list) {
        double sum = 0;
        for (Integer d : list) {
            sum += d;
        }
        return sum / list.size();
    }

    public static strictfp double meanD(ArrayList<Double> list) {
        double sum = 0;
        for (Double d : list) {
            sum += d;
        }
        return sum / list.size();
    }

    public static strictfp double getStandardDeviation(ArrayList<Integer> list) {
        double mean = mean(list);
        int n = list.size();
        double dv = 0;
        double diff;
        for (Integer d : list) {
            diff = d - mean;
            dv += diff * diff;
        }
        return Math.sqrt(dv / (n - 1));
    }

    public static strictfp double getStandardDeviationD(ArrayList<Double> list) {
        double mean = meanD(list);
        int n = list.size();
        double dv = 0;
        double diff;
        for (Double d : list) {
            diff = d - mean;
            dv += diff * diff;
        }
        return Math.sqrt(dv / (n - 1));
    }
}
