// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.searchlink.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import org.searchlink.domain.Author;

privileged aspect Author_Roo_Jpa_Entity {
    
    declare @type: Author: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Author.id;
    
    @Version
    @Column(name = "version")
    private Integer Author.version;
    
    public Long Author.getId() {
        return this.id;
    }
    
    public void Author.setId(Long id) {
        this.id = id;
    }
    
    public Integer Author.getVersion() {
        return this.version;
    }
    
    public void Author.setVersion(Integer version) {
        this.version = version;
    }
    
}
