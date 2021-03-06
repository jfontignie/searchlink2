// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.searchlink.domain;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.searchlink.domain.Sense;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Sense_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Sense.entityManager;
    
    public static final EntityManager Sense.entityManager() {
        EntityManager em = new Sense().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Sense.countSenses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Sense o", Long.class).getSingleResult();
    }
    
    public static List<Sense> Sense.findAllSenses() {
        return entityManager().createQuery("SELECT o FROM Sense o", Sense.class).getResultList();
    }
    
    public static Sense Sense.findSense(Long id) {
        if (id == null) return null;
        return entityManager().find(Sense.class, id);
    }
    
    public static List<Sense> Sense.findSenseEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Sense o", Sense.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Sense.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Sense.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Sense attached = Sense.findSense(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Sense.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Sense.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Sense Sense.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Sense merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
