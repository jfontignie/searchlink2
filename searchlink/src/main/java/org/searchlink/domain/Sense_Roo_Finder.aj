// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.searchlink.domain;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.searchlink.domain.Sense;

privileged aspect Sense_Roo_Finder {
    
    public static TypedQuery<Sense> Sense.findSensesByWordEquals(String word) {
        if (word == null || word.length() == 0) throw new IllegalArgumentException("The word argument is required");
        EntityManager em = Sense.entityManager();
        TypedQuery<Sense> q = em.createQuery("SELECT o FROM Sense AS o WHERE o.word = :word", Sense.class);
        q.setParameter("word", word);
        return q;
    }
    
}