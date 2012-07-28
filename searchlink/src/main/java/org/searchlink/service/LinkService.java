package org.searchlink.service;

import org.searchlink.domain.Link;
import org.searchlink.domain.Product;
import org.springframework.roo.addon.layers.service.RooService;

import java.util.List;

/**
 * Author: Jacques Fontignie
 * Date: 6/22/12
 * Time: 9:29 AM
 */
@RooService(domainTypes = {Link.class})
public interface LinkService {
    List<Link> findLinksByProduct(Product product);

    List<Link> findLinksBySource(Product target);
}
