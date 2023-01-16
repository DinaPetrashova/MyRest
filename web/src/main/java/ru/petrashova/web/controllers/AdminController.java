package ru.petrashova.web.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.petrashova.web.models.User;
import ru.petrashova.web.services.RoleService;
import ru.petrashova.web.services.UserService;
import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
    }

    @GetMapping()
    public String admin(Model model, Principal principal){
        model.addAttribute("userCurrent", userService.getUser(principal.getName()));
        return "/main";
    }

}
