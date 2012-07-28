package org.searchlink.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(finders = {"findLinksBySourceAndTarget", "findLinksBySourceOrTarget","findLinksBySource"})
public class Link implements Comparable<Link> {

    @ManyToOne
    private Product source;

    @ManyToOne
    private Product target;

    @Enumerated
    private LinkType type;

    private Double score;

    @Override
    public int compareTo(Link link) {
        double diff = link.score - score;
        if (diff > 0) return 1;
        if (diff < 0) return -1;
        return 0;
    }
}
