package org.searchlink.queue;

import org.searchlink.domain.Product;

/**
 * Author: Jacques Fontignie
 * Date: 6/19/12
 * Time: 10:20 AM
 */
public interface Queue {

    public void add(Product... link);

    public Product get();

    public int size();
}
