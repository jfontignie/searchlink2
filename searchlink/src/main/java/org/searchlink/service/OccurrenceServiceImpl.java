package org.searchlink.service;

import org.searchlink.domain.Correlation;
import org.searchlink.domain.Keyword;
import org.searchlink.domain.Occurrence;
import org.searchlink.domain.Product;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

/**
 * Author: Jacques Fontignie
 * Date: 6/22/12
 * Time: 4:15 PM
 */
public class OccurrenceServiceImpl implements OccurrenceService {
    @Override
    public List<Occurrence> findOccurrenceByProduct(Product product, int minCount) {
        return Occurrence.findOccurrencesByProductAndCountGreaterThan(product, minCount - 1).getResultList();
    }

    @Override
    public List<Occurrence> findOccurenceByKeywords(Set<Keyword> keywords, int maxResults, int minCount) {
        if (keywords == null) throw new IllegalArgumentException("The keyword argument is required");
        EntityManager em = Correlation.entityManager();
        TypedQuery<Occurrence> q = em.createQuery("SELECT o FROM Occurrence AS o WHERE (o.keyword in (:keywordList)) " +
                "AND o.count >:minCount ORDER BY o.count DESC",
                Occurrence.class);
        if (maxResults > 0)
            q.setMaxResults(maxResults);

        q.setParameter("keywordList", keywords);
        q.setParameter("minCount", minCount);
        return q.getResultList();
    }
}
