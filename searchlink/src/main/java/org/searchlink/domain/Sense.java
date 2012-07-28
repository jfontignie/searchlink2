package org.searchlink.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Column;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findSensesByWordEquals" })
public class Sense {

    @Column(unique = true)
    private String word;

    private String sense;
}
