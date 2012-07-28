package org.searchlink.service;

import org.searchlink.domain.Correlation;
import org.searchlink.domain.Keyword;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: Jacques Fontignie
 * Date: 6/22/12
 * Time: 4:19 PM
 */
public class KeywordServiceImpl implements KeywordService {


    @Autowired
    private CorrelationService correlationService;


    @Override
    public Set<Keyword> findSimilar(Set<Keyword> keywords) {
        EntityManager em = Keyword.entityManager();

        List<Correlation> correlations = correlationService.findCorrelationsByKeywordAndSimilarityGreaterThan(keywords, 0.5);

        HashSet<Keyword> added = new HashSet<Keyword>();

        for (Correlation c : correlations) {
            added.add(c.getX());
            added.add(c.getY());
        }

        return added;
    }
}
