package org.searchlink.service;

import org.searchlink.Crawler;
import org.searchlink.domain.Product;
import org.searchlink.domain.ProductState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.task.TaskExecutor;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Jacques Fontignie
 * Date: 6/22/12
 * Time: 10:52 PM
 */
public class BackgroundCrawler implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger logger = Logger.getLogger(BackgroundCrawler.class.getName());

    @Autowired
    private TaskExecutor taskExecutor;

    private String initialSearch;

    @Autowired
    private Crawler crawler;

    @Required
    public void setInitialSearch(String initialSearch) {
        logger.info("Initial search term has been set to: " + initialSearch);
        this.initialSearch = initialSearch;
    }


    private void run() {
        logger.info("Start crawler");
        if (Product.countProducts() == 0)
            crawler.start(initialSearch);

        do {
            TypedQuery<Product> typedQuery = Product.findProductsByState(ProductState.FETCHED);
            List<Product> resultList = typedQuery.getResultList();
            if (resultList.size() == 0) break;
            for (Product product : resultList) {
                crawler.goToLink(product);
            }
        } while (true);
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Bakground job has been triggered");
        taskExecutor.execute(new CrawlerRunner());
    }

    private class CrawlerRunner implements Runnable {

        @Override
        public void run() {
            BackgroundCrawler.this.run();
        }
    }
}
