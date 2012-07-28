// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.searchlink.service;

import java.util.List;
import org.searchlink.domain.Product;
import org.searchlink.service.ProductServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ProductServiceImpl_Roo_Service {
    
    declare @type: ProductServiceImpl: @Service;
    
    declare @type: ProductServiceImpl: @Transactional;
    
    public long ProductServiceImpl.countAllProducts() {
        return Product.countProducts();
    }
    
    public void ProductServiceImpl.deleteProduct(Product product) {
        product.remove();
    }
    
    public Product ProductServiceImpl.findProduct(Long id) {
        return Product.findProduct(id);
    }
    
    public List<Product> ProductServiceImpl.findAllProducts() {
        return Product.findAllProducts();
    }
    
    public List<Product> ProductServiceImpl.findProductEntries(int firstResult, int maxResults) {
        return Product.findProductEntries(firstResult, maxResults);
    }
    
    public void ProductServiceImpl.saveProduct(Product product) {
        product.persist();
    }
    
    public Product ProductServiceImpl.updateProduct(Product product) {
        return product.merge();
    }
    
}