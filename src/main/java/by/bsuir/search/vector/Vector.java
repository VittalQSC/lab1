package by.bsuir.search.vector;

import by.bsuir.search.vectorSpaceModel.VectorSpaceModel;
import org.apache.commons.math3.linear.ArrayRealVector;

import java.util.ArrayList;
import java.util.List;

public class Vector {
    public static ArrayList<ArrayList<Double>> getVectors(List<List<String>> docsTerms, ArrayList<String> termsPool) {
        ArrayList<ArrayList<Double>> filesVectors = new ArrayList<>();
        for (int i = 0; i < docsTerms.size(); i++) {
            filesVectors.add(new ArrayList<>());
            for (int j = 0; j < termsPool.size(); j++) {
                String word = termsPool.get(j);
                Double value = VectorSpaceModel.tf_Idf(docsTerms.get(i), docsTerms, word);
                filesVectors.get(i).add(value);
            }
        }
        return filesVectors;
    }

    public static double getSimilarity(List<Double> firstArray, List<Double> secondArray) {
        double[] firstArray1 = new double[firstArray.size()];
        for (int i = 0; i < firstArray1.length; i++) {
            firstArray1[i] = firstArray.get(i);                // java 1.5+ style (outboxing)
        }

        double[] secondArray1 = new double[secondArray.size()];
        for (int i = 0; i < firstArray1.length; i++) {
            secondArray1[i] = secondArray.get(i);                // java 1.5+ style (outboxing)
        }
        return new ArrayRealVector(firstArray1).cosine(new ArrayRealVector(secondArray1));
    }
}
