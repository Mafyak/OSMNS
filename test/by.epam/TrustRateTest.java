package by.epam;

import by.epam.utils.calculator.TrustRateCalculator;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class TrustRateTest {

    @Test
    public void testTrustRate() {
        ArrayList<Integer> allReviewsList1 = new ArrayList<>();
        allReviewsList1.add(3);
        allReviewsList1.add(5);
        allReviewsList1.add(6);
        allReviewsList1.add(5);
        allReviewsList1.add(5);
        allReviewsList1.add(9);
        allReviewsList1.add(9);
        allReviewsList1.add(7);
        allReviewsList1.add(9);
        allReviewsList1.add(7);
        ArrayList<Double> avgReviewsList1 = new ArrayList<>();
        avgReviewsList1.add(4.8);
        avgReviewsList1.add(8.2);
        //  ArrayList<Integer> allReviewsList = userDAO.getReviewsRateForHrById(idHR);
        //  ArrayList<Double> avgReviewsList = userDAO.getAVGReviewsRateForHrById(idHR);
        Assert.assertEquals(TrustRateCalculator.generateTrustRate(allReviewsList1, avgReviewsList1), 63, 0.01);
        Assert.assertEquals(TrustRateCalculator.getStandardDeviation(allReviewsList1), 2.06, 0.01);
        Assert.assertEquals(TrustRateCalculator.getStandardDeviationD(avgReviewsList1), 2.40, 0.01);
    }
}
