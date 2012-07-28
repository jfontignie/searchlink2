package org.searchlink.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Column;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(finders = { "findAuthorsByNameEquals" })
public class Author {

    @Column(unique = true)
    private String name;

}
