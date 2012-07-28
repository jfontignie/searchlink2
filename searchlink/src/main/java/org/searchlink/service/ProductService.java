package org.searchlink.service;

import org.searchlink.domain.Link;
import org.searchlink.domain.Product;
import org.springframework.roo.addon.layers.service.RooService;

import java.util.List;

/**
 * Author: Jacques Fontignie
 * Date: 6/22/12
 * Time: 9:38 AM
 */
@RooService(domainTypes = {Product.class})
public interface ProductService {

    public List<Product> findRelations(Product product);

    List<Product> findLinkedProducts(Product product);

    List<Link> findSimilar(Product product, int number);

    List<Product> findProductsWithKeyword(String... keywords);

    List<Product> findProductsWithKeyword(List<String> keywords);

    List<Link> findMatching(Product product, int number);
}
