package org.searchlink.service;

import org.searchlink.domain.Keyword;
import org.searchlink.domain.Occurrence;
import org.searchlink.domain.Product;
import org.springframework.roo.addon.layers.service.RooService;

import java.util.List;
import java.util.Set;

/**
 * Author: Jacques Fontignie
 * Date: 6/22/12
 * Time: 4:15 PM
 */
@RooService(domainTypes = {Occurrence.class})
public interface OccurrenceService {

    public List<Occurrence> findOccurrenceByProduct(Product product, int minCount);

    List<Occurrence> findOccurenceByKeywords(Set<Keyword> keywords, int maxResults, int minCount);
}
