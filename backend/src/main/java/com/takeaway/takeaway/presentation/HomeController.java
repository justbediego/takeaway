package com.takeaway.takeaway.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(path = {"/"})
    public String index() {
        return "index";
    }
}