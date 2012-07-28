package org.searchlink;

import com.amazon.webservices.awsecommerceservice._2011_08_01.*;
import org.jsoup.Jsoup;
import org.searchlink.domain.*;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Author: Jacques Fontignie
 * Date: 6/17/12
 * Time: 6:12 PM
 */
//TODO USE REPOSITORY
public class AmazonCrawler implements Crawler {

    public static final String BOOKS = "Books";
    public static final String LARGE = "Large";
    public static final String ASIN = "ASIN";
    private Logger logger = Logger.getLogger(AmazonCrawler.class.getName());
    private AWSECommerceServicePortType port;

    private String accessKey;
    private String secretKey;
    private String associateTag;


    private ContentParser parser;

    public AmazonCrawler() {

    }

    public void init() {
        logger.info("Initializing the AWS service");
        AWSECommerceService service = new AWSECommerceService();
        service.setHandlerResolver(new AwsHandlerResolver(secretKey));
        port = service.getAWSECommerceServicePort();

    }

    @Required
    public void setContentParser(ContentParser contentParser) {
        this.parser = contentParser;
    }

    @Required
    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }


    @Required
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Required
    public void setAssociateTag(String associateTag) {
        this.associateTag = associateTag;
    }

    private ItemSearchResponse query(String searchIndex, String key) {
        ItemSearchRequest request = new ItemSearchRequest();
        request.setSearchIndex(searchIndex);
        request.setKeywords(key);
        request.getResponseGroup().add(LARGE);

        ItemSearch search = new ItemSearch();
        search.getRequest().add(request);
        search.setAssociateTag(associateTag);
        search.setAWSAccessKeyId(accessKey);

        ItemSearchResponse response = port.itemSearch(search);

        if (response.getOperationRequest().getErrors() != null) {

            for (com.amazon.webservices.awsecommerceservice._2011_08_01.Errors.Error error : response.getOperationRequest().getErrors().getError()) {
                logger.warning(error.getCode() + ":" + error.getMessage());
            }
            throw new IllegalStateException("Error during query");
        }
        return response;
    }

    private ItemLookupResponse queryId(String key) {
        ItemLookupRequest itemLookupRequest = new ItemLookupRequest();
        itemLookupRequest.setIdType(ASIN);
        itemLookupRequest.getItemId().add(key);
        itemLookupRequest.getResponseGroup().add(LARGE);

        ItemLookup itemLookup = new ItemLookup();
        itemLookup.setAWSAccessKeyId(accessKey);
        itemLookup.setAssociateTag(associateTag);
        itemLookup.getRequest().add(itemLookupRequest);


        return port.itemLookup(itemLookup);

    }

    public List<Product> start(String startPoint) {
        logger.info("Start: " + startPoint);


        ItemSearchResponse response = query(BOOKS, startPoint);

        List<Product> links = new ArrayList<Product>();

        for (Items items : response.getItems()) {
            List<Item> item_list = items.getItem();
            for (Item item : item_list) {
                links.add(createProduct(item));
            }
        }

        logger.fine("size is: " + links.size());
        return links;
    }

    public Product goToLink(Product link) {

        ItemLookupResponse response = queryId(link.getIdentifier());
        if (response == null) return null;
        for (Items items : response.getItems()) {

            if (items.getItem() != null) {
                if (items.getItem().size() == 0) continue;
                Item item = items.getItem().get(0);
                if (item == null) break;
                return createProduct(item);
            }
        }

        return null;
    }

    @Transactional
    private Product createProduct(Item item) {

        logger.info("Creating product: " + item.getItemAttributes().getTitle());
        String asin = item.getASIN();
        String title = item.getItemAttributes().getTitle();

        Set<Author> authors = new HashSet<Author>();


        if (item.getItemAttributes().getAuthor() != null) {
            for (String author : item.getItemAttributes().getAuthor()) {
                TypedQuery<Author> typedQuery = Author.findAuthorsByNameEquals(author);
                List<Author> result = typedQuery.getResultList();
                if (result.size() == 1)
                    authors.add(result.get(0));
                else {
                    Author auth = new Author();
                    auth.setName(author);
                    auth.persist();
                    authors.add(auth);
                }
            }

        }
        String content = "";

        if (item.getEditorialReviews() != null &&
                item.getEditorialReviews().getEditorialReview() != null) {
            for (EditorialReview review : item.getEditorialReviews().getEditorialReview()) {
                if (review.getSource().toLowerCase().contains("description"))
                    content = content + "\n" + review.getContent();
            }
        }

        TypedQuery<Product> query = Product.findProductsByIdentifierEquals(asin);
        List<Product> resultList = query.getResultList();
        Product product;
        if (resultList.size() == 0) {
            product = new Product();
        } else {
            product = resultList.get(0);
        }

        product.setIdentifier(asin);
        product.setState(ProductState.PARSED);
        product.setContent(Jsoup.parse(content).text());
        if (title.length() > 255)
            product.setName(title.substring(0, 255));
        else
            product.setName(title);


        product.getAuthors().addAll(authors);
        product.persist();

        if (authors.size() > 0) {
            TypedQuery<Product> authorTypedQuery = Product.findProductsFromAuthors(authors);
            List<Product> sameAuthors = authorTypedQuery.getResultList();
            for (Product sameAuthor : sameAuthors) {
                Link link = new Link();
                link.setSource(product);
                link.setTarget(sameAuthor);
                link.setType(LinkType.AUTHOR);
                link.persist();
            }
        }

        for (RelatedItems relatedItems : item.getRelatedItems()) {
            for (RelatedItem relatedItem : relatedItems.getRelatedItem()) {
                TypedQuery<Product> typedQuery = Product.findProductsByIdentifierEquals(relatedItem.getItem().getASIN());
                List<Product> results = typedQuery.getResultList();

                Product p;

                if (results.size() > 0) {
                    p = results.get(0);
                } else {
                    p = new Product();
                    p.setIdentifier(relatedItem.getItem().getASIN());
                    p.setState(ProductState.FETCHED);
                    p.setInboundLinks(0);

                }

                p.setInboundLinks(p.getInboundLinks()+1);
                p.persist();

                Link link = new Link();
                link.setSource(product);
                link.setTarget(p);
                link.setType(LinkType.RELATED);
                link.persist();

            }
        }


        SimilarProducts similarProducts = item.getSimilarProducts();
        if (similarProducts != null) {
            for (SimilarProducts.SimilarProduct similarProduct : similarProducts.getSimilarProduct()) {
                TypedQuery<Product> typedQuery = Product.findProductsByIdentifierEquals(similarProduct.getASIN());
                List<Product> results = typedQuery.getResultList();

                Product p;

                if (results.size() > 0) {
                    p = results.get(0);
                } else {
                    p = new Product();
                    p.setIdentifier(similarProduct.getASIN());
                    p.setState(ProductState.FETCHED);
                    p.setInboundLinks(0);
                }

                p.setInboundLinks(p.getInboundLinks()+1);
                p.persist();

                Link link = new Link();
                link.setSource(product);
                link.setTarget(p);
                link.setType(LinkType.SIMILAR);
                link.persist();
            }
        }

        parser.parse(product);

        return product;
    }


}
