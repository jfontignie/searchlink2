package org.searchlink.controller;

import org.searchlink.domain.Link;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: Jacques Fontignie
 * Date: 6/26/12
 * Time: 4:56 PM
 */
@RequestMapping("/links")
@Controller
@RooWebScaffold(path = "links", formBackingObject = Link.class)
public class LinkController {
}
