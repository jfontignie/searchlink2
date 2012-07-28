package org.searchlink.controller;

import org.searchlink.domain.Author;
import org.searchlink.domain.Link;
import org.searchlink.domain.Product;
import org.searchlink.service.KeywordService;
import org.searchlink.service.LinkService;
import org.searchlink.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTMLDocument;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Default network controller. The possibilities are the fowllowing:
 *
 * /network : display only the search button
 * /network?id=5 : display the similiraty with product 5
 * /network?search=abcd+efgh: display the best product with this keywords in the neighborhood
 */
@RequestMapping("/graph/**")
@Controller
public class GraphController {

    @Autowired
    private ProductService productService;

    @Autowired
    private LinkService linkService;
    @Autowired
    private KeywordService keywordService;




    //@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    @RequestMapping(value = "/{id}")
    public String post(@PathVariable Long id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        Product product = productService.findProduct(id);
        Node rootNode = getNetwork(product);
        //return Link.toJsonArray(links);
        return rootNode.toJson();
    }

    private Node getNetwork(Product product) {
        List<Link> links = productService.findMatching(product,10);

        Node rootNode = new Node(product);

        for (Link link : links) {
            Product target = link.getTarget();
            Node node = new Node(target);
            List<Link> links2 = linkService.findLinksBySource(target);
            for (Link link2 : links2) {
                node.addNode(new Node(link2.getTarget()));
            }


            rootNode.addNode(node);
        }
        return rootNode;
    }

    @RequestMapping(value = "/network")
    private String network(HttpServletRequest httpServletRequest, @RequestParam(value = "search", required = false) String search) {
        if (search == null) return "graph/network";
        List<Product> products = productService.findProductsWithKeyword(search);
        return "redirect:graph/network/" + encodeUrlPathSegment(products.get(0).getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/network/{id}")
    public String network(Long id) {
        Product product = productService.findProduct(id);
        return "graph/network";
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "graph/index";
    }

    private class Node {
        String id;
        String name;
        private List<Node> nodes;

        private String data;

        public Node(Product product) {
            id = product.getIdentifier();
            name = product.getName();

            HTMLDocument document = new HTMLDocument();
            data = "<h2>" + name + "</h2><br/>";
            if (product.getAuthors().size() > 0) {
                data += "<ul>";
                for (Author author : product.getAuthors()) {
                    data += "<li>" + author.getName() + "</li>";
                }
                data += "</ul><br/>";


            }
            if (product.getContent() != null)
                data += product.getContent();

            data = data.replaceAll("\"", "&quot;").replaceAll("\n", "");
            //data = StringEscapeUtils.escapeHtml4(data);
            nodes = new ArrayList<Node>();
        }


        public String toJson() {
            StringBuilder builder = new StringBuilder("{");

            builder.append("\"id\":\"" + id + "\",\n");
            builder.append("\"name\":\"" + name + "\",\n");
            builder.append("\"data\":{\"relation\":\"" + data + "\"},\n");

            builder.append("\"children\":[");

            boolean added = false;
            for (Node node : nodes) {
                if (added) builder.append(",");
                builder.append(node.toJson());
                added = true;
            }

            builder.append("]");

            builder.append("}");
            return builder.toString();
        }

        public void addNode(Node node) {
            nodes.add(node);
        }
    }

    private String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {
        }
        return pathSegment;
    }
}
