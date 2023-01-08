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
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping()
    public String admin(Model model, Principal principal){
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("userCurrent", userService.getUser(principal.getName()));
        model.addAttribute("roles",roleService.getAll());
        model.addAttribute("newUser", new User());
        return "/main";
    }
    @PostMapping(value = "/new")
    public String newUser(@ModelAttribute("newUser") @Valid User user){
        userService.save(user);
        return "redirect:/admin";
    }

    @PutMapping("/{id}")
    public String edit(@ModelAttribute("user") @Valid User user, @PathVariable int id){
        System.out.println("patch");
        userService.update(user, id);
        System.out.println(user.getId());
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id){
        userService.delete(id);
        return "redirect:/admin";
    }
}