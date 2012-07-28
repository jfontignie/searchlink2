package org.searchlink;

import org.searchlink.domain.Product;
import org.searchlink.domain.ProductState;
import org.searchlink.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.persistence.TypedQuery;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Jacques Fontignie
 * Date: 6/17/12
 * Time: 6:21 PM
 */

/**
 * Use this for the UI: http://thejit.org/demos/
 */
public class LinkBuilder {

    private final Logger logger = Logger.getLogger(LinkBuilder.class.getName());

    public static final String CONFIG_LOCATION = "searchlink/src/main/resources/META-INF/spring/applicationContext.xml";


    private Crawler crawler;

    @Autowired
    private PearsonCalculator pearsonCalculator;


    private SynonymService synonymService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ProductService productService;

    public LinkBuilder() {
    }

    @Required
    public void setSynonymService(SynonymService synonymService) {
        this.synonymService = synonymService;
    }

    @Required
    public void setCrawler(Crawler crawler) {
        this.crawler = crawler;
    }

    public void run() {
        logger.info("Start crawler");
        if (Product.countProducts() == 0)
            crawler.start("Pratchett");

        do {
            TypedQuery<Product> typedQuery = Product.findProductsByState(ProductState.FETCHED);
            List<Product> resultList = typedQuery.getResultList();
            if (resultList.size() == 0) break;
            for (Product product : resultList) {
                crawler.goToLink(product);
            }
        } while (true);
    }


    public void fillSynonyms() throws IOException {
        synonymService.fillSynonyms();
    }

    public static void main(String[] args) throws IOException {

        //ApplicationContext context = new ClassPathXmlApplicationContext(CONFIG_LOCATION);
        ApplicationContext context = new FileSystemXmlApplicationContext(CONFIG_LOCATION);

        LinkBuilder builder = context.getBean(LinkBuilder.class);

        builder.fillSynonyms();

        //builder.getSimilar();
        //builder.pearson();

        builder.run();

    }

}
