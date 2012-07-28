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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RequestMapping("/search/**")
@Controller
public class SearchController {
    private Logger logger = Logger.getLogger(SearchController.class.getName());

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.POST, value = "{id}")
    public void post(@PathVariable Long id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    }

    @RequestMapping()
    public String index(@RequestParam(value = "search", required = false) String search,
                        @RequestParam(value = "graph", required = false) Long graph,
                        @RequestParam(value = "product", required = false) Long product,
                        Model uiModel) {
        if (search != null) return search(search, uiModel);
        if (graph != null) return renderGraph(graph, uiModel);
        if (product != null) return renderProduct(product, uiModel);
        return "search/index";
    }

    private String renderProduct(Long productId, Model uiModel) {

        long start = System.currentTimeMillis();
        Product product = productService.findProduct(productId);
        List<Link> links = productService.findSimilar(product,20);
        uiModel.addAttribute("links", links);
        uiModel.addAttribute("original",product);

        long end = System.currentTimeMillis();
        logger.info("It took " + (end-start) + " ms. to find similar products");
        return "search/index";
    }

    private String renderGraph(Long productId, Model uiModel) {
        Product product = productService.findProduct(productId);
//        List<Link> links = productService.findSimilar(product, 20);
        uiModel.addAttribute("graph", true);
        uiModel.addAttribute("original",product);
        return "search/index";
    }

    private String search(String search, Model uiModel) {
        //We are looking for the term... search
        logger.info("Searching term: " + search);

        String keywords[] = search.split(" ");

        ArrayList<String> listKeywords = new ArrayList<String>();
        for (String keyword : keywords)
            listKeywords.add(keyword);
        List<Product> products = productService.findProductsWithKeyword(listKeywords);

        logger.info("Number of products found: " + products.size());
        uiModel.addAttribute("products", products);


        return "search/index";
    }


}
