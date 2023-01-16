package ru.petrashova.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.petrashova.web.models.Role;
import ru.petrashova.web.models.User;
import ru.petrashova.web.services.RoleService;
import ru.petrashova.web.services.UserService;

import java.security.Principal;
import java.util.List;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
    private final UserService userService;
    private final RoleService roleService;

    public RestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;    }


    @RequestMapping("/test")
    public String test() {
        return "Test OK";
    }

    @GetMapping("/users")
    public ResponseEntity <List<User>> tabAllUsers() {
        List <User> users = userService.getAllUsers();
        return new ResponseEntity <> (users, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity <User> userCurrent(Principal principal){
        return new ResponseEntity <> (userService.getUser(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity <User> userById(@PathVariable Integer id){
        return new ResponseEntity  (userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/admin/new")
    public ResponseEntity <User> newUser (@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PatchMapping("/admin/{id}")
    public ResponseEntity <User> editUser (@RequestBody User user, @PathVariable Integer id) {
        System.out.println(user);
        userService.update(user, id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity <User> deleteUser (@PathVariable Integer id){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
