// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.searchlink.domain;

import org.searchlink.domain.Link;
import org.searchlink.domain.LinkType;
import org.searchlink.domain.Product;

privileged aspect Link_Roo_JavaBean {
    
    public Product Link.getSource() {
        return this.source;
    }
    
    public void Link.setSource(Product source) {
        this.source = source;
    }
    
    public Product Link.getTarget() {
        return this.target;
    }
    
    public void Link.setTarget(Product target) {
        this.target = target;
    }
    
    public LinkType Link.getType() {
        return this.type;
    }
    
    public void Link.setType(LinkType type) {
        this.type = type;
    }
    
    public Double Link.getScore() {
        return this.score;
    }
    
    public void Link.setScore(Double score) {
        this.score = score;
    }
    
}
