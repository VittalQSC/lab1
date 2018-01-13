package by.bsuir.search.document;

import java.util.LinkedList;
import java.util.List;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.CoreMap;
import org.apache.lucene.analysis.core.StopAnalyzer;

import java.util.*;

//https://stanfordnlp.github.io/CoreNLP/api.html

public class Splitter {
    private static StanfordCoreNLP pipeline = new StanfordCoreNLP(getStanfordCoreNLPProps());
    private static Set<?> stopWords = StopAnalyzer.ENGLISH_STOP_WORDS_SET;

    Splitter () {}

    private static Properties getStanfordCoreNLPProps() {
        Properties properties = new Properties();
        properties.put("annotators", "tokenize, ssplit, pos, lemma");
        return properties;
    }

    public static List<String> split(String text) {
        List<String> words = new LinkedList<String>();

        Annotation document = new Annotation(text);
        pipeline.annotate(document);

        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        sentences
                .forEach(
                        sentence -> sentence.get(CoreAnnotations.TokensAnnotation.class).stream().filter(
                                it -> !stopWords.contains(it.word().toLowerCase())
                        ).filter(
                                it -> !it.word().toLowerCase().matches("[\'\"-.!?,;:]")
                        ).forEach(
                                token -> words.add(token.get(CoreAnnotations.LemmaAnnotation.class).toLowerCase())
                        )
                );

        return words;
    }

}
