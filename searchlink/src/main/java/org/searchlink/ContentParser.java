package org.searchlink;

import org.jsoup.Jsoup;
import org.searchlink.domain.Comparison;
import org.searchlink.domain.Keyword;
import org.searchlink.domain.Occurrence;
import org.searchlink.domain.Product;
import org.searchlink.service.CorrelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;


/**
 * Author: Jacques Fontignie
 * Date: 6/19/12
 * Time: 4:43 PM
 */
public class ContentParser {

    private final Logger logger = Logger.getLogger(ContentParser.class.getName());

    private SynonymService synonymService;

    @Autowired
    private CorrelationService correlationService;

    private final static String[] STOP_WORDS = new String[]{"a", "able", "about", "across", "after",
            "all", "almost", "also", "am", "among", "an", "and", "any", "are", "as", "at",
            "be", "because", "been", "book", "but", "by", "can", "cannot", "could", "dear", "did", "do",
            "does", "either", "else", "even", "ever", "every", "for", "from", "get", "got", "had", "has",
            "have", "he", "her", "hers", "him", "his", "how", "however", "i", "if", "in", "into",
            "is", "it", "its", "just", "least", "let", "like", "likely", "may", "me", "might",
            "more", "most", "must", "my", "neither", "new", "no", "nor", "not", "of", "off", "often", "on", "one",
            "only", "or", "other", "our", "out", "own", "rather", "said", "say", "says", "she", "should", "since",
            "so", "some", "than", "that", "the", "their", "them", "then", "there", "these", "they",
            "this", "tis", "to", "too", "twas", "two", "up", "us", "wants", "was", "we", "were", "what", "when",
            "where", "which", "while", "who", "whom", "why", "will", "with", "would", "yet", "you",
            "your"};


    /**
     * List of stopwords obtained from http://dev.mysql.com/doc/refman/5.5/en/fulltext-stopwords.html
     */
    private static final String[] MYSQL_STOP_WORDS = new String[]{"able", "about", "above", "according",
            "accordingly", "across", "actually", "after", "afterwards",
            "again", "against", "ain't", "all", "allow",
            "allows", "almost", "alone", "along", "already",
            "also", "although", "always", "am", "among",
            "amongst", "an", "and", "another", "any",
            "anybody", "anyhow", "anyone", "anything", "anyway",
            "anyways", "anywhere", "apart", "appear", "appreciate",
            "appropriate", "are", "aren't", "around", "as",
            "aside", "ask", "asking", "associated", "at",
            "available", "away", "awfully", "be", "became",
            "because", "become", "becomes", "becoming", "been",
            "before", "beforehand", "behind", "being", "believe",
            "below", "beside", "besides", "best", "better",
            "between", "beyond", "both", "brief", "but",
            "by", "c'mon", "c's", "came", "can",
            "can't", "cannot", "cant", "cause", "causes",
            "certain", "certainly", "changes", "clearly", "co",
            "com", "come", "comes", "concerning", "consequently",
            "consider", "considering", "contain", "containing", "contains",
            "corresponding", "could", "couldn't", "course", "currently",
            "definitely", "described", "despite", "did", "didn't",
            "different", "do", "does", "doesn't", "doing",
            "don't", "done", "down", "downwards", "during",
            "each", "edu", "eg", "eight", "either",
            "else", "elsewhere", "enough", "entirely", "especially",
            "et", "etc", "even", "ever", "every",
            "everybody", "everyone", "everything", "everywhere", "ex",
            "exactly", "example", "except", "far", "few",
            "fifth", "first", "five", "followed", "following",
            "follows", "for", "former", "formerly", "forth",
            "four", "from", "further", "furthermore", "get",
            "gets", "getting", "given", "gives", "go",
            "goes", "going", "gone", "got", "gotten",
            "greetings", "had", "hadn't", "happens", "hardly",
            "has", "hasn't", "have", "haven't", "having",
            "he", "he's", "hello", "help", "hence",
            "her", "here", "here's", "hereafter", "hereby",
            "herein", "hereupon", "hers", "herself", "hi",
            "him", "himself", "his", "hither", "hopefully",
            "how", "howbeit", "however", "i'd", "i'll",
            "i'm", "i've", "ie", "if", "ignored",
            "immediate", "in", "inasmuch", "inc", "indeed",
            "indicate", "indicated", "indicates", "inner", "insofar",
            "instead", "into", "inward", "is", "isn't",
            "it", "it'd", "it'll", "it's", "its",
            "itself", "just", "keep", "keeps", "kept",
            "know", "known", "knows", "last", "lately",
            "later", "latter", "latterly", "least", "less",
            "lest", "let", "let's", "like", "liked",
            "likely", "little", "look", "looking", "looks",
            "ltd", "mainly", "many", "may", "maybe",
            "me", "mean", "meanwhile", "merely", "might",
            "more", "moreover", "most", "mostly", "much",
            "must", "my", "myself", "name", "namely",
            "nd", "near", "nearly", "necessary", "need",
            "needs", "neither", "never", "nevertheless", "new",
            "next", "nine", "no", "nobody", "non",
            "none", "noone", "nor", "normally", "not",
            "nothing", "novel", "now", "nowhere", "obviously",
            "of", "off", "often", "oh", "ok",
            "okay", "old", "on", "once", "one",
            "ones", "only", "onto", "or", "other",
            "others", "otherwise", "ought", "our", "ours",
            "ourselves", "out", "outside", "over", "overall",
            "own", "particular", "particularly", "per", "perhaps",
            "placed", "please", "plus", "possible", "presumably",
            "probably", "provides", "que", "quite", "qv",
            "rather", "rd", "re", "really", "reasonably",
            "regarding", "regardless", "regards", "relatively", "respectively",
            "right", "said", "same", "saw", "say",
            "saying", "says", "second", "secondly", "see",
            "seeing", "seem", "seemed", "seeming", "seems",
            "seen", "self", "selves", "sensible", "sent",
            "serious", "seriously", "seven", "several", "shall",
            "she", "should", "shouldn't", "since", "six",
            "so", "some", "somebody", "somehow", "someone",
            "something", "sometime", "sometimes", "somewhat", "somewhere",
            "soon", "sorry", "specified", "specify", "specifying",
            "still", "sub", "such", "sup", "sure",
            "t's", "take", "taken", "tell", "tends",
            "th", "than", "thank", "thanks", "thanx",
            "that", "that's", "thats", "the", "their",
            "theirs", "them", "themselves", "then", "thence",
            "there", "there's", "thereafter", "thereby", "therefore",
            "therein", "theres", "thereupon", "these", "they",
            "they'd", "they'll", "they're", "they've", "think",
            "third", "this", "thorough", "thoroughly", "those",
            "though", "three", "through", "throughout", "thru",
            "thus", "to", "together", "too", "took",
            "toward", "towards", "tried", "tries", "truly",
            "try", "trying", "twice", "two", "un",
            "under", "unfortunately", "unless", "unlikely", "until",
            "unto", "up", "upon", "us", "use",
            "used", "useful", "uses", "using", "usually",
            "value", "various", "very", "via", "viz",
            "vs", "want", "wants", "was", "wasn't",
            "way", "we", "we'd", "we'll", "we're",
            "we've", "welcome", "well", "went", "were",
            "weren't", "what", "what's", "whatever", "when",
            "whence", "whenever", "where", "where's", "whereafter",
            "whereas", "whereby", "wherein", "whereupon", "wherever",
            "whether", "which", "while", "whither", "who",
            "who's", "whoever", "whole", "whom", "whose",
            "why", "will", "willing", "wish", "with",
            "within", "without", "won't", "wonder", "would",
            "wouldn't", "yes", "yet", "you", "you'd",
            "you'll", "you're", "you've", "your", "yours",
            "yourself", "yourselves", "zero"};

    private final HashMap<String, Void> stopWords;

    @Required
    public void setSynonymService(SynonymService synonymService) {
        this.synonymService = synonymService;
    }

    public ContentParser() {
        stopWords = new HashMap<String, Void>();
        for (String word : STOP_WORDS)
            stopWords.put(word, null);
        for (String word : MYSQL_STOP_WORDS)
            stopWords.put(word, null);
    }

    @Transactional
    public void parse(Product product) {
        HashMap<String, Score> map = new HashMap<String, Score>();
        HashMap<String, Keyword> keywords = new HashMap<String, Keyword>();

        int count = extractTokens(product, map);

        updateKeywods(product, map, keywords, count);


        logger.info("number of keywords: " + map.keySet().size() + " and number of words: " + count);
        calculateStatistics(map, keywords, count);
    }

    private int extractTokens(Product product, HashMap<String, Score> map) {
        String content = " " + product.getName();
        content += product.getContent();

        content = content.toLowerCase();

        content = Jsoup.parse(content).text();
        //String delims = "[ /‘’“”\"':;\\[ \\]\\(\\).,?!]+";
        //String delims = "[^a-zA-Z]+";
        String delims = "[\\W]+";
        int count = 0;
        for (String token : content.split(delims)) {

            if (token.length() <= 2) continue;
            if (stopWords.containsKey(token)) continue;
            count++;
            //Remove the plural
            if (token.endsWith("sses")) token = token.substring(0, token.length() - 2);
            if (token.endsWith("ies")) token = token.substring(0, token.length() - 3) + "y";
            if (token.endsWith("s") && !token.endsWith("ss") &&
                    !token.endsWith("ous") && !token.endsWith("ys"))
                token = token.substring(0, token.length() - 1);

            String result = synonymService.getSynonym(token);
            if (result != null) token = result;


            Score score = map.get(token);
            if (score == null) {
                score = new Score();
                map.put(token, score);
            }
            score.setCount(score.getCount() + 1);
            score.setSum(score.getSum() + count);
        }
        return count;
    }

    private void calculateStatistics(HashMap<String, Score> map, HashMap<String, Keyword> keywords, int count) {
        int numWords = count;
        long start = System.currentTimeMillis();

        count = 0;
        int numUpdates = 0;
        for (String w1 : map.keySet()) {
            if (map.get(w1).getCount() < 2) continue;

            numUpdates += correlationService.addOccurrence(keywords.get(w1), map.get(w1).getCount());

            for (String w2 : map.keySet()) {
                if (w1.equals(w2)) continue;
                if (map.get(w2).getCount() < 2) continue;
                int comp = w1.compareTo(w2);
                if (comp < 0) continue;

                count++;

                Keyword k1 = keywords.get(w1);
                Keyword k2 = keywords.get(w2);

                correlationService.setCorrelation(k1, map.get(w1).getCount(), k2, map.get(w2).getCount());

            }
        }

        long end = System.currentTimeMillis();
        logger.info("It took: " + (end - start) + " ms. over " + count + " comparisons and " + numUpdates + " updates");

        Comparison c = new Comparison();
        c.setMs(end - start);
        c.setCount(count);
        c.setKeywords(map.size());
        c.setWords(numWords);
        c.setNumUpdates(numUpdates);
        c.persist();
    }

    private void updateKeywods(Product product, HashMap<String, Score> map, HashMap<String, Keyword> keywords, int count) {
        //All the keywords have been parsed. Let's populate the DB
        for (String key : map.keySet()) {
            Score score = map.get(key);
            List<Keyword> list = Keyword.findKeywordsByNameEquals(key).getResultList();
            Keyword keyword;
            if (list.size() == 0) {
                keyword = new Keyword();
                keyword.setName(key);
                keyword.setSum(0);
                keyword.setSumSquare(0);
                keyword.setCount(0);
            } else {
                keyword = list.get(0);
            }

            keyword.setSum(keyword.getSum() + score.getCount());
            keyword.setSumSquare(keyword.getSumSquare() + sqr(score.getCount()));
            keyword.setCount(keyword.getCount() + 1);
            keyword.persist();

            keywords.put(key, keyword);
            Occurrence occurrence = new Occurrence();
            occurrence.setProduct(product);
            occurrence.setKeyword(keyword);
            occurrence.setCount(score.getCount());


            //TODO COMPARE THE RESULT WITH A inv(SQR)
            double avg = score.getSum() * 1. / score.getCount();
            occurrence.setScore((count - avg) / count);
            occurrence.persist();

        }
    }

    private int sqr(int count) {
        return count * count;
    }

    public void setCorrelationService(CorrelationService correlationService) {
        this.correlationService = correlationService;
    }

    private class Score {
        private int count;

        private double sum;

        public Score() {
            count = 0;
            sum = 0;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public double getSum() {
            return sum;
        }

        public void setSum(double sum) {
            this.sum = sum;
        }
    }
}
