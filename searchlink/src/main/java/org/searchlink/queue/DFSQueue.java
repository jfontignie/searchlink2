package org.searchlink.queue;

import org.searchlink.domain.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: Jacques Fontignie
 * Date: 6/19/12
 * Time: 10:24 AM
 */
@Component
public class DFSQueue implements Queue {
    private final List<Product> links;

    public DFSQueue() {
        links = new ArrayList<Product>();
    }


    public void add(Product... links) {
        Collections.addAll(this.links, links);
    }

    public Product get() {
        return links.remove(links.size() - 1);
    }

    public int size() {
        return links.size();
    }
}
