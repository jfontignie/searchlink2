package org.searchlink.controller;

import org.searchlink.domain.Sense;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/senses")
@Controller
@RooWebScaffold(path = "senses", formBackingObject = Sense.class)
public class SenseController {
}
