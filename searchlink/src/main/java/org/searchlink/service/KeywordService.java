package org.searchlink.service;

import org.searchlink.domain.Keyword;
import org.springframework.roo.addon.layers.service.RooService;

import java.util.Set;

/**
 * Author: Jacques Fontignie
 * Date: 6/22/12
 * Time: 4:19 PM
 */
@RooService(domainTypes = {Keyword.class})
public interface KeywordService {

    Set<Keyword> findSimilar(Set<Keyword> keywords);
}
