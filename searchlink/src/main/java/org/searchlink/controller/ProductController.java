package org.searchlink.controller;

import org.searchlink.domain.Product;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: Jacques Fontignie
 * Date: 6/26/12
 * Time: 5:48 PM
 */
@RequestMapping("/products")
@Controller
@RooWebScaffold(path = "products", formBackingObject = Product.class)
public class ProductController {
}
