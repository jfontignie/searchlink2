
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.amazon.webservices.awsecommerceservice._2011_08_01;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.6.1
 * 2012-06-19T16:33:23.989+02:00
 * Generated source version: 2.6.1
 * 
 */

@javax.jws.WebService(
                      serviceName = "AWSECommerceService",
                      portName = "AWSECommerceServicePortFR",
                      targetNamespace = "http://webservices.amazon.com/AWSECommerceService/2011-08-01",
                      wsdlLocation = "file:/Users/jacques/projects/searchlink/amazon/src/main/wsdl/AWSECommerceService.wsdl",
                      endpointInterface = "com.amazon.webservices.awsecommerceservice._2011_08_01.AWSECommerceServicePortType")
                      
public class AWSECommerceServicePortTypeImpl implements AWSECommerceServicePortType {

    private static final Logger LOG = Logger.getLogger(AWSECommerceServicePortTypeImpl.class.getName());

    /* (non-Javadoc)
     * @see com.amazon.webservices.awsecommerceservice._2011_08_01.AWSECommerceServicePortType#itemSearch(com.amazon.webservices.awsecommerceservice._2011_08_01.ItemSearch  body )*
     */
    public com.amazon.webservices.awsecommerceservice._2011_08_01.ItemSearchResponse itemSearch(ItemSearch body) { 
        LOG.info("Executing operation itemSearch");
        System.out.println(body);
        try {
            com.amazon.webservices.awsecommerceservice._2011_08_01.ItemSearchResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.amazon.webservices.awsecommerceservice._2011_08_01.AWSECommerceServicePortType#similarityLookup(com.amazon.webservices.awsecommerceservice._2011_08_01.SimilarityLookup  body )*
     */
    public com.amazon.webservices.awsecommerceservice._2011_08_01.SimilarityLookupResponse similarityLookup(SimilarityLookup body) { 
        LOG.info("Executing operation similarityLookup");
        System.out.println(body);
        try {
            com.amazon.webservices.awsecommerceservice._2011_08_01.SimilarityLookupResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.amazon.webservices.awsecommerceservice._2011_08_01.AWSECommerceServicePortType#browseNodeLookup(com.amazon.webservices.awsecommerceservice._2011_08_01.BrowseNodeLookup  body )*
     */
    public com.amazon.webservices.awsecommerceservice._2011_08_01.BrowseNodeLookupResponse browseNodeLookup(BrowseNodeLookup body) { 
        LOG.info("Executing operation browseNodeLookup");
        System.out.println(body);
        try {
            com.amazon.webservices.awsecommerceservice._2011_08_01.BrowseNodeLookupResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.amazon.webservices.awsecommerceservice._2011_08_01.AWSECommerceServicePortType#cartGet(com.amazon.webservices.awsecommerceservice._2011_08_01.CartGet  body )*
     */
    public com.amazon.webservices.awsecommerceservice._2011_08_01.CartGetResponse cartGet(CartGet body) { 
        LOG.info("Executing operation cartGet");
        System.out.println(body);
        try {
            com.amazon.webservices.awsecommerceservice._2011_08_01.CartGetResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.amazon.webservices.awsecommerceservice._2011_08_01.AWSECommerceServicePortType#cartCreate(com.amazon.webservices.awsecommerceservice._2011_08_01.CartCreate  body )*
     */
    public com.amazon.webservices.awsecommerceservice._2011_08_01.CartCreateResponse cartCreate(CartCreate body) { 
        LOG.info("Executing operation cartCreate");
        System.out.println(body);
        try {
            com.amazon.webservices.awsecommerceservice._2011_08_01.CartCreateResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.amazon.webservices.awsecommerceservice._2011_08_01.AWSECommerceServicePortType#cartClear(com.amazon.webservices.awsecommerceservice._2011_08_01.CartClear  body )*
     */
    public com.amazon.webservices.awsecommerceservice._2011_08_01.CartClearResponse cartClear(CartClear body) { 
        LOG.info("Executing operation cartClear");
        System.out.println(body);
        try {
            com.amazon.webservices.awsecommerceservice._2011_08_01.CartClearResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.amazon.webservices.awsecommerceservice._2011_08_01.AWSECommerceServicePortType#cartModify(com.amazon.webservices.awsecommerceservice._2011_08_01.CartModify  body )*
     */
    public com.amazon.webservices.awsecommerceservice._2011_08_01.CartModifyResponse cartModify(CartModify body) { 
        LOG.info("Executing operation cartModify");
        System.out.println(body);
        try {
            com.amazon.webservices.awsecommerceservice._2011_08_01.CartModifyResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.amazon.webservices.awsecommerceservice._2011_08_01.AWSECommerceServicePortType#cartAdd(com.amazon.webservices.awsecommerceservice._2011_08_01.CartAdd  body )*
     */
    public com.amazon.webservices.awsecommerceservice._2011_08_01.CartAddResponse cartAdd(CartAdd body) { 
        LOG.info("Executing operation cartAdd");
        System.out.println(body);
        try {
            com.amazon.webservices.awsecommerceservice._2011_08_01.CartAddResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.amazon.webservices.awsecommerceservice._2011_08_01.AWSECommerceServicePortType#itemLookup(com.amazon.webservices.awsecommerceservice._2011_08_01.ItemLookup  body )*
     */
    public com.amazon.webservices.awsecommerceservice._2011_08_01.ItemLookupResponse itemLookup(ItemLookup body) { 
        LOG.info("Executing operation itemLookup");
        System.out.println(body);
        try {
            com.amazon.webservices.awsecommerceservice._2011_08_01.ItemLookupResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
