package com.vn.websocketwebflux.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @GetMapping("/{id}")
    public String home(@PathVariable String id, Model model) {
        return "index";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}