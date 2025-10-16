package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.listAllUsers());
        return "users/list";
    }

    @GetMapping("/new")
    public String formNew() {
        return "users/new";
    }

    @PostMapping
    public String create(@RequestParam int id, @RequestParam String name, @RequestParam String email, Model model) {
        userService.createUser(id, name, email);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable int id, Model model) {
        model.addAttribute("detail", userService.readUser(id));
        return "users/detail";
    }
}
