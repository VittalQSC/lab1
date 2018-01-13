package by.bsuir.search.termsManager;

import by.bsuir.search.document.FileContainer;
import by.bsuir.search.document.Splitter;
import by.bsuir.search.rocchio.Rocchio;
import by.bsuir.search.vector.Vector;
import by.bsuir.search.vectorSpaceModel.VectorSpaceModel;

import java.util.ArrayList;
import java.util.List;

public class TermsManager {
    private List<List<String>> docsTerms;

    private List<String> eachDocCategory;

    public TermsManager(List<FileContainer> documents) {
        this.eachDocCategory = new ArrayList<>();
        documents.forEach(document -> this.eachDocCategory.add(document.getCategory()));
        this.docsTerms = new ArrayList<>();
        documents.forEach(document -> this.docsTerms.add(getTerms(document.getContent())));
    }

    public static List<String> getTerms(String text) {
        return Splitter.split(text);
    }

    public ArrayList<String> getTermsPool (String searchContent) {
        List<List<String>> docsTerms = this.docsTerms;
        ArrayList<String> pool = new ArrayList<>();
        for (List<String> docTerms : docsTerms) {
            for (String term : docTerms) {
                if (!pool.contains(term)) {
                    pool.add(term);
                }
            }
        }

        List<String> words = getTerms(searchContent);
        for (String word : words) {
            if (!pool.contains(word)) {
                pool.add(word);
            }
        }
        return pool;
    }

    public ArrayList<Double> search(String searchContent, String category) {
        int documentsNum = this.docsTerms.size();
        List<List<String>> docs = this.docsTerms;

        ArrayList<String> pool = this.getTermsPool(searchContent);

        List<String> searchTerms = getTerms(searchContent);
        List<List<String>> docsAndQuery = new ArrayList<>();
        for (List<String> doc : docs) {
            docsAndQuery.add(new ArrayList<>(doc));
        }
        docsAndQuery.add(searchTerms);

        ArrayList<ArrayList<Double>> filesVectors = Vector.getVectors(docsAndQuery, pool);

//        ArrayList<Double> searchVector = filesVectors.get(docsAndQuery.size() - 1);

        List<List<Double>> positiveVectors = new ArrayList<>();
        List<List<Double>> negativeVectors = new ArrayList<>();
        for (int i = 0; i < this.eachDocCategory.size(); i++) {
            if (this.eachDocCategory.get(i).equals(category)) {
                positiveVectors.add(filesVectors.get(i));
            } else {
                negativeVectors.add(filesVectors.get(i));
            }
        }

        List<Double> searchVector = Rocchio.getQuery(
                filesVectors.get(docsAndQuery.size() - 1), 0.9, 0.1,
                positiveVectors, negativeVectors
        );

        ArrayList<Double> result = new ArrayList<>();
        for (int i = 0; i < documentsNum; i++) {
            result.add(Vector.getSimilarity(filesVectors.get(i), searchVector));

        }
        return result;
    }

}

