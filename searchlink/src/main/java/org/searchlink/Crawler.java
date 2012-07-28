package org.searchlink;

import org.searchlink.domain.Product;

import java.util.List;

/**
 * Author: Jacques Fontignie
 * Date: 6/17/12
 * Time: 6:12 PM
 */
public interface Crawler {

    /**
     * Start crawling from a starting query. Usually, a key is provided and startup links are provided
     * It could be an URL or a string or whatever understood by the crawler.
     * Given this, the crawler must be able to list the links
     *
     * @param startPoint the starting point of the crawler
     */
    List<Product> start(String startPoint);

    /**
     * Equivalent to start
     *
     * @param link : the link to open and read
     * @see Crawler#start(String)
     */
    Product goToLink(Product link);
}
