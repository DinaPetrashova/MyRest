package ru.petrashova.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.petrashova.web.services.UserService;
import java.security.Principal;


@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user/{id}")
    public String user (Model model, Principal principal, @PathVariable int id) {
        model.addAttribute("userCurrent", userService.getUser(principal.getName()));
        return "/main";
    }
    @GetMapping("/")
    public String index(Model model){
        return "redirect:/login";
    }

}
