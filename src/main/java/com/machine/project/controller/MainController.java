package com.machine.project.controller;

import com.machine.project.domain.Product;
import com.machine.project.domain.User;
import com.machine.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    private ProductService productService;


    @Autowired
    public MainController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String index (Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/home")
    public String home (Model model, @AuthenticationPrincipal User user) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("user", user);
        return "home";
    }

}
