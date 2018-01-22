package by.bsuir.search.rocchio;

import by.bsuir.search.document.FileContainer;
import by.bsuir.search.termsManager.TermsManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rocchio {
    public static Map<String, List<Double>> profile = new HashMap<String, List<Double>>();

    public static void initProfiles (List<FileContainer> learn_docs) {
//        Rocchio.profile =

    }

    public static ArrayList<Double> getQuery(ArrayList<Double> oldQuery, double alpha, double beta,
    List<List<Double>> positiveVectors, List<List<Double>> negativeVectors) {
        ArrayList<Double> newQuery = new ArrayList<>();
        List<Double> profile = Learn.getProfile(positiveVectors, negativeVectors, alpha, beta);
        for (int i = 0; i < oldQuery.size(); i++) {
            double value = 0;

            value += oldQuery.get(i) + profile.get(i);
            newQuery.add(value >= 0 ? value : 0);
        }
        return newQuery;
    }
}
