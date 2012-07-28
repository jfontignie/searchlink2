package org.searchlink.service;

import org.searchlink.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.logging.Logger;

/**
 * Author: Jacques Fontignie
 * Date: 6/22/12
 * Time: 9:39 AM
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
public class ProductServiceImpl implements ProductService {


    private final Logger logger = Logger.getLogger(ProductServiceImpl.class.getName());

    @Autowired
    private LinkService linkService;

    @Autowired
    private CorrelationService correlationService;

    @Autowired
    private OccurrenceService occurrenceService;

    @Autowired
    private KeywordService keywordService;

    @Override
    public List<Product> findRelations(Product product) {
        List<Product> result = new ArrayList<Product>();
        result.addAll(findLinkedProducts(product));

        return result;
    }

    @Override
    public List<Link> findMatching(Product product, int number) {
        List<Occurrence> occurrences = occurrenceService.findOccurrenceByProduct(product, 0);
        HashSet<Keyword> keywords = new HashSet<Keyword>();
        HashMap<Keyword, Double> factor = new HashMap<Keyword, Double>();

        for (Occurrence o : occurrences) {
            keywords.add(o.getKeyword());
            factor.put(o.getKeyword(), 1. * o.getScore()); //similarity with itself = 1
        }

        logger.info("number of keywords: " + keywords.size());

        //We have now the keywords and the first rank keywords
        //Let's look for the occurrences
        List<Occurrence> foundOccurrences = occurrenceService.findOccurenceByKeywords(keywords, -1, 0);

        logger.info("Number of related occurrences: " + foundOccurrences.size());

        //Let's calculate the scores...
        HashMap<Product, Link> scores = new HashMap<Product, Link>();

        for (Occurrence o : foundOccurrences) {
            if (!scores.containsKey(o.getProduct())) {
                Link link = new Link();
                link.setSource(product);
                link.setTarget(o.getProduct());
                link.setScore(0.);
                link.setType(LinkType.SIMILARITY_DISTANCE);
                scores.put(o.getProduct(), link);
            }

            Link link = scores.get(o.getProduct());
            double current = factor.get(o.getKeyword()) * o.getScore();
            link.setScore(link.getScore() + current);
        }

        ArrayList<Link> links = new ArrayList<Link>();
        links.addAll(scores.values());
        Collections.sort(links);

        return links.subList(0, number);
    }


    private class WordScore implements Comparable<WordScore> {
        private Keyword keyword;
        private double score;

        private WordScore(Keyword keyword, double score) {
            this.keyword = keyword;
            this.score = score;
        }

        public Keyword getKeyword() {
            return keyword;
        }

        public double getScore() {
            return score;
        }

        @Override
        public int compareTo(WordScore wordScore) {
            double diff = wordScore.getScore() - score;
            if (diff > 0) return 1;
            if (diff < 0) return -1;
            return 0;
        }
    }

    @Override
    @Cacheable(value = "similar")
    public List<Link> findSimilar(Product product, int number) {
        List<Occurrence> occurrences = occurrenceService.findOccurrenceByProduct(product, 0);
        HashSet<Keyword> keywords = new HashSet<Keyword>();
        List<WordScore> scores = new ArrayList<WordScore>();
        HashMap<Keyword, WordScore> factor = new HashMap<Keyword, WordScore>();
        long countKeywords = keywordService.countAllKeywords();

        for (Occurrence o : occurrences) {
            keywords.add(o.getKeyword());
            double wordProbability = o.getKeyword().getCount() * 1. / countKeywords;
            WordScore score = new WordScore(o.getKeyword(), (1 - wordProbability) * o.getScore());
            scores.add(score);
            factor.put(o.getKeyword(), score);
        }


        //Sort the keywords
        Collections.sort(scores);

        //Take the one with the most interest

        for (int i = 0; i < scores.size() && i < 20; i++) {
            keywords.add(scores.get(i).getKeyword());
        }

        //We have now the keywords and the first rank keywords
        //Let's look for the occurrences
        List<Occurrence> foundOccurrences = occurrenceService.findOccurenceByKeywords(keywords, -1, 0);

        logger.info("Number of related occurrences: " + foundOccurrences.size());

        //Let's calculate the scores...
        HashMap<Product, Link> result = new HashMap<Product, Link>();

        for (Occurrence o : foundOccurrences) {
            if (o.getProduct().equals(product)) continue;
            if (!result.containsKey(o.getProduct())) {
                Link link = new Link();
                link.setSource(product);
                link.setTarget(o.getProduct());
                link.setScore(0.);
                link.setType(LinkType.SIMILARITY_DISTANCE);
                result.put(o.getProduct(), link);
            }

            Link link = result.get(o.getProduct());
            double current = factor.get(o.getKeyword()).getScore() * o.getScore();
            link.setScore(link.getScore() + current);
        }

        ArrayList<Link> links = new ArrayList<Link>();
        links.addAll(result.values());
        Collections.sort(links);


        return links.subList(0, number);
    }


//    @Override
//    @Cacheable(value="similar")
//    public List<Link> findSimilar(Product product, int number) {
//        List<Occurrence> occurrences = occurrenceService.findOccurrenceByProduct(product, 0);
//        HashSet<Keyword> keywords = new HashSet<Keyword>();
//        HashMap<Keyword, Double> factor = new HashMap<Keyword, Double>();
//
//        for (Occurrence o : occurrences) {
//            keywords.add(o.getKeyword());
//            factor.put(o.getKeyword(), 1. * o.getScore()); //similarity with itself = 1
//        }
//
//        logger.info("number of keywords: " + keywords.size());
//
//        HashSet<Keyword> original = (HashSet<Keyword>) keywords.clone();
//
//        List<Correlation> correlations = correlationService.findCorrelationsByKeywordAndSimilarityGreaterThanAndCountGreaterThan(keywords, 0.3, 1);
//
//        logger.info("Number of correlations: " + correlations.size());
//
//
//        int index = 0;
//        for (Correlation c : correlations) {
//            if (index++ > 500) break;
//            keywords.add(c.getY());
//            if (original.contains(c.getX())) {
//                if (!original.contains(c.getY())) {
//                    //y is a first rank and x is an existing one
//                    double value = c.getSimilarity() * factor.get(c.getX());
//                    if (factor.containsKey(c.getY())) {
//                        value += factor.get(c.getY());
//                    }
//                    factor.put(c.getY(), value);
//                }
//            } else if (original.contains(c.getY())) {
//                if (!original.contains(c.getX())) {
//                    //x is a first rank and y is an existing one
//                    double value = c.getSimilarity() * factor.get(c.getY());
//                    if (factor.containsKey(c.getX())) {
//                        value += factor.get(c.getX());
//                    }
//                    factor.put(c.getX(), value);
//                }
//            } else {
//                throw new IllegalStateException("Either x or y should be in the original list");
//            }
//        }
//
////        for (Keyword k : keywords) {
////            if (factor.get(k) > 1) {
////                factor.put(k, 1.);
////            }
////        }
//
//        logger.info("Number of correlated keywords: " + keywords.size());
//
//        //We have now the keywords and the first rank keywords
//        //Let's look for the occurrences
//        List<Occurrence> foundOccurrences = occurrenceService.findOccurenceByKeywords(keywords, -1, 0);
//
//        logger.info("Number of related occurrences: " + foundOccurrences.size());
//
//        //Let's calculate the scores...
//        HashMap<Product, Link> scores = new HashMap<Product, Link>();
//
//        for (Occurrence o : foundOccurrences) {
//            if (!scores.containsKey(o.getProduct())) {
//                Link link = new Link();
//                link.setSource(product);
//                link.setTarget(o.getProduct());
//                link.setScore(0.);
//                link.setType(LinkType.SIMILARITY_DISTANCE);
//                scores.put(o.getProduct(), link);
//            }
//
//            Link link = scores.get(o.getProduct());
//            double current = factor.get(o.getKeyword()) * o.getScore();
//            link.setScore(link.getScore() + current);
//        }
//
//        ArrayList<Link> links = new ArrayList<Link>();
//        links.addAll(scores.values());
//        Collections.sort(links);
//
//
//        return links.subList(0, number);
//    }

    @Override
    public List<Product> findProductsWithKeyword(String... keywords) {
        if (keywords == null) throw new IllegalArgumentException("The keyword argument is required");
        EntityManager em = Product.entityManager();
        TypedQuery<Product> q = em.createQuery("SELECT DISTINCT p FROM Product AS p, Keyword AS K, Occurrence as o WHERE " +
                "(k.name in (:keywordList)) AND  k.id = o.keyword AND p.id = o.product " +
                "ORDER BY o.count DESC",
                Product.class);

        q.setParameter("keywordList", keywords);
        return q.getResultList();
    }


    @Override
    public List<Product> findProductsWithKeyword(List<String> keywords) {
        if (keywords == null) throw new IllegalArgumentException("The keyword argument is required");
        EntityManager em = Product.entityManager();
        TypedQuery<Product> q = em.createQuery("SELECT DISTINCT p FROM Product AS p, Keyword AS K, Occurrence as o WHERE " +
                "(k.name in (:keywordList)) AND  k.id = o.keyword AND p.id = o.product " +
                "ORDER BY o.count DESC",
                Product.class);

        q.setParameter("keywordList", keywords);
        return q.getResultList();
    }

    public List<Product> findProductsWithKeyword(Set<Keyword> keywordSet) {
        if (keywordSet == null) throw new IllegalArgumentException("The keyword argument is required");
        List<Keyword> keywords = new ArrayList<Keyword>();
        keywords.addAll(keywordSet);
        EntityManager em = Product.entityManager();
        TypedQuery<Product> q = em.createQuery("SELECT DISTINCT p FROM Product AS p, Keyword AS K, Occurrence as o WHERE " +
                "(k.name in (:keywordList)) AND  k.id = o.keyword AND p.id = o.product " +
                "ORDER BY o.count DESC",
                Product.class);

        q.setParameter("keywordList", keywords);
        return q.getResultList();
    }


    @Override
    public List<Product> findLinkedProducts(Product product) {
        List<Link> links = linkService.findLinksByProduct(product);
        List<Product> products = new ArrayList<Product>();
        for (Link link : links) {
            if (product.equals(link.getSource()))
                products.add(link.getTarget());
            else
                products.add(link.getSource());
        }
        return products;
    }

}
