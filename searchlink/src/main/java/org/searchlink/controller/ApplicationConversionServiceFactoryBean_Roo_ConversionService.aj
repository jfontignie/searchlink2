// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.searchlink.controller;

import org.searchlink.controller.ApplicationConversionServiceFactoryBean;
import org.searchlink.domain.Link;
import org.searchlink.domain.Product;
import org.searchlink.domain.Sense;
import org.searchlink.service.LinkService;
import org.searchlink.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    declare @type: ApplicationConversionServiceFactoryBean: @Configurable;
    
    @Autowired
    LinkService ApplicationConversionServiceFactoryBean.linkService;
    
    @Autowired
    ProductService ApplicationConversionServiceFactoryBean.productService;
    
    public Converter<Link, String> ApplicationConversionServiceFactoryBean.getLinkToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.searchlink.domain.Link, java.lang.String>() {
            public String convert(Link link) {
                return new StringBuilder().append(link.getScore()).toString();
            }
        };
    }
    
    public Converter<Long, Link> ApplicationConversionServiceFactoryBean.getIdToLinkConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.searchlink.domain.Link>() {
            public org.searchlink.domain.Link convert(java.lang.Long id) {
                return linkService.findLink(id);
            }
        };
    }
    
    public Converter<String, Link> ApplicationConversionServiceFactoryBean.getStringToLinkConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.searchlink.domain.Link>() {
            public org.searchlink.domain.Link convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Link.class);
            }
        };
    }
    
    public Converter<Product, String> ApplicationConversionServiceFactoryBean.getProductToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.searchlink.domain.Product, java.lang.String>() {
            public String convert(Product product) {
                return new StringBuilder().append(product.getDateCreated()).append(' ').append(product.getIdentifier()).append(' ').append(product.getName()).append(' ').append(product.getInboundLinks()).toString();
            }
        };
    }
    
    public Converter<Long, Product> ApplicationConversionServiceFactoryBean.getIdToProductConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.searchlink.domain.Product>() {
            public org.searchlink.domain.Product convert(java.lang.Long id) {
                return productService.findProduct(id);
            }
        };
    }
    
    public Converter<String, Product> ApplicationConversionServiceFactoryBean.getStringToProductConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.searchlink.domain.Product>() {
            public org.searchlink.domain.Product convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Product.class);
            }
        };
    }
    
    public Converter<Sense, String> ApplicationConversionServiceFactoryBean.getSenseToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.searchlink.domain.Sense, java.lang.String>() {
            public String convert(Sense sense) {
                return new StringBuilder().append(sense.getWord()).append(' ').append(sense.getSense()).toString();
            }
        };
    }
    
    public Converter<Long, Sense> ApplicationConversionServiceFactoryBean.getIdToSenseConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.searchlink.domain.Sense>() {
            public org.searchlink.domain.Sense convert(java.lang.Long id) {
                return Sense.findSense(id);
            }
        };
    }
    
    public Converter<String, Sense> ApplicationConversionServiceFactoryBean.getStringToSenseConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.searchlink.domain.Sense>() {
            public org.searchlink.domain.Sense convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Sense.class);
            }
        };
    }
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getLinkToStringConverter());
        registry.addConverter(getIdToLinkConverter());
        registry.addConverter(getStringToLinkConverter());
        registry.addConverter(getProductToStringConverter());
        registry.addConverter(getIdToProductConverter());
        registry.addConverter(getStringToProductConverter());
        registry.addConverter(getSenseToStringConverter());
        registry.addConverter(getIdToSenseConverter());
        registry.addConverter(getStringToSenseConverter());
    }
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
}
