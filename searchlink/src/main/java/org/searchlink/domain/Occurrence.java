package org.searchlink.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findOccurrencesByProduct", "findOccurrencesByProductAndCountGreaterThan"})
public class Occurrence {

    @ManyToOne
    private Product product;

    @ManyToOne
    private Keyword keyword;

    private int count;

    private double score;
}
