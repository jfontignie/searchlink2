package org.searchlink.domain;

import org.hibernate.annotations.Index;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = {"findCorrelationsByXAndY", "findCorrelationsByXOrY", "findCorrelationsByX", "findCorrelationsByY"})
public class Correlation implements Comparable<Correlation> {

    @ManyToOne
    @Index(name = "index_X")
    private Keyword x;

    @ManyToOne
    @Index(name = "index_Y")
    private Keyword y;

    private int sumXY;

    private int count;

    @Index(name = "index_similarity")
    private Double similarity;

    private Double covariance;

    @Override
    public int compareTo(Correlation correlation) {
        double diff = correlation.similarity - similarity;
        if (diff > 0) return 1;
        if (diff < 0) return -1;
        return 0;
    }
}
