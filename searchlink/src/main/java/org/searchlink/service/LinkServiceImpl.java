package org.searchlink.service;

import org.searchlink.domain.Link;
import org.searchlink.domain.Product;

import java.util.List;

public class LinkServiceImpl implements LinkService {
    @Override
    public List<Link> findLinksByProduct(Product product) {
        return Link.findLinksBySourceOrTarget(product,product).getResultList();
    }

    @Override
    public List<Link> findLinksBySource(Product product) {
        return Link.findLinksBySource(product).getResultList();
    }


}
