// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.searchlink.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import org.searchlink.domain.Keyword;

privileged aspect Keyword_Roo_Jpa_Entity {
    
    declare @type: Keyword: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Keyword.id;
    
    @Version
    @Column(name = "version")
    private Integer Keyword.version;
    
    public Long Keyword.getId() {
        return this.id;
    }
    
    public void Keyword.setId(Long id) {
        this.id = id;
    }
    
    public Integer Keyword.getVersion() {
        return this.version;
    }
    
    public void Keyword.setVersion(Integer version) {
        this.version = version;
    }
    
}
