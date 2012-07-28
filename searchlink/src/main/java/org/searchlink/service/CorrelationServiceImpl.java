package org.searchlink.service;

import org.searchlink.domain.Correlation;
import org.searchlink.domain.Keyword;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Author: Jacques Fontignie
 * Date: 6/22/12
 * Time: 9:51 AM
 */
public class CorrelationServiceImpl implements CorrelationService {

    private Logger logger = Logger.getLogger(CorrelationServiceImpl.class.getName());

    public List<Correlation> findCorrelationByKeyword(Keyword keyword) {
        return Correlation.findCorrelationsByXOrY(keyword, keyword).getResultList();
    }

    @Override
    public int addOccurrence(Keyword keyword, int count) {
        List<Correlation> correlations = findCorrelationByKeyword(keyword);
        for (Correlation correlation : correlations) {
            if (keyword.equals(correlation.getX()))
                updateCorrelation(correlation, correlation.getX(), count, correlation.getY(), 0);
            else
                updateCorrelation(correlation, correlation.getX(), 0, correlation.getY(), count);
        }
        logger.finer("#Correlations updated: " + correlations.size());
        return correlations.size();
    }

    @Override
    public void setCorrelation(Keyword k1, int count1, Keyword k2, int count2) {
        if (k1.getName().compareTo(k2.getName()) < 0) {
            Keyword k;
            k = k1;
            k1 = k2;
            k2 = k;

            int c;
            c = count1;
            count1 = count2;
            count2 = c;
        }


        List<Correlation> correlations = Correlation.findCorrelationsByXAndY(k1, k2).getResultList();
        Correlation correlation;
        if (correlations.size() == 0) {
            correlation = new Correlation();
            correlation.setX(k1);
            correlation.setY(k2);
            correlation.setCount(0);
        } else {
            correlation = correlations.get(0);
        }


        updateCorrelation(correlation, k1, count1, k2, count2);


        //                //TODO this is an estimation and not the exact value
//                double sumXY = correlation.getSumXY();
//                double sumX = k1.getSum();
//                double sumY = k2.getSum();
//                double sumXSquare = k1.getSumSquare();
//                double sumYSquare = k2.getSumSquare();
//                int n = k1.getCount() + k2.getCount() - correlation.getCount();
//                if (n != 0) {
//                    double p = sumXY - (sumX * sumY) / n;
//                    double quotient = Math.sqrt((sumXSquare - sqr(sumX) / n) * (sumYSquare - sqr(sumY) / n));
//                    if (quotient != 0)
//                        correlation.setPearson(p / quotient);
//                }


    }

    private void updateCorrelation(Correlation correlation, Keyword k1, int count1, Keyword k2, int count2) {


        int n = k1.getCount() + k2.getCount() - correlation.getCount();
        if (count1 != 0 && count2 != 0) {
            correlation.setCount(correlation.getCount() + 1);
            correlation.setSumXY(correlation.getSumXY() + count1 * count2);
        }

        if (n > 0)
            correlation.setCovariance((correlation.getSumXY() - k1.getSum() * k2.getSum() * 1. / n) / n);
        correlation.setSimilarity(correlation.getCount() * 2. / (k1.getCount() + k2.getCount()));
        correlation.persist();
    }

    @Override
    public List<Correlation> findCorrelationsByKeywordAndSimilarityGreaterThan(Set<Keyword> keywords, double similarity) {
        if (keywords == null) throw new IllegalArgumentException("The keyword argument is required");
        EntityManager em = Correlation.entityManager();
        TypedQuery<Correlation> q = em.createQuery("SELECT o FROM Correlation AS o WHERE (o.y in (:keywordList) OR o.x in (:keywordList)) AND o.similarity > :similarity ",
                Correlation.class);

        q.setParameter("keywordList", keywords);
        q.setParameter("similarity", similarity);
        return q.getResultList();
    }

    @Override
    public List<Correlation> findCorrelationsByKeywordAndSimilarityGreaterThanAndCountGreaterThan(Set<Keyword> keywords, double similarity, int count) {
        if (keywords == null) throw new IllegalArgumentException("The keyword argument is required");
        EntityManager em = Correlation.entityManager();
        TypedQuery<Correlation> q = em.createQuery("SELECT o FROM Correlation AS o " +
                "WHERE (o.y in (:keywordList) OR o.x in (:keywordList)) AND o.similarity > :similarity AND o.count > :count ORDER BY o.similarity",
                Correlation.class);

        q.setParameter("keywordList", keywords);
        q.setParameter("similarity", similarity);
        q.setParameter("count",count);
        return q.getResultList();
    }
}
