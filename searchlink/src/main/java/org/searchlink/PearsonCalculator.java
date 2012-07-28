package org.searchlink;

import org.searchlink.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Jacques Fontignie
 * Date: 6/19/12
 * Time: 9:00 PM
 */
@Service
public class PearsonCalculator {

    private final Logger logger = Logger.getLogger(PearsonCalculator.class.getName());

    private final int minOccurrences = 2;

    public void run() {
        TypedQuery<Product> typedQuery = Product.findProductsByState(ProductState.PARSED);
        List<Product> products = typedQuery.getResultList();
        logger.info("Analysing " + products.size() + " products");
        for (Product product : products) {
            TypedQuery<Product> comparedList = Product.findProductsByState(ProductState.COMPARED);
            List<Product> compared = comparedList.getResultList();
            for (Product aCompared : compared) {
                //If they are already linked, we do not have to calculate it
                if (Link.findLinksBySourceAndTarget(product, aCompared).getResultList().size() > 0) continue;
                if (Link.findLinksBySourceAndTarget(aCompared, product).getResultList().size() > 0) continue;
                analyze(product, aCompared);
            }
            product.setState(ProductState.COMPARED);
            product.merge();

        }
    }

    private List<Occurrence> getOccurrences(Product p) {
        TypedQuery<Occurrence> typedQuery = Occurrence.findOccurrencesByProduct(p);
        return typedQuery.getResultList();
    }

    @Transactional
    private double analyze(Product p1, Product p2) {
        List<Occurrence> occurrences1 = getOccurrences(p1);
        List<Occurrence> occurrences2 = getOccurrences(p2);


        //http://www.stat.wmich.edu/s216/book/node122.html
        int n;
        double sumX = 0;
        double sumY = 0;
        double sumXSquare = 0;
        double sumYSquare = 0;
        double sumXY = 0;

        HashMap<Keyword, Values> map = new HashMap<Keyword, Values>();

        for (Occurrence o1 : occurrences1) {
            if (o1.getCount() < minOccurrences) continue;
            Values v = new Values();
            v.setX(o1.getCount());
            map.put(o1.getKeyword(), v);
        }

        for (Occurrence o2 : occurrences2) {
            if (map.containsKey(o2.getKeyword())) {
                map.get(o2.getKeyword()).setY(o2.getCount());
            } else {
                if (o2.getCount() < minOccurrences) continue;
                Values v = new Values();
                v.setY(o2.getCount());
                map.put(o2.getKeyword(), v);
            }
        }

        n = map.size();
        for (Values v : map.values()) {
            sumX += v.getX();
            sumY += v.getY();
            sumXSquare += sqr(v.getX());
            sumYSquare += sqr(v.getY());
            sumXY += v.getX() * v.getY();
        }

        if (n == 0) return Double.MAX_VALUE;
        double product = sumXY - (sumX * sumY) / n;
        double quotient = Math.sqrt((sumXSquare - sqr(sumX) / n) * (sumYSquare - sqr(sumY) / n));
        if (quotient == 0) return Double.MAX_VALUE;
        double pearson = product / quotient;
        double distance = 1 - pearson;
        if (distance < 0) distance = 0;


        logger.info("Distance between(" + p1.getName() + ") and (" + p2.getName() + ") is: " + distance);
        if (distance > 1.5) return Double.MAX_VALUE;
        Link link = new Link();
        link.setSource(p1);
        link.setTarget(p2);
        link.setType(LinkType.PEARSON);
        link.setScore(distance);
        link.persist();

        logger.info("Successfully stored the link");
        return distance;
    }


    private double sqr(double i) {
        return i * i;
    }

    private class Values {
        int x;
        int y;

        private Values() {
            x = 0;
            y = 0;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
