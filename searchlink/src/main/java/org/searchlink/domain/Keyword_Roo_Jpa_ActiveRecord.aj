// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.searchlink.domain;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.searchlink.domain.Keyword;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Keyword_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Keyword.entityManager;
    
    public static final EntityManager Keyword.entityManager() {
        EntityManager em = new Keyword().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Keyword.countKeywords() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Keyword o", Long.class).getSingleResult();
    }
    
    public static List<Keyword> Keyword.findAllKeywords() {
        return entityManager().createQuery("SELECT o FROM Keyword o", Keyword.class).getResultList();
    }
    
    public static Keyword Keyword.findKeyword(Long id) {
        if (id == null) return null;
        return entityManager().find(Keyword.class, id);
    }
    
    public static List<Keyword> Keyword.findKeywordEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Keyword o", Keyword.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Keyword.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Keyword.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Keyword attached = Keyword.findKeyword(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Keyword.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Keyword.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Keyword Keyword.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Keyword merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}