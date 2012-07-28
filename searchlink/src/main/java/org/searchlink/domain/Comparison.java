package org.searchlink.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Author: Jacques Fontignie
 * Date: 6/22/12
 * Time: 11:31 AM
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Comparison {

    private int count;
    private int numUpdates;

    private long ms;

    private int keywords;
    private int words;
}
