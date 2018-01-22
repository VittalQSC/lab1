package by.bsuir.search.rocchio;

import by.bsuir.search.document.FileContainer;
import by.bsuir.search.termsManager.TermsManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Learn {
    TermsManager tm;
    Map<String, List<Double>> profiles;
    public Learn (List<FileContainer> documents) {
        tm = new TermsManager(documents);
        profiles = new HashMap<String, List<Double>>();

        List<ArrayList<Double>> filesVectors = tm.getFilesVectors(tm.docsTerms);

        for (int i = 0; i < documents.size(); i++) {
            FileContainer document = documents.get(i);
            String category = document.getCategory();

            List<List<Double>> positiveVectors = new ArrayList<>();
            List<List<Double>> negativeVectors = new ArrayList<>();
            for (int j = 0;j < tm.eachDocCategory.size(); j++) {
                if (tm.eachDocCategory.get(j).equals(category)) {
                    positiveVectors.add(filesVectors.get(j));
                } else {
                    negativeVectors.add(filesVectors.get(j));
                }
            }

            profiles.put(category, getProfile(positiveVectors, negativeVectors, 0.9,0.1));
        }

    }

    public ArrayList<Double> getProfileVector (List<String> pool, String category) {
        ArrayList<Double> result = new ArrayList<>();
        List<Double> profile = this.profiles.get(category);
        List<String> learnPool = tm.getTermsPool("");
        for (int i = 0; i < pool.size(); i++) {
            String term = pool.get(i);
            int index = learnPool.indexOf(term);
            result.add(index != -1 ? profile.get(index) : 0.0);

        }
        return result;
    }

    public static List<Double> getProfile(
            List<List<Double>> positiveVectors,
            List<List<Double>> negativeVectors,
            double alpha, double beta) {
        ArrayList<Double> profile =  new ArrayList<>();
        for (int i = 0; i < positiveVectors.get(0).size(); i++) {
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

            profile.add(positiveValue >= negativeValue ? positiveValue - negativeValue : 0.0);
        }

        return profile;
    }


//    private List<List<String>> docsTerms;
//
//    private List<String> eachDocCategory;
//
//    public Learn(List<FileContainer> documents) {
//        this.eachDocCategory = new ArrayList<>();
//        documents.forEach(document -> this.eachDocCategory.add(document.getCategory()));
//        this.docsTerms = new ArrayList<>();
//        documents.forEach(document -> this.docsTerms.add(getTerms(document.getContent())));
//    }
//    static List<String> getTerms(String text) {
//        return Splitter.split(text);
//    }
//
//    private List<Double> getProfile() {
//        return new ArrayList<>();
//    }
//
//    void learn () {
//        Map<String, List<Double>> profile = new HashMap<String, List<Double>>();
//        Map<String, List<String>> profileTerms = new HashMap<String, List<String>>();
//        for (int i = 0; i < eachDocCategory.size(); i++) {
//             String category = eachDocCategory.get(i);
//
//        }
//    }
}
