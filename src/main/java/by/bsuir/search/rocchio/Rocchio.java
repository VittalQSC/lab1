package by.bsuir.search.rocchio;

import java.util.ArrayList;
import java.util.List;

public class Rocchio {
    public static ArrayList<Double> getQuery(ArrayList<Double> oldQuery, double alpha, double beta,
    List<List<Double>> positiveVectors, List<List<Double>> negativeVectors) {
        ArrayList<Double> newQuery = new ArrayList<>();
        for (int i = 0; i < oldQuery.size(); i++) {
            double value = 0;
            double positiveValue = 0;
            for (List<Double> positiveVector : positiveVectors) {
                positiveValue += positiveVector.get(i);
            }
            positiveValue = alpha * positiveValue / positiveVectors.size();

            double negativeValue = 0;
            for (List<Double> negativeVector : negativeVectors) {
                negativeValue += negativeVector.get(i);
            }
            negativeValue = beta * negativeValue / negativeVectors.size();

            value += oldQuery.get(i) + positiveValue - negativeValue;
            newQuery.add(value >= 0 ? value : 0);
        }
        return newQuery;
    }
}
