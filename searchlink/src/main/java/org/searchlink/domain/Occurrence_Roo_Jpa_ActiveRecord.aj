// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.searchlink.domain;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.searchlink.domain.Occurrence;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Occurrence_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Occurrence.entityManager;
    
    public static final EntityManager Occurrence.entityManager() {
        EntityManager em = new Occurrence().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Occurrence.countOccurrences() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Occurrence o", Long.class).getSingleResult();
    }
    
    public static List<Occurrence> Occurrence.findAllOccurrences() {
        return entityManager().createQuery("SELECT o FROM Occurrence o", Occurrence.class).getResultList();
    }
    
    public static Occurrence Occurrence.findOccurrence(Long id) {
        if (id == null) return null;
        return entityManager().find(Occurrence.class, id);
    }
    
    public static List<Occurrence> Occurrence.findOccurrenceEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Occurrence o", Occurrence.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Occurrence.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Occurrence.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Occurrence attached = Occurrence.findOccurrence(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Occurrence.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Occurrence.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Occurrence Occurrence.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Occurrence merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
