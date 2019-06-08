package com.machine.project.controller;

import com.machine.project.domain.Product;
import com.machine.project.domain.User;
import com.machine.project.service.ProductService;
import com.machine.project.service.UserService;
import com.machine.project.service.impl.ProductServiceImpl;
import com.machine.project.utils.PassEncoding;
import com.machine.project.utils.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;


    @RequestMapping({"/", "/login" })
    public String root(Model model) {
       // model.addAttribute("reqUser", new User());
        logger.info("root");
        return "login";
    }


    @RequestMapping("/home")
    public String home(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        logger.info("home");
        return "home";
    }

    @RequestMapping("/admin")
    public String helloAdmin() {
        logger.info("admin");
        return "admin";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        model.addAttribute("reqUser", new User());
        logger.info("register");
        return "register";
    }

    //ToDO Add optional!!!!!!!!!!!!!!!!!!!1
    @RequestMapping(value = {"/user/register"}, method = RequestMethod.POST)
    public String register(@ModelAttribute("reqUser") User reqUser,
                           final RedirectAttributes redirectAttributes) {

        logger.info("/user/register");
        User user = userService.findByUserName(reqUser.getUsername());
        if (user != null) {
            redirectAttributes.addFlashAttribute("saveUser", "exist-name");
            return "redirect:/register";
        }


        user = userService.findByEmail(reqUser.getEmail());

        if (user != null) {
            redirectAttributes.addFlashAttribute("saveUser", "exist-email");
            return "redirect:/register";
        }

        reqUser.setPassword(PassEncoding.getInstance().passwordEncoder.encode(reqUser.getPassword()));
        reqUser.setRole(Roles.ROLE_USER.getValue());

        if (userService.save(reqUser) != null) {
            redirectAttributes.addFlashAttribute("saveUser", "success");
        } else {
            redirectAttributes.addFlashAttribute("saveUser", "fail");
        }

        return "redirect:/register";
    }


}
