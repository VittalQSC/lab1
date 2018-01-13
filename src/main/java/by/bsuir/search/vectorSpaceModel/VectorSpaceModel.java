package by.bsuir.search.vectorSpaceModel;

import org.apache.commons.math3.linear.ArrayRealVector;

import java.util.List;

public class VectorSpaceModel {
    //    https://ru.wikipedia.org/wiki/%D0%92%D0%B5%D0%BA%D1%82%D0%BE%D1%80%D0%BD%D0%B0%D1%8F_%D0%BC%D0%BE%D0%B4%D0%B5%D0%BB%D1%8C#%D0%9A%D0%BE%D1%81%D0%B8%D0%BD%D1%83%D1%81%D0%BD%D0%BE%D0%B5_%D1%81%D1%85%D0%BE%D0%B4%D1%81%D1%82%D0%B2%D0%BE
    public static double getSimilarity(final double[] firstArray, final double[] secondArray) {
        return new ArrayRealVector(firstArray).cosine(new ArrayRealVector(secondArray));
    }

    //    https://ru.wikipedia.org/wiki/%D0%92%D0%B5%D0%BA%D1%82%D0%BE%D1%80%D0%BD%D0%B0%D1%8F_%D0%BC%D0%BE%D0%B4%D0%B5%D0%BB%D1%8C#%D0%9C%D0%B5%D1%82%D0%BE%D0%B4%D1%8B_%D0%B2%D0%B7%D0%B2%D0%B5%D1%88%D0%B8%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F_%D1%82%D0%B5%D1%80%D0%BC%D0%BE%D0%B2
    public static double tf(List<String> doc, String term) {
        double result = 0;
        for (String word : doc) {
            if (term.equalsIgnoreCase(word))
                result++;
        }
        return result / doc.size();
    }

    public static double idf(List<List<String>> docs, String term) {
        double n = 0;
        for (List<String> doc : docs) {
            for (String word : doc) {
                if (term.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
            }
        }

        if (n == 0 || n == 1) {
            return n;
        }

        return Math.log(docs.size() / n);
    }

    public static double tf_Idf(List<String> doc, List<List<String>> docs, String term) {
        return tf(doc, term) * idf(docs, term);
    }

}
