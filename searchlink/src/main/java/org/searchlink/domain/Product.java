package org.searchlink.domain;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(finders = { "findProductsByIdentifierEquals", "findProductsByState","findProductsByAuthors"})
public class Product {

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dateCreated;

    @Column(unique = true)
    private String identifier;

    private String name;

    private ProductState state;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Author> authors = new HashSet<Author>();

    private int inboundLinks;

    @Lob
    private String content;

    @PrePersist
    public void onPersist() {
        dateCreated = new Date();
    }

    public static TypedQuery<Product> findProductsFromAuthors(Set<Author> authors) {
        if (authors == null) throw new IllegalArgumentException("The authors argument is required");
        EntityManager em = Product.entityManager();
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM Product AS o WHERE");
        for (int i = 0; i < authors.size(); i++) {
            if (i > 0) queryBuilder.append(" OR");
            queryBuilder.append(" :authors_item").append(i).append(" MEMBER OF o.authors");
        }
        TypedQuery<Product> q = em.createQuery(queryBuilder.toString(), Product.class);
        int authorsIndex = 0;
        for (Author _author: authors) {
            q.setParameter("authors_item" + authorsIndex++, _author);
        }
        return q;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (identifier != null ? !identifier.equals(product.identifier) : product.identifier != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return identifier != null ? identifier.hashCode() : 0;
    }
}
