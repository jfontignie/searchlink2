package org.searchlink.controller;

import org.searchlink.domain.Link;
import org.searchlink.domain.Product;
import org.searchlink.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Logger;

@RequestMapping("/relative/**")
@Controller
public class RelativeController {

    private Logger logger = Logger.getLogger(RelativeController.class.getName());

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/search/{id}")
    private String relatives(@PathVariable("id") Long id, Model uiModel) {
        logger.info("Looking for product: " + id);
        Product product = productService.findProduct(id);
        logger.info("Product found is: " + product.toString());
        List<Link> links = productService.findMatching(product, 1000);
        uiModel.addAttribute("product", product);
        uiModel.addAttribute("links", links);
        return "relative/search";
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "relative/index";
    }
}
