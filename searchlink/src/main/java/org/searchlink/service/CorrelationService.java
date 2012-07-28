package org.searchlink.service;

import org.searchlink.domain.Correlation;
import org.searchlink.domain.Keyword;
import org.springframework.roo.addon.layers.service.RooService;

import java.util.List;
import java.util.Set;

/**
 * Author: Jacques Fontignie
 * Date: 6/22/12
 * Time: 9:51 AM
 */
@RooService(domainTypes = {Correlation.class})
public interface CorrelationService {
    public int addOccurrence(Keyword k, int count);

    void setCorrelation(Keyword k1, int count1, Keyword k2, int count2);

    List<Correlation> findCorrelationsByKeywordAndSimilarityGreaterThan(Set<Keyword> keywords, double similarity);

    List<Correlation> findCorrelationsByKeywordAndSimilarityGreaterThanAndCountGreaterThan(Set<Keyword> keywords, double similarity, int count);
}
